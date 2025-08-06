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
  {
    path: '/admin/operator',
    component: Layout,
    redirect: '/admin/operator/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/operatorView.vue'),
        name: 'admin-operator',
        meta: { title: '操作者管理', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/admin/good',
    component: Layout,
    redirect: '/admin/good/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/adminGoodView.vue'),
        name: 'admin-good',
        meta: { title: '商品管理', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user/good',
    component: Layout,
    redirect: '/user/good/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/goodView.vue'),
        name: 'user-good',
        meta: { title: '用户商品', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/admin/template',
    component: Layout,
    redirect: '/admin/template/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/templateView.vue'),
        name: 'admin-template',
        meta: { title: '模板', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user/favorite',
    component: Layout,
    redirect: '/user/favorite/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/favoriteView.vue'),
        name: 'user-favorite',
        meta: { title: '用户收藏', icon: 'dashboard', affix: true }
      }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(), // HTML5 history 模式
  routes
})

export default router