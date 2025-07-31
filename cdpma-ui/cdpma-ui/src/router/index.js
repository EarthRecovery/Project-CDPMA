import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout'

const routes = [
  {
    path: '/login',
    component: () => import('@/views/loginView.vue') // 异步加载 Login 组件
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/indexView.vue'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(), // HTML5 history 模式
  routes
})

export default router