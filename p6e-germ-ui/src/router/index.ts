import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
import Home from '../views/Home.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
];

/**
 * 消息中心管理
 */
export const messageRoutes: Array<RouteRecordRaw> = [
  {
    path: '/message',
    name: 'MessageHome',
    component: () => import('@/views/message/MessageHome.vue')
  },
  {
    path: '/message/group',
    name: 'MessageGroup',
    component: () => import('@/views/message/MessageGroup.vue')
  },
  {
    path: '/message/group/platform',
    name: 'MessageGroupPlatform',
    component: () => import('@/views/message/MessageGroupPlatform.vue')
  },
  {
    path: '/message/log',
    name: 'MessageLog',
    component: () => import('@/views/message/MessageLog.vue')
  }
];

/**
 * 权限管理
 */
export const jurisdictionRoutes: Array<RouteRecordRaw> = [
  {
    path: '/jurisdiction',
    name: 'JurisdictionHome',
    component: () => import('@/views/jurisdiction/JurisdictionHome.vue')
  }
];

routes.push(...messageRoutes);
routes.push(...jurisdictionRoutes);

const router = createRouter({
  history: createWebHashHistory(),
  routes
});

export default router;
