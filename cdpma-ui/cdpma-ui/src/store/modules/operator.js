import { login } from '@/api/login'
import {setToken, getToken} from '@/utils/auth'

const operator = {
  state: {
    token: getToken(),
    id: '',
    operatorName: '',
    nickName: '',
    phoneNumber: '',
    email: '',
    avatar: '',
    tags:[]
  },

  mutations: {
    SET_ID: (state, id) => {
      state.id = id
    },
    SET_OPERATOR_NAME: (state, operatorName) => {
      state.operatorName = operatorName
    },
    SET_NICK_NAME: (state, nickName) => {
      state.nickName = nickName
    },
    SET_PHONE_NUMBER: (state, phoneNumber) => {
      state.phoneNumber = phoneNumber
    },
    SET_EMAIL: (state, email) => {
      state.email = email
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_TAGS: (state, tags) => {
      state.tags = tags
    }, 
    SET_TOKEN: (state, token) => {
      state.token = token
    },
  },

  actions: {
    // 登录
    Login(context, userInfo) {
      console.log('Login action called with userInfo:', userInfo)
      const operatorName = userInfo.username
      const operatorPhone = userInfo.phoneNumber
      const operatorEmail = userInfo.email
      const operatorPassword = userInfo.password
      console.log('Login parameters:', operatorName, operatorPhone, operatorEmail, operatorPassword)

      return new Promise((resolve, reject) => {
        login(operatorName, operatorPhone, operatorEmail, operatorPassword)
          .then(res => {
            let data = res.data
            setToken(data.access_token)
            context.commit('SET_TOKEN', data.access_token)
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    }
  }
}

export default operator