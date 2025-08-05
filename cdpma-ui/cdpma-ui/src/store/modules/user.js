import { getUserByOperatorID } from '@/api/user'
import { getToken} from '@/utils/auth'
// import { isEmpty } from "@/utils/validate"

// private Long userId;
//     private Long operatorId;
//     private BigDecimal accountBalance;
//     private Integer points;
//     private Integer coupons;
//     private Integer orders;
//     private Integer userLevel;
//     private String rfm;
//     private String lifecycle;
//     private Date createdAt;
//     private Date updatedAt;
//     private Boolean isDisabled;

const user = {
  state: {
    token: getToken(),
    userId: '',
    operatorId: '',
    accountBalance: 0,
    points: 0,
    coupons: 0,
    orders: 0,
    userLevel: 0,
    rfm: '',
    lifecycle: '',
    createdAt: '',
    updatedAt: '',
    isDisabled: false
  },

  mutations: {
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_USER_ID: (state, userId) => {
        state.userId = userId
    },
    SET_OPERATOR_ID: (state, operatorId) => {
        state.operatorId = operatorId
    },
    SET_ACCOUNT_BALANCE: (state, balance) => {
        state.accountBalance = balance
    },
    SET_POINTS: (state, points) => {
        state.points = points
    },
    SET_COUPONS: (state, coupons) => {
        state.coupons = coupons
    },
    SET_ORDERS: (state, orders) => {
        state.orders = orders
    },
    SET_USER_LEVEL: (state, level) => {
        state.userLevel = level
    },
    SET_RFM: (state, rfm) => {
        state.rfm = rfm
    },
    SET_LIFECYCLE: (state, lifecycle) => {
        state.lifecycle = lifecycle
    },
    SET_CREATED_AT: (state, createdAt) => {
        state.createdAt = createdAt
    },
    SET_UPDATED_AT: (state, updatedAt) => {
        state.updatedAt = updatedAt
    },
    SET_IS_DISABLED: (state, disabled) => {
        state.isDisabled = disabled
    },
    },


  actions: {
    GetUserInfo({ commit }, operatorId) {
        return new Promise((resolve, reject) => {
        getUserByOperatorID(operatorId).then(res => {
            const data = res.data
            commit('SET_USER_ID', data.userId)
            commit('SET_OPERATOR_ID', data.operatorId)
            commit('SET_ACCOUNT_BALANCE', data.accountBalance)
            commit('SET_POINTS', data.points)
            commit('SET_COUPONS', data.coupons)
            commit('SET_ORDERS', data.orders)
            commit('SET_USER_LEVEL', data.userLevel)
            commit('SET_RFM', data.rfm)
            commit('SET_LIFECYCLE', data.lifecycle)
            commit('SET_CREATED_AT', data.createdAt)
            commit('SET_UPDATED_AT', data.updatedAt)
            commit('SET_IS_DISABLED', data.isDisabled)
            resolve(res)
        }).catch(error => {
            reject(error)
        })
        })
    },
    }
}

export default user