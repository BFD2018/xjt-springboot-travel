import Config from '@/settings'

const TokenKey = Config.TokenKey

export function getToken() {
	return window.sessionStorage.getItem(TokenKey)
}

export function setToken(token) {
	window.sessionStorage.setItem(TokenKey,token);
}

export function removeToken() {
	return window.sessionStorage.removeItem(TokenKey);
}
