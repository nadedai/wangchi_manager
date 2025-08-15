<template>
	<view class="container">
		<view class="uni-margin-wrap">
			<swiper style="height: 100vh; width: 100%;" class="swiper" autoplay="true" circular="true">
				<swiper-item>
					<image style="height: 100%; width: 100%;" mode="aspectFill" src="/static/swiper/1.jpg" />
				</swiper-item>
				<swiper-item>
					<image style="height: 100%; width: 100%;" mode="aspectFill" src="/static/swiper/2.jpg" />
				</swiper-item>
				<swiper-item>
					<image style="height: 100%; width: 100%;" mode="aspectFill" src="/static/swiper/3.jpg" />
				</swiper-item>
				<swiper-item>
					<image style="height: 100%; width: 100%;" mode="aspectFill" src="/static/swiper/4.jpg" />
				</swiper-item>
			</swiper>
			<div class="centered-bottom" @click="gogo">
				<image style="height: 300px; width: 300px;" mode="aspectFit" src="/static/swiper/logo.jpg" />
			</div>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		onLoad() {

		},
		methods: {
			gogo() {
				uni.switchTab({
					url: '/pages/project/product/index'
				});
			}
		}
	}
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