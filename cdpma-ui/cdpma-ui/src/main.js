import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import registerSvgIcons from './assets/icons'
import store from './store'
import './permission'
import 'element-plus/dist/index.css'
import * as echarts from 'echarts'

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(store)
app.config.globalProperties.$echarts = echarts
registerSvgIcons(app)
app.mount('#app')
