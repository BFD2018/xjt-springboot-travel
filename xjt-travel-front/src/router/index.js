import router from './routers'
import Config from '@/settings'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import {getToken} from '@/utils/auth' // getToken from cookie

NProgress.configure({ showSpinner: false })   // NProgress Configuration

const whiteList = ['/login','/register']    // no redirect whitelist

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title + ' - ' + Config.title
  }
  NProgress.start();
  if (getToken()) {
    // 已登录且要跳转的页面是登录页
    if (to.path === '/login') {
      next({ path: '/' });
      NProgress.done()
    } else {
      next();
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`);    // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})


router.afterEach(() => {
  NProgress.done()      // finish progress bar
})
