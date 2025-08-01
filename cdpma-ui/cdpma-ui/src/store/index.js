import { createStore } from 'vuex'
import operator from './modules/operator'

const store = createStore({
  modules: {
    operator
  }
})

export default store

