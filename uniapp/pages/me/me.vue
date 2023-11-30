<template>
	<view style="padding: 20rpx;">
		<view class="box" style="display: flex;align-items: center;margin-bottom: 20rpx;">
			<image :src="user.imgurl" style="width: 150rpx;border-radius: 50%;" mode="widthFix"></image>
			<view style="flex: 1;font-size: 32rpx;">{{user.username}}</view>
			<view style="color: #888;">{{user.uniname}}</view>
		</view>
		<view class="box">
			<navigator style="padding: 15rpx;border-bottom: 2rpx solid #eee;" :url="'/pages/info/info'">
				<uni-icons type="preson" size="18"></uni-icons>
				<text style="margin-left: 10rox;">个人信息</text>
				<uni-icons type="right" color="#999" style="float: right;"></uni-icons>
			</navigator>
			<navigator style="padding: 15rpx;border-bottom: 2rpx solid #eee;" :url="'/pages/addAdress/addAdress'">
				<uni-icons type="preson" size="18"></uni-icons>
				<text style="margin-left: 10rox;">收货地址</text>
				<uni-icons type="right" color="#999" style="float: right;"></uni-icons>
			</navigator>
			<view style="padding: 15rpx;border-bottom: 2rpx solid #eee;" @click="outLogin()">
				<uni-icons type="preson" size="18"></uni-icons>
				<text style="margin-left: 10rox;">退出登录</text>
				<uni-icons type="right" color="#999" style="float: right;"></uni-icons>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:uni.getStorageSync("xm-user")
			}
		},
		methods: {
			outLogin(){
				uni.removeStorageSync("xm-user")
				uni.navigateTo({
					url:'/pages/login/login'
				})
			}
		}
	}
</script>

<style>

</style>
