import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import registerSvgIcons from './assets/icons'
import store from './store'
import 'element-plus/dist/index.css'

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(store)
registerSvgIcons(app)
app.mount('#app')
