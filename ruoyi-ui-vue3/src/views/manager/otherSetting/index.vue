<template>
    <div class="app-container">
        <el-card header="轮播图配置">
            <imageUpload v-model="swiperPhoto" :limit="5" />
        </el-card>

        <el-card header="相册封面配置" style="margin-top: 18px;">
            <imageUpload v-model="photoCover" :limit="1" />
        </el-card>

        <el-card header="分享封面配置" style="margin-top: 18px;">
            <imageUpload v-model="shareCover" :limit="1" />
        </el-card>
    </div>
</template>
<script setup>
import { getSwiperPhoto, getPhotoCover, setSwiperPhoto, setPhotoCover, getShareCover, setShareCover } from '@/api/manager/index.js'
import { nextTick, ref, watch } from "vue";
const swiperPhoto = ref()
const photoCover = ref()
const shareCover = ref()


const swiperPhotoCanChange = ref(false)
const photoCoverCanChange = ref(false)
const shareCoverCanChange = ref(false)

const { proxy } = getCurrentInstance();

watch(swiperPhoto, val => {
    if (swiperPhotoCanChange.value) {
        setSwiperPhoto({ imgs: val }).then(res => {
            proxy.$modal.msgSuccess("修改成功");
        });
    }
}, { deep: true })

watch(photoCover, val => {
    if (photoCoverCanChange.value) {
        setPhotoCover({ img: val }).then(res => {
            proxy.$modal.msgSuccess("修改成功");
        });
    }

}, { deep: true })

watch(shareCover, val => {
    if (shareCoverCanChange.value) {
        setShareCover({ img: val }).then(res => {
            proxy.$modal.msgSuccess("修改成功");
        });
    }

}, { deep: true })


function init() {
    getSwiperPhoto().then((res) => {
        swiperPhoto.value = res.data
        nextTick(() => {
            swiperPhotoCanChange.value = true
        })

    })
    getPhotoCover().then((res) => {
        photoCover.value = [res.msg]
        nextTick(() => {
            photoCoverCanChange.value = true
        })
    })
    getShareCover().then((res) => {
        shareCover.value = [res.msg]
        nextTick(() => {
            shareCoverCanChange.value = true
        })
    })
}


init()

</script>