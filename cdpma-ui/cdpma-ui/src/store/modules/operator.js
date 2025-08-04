import { login, getInfo  } from '@/api/login'
import {setToken, getToken} from '@/utils/auth'
// import { isEmpty } from "@/utils/validate"

const operator = {
  state: {
    token: getToken(),
    id: '',
    operatorName: '',
    nickName: '',
    phoneNumber: '',
    email: '',
    region:'',
    idCard:'',
    gender:'',
    age:'',
    role:''
  },

  mutations: {
    SET_ID: (state, id) => {
      state.id = id
    },
    SET_TOKEN: (state, token) => {
      state.token = token
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
    SET_REGION: (state, region) => {
      state.region = region
    },
    SET_ID_CARD: (state, idCard) => {
      state.idCard = idCard
    },
    SET_GENDER: (state, gender) => {
      state.gender = gender
    },
    SET_AGE: (state, age) => {
      state.age = age
    },
    SET_ROLE: (state, role) => {
      state.role = role
    },
  },

  actions: {
    // 登录
    Login(context, userInfo) {
      const operatorName = userInfo.username
      const operatorPhone = userInfo.phoneNumber
      const operatorEmail = userInfo.email
      const operatorPassword = userInfo.password

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
    },

    // 获取用户信息
    GetInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          commit('SET_ID', res.data.operatorId)
          commit('SET_OPERATOR_NAME', res.data.operatorName)
          commit('SET_NICK_NAME', res.data.operatorNickName)
          commit('SET_PHONE_NUMBER', res.data.operatorPhone)
          commit('SET_EMAIL', res.data.operatorEmail)
          commit('SET_REGION', res.data.operatorRegion)
          commit('SET_ID_CARD', res.data.operatorIdCard)
          commit('SET_GENDER', res.data.operatorGender)
          commit('SET_AGE', res.data.operatorAge)
          commit('SET_ROLE', res.data.operatorRole)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
  }
}

export default operator