import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', component: () => import('@/views/HomeView.vue') },
  { path: '/about', component: () => import('@/views/AboutView.vue') }
]

const router = createRouter({
  history: createWebHistory(), // HTML5 history 模式
  routes
})

export default router