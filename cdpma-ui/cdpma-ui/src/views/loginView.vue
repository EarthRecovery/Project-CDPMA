<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
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

<script>
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'

const atLeastOneValidator = (vm) => (rule, value, callback) => {
  console.log(vm.loginForm)
  const username = vm.loginForm.username
  const phoneNumber = vm.loginForm.phoneNumber
  const email = vm.loginForm.email

  if (!username && !phoneNumber && !email) {
    callback(new Error('用户名、手机号或邮箱，至少填写一项'))
  } else {
    // 如果当前字段有值，再额外做格式校验
    if (rule.field === 'phoneNumber' && value) {
      const phoneRegex = /^1[3-9]\d{9}$/
      if (!phoneRegex.test(value)) {
        callback(new Error('请输入正确的手机号'))
        return
      }
    }
    if (rule.field === 'email' && value) {
      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
      if (!emailRegex.test(value)) {
        callback(new Error('请输入正确的邮箱地址'))
        return
      }
    }
    // 通过校验
    callback()
  }
}

export default {
  name: "LoginView",
  data() {
    return {
      title: "CDP-MA",
      loginForm: {
        username: "admin",
        password: "admin123",
        phoneNumber: "18989352240",
        email: "ssyyl35@nottingham.ac.uk",
        rememberMe: false,
        uuid: ""
      },
      loginRules: {
        username: [
          { validator: atLeastOneValidator(this), trigger: 'blur' }
        ],
        phoneNumber: [
          { validator: atLeastOneValidator(this), trigger: 'blur' }
        ],
        email: [
          { validator: atLeastOneValidator(this), trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入您的密码', trigger: 'blur' }
        ]
      },
      loading: false,
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCookie()
  },
  methods: {
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get("rememberMe")
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set("rememberMe", this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove("rememberMe")
          }
          // 这里写登录请求逻辑
          console.log("登录请求", this.loginForm)
        }
      })
    }
  }
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
