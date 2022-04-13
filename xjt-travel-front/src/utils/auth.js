import Config from '@/settings.js'

const TokenKey = Config.TokenKey;

export function getToken() {
  return JSON.parse(sessionStorage.getItem(TokenKey))
}

export function setToken(token) {
  return sessionStorage.setItem(TokenKey,JSON.stringify(token));
}

export function removeToken() {
  return sessionStorage.removeItem(TokenKey);
}
