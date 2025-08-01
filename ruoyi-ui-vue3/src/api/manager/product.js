import request from '@/utils/request'

export function listProduct(params) {
    return request({
        url: '/product/list',
        method: 'get',
        params
    })
}

export function addProduct(data) {
    return request({
        url: '/product',
        method: 'post',
        data
    })
}

export function updateProduct(data) {
    return request({
        url: '/product',
        method: 'put',
        data
    })
}

export function delProduct(id) {
    return request({
        url: '/product/' + id,
        method: 'delete',
    })
}

export function infoProduct(id) {
    return request({
        url: '/product/info/' + id,
        method: 'get',
    })
}