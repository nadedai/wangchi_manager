<template>
	<view>
		<u-loading-page :loading="pageLoading"></u-loading-page>
		<view>
			<u-search style="margin-top: 8px;" v-model="searchText" shape="round" :showAction="false" @search="onSearch" @clear="onSearch"></u-search>
		</view>
		<!-- <u-loading-page :loading="loading"> -->
		<u-sticky bgColor="#fff">
			<!--  -->
			<view v-if="!pageLoading" class="productTypeContainer">
				<view class="item">
					<view class="name">风格:</view>
					<u-tabs class="tabs" ref="productTypeRootRef" :list="productTypeRoot" :lineColor="'#606266'" @change="productTypeRootChange" />
					<view class="detail" @tap="detailShow=!detailShow">
						<span>{{detailShow ? '折叠' : '展开'}}</span>
						<u-icon :name="detailShow ? 'arrow-up' : 'arrow-down'" size="18"></u-icon>
					</view>
				</view>

				<view v-if="detailShow" class="item">
					<view class="name">类别:</view>
					<u-tabs ref="productTypeChildrenRef" :list="productTypeChildren" :lineColor="'#606266'" @change="productTypeChange" />
				</view>

				<view v-if="detailShow" class="item">
					<view class="name">排序:</view>
					<u-tabs ref="orderTypeRef" :list="orderType" :lineColor="'#606266'" @change="productTypeChange" />
				</view>
			</view>
		</u-sticky>
		<!-- </u-loading-page> -->
		<u-waterfall v-model="productData" ref="uWaterfall" add-time="100">
			<template #default="{ columnList, columnIndex }">
				<view class="productContainer" v-for="(item, index) in columnList" :key="index" @click="productClick(item)">
					<u-lazy-load mode="aspectFit" threshold="0" height="180" border-radius="10" loading-img='' :is-effect="false" :image="item.picRealUrl + '?x-oss-process=image/resize,p_20'" index="index" />
					<view class="title"><span class="new-tag" v-if="item.isNew">NEW</span>{{ item.name }}</view>
					<view class="price"><span style="font-size: 28rpx;">¥</span><span style="font-weight: 600">{{ item.price }}</span></view>
				</view>
			</template>
		</u-waterfall>
		<u-loadmore v-if="!pageLoading" :status="loadStatus"></u-loadmore>
	</view>
</template>

<script setup>
	import { ref, nextTick, getCurrentInstance } from 'vue'
	import { productTypeList, productList, listOssByIds } from '@/api/api.js'
	import { onReachBottom, onShareAppMessage } from '@dcloudio/uni-app'
	import { share } from '@/utils/common.js'
	const productData = ref([])
	const productTypeRoot = ref([])
	const productTypeChildren = ref([{ name: "全部" }])
	const orderType = ref([{ name: "默认" }, { name: "新品" }, { name: "价格↑" }, { name: "价格↓" }])
	const productTypeChildrenFresh = ref(true)
	const detailShow = ref(false)
	const proxy = getCurrentInstance()
	const loadStatus = ref('loading')
	const query = ref({
		pageNum: 1,
		pageSize: 10,
		isSale: true,
		name: undefined,
		isAsc: 'desc',
		orderByColumn: 'createTime'
	})
	const searchText = ref("")
	const pageLoading = ref(true)

	function productTypeRootChange(e) {
		const v = [{ name: "全部" }]
		if (e.children) {
			v.push(...e.children)
		} else {
			if (proxy.refs.productTypeChildrenRef) {
				proxy.refs.productTypeChildrenRef.innerCurrent = 0;
			}
		}
		productTypeChildren.value = v
		if (proxy.refs.productTypeChildrenRef) {
			proxy.refs.productTypeChildrenRef.innerCurrent = 0
		}
		productTypeChange()
	}

	function getData(needClear) {
		loadStatus.value = 'loading'
		productList(query.value).then(async res => {
			nextTick(() => {
				productData.value.push(...res.data.records)
			})
			if (query.value.pageNum >= res.data.pages) {
				loadStatus.value = 'nomore'
				return
			}
		})
	}

	function productClick(row) {
		uni.navigateTo({
			url: '/pages/project/product/detail/index?id=' + row.id
		});
	}

	function productTypeChange() {
		let rCur = proxy.refs.productTypeRootRef?.innerCurrent;
		let cCur = proxy.refs.productTypeChildrenRef?.innerCurrent;
		let oCUr = proxy.refs.orderTypeRef?.innerCurrent;
		query.value.pageNum = 1;
		query.value.productType = rCur > 0 ? productTypeRoot.value[rCur].id : ''
		if (cCur > 0) {
			query.value.productType += ',' + productTypeChildren.value[cCur].id
		}
		if (oCUr === 1) {
			query.value.isAsc = 'desc'
			query.value.orderByColumn = 'isNew'
		} else if (oCUr === 2) {
			query.value.isAsc = 'asc'
			query.value.orderByColumn = 'price'
		} else if (oCUr === 3) {
			query.value.isAsc = 'desc'
			query.value.orderByColumn = 'price'
		} else {
			query.value.isAsc = 'asc,desc'
			query.value.orderByColumn = 'orderNum,createTime'
		}
		query.value.name = query.value.name ? query.value.name : ""
		proxy.refs.uWaterfall.clear()
		getData(true)
	}

	function onSearch(e) {
		query.value.name = e
		productTypeChange()
	}

	onReachBottom(() => {
		if (loadStatus.value === 'nomore') {
			return
		}
		query.value.pageNum = query.value.pageNum + 1
		loadStatus.value = 'loading';
		getData();
	})

	productTypeList({ enabled: true }).then((res) => {
		productTypeRoot.value.push({ name: "全部" })
		productTypeRoot.value.push(...res.data)
		pageLoading.value = false
		productTypeChange()
	})

	onShareAppMessage(() => {
		return share('/pages/project/product/index')
	})
</script>

<style scoped lang="scss">
	.productTypeContainer {}

	::v-deep .u-wrap {
		background-color: #ffffff00 !important;
	}

	::v-deep .u-tabs__wrapper__nav__item {
		padding: 0 6px !important;
	}

	::v-deep .u-tabs__wrapper__nav__line {
		transform: translate(11px);
	}

	::v-deep .u-tabs {
		width: 70%;
	}

	.productTypeContainer .item {
		padding-left: 8px;
		position: relative;
		display: flex;
		align-items: center;
		flex-wrap: wrap;
	}

	.productTypeContainer .detail {
		display: flex;
		min-width: 60px;
		color: #606266;
		font-size: 13px;
		justify-content: flex-end;
		position: absolute;
		top: 30%;
		right: 10px;
	}

	.productTypeContainer .name {
		color: #606266;
		font-weight: 550;
	}

	// .productTypeContainer .tabs {
	// 	width: 70% !important;
	// }

	.productContainer {
		border-radius: 8px;
		margin: 5px;
		background-color: #ffffff;
		position: relative;
	}

	.productContainer .image {
		width: 100%;
		border-radius: 4px;
	}

	.productContainer .title {
		padding-top: 0px;
		padding-left: 8px;
		font-size: 35rpx;
		margin-top: 5px;
	}

	.productContainer .price {
		padding: 8px;
		padding-top: 0;
		font-size: 33rpx;
	}

	.new-tag {
		display: inline-block;
		background: #000;
		color: #fff;
		font-size: 8px;
		font-weight: 700;
		padding: 3px 3px;
		margin-right: 3px;
		letter-spacing: 0.5px;
		vertical-align: middle;
	}

	::v-deep .u-sticky {
		top: 0 !important;
	}
</style>