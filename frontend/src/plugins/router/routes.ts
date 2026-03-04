export const routes = [
  { path: '/', redirect: '/traps' },
  {
    path: '/',
    component: () => import('@/layouts/default.vue'),
    children: [
      /*{
        path: 'dashboard',
        component: () => import('@/pages/dashboard.vue'),
      },*/
      {
        path: 'traps',
        component: () => import('@/pages/traps.vue'),
      },
      {
        path: 'events',
        component: () => import('@/pages/events.vue'),
      },
      {
        path: 'settings',
        component: () => import('@/pages/settings.vue'),
      },
      {
        path: 'account',
        component: () => import('@/pages/account-settings.vue'),
      }
    ],
  },
  {
    path: '/',
    component: () => import('@/layouts/blank.vue'),
    children: [
      {
        path: 'login',
        component: () => import('@/pages/login.vue'),
      },
      {
        path: 'register',
        component: () => import('@/pages/register.vue'),
      },
      {
        path: '/:pathMatch(.*)*',
        component: () => import('@/pages/[...error].vue'),
      },
    ],
  },
]
