import request from '@/utils/request'

export function list() {
    return request({
        url: '/productType/list',
        method: 'get',
    })
}

export function add(data) {
    return request({
        url: '/productType',
        method: 'post',
        data
    })
}

export function update(data) {
    return request({
        url: '/productType',
        method: 'put',
        data
    })
}

export function del(id) {
    return request({
        url: '/productType/' + id,
        method: 'delete',
    })
}

export function info(id) {
    return request({
        url: '/productType/info/' + id,
        method: 'get',
    })
}