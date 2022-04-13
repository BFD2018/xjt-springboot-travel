import Vue from 'vue'
import App from './App.vue'
import routers from './router/routers.js'
import store from './store/store.js'

Vue.config.productionTip = false

//使用element-ui
import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
Vue.use(ElementUI);

import less from 'less'
Vue.use(less)

import './router/index.js' // permission control

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
  attempt: 5
})

/*定义常量*/
import * as Myconst from "@/utils/Myconst.js"
Vue.prototype.Myconst = Myconst;

import {$request,$getRequest,$postRequest,$getRequestBlob,$postKeyValueRequest} from "./network/request";
Vue.prototype.$request = $request;
Vue.prototype.$getRequest = $getRequest;
Vue.prototype.$postRequest = $postRequest;
Vue.prototype.$getRequestBlob = $getRequestBlob;
Vue.prototype.$postKeyValueRequest = $postKeyValueRequest;

/*使用hover.css*/
import hover from "hover.css"
Vue.use(hover);

/*全局引入mixin*/
import mymixin from "@/utils/mymixin";
Vue.use(mymixin);

new Vue({
  router: routers,
  store,
  render: h => h(App)
}).$mount('#app')
