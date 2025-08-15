<template>
  <view class="child-item" :class="{ isShow }">
    <img class="pic" :src="item.imgUrl" mode="widthFix" @load="load" @error="load" />
    <view class="info">
    </view>
  </view>
</template>

<script setup>
  import { ref } from 'vue'
  defineProps({
    item: {
      type: Object,
      default: () => ({})
    }
  })

  const isShow = ref(false)

  const emit = defineEmits(['load', 'error'])

  function load(e) {
    isShow.value = true
    emit('load', e)
  }
</script>

<style lang="scss" scoped>
  .child-item {
    font-size: 0;
    border-radius: 10rpx;
    // margin-bottom: 20rpx;
    overflow: hidden;
    transition: all ease 0.35s;
    opacity: 0;
    .pic {
      width: 100%;
    }
    .info {
      background: #fff;
      padding: 5rpx;
      .title,
      .desc {
        display: block;
        font-size: 28rpx;
        // 如果不限制行数，把以下代码注释即可
        word-break: break-all; // 允许单词内自动换行，如果一个单词很长的话
        text-overflow: ellipsis; // 溢出用省略号显示
        overflow: hidden; // 超出的文本隐藏
        display: -webkit-box; // 作为弹性伸缩盒子模型显示
        -webkit-line-clamp: 2; // 显示的行
        -webkit-box-orient: vertical; // 设置伸缩盒子的子元素排列方式--从上到下垂直排列
      }
      .desc {
        margin-top: 10rpx;
        color: #666;
        font-size: 26rpx;
      }
    }
    &.isShow {
      opacity: 1;
    }
  }
</style>
