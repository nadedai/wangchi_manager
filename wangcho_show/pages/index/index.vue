<template>
	<view class="container">
		<view class="uni-margin-wrap">
			<swiper style="height: 100vh; width: 100%;" class="swiper" autoplay="true" circular="true">
				<swiper-item v-for="(item, index) in imgs">
					<image style="height: 100%; width: 100%;" mode="aspectFill" :src="item" :id="index" />
				</swiper-item>
			</swiper>
			<div class="centered-bottom" @click="gogo">
				<image style="height: 300px; width: 300px;" mode="aspectFit" src="/static/swiper/logo.jpg" />
			</div>
		</view>
	</view>
</template>

<script setup>
	import { getSwiperPhoto } from '@/api/api.js'
	import { ref } from 'vue'

	const imgs = ref([])

	function gogo() {
		uni.switchTab({
			url: '/pages/project/product/index'
		});
	}

	function getImgs() {
		getSwiperPhoto().then(res => {
			if (res) {
				imgs.value = res.data
			}
		})
	}

	getImgs()
</script>

<style>
	.container {
		position: relative;
	}

	.centered-bottom {
		position: absolute;
		bottom: 0;
		left: 50%;
		transform: translateX(-50%);
	}
</style>