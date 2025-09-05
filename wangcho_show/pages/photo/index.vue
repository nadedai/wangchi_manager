<template>
	<view>
		<u-loading-page :loading="pageLoading"></u-loading-page>
		<u-sticky bgColor="#fff">
			<image style="height: 30vh; width: 100%;" mode="aspectFill" :src="photoCoverImg" />
			<u-tabs v-if="!pageLoading" :list="productList" @change="photoTypeChange" :lineColor="'#606266'" :activeStyle="{
		        color: '#303133',
		        fontWeight: 'bold',
		        transform: 'scale(1.05)'
		    }" :inactiveStyle="{
		        color: '#606266',
		        transform: 'scale(1)'
		    }"></u-tabs>
		</u-sticky>

		<u-waterfall v-model="photoData" ref="uWaterfall" add-time="0">
			<template #default="{ columnList, columnIndex }">
				<view class="container" v-for="(item, index) in columnList" :key="index" @click="onPhotoClick(item)">
					<u-lazy-load threshold="0" height="150" border-radius="10" loading-img='' :is-effect="false" :image="item.realUrl + '?x-oss-process=image/resize,p_25' " index="index" />
				</view>
			</template>
		</u-waterfall>
	</view>
	<u-loadmore :status="loadStatus"></u-loadmore>
</template>

<script setup>
	import imgListView from './components/img-list-view.vue'
	import { ref, getCurrentInstance } from 'vue'
	import { onLoad, onReachBottom } from '@dcloudio/uni-app'
	import { photoTypeList, photoList, listOssByIds, getPhotoCover } from '@/api/api.js'
	const proxy = getCurrentInstance()
	const curItem = ref()
	const productList = ref([])
	const photoData = ref([])
	const photoCoverImg = ref()
	const loadStatus = ref('loadmore')


	const q = ref({
		pageNum: 1,
		pageSize: 10,
		enabled: true,
		isAsc: 'asc,desc',
		orderByColumn: 'orderNum,createTime',
		type: undefined
	})
	const totolPage = ref(0)
	const pageLoading = ref(true)

	function getPhotoType() {
		photoTypeList({ enabled: true }).then(res => {
			productList.value = res.data
			curItem.value = res.data[0]
			photoTypeChange(curItem.value)
			pageLoading.value = false
		})
	}

	function getPhotoList() {
		loadStatus.value = 'loading'
		photoList(q.value).then(async res => {
			photoData.value.push(...res.data.records)
			if (q.value.pageNum >= res.data.pages) {
				loadStatus.value = 'nomore'
				return
			}
		})
	}

	function photoTypeChange(e) {
		curItem.value = e
		q.value.type = curItem.value.id
		q.value.pageNum = 1
		photoData.value = []
		proxy.refs.uWaterfall.clear()
		getPhotoList()
	}

	function onPhotoClick(item) {
		uni.previewImage({
			urls: [item.realUrl]
		})
	}

	function getPhotoCoverImg() {
		getPhotoCover().then(res => {
			photoCoverImg.value = res.msg
		})
	}

	onReachBottom(() => {
		if (loadStatus.value === 'nomore') {
			return
		}
		q.value.pageNum = q.value.pageNum + 1
		loadStatus.value = 'loading';
		getPhotoList();
	})
	getPhotoType()
	getPhotoCoverImg()
</script>

<style scoped lang="scss">
	.container {
		border-radius: 8px;
		margin: 3px;
		background-color: #ffffff;
		position: relative;
	}

	::v-deep .u-wrap {
		background-color: #ffffff00;
	}
</style>