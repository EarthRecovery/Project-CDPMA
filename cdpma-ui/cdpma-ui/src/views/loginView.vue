<template>
  <div class="login">
    <el-form ref="loginFormRef"  :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">{{ title }}</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          autocomplete="off"
          placeholder="账号"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="phoneNumber">
        <el-input
          v-model="loginForm.phoneNumber"
          type="text"
          autocomplete="off"
          placeholder="手机号"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          v-model="loginForm.email"
          type="text"
          autocomplete="off"
          placeholder="邮箱"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          autocomplete="off"
          placeholder="密码"
          @keyup.enter="handleLogin"
        >
        </el-input>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin: 0 0 25px 0;">记住密码</el-checkbox>
      <el-form-item style="width: 100%;">
        <el-button
          :loading="loading"
          size="default"
          type="primary"
          style="width: 100%;"
          @click.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import Cookies from 'js-cookie'
import { encrypt, decrypt } from '@/utils/jsencrypt'

// 1. 初始化 store 和 router
const store = useStore()
const router = useRouter()
const route = useRoute()

// 2. 登录表单数据
const loginForm = reactive({
  username: 'admin',
  password: 'admin123',
  phoneNumber: '18989352240',
  email: 'ssyyl35@nottingham.ac.uk',
  rememberMe: false,
  uuid: ''
})

// 3. 其他状态
const title = ref('CDP-MA')
const loading = ref(false)
const redirect = ref(undefined)

// 4. 表单引用
const loginFormRef = ref(null)

const atLeastOneValidator = (rule, value, callback) => {
  const { username, phoneNumber, email } = loginForm
  console.log("validator")
  // 仅在全部为空时报错
  if (!username && !phoneNumber && !email) {
    return callback(new Error('用户名、手机号或邮箱至少填写一项'))
  }
  // 如果当前字段有值，单独校验格式
  if (rule.field === 'phoneNumber' && value) {
    const phoneRegex = /^1[3-9]\d{9}$/
    if (!phoneRegex.test(value)) return callback(new Error('请输入正确的手机号'))
  }
  if (rule.field === 'email' && value) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
    if (!emailRegex.test(value)) return callback(new Error('请输入正确的邮箱地址'))
  }
  callback()
}


// 6. 校验规则
const loginRules = {
  username: [{ validator: atLeastOneValidator, trigger: 'blur' }],
  phoneNumber: [{ validator: atLeastOneValidator, trigger: 'blur' }],
  email: [{ validator: atLeastOneValidator, trigger: 'blur' }],
  password: [{ required: true, message: '请输入您的密码', trigger: 'blur' }]
}

// 7. Cookie 恢复数据
const getCookie = () => {
  // 仅在初次挂载时恢复，不在后续修改时覆盖
  if (!loginForm.username) loginForm.username = Cookies.get('username') || ''
  if (!loginForm.password) loginForm.password = Cookies.get('password') ? decrypt(Cookies.get('password')) : ''
  if (!loginForm.email) loginForm.email = Cookies.get('email') || ''
  if (!loginForm.phoneNumber) loginForm.phoneNumber = Cookies.get('phoneNumber') || ''
  loginForm.rememberMe = Cookies.get('rememberMe') ? Boolean(Cookies.get('rememberMe')) : false
}


// 8. 监听路由变化
watch(
  () => route.query.redirect,
  (val) => {
    redirect.value = val
  },
  { immediate: true }
)

// 9. 组件挂载时获取 Cookie
let initialized = false
onMounted(() => {
  if(!initialized) {
    getCookie()
    initialized = true
  }
})

// 10. 登录处理
const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      if (loginForm.rememberMe) {
        Cookies.set('username', loginForm.username, { expires: 30 })
        Cookies.set('password', encrypt(loginForm.password), { expires: 30 })
        Cookies.set('rememberMe', loginForm.rememberMe, { expires: 30 })
        Cookies.set('email', loginForm.email, { expires: 30 })
        Cookies.set('phoneNumber', loginForm.phoneNumber, { expires: 30 })
      } else {
        Cookies.remove('username')
        Cookies.remove('password')
        Cookies.remove('rememberMe')
        Cookies.remove('email')
        Cookies.remove('phoneNumber')
      }
      if(loginForm.username == undefined || loginForm.username == null) {
        loginForm.username = null
      }
      if(loginForm.email == undefined || loginForm.email == null) {
        loginForm.email = null
      }
      if(loginForm.phoneNumber == undefined || loginForm.phoneNumber == null) {
        loginForm.phoneNumber = null
      }
      // 调用 Vuex action
      console.log('Attempting login with loginForm:', loginForm)
      store.dispatch('Login', loginForm)
        .then(() => {
          router.push({ path: redirect.value || '/' }).catch(() => {})
        })
        .catch(() => {
          loading.value = false
        })
    }
  })
}
</script>


<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  /* 建议用相对路径或webpack别名 */
  background-image: url("@/assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}
.login-form {
  border-radius: 6px;
  background: #fff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  z-index: 1;

  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
</style>
