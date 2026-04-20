import request from '@/utils/request'

export function getSwiperPhoto() {
    return request({
        url: '/index/swiperPhoto',
        method: 'get',
    })
}

export function getPhotoCover() {
    return request({
        url: '/index/photoCover',
        method: 'get',
    })
}

export function getShareCover() {
    return request({
        url: '/index/shareCover',
        method: 'get',
    })
}

export function setSwiperPhoto(params) {
    return request({
        url: '/index/swiperPhoto',
        method: 'put',
        params
    })
}

export function setPhotoCover(params) {
    return request({
        url: '/index/photoCover',
        method: 'put',
        params
    })
}

export function setShareCover(params) {
    return request({
        url: '/index/shareCover',
        method: 'put',
        params
    })
}