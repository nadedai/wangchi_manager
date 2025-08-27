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

		<u-popup closeable :show="popShow" @close="popShow = false" duration="100">
			<view style="height: 60vh;padding: 20px;">
				<view style="font-size: 18px;font-weight: 600;margin-bottom: 15px;">产品参数</view>
				<u-list style="height: 80%;">
					<u-list-item v-for="(item, index) in data.attrs" :key="index">
						<div class="spec-item">
							<div class="spec-name">{{item.attrName}}</div>
							<div class="spec-value">{{item.attrValue}}</div>
						</div>
					</u-list-item>
				</u-list>
				<u-button type="primary" text="确定" color="#333" @click="popShow = false"></u-button>
			</view>
		</u-popup>

		<view class="attr-container" v-if="data.attrs && data.attrs.length > 0">
			<div class="prefix-text"><u-icon name="order" color="gray" size="24" /></div>
			<div class="scroll-container">
				<div class="grid">
					<div class="grid-item" v-for="(item,index) in data.attrs">
						<div class="item-number">{{item.attrValue}}</div>
						<div class="item-text">{{item.attrName}}</div>
					</div>
				</div>
			</div>
			<div class="detail-btn">
				<u-icon name="arrow-right" color="gray" size="15" @click="popShow = true" />
			</div>
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
	const popShow = ref(false)
	const pageLoading = ref(true)

	function getInfo(id) {
		productInfo(id).then((res) => {
			data.value = res.data
			imgs.value = res.data.galleryRealUrl.split(",")
			pageLoading.value = false
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

	.product-card {
		background-color: white;
		border-radius: 8px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
		width: 100%;
	}

	.product-title {
		font-size: 18px;
		font-weight: 600;
		color: #333;
		margin-bottom: 20px;
		text-align: center;
	}

	.container {
		background-color: white;
		border-radius: 12px;
		box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
		width: 100%;
	}

	.title {
		font-size: 20px;
		font-weight: 600;
		color: #333;
		margin-bottom: 25px;
		text-align: left;
		padding-bottom: 15px;
		border-bottom: 1px solid #eaeaea;
	}

	.attr-container {
		display: flex;
	}

	.prefix-text {
		color: #666;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-left: 8px;
	}

	.detail-btn {
		color: #666;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-right: 8px;

	}

	/* 滚动容器样式 */
	.scroll-container {
		width: 100%;
		overflow-x: auto;
		padding: 10px 0;
	}

	/* 内容网格样式 */
	.grid {
		display: flex;
		width: max-content;
		gap: 15px;
		padding: 0 10px;
	}

	.grid-item {
		width: calc(14% - 12px);
		/* 动态计算宽度，减去间隙 */
		min-width: 150px;
		/* 最小宽度限制 */
		flex-shrink: 0;
		height: 40px;
		background: white;
		border-radius: 12px;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: flex-start;
		transition: transform 0.3s ease, box-shadow 0.3s ease;
		position: relative;
	}

	.grid-item:not(:last-child)::after {
		content: "";
		position: absolute;
		top: 20%;
		right: -7px;
		/* 位置在gap中间 */
		height: 60%;
		width: 1px;
		background-color: #dcdde1;
		/* 灰色竖线 */
	}

	.item-number {
		font-size: 14px;
		font-weight: 500;
		margin-bottom: 3px;
	}

	.item-text {
		font-size: 12px;
		text-align: center;
		max-width: 100%;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		color: #666;
	}

	.detail-container {
		display: flex;
	}

	.spec-item {
		display: flex;
		border-bottom: 1px solid #f0f0f0;
		transition: background-color 0.2s ease;
	}

	.spec-name {
		flex: 0 0 40%;
		padding: 18px 25px;
		font-weight: 500;
		color: #666;
	}

	.spec-value {
		flex: 1;
		padding: 18px 25px;
		color: #333;
	}
</style>