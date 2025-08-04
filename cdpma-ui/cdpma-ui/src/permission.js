import router from './router'
import store from './store'
// import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isPathMatch } from '@/utils/validate'
// import { isRelogin } from '@/utils/request'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/register']

const isWhiteList = (path) => {
  return whiteList.some(pattern => isPathMatch(pattern, path))
}

router.beforeEach((to, from, next) => {
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
            next()
        }
        // } else {
        //     // 这里还有权限获取，生成路由表模块
        // }
        
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