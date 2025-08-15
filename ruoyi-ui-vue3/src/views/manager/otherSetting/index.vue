<template>
    <div class="app-container">
        <el-card header="轮播图配置">
            <imageUpload v-model="swiperPhoto" :limit="5" />
        </el-card>

        <el-card header="相册封面配置" style="margin-top: 18px;">
            <imageUpload v-model="photoCover" :limit="1" />
        </el-card>
    </div>
</template>
<script setup>
import { getSwiperPhoto, getPhotoCover, setSwiperPhoto, setPhotoCover } from '@/api/manager/index.js'
import { nextTick, ref, watch } from "vue";
const swiperPhoto = ref()
const photoCover = ref()

const swiperPhotoCanChange = ref(false)
const photoCoverCanChange = ref(false)
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
}


init()

</script>