<template>
	<view>
		<view style="height: 200px;width: 100%;">
			<image style="width: 100%;height: 100%;" mode="aspectFill" src="/static/swiper/1.jpg"></image>
		</view>
		<u-search style="margin: 8px;" shape="round" :showAction="false" actionText="false"></u-search>
		<view  class="container" style="align-items:start !important">
			<view style="width: 150px;"> 
				<view :class="i==active ? 'tab active' : 'tab'" v-for="(e,i) in catalogList">
					<view @click="active=i">{{e.name}}</view></view>
			</view>
			<view style="width:100%; 100vh;">
				<view style="width: 100%;height: 80px;" @click="gogo">
					<image style="width: 100%;height: 100%;" mode="aspectFill" src="/static/catalog/1.jpg"></image>
				</view>
				<view style="width: 100%;height: 80px; margin-top: 3px;" @click="gogo">
					<image style="width: 100%;height: 100%;" mode="aspectFill" src="/static/catalog/2.jpg"></image>
				</view>
				<view style="width: 100%;height: 80px;margin-top: 3px;" @click="gogo">
					<image style="width: 100%;height: 100%;" mode="aspectFill" src="/static/catalog/3.jpg"></image>
				</view>
				<view style="width: 100%;height: 80px;margin-top: 3px;" @click="gogo">
					<image style="width: 100%;height: 100%;" mode="aspectFill" src="/static/catalog/4.jpg"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				active: 0,
				catalogList: [{
					name: "现代简奢",
				}, {
					name: "现代轻奢"
				}, {
					name: "现代极简"
				}, {
					name: "高端定制"
				}]
			}
		},
		methods: {
			gogo() {
				uni.navigateTo({
					url: '/pages/project/product/index'
				});
			}
		}
	}
</script>

<style>
	.tab {
		height: 30px;
		line-height: 30px;
		text-align: center;
		padding: 10px;
		background: #fbfbfb;
		border: none;
		font-size: 18px;
		transition: 0.3s;
		margin-right: 5px;
		border-radius: 5px 5px 0 0;
	}

	.tab.active {
		background: white;
		font-weight: 700;
	}
	
	.container {
	  display: flex;
	  flex-direction: row;
	  align-items: center;
	}
</style>