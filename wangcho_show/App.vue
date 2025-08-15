<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	@import "@/uni_modules/uview-next/index.scss";
	/*每个页面公共css */
	.uni-tabbar-border {
	    background-color: transparent !important;
	    height: 0 !important;
	    border: none !important;
	}
	 
	/* 小程序平台强化处理 */
	/* #ifdef MP-WEIXIN */
	uni-tabbar .uni-tabbar__border {
	    display: none !important;
	}
	/* #endif */
	 
	/* 专门处理 APP 平台 */
	/* #ifdef APP-PLUS */
	/* 移除 iOS 阴影线 */
	.uni-tabbar.uni-tabbar--topselected {
	    border-top: none !important;
	}
	 
	/* 移除 Android 边框线 */
	.uni-tabbar {
	    border-top-color: transparent !important;
	    border-top-width: 0px !important;
	    box-shadow: none !important;
	}
	 
	/* 处理 iOS 15+ 新样式 */
	.uni-tabbar__content {
	    background-image: none !important;
	}
	/* #endif */
</style>
