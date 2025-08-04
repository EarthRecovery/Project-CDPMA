import { createStore } from 'vuex'
import operator from './modules/operator'
import settings from './modules/settings'

const store = createStore({
  modules: {
    operator,
    settings
  }
})

export default store

