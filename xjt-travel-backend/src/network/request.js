import axios from "axios";
//导入路由
import router from '@/router/router.js'
import Qs from 'qs'

import {Message} from 'element-ui';

export function $request(config) {
  const instance1 = axios.create({
    baseURL: '/api',
    timeout: 15000,
  })

  //请求拦截
  instance1.interceptors.request.use(config => {
    return config;
  }, err => {
    Message.error(err);
  })
  //响应拦截
  instance1.interceptors.response.use(res => {
    return res;
  }, error => {
    //console.log(error.response);
    if (error.response.status == 504 || error.response.status == 404) {
      Message.error("服务器被吃了哦！")
    } else if (error.response.status == 403) {
      Message.error({message: "权限不足，请联系管理员"})
    } else if (error.response.status == 401) {
      Message.error({message: "尚未登录"})
      router.replace('/')
    } else {
      //服务器返回的错误信息
      if (error.response.data.msg) {
        Message.error({message: error.response.data.msg})
      } else {
        Message.error({message: "未知错误"})
      }
    }
    return;
  })

  return instance1(config);
}

/*封装一些常用请求放在Vue原型链上*/
let baseURL = "/api";
export const $postKeyValueRequest = (url, params) => {
  return axios({
    method: "post",
    url: `${baseURL}${url}`,
    data: params instanceof Object ? Qs.stringify(params) : params,		//直接这样以json的形式传给服务器，这是不支持的,需要转为查询字符串如 a=xxx&b=xxx
    //定义header
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded '
    }
  })
}

export const $getRequest = (url, params) => {
  return axios({
    method: "get",
    url: `${baseURL}${url}`,
    data: params		//这是用json来传递的，所以不用加transformRequest转换了
  })
}

export const $postRequest = (url, params) => {
  return axios({
    method: "post",
    url: `${baseURL}${url}`,
    data: params
  })
}

//返回二进制流
export const $getRequestBlob = (url, params) => {
  return axios({
    method: "get",
    url: `${baseURL}${url}`,
    responseType: 'blob', // 声明返回blob格式
    data: params		//这是用json来传递的，所以不用加transformRequest转换了
  })
}

export const putRequest = (url, params) => {
  return axios({
    method: "put",
    url: `${baseURL}${url}`,
    data: params		//这是用json来传递的，所以不用加transformRequest转换了
  })
}

export const deleteRequest = (url, params) => {
  return axios({
    method: "delete",
    url: `${baseURL}${url}`,
    data: params		//这是用json来传递的，所以不用加transformRequest转换了
  })
}

