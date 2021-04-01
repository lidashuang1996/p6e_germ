import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
import Index from '../views/Index.vue';
import SignIn from '../views/sign/SignIn.vue';
import SignUp from '../views/sign/SignUp.vue';
import Notice from '../views/notice/Notice.vue';
import Me from '../views/me/Me.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'index',
    component: Index,
    children: [
      {
        path: '/',
        name: 'sign_in',
        component: SignIn
      },
      {
        path: '/sign_up',
        name: 'sign_up',
        component: SignUp
      },
      {
        path: '/notice',
        name: 'notice',
        component: Notice
      },
      {
        path: '/me',
        name: 'me',
        component: Me
      }
    ]
  }
  // {
  //   path: '/error',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
});

export default router;
