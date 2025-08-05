import router from './router'
import store from './store'
// import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isPathMatch } from '@/utils/validate'
import { computed } from 'vue'
// import { isRelogin } from '@/utils/request'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/register']

const isWhiteList = (path) => {
  return whiteList.some(pattern => isPathMatch(pattern, path))
}

router.beforeEach(async (to, from, next) => {
    NProgress.start()
    if(getToken()){
        console.log('Token exists:', getToken())
        to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
        if (to.path === '/login') {
            next({ path: '/' })
            NProgress.done()
        } else if( isWhiteList(to.path) ){
            // 如果是白名单路径，直接放行
            next()
        }else{
            const operatorId = computed(() => store.state.operator.id)
            console.log('Operator ID:', operatorId.value)
            console.log(!operatorId.value || operatorId.value == '')
            if (!operatorId.value || operatorId.value === '') {
                try {
                // 等待 dispatch 完成
                await store.dispatch('GetInfo');
                // 获取角色信息
                const operatorRole = computed(() => store.state.operator.role);
                if(operatorRole.value == 'USER'){
                    await store.dispatch('GetUserInfo', operatorId.value)
                }
                } catch (error) {
                // 错误处理
                console.error('获取信息失败:', error);
                // 你可以在这里决定是否调用 next() 或者跳转到错误页面
                }
            }
            next()
        }
    }else{
        // 没有 token
        if (isWhiteList(to.path)) {
            // 如果是白名单路径，直接放行
            next()
        } else {
            // 如果不是白名单路径，重定向到登录页
            next(`/login?redirect=${to.path}`)
            NProgress.done()
        }
    }
})

router.afterEach(() => {
  NProgress.done()
})