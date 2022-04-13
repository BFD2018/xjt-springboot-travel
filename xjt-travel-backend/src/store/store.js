import Vue from 'vue'
import Vuex from 'vuex'
import { getToken,setToken,removeToken } from '@/utils/auth'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    HOST: 'http://localhost:8880',

    login_user:getToken() || {},

    RouteCategories:['所有','国内游','港澳台','东南亚','欧美游','日韩游','跟团游'],
  },
  mutations: {
    updateLoginUser(state,payload){
      setToken(payload);
      state.login_user = payload;
    },
    UserLogout(state){
      removeToken();
      state.login_user = {};
    },
  },
  actions: {

  },
  modules: {
  }
})
