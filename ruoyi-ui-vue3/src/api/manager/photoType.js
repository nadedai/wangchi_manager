import request from '@/utils/request'

export function list() {
    return request({
        url: '/photoType/list',
        method: 'get',
    })
}

export function add(data) {
    return request({
        url: '/photoType',
        method: 'post',
        data
    })
}

export function update(data) {
    return request({
        url: '/photoType',
        method: 'put',
        data
    })
}

export function del(id) {
    return request({
        url: '/photoType/' + id,
        method: 'delete',
    })
}

export function info(id) {
    return request({
        url: '/photoType/info/' + id,
        method: 'get',
    })
}