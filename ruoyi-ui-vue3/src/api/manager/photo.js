import request from '@/utils/request'

export function listPhoto(params) {
    return request({
        url: '/photo/list',
        method: 'get',
        params
    })
}

export function addPhoto(data) {
    return request({
        url: '/photo',
        method: 'post',
        data
    })
}

export function updatePhoto(data) {
    return request({
        url: '/photo',
        method: 'put',
        data
    })
}

export function delPhoto(id) {
    return request({
        url: '/photo/' + id,
        method: 'delete',
    })
}

export function infoPhoto(id) {
    return request({
        url: '/photo/info/' + id,
        method: 'get',
    })
}