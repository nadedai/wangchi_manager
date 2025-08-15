<template>
	<view class="container">
		<view v-for="colNum in state.columnNum" :key="colNum" class="columnItem">
			<child-item v-for="(item, i) in state.columnData[colNum]" :key="i" :item="item" @load="imageLoad" />
		</view>
	</view>
	<view class="loading">加载中…</view>
</template>

<script setup>
	import { onLoad, onReachBottom } from '@dcloudio/uni-app'
	  import { data } from './data.js'
	  import { reactive, getCurrentInstance } from 'vue'
	  import childItem from './child-item.vue'
	  const { proxy } = getCurrentInstance()
	  const state = reactive({
	    columnNum: 2,
	    minHeightColNum: 1,
	    columnData: {},
	    totalList: [],
	    pages: {
	      page: 1,
	      pageSize: 10
	    }
	  })

	const props = defineProps({
		item: {
			type: Object,
			default: () => ({})
		}
	})

	onLoad(() => {
		setDefaultData()
		getData()
	})

	// 模拟获取数据
	function getData() {
		if (state.pages.page == 1) setDefaultData()
		return new Promise((resolve) => {
			setTimeout(() => {
				state.totalList.push(...data)
				loadNextItem()
				resolve()
			}, 500)
		})
	}

	/** @加载更多 **/
	onReachBottom(() => {
		state.pages.page++
		getData()
	})

	/** @图片加载成功 **/
	function imageLoad() {
		getMinHeightColumnNum().then(() => loadNextItem())
	}

	/** @加载下一个元素 **/
	function loadNextItem() {
		const totalLength = Object.values(state.columnData).flat().length
		if (totalLength == state.totalList.length) return
		state.columnData[state.minHeightColNum].push(state.totalList[totalLength])
	}

	/** @获取最小列数 **/
	async function getMinHeightColumnNum() {
		let minHeight = Infinity // 初始化为无限大
		return new Promise((resolve) => {
			$('.columnItem', proxy).then((columItems) => {
				columItems.forEach((item, index) => {
					if (item.height < minHeight) {
						minHeight = item.height
						state.minHeightColNum = index + 1
					}
				})
				resolve()
			})
		})
	}

	/** @设置默认数据 **/
	function setDefaultData() {
		state.totalList = []
		Array(state.columnNum)
			.fill(null)
			.map((item, i) => {
				state.columnData[i + 1] = []
			})
	}

	/** @封装[jvideo](https://v.ixigua.com/iNQLjWJS/)查询DOM **/
	function $(selecter, proxy) {
		return new Promise((resolve) => {
			uni
				.createSelectorQuery()
				.in(proxy)
				.selectAll(selecter)
				.boundingClientRect()
				.exec((res) => {
					resolve(res[0])
				})
		})
	}
</script>

<style lang="scss" scoped>
	.container {
		display: flex;
		padding: 10rpx;

		.columnItem {
			width: calc(100% / v-bind('state.columnNum'));
			height: fit-content;
			&+.columnItem {
				margin-left: 20rpx;
			}
		}
	}

	.loading {
		padding: 20rpx;
		text-align: center;
		font-size: 28rpx;
		color: #aaa;
	}
</style>