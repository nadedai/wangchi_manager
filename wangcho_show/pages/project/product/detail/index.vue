<template>
	<view>
		<u-loading-page :loading="pageLoading"></u-loading-page>
		<swiper indicator-dots="true" indicator-active-color="#808080" style="height: 400px; width: 100%;" class="swiper" circular="true">
			<swiper-item v-for="item in imgs">
				<image style="height: 100%; width: 100%;" mode="aspectFill" :src="item.url" @click="previewImage(item.url)" />
			</swiper-item>
		</swiper>
		<view>
			<view style="font-size: 18px;font-weight: 800;margin: 8px;">￥{{data.price}}</view>
			<view style="margin: 8px;">{{data.name}}</view>
		</view>
		<u-gap height="6" bgColor="rgb(234 234 234)" />
		<u-parse :content="data?.detail"></u-parse>
		<view class="tab-bar">
			<view class="item-container">
				<view class="item">
					<u-icon name="chat" color="gray" size="24" />
					<view>客服</view>
				</view>

				<view class="item">
					<u-icon name="share" color="gray" size="24" />
					<view>分享</view>
				</view>
			</view>

		</view>
	</view>

</template>

<script setup>
	import { ref } from 'vue'
	import { onLoad } from '@dcloudio/uni-app';
	import { productInfo, listOssByIds } from '@/api/api.js'
	const imgs = ref([])
	const data = ref({})
	const pageLoading = ref(true)

	function getInfo(id) {
		productInfo(id).then((res) => {
			data.value = res.data
			listOssByIds(res.data.gallery).then((r) => {
				imgs.value = r.data
				pageLoading.value = false
			})
		})
	}

	onLoad((options) => {
		const id = options.id;
		getInfo(id)
	});

	function previewImage(url) {
		uni.previewImage({
			urls: imgs.value.map(r => r.url) // 需要预览的图片http链接列表
		})
	}
</script>

<style scoped lang="scss">
	.tab-bar {
		position: fixed;
		bottom: 0;
		left: 0px;
		right: 0px;
		border: 1px solid rgb(234 234 234);
		background-color: white;
	}

	.tab-bar .item-container {
		display: flex;
		padding-top: 6px;
		padding-bottom: 4px;
	}

	.tab-bar .item {
		display: flex;
		flex-direction: column;
		color: gray;
		font-size: 12px;
		width: 28px;
		margin-left: 8px;
	}
</style>