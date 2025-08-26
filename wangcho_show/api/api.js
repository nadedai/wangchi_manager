import request from '@/utils/request'

export function productTypeList(data) {
	return request({
		url: '/productType/list',
		method: 'GET',
		data
	})
}

export function photoTypeList(data) {
	return request({
		url: '/photoType/list',
		method: 'GET',
		data
	})
}

export function photoList(data) {
	return request({
		url: '/photo/list',
		method: 'GET',
		data
	})
}

export function productList(data) {
	return request({
		url: '/product/list',
		method: 'GET',
		data
	})
}

export function productInfo(id) {
	return request({
		url: '/product/info/' + id,
		method: 'GET'
	})
}

export function listOssByIds(ossId) {
	return request({
		url: '/system/oss/listByIds/' + ossId,
		method: 'GET'
	})
}

export function getSwiperPhoto() {
	return request({
		url: '/index/swiperPhoto',
		method: 'GET',
	})
}

export function getPhotoCover() {
	return request({
		url: '/index/photoCover',
		method: 'GET',
	})
}