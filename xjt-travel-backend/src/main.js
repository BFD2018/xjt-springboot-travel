import Vue from 'vue'
import App from './App.vue'
import router from './router/router.js'
import store from './store/store.js'

Vue.config.productionTip = false

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)

import less from 'less'
Vue.use(less)

//粒子特效
import VueParticles from 'vue-particles'
Vue.use(VueParticles)

/*qs*/
import qs from "qs"
Vue.prototype.$qs = qs;

/*日期时间格式化*/
import Moment from 'moment'
Vue.prototype.$moment = Moment;

/*图片懒加载*/
import VueLazyload from 'vue-lazyload'
Vue.use(VueLazyload, {		//options配置项
  preLoad: 1.5,
  error: require('./assets/images/common/404.jpg'),
  loading: require('./assets/images/common/loading.gif'),
  attempt: 6
})

/*使用hover.css*/
import hover from "hover.css"
Vue.use(hover);

import 'normalize.css/normalize.css'
import './assets/styles/index.scss'       // global css

// svg-icon
import '@/assets/icons/index.js'

//echarts
import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts;

// 使用 mavonEditor
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

/*// permission control*/
import "@/router/guard.js"

/*全局引入mixin*/
import mymixin from "@/utils/mymixin";
Vue.use(mymixin);

/*全局注册-网络请求*/
import {$request,$getRequest,$postRequest} from "./network/request";
Vue.prototype.$request = $request;
Vue.prototype.$getRequest = $getRequest;
Vue.prototype.$postRequest = $postRequest;

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
