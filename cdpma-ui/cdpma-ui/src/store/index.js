import { createStore } from 'vuex'
import operator from './modules/operator'
import settings from './modules/settings'
import user from './modules/user'

const store = createStore({
  modules: {
    operator,
    settings,
    user
  }
})

export default store

