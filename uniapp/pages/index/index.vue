<template>
	<view>
		<swiper circular :interval="3000" autoplay :duration="500" indicator-dots style="height: 350rpx;"
			indicator-color="rgba(255,255,255,0.6)" indicator-active-color="#3cb371">
			<swiper-item v-for="item in banners" :key="item.id">
					<image :src="item.img" mode="widthFix" style="width: 100%"></image>
			</swiper-item>
		</swiper>
		<view style="padding: 20rpx;">
			<!-- 公告 -->
			<view class="box">
				<uni-icons type="sound" size="20" style="position: relative; top: 5rpx;"></uni-icons>
				<text style="margin-left: 5rpx; margin-bottom: 6rpx;" >{{natice}}</text>
			</view>
		</view>
		
		<view class="box" style="color: orange;font-size: 32rpx;font-weight: bold;margin-bottom: 10rpx;padding: 20rpx;">
			热门商家
		</view>
		
		<view style="padding: 20rpx;">
			<view class="box" v-for="item in shopinfoList" :key="item.id" style="display: flex; grid-gap: 30rpx; margin-bottom: 10rpx;
			" @click="goToDetail(item.id,item.username)">
				<view style="width: 30;">
					<image style="width: 100px;  height: 200rpx ;border-radius: 10rpx;" :src="item.imgurl"></image>
				</view>
				<view style="flex: 1;display: flex; flex-direction: column;justify-content: space-between; grid-gap: 10rpx;">
					<view style="font-size: 32rpx; font-weight: bold;">{{item.shoping}}</view>
					<view style="display: flex; font-size: 24rpx;color: #666;"> 
						<view style="flex: 1;">
							<text style="color: #ff9800;">4.5</text>
							<text style="margin-left: 10rpx;">已售50</text>
						</view>
						<view style="flex: 1; text-align: right;">30分钟内送达</view>
					</view>
					<view style="color: #ff9800;">免费配送</view>
					<view style="background-color: #ffd281;color: brown;border-radius: 4rpx;width: fit-content;padding: 0 5rpx;">{{item.notice}}</view>
				</view>
				
			</view>
		</view>
	</view>
</template>

<script>

	export default {
		data() {
			return {
				banners:[
					{img:'http://localhost:8081/wm/file/download/1700272496240_123.jpg'},
					{img:'http://localhost:8081/wm/file/download/1700272496240_123.jpg'},
					{img:'http://localhost:8081/wm/file/download/1700272496240_123.jpg'},
				],
				naticeList:[
					["打游戏了没"],
					["吃饭了没"],
					["振刀了没"],
					["羊了个羊没"]
				],
				natice:'',
				shopinfoList:[]
			}
		},
		onLoad() {
			this.load()
		},
		methods: {
			load(){
				let i = 0
				this.natice = this.naticeList[i]
				setInterval(() => {
					if(i === this.naticeList.length - 1){
						i = 0
					}
					this.natice = this.naticeList[i++]+""
					
				},2000)
				
				this.$request.get("/shop/getShopInfo").then(res => {
					this.shopinfoList = res.result
					console.log(this.shopinfoList)
				})
			},
			goToDetail(id,username){
				uni.navigateTo({
					url:'/pages/detail/detail?shopid='+id+'&username='+username
				})
			}
		}
	}
</script>

<style>
</style>
