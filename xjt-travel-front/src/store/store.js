import Vue from 'vue'
import Vuex from 'vuex'
import { getToken, setToken, removeToken } from '@/utils/auth.js'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    HOST: 'http://localhost:8880',
    login_user:getToken || {},
  },
  mutations: {
    updateLoginUser(state,payload){
      console.log(payload);
      setToken(payload);
      state.login_user = payload;
    },
    removeLoginUser(state){
      removeToken();
      state.login_user = {};
    },
  },
  actions: {
  },
  modules: {
  }
})
