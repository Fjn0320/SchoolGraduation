<template>
	<view>
		<uni-segmented-control :current="current" :values="items" @clickItem="onClickItem" 
			styleType="button" activeColor="#ff9900"></uni-segmented-control>
		        <view style="padding: 20rpx;">
		            <view v-show="current === 0">
		               <view class="box" v-for="item in ordersList" :key="item.id" style="margin-bottom: 10rpx;">
						   <view style="display: flex;align-items: baseline;margin-bottom: 10rpx;">
							   <!-- 跳转到商家页面 -->
							   <navigator :url="'/page/detail/detail?shopid='+item.shopid" 
							   style="flex: 1;font-size: 32rpx;">
								   <view>{{item.shoping}}
									<uni-icons type="right" size="16rpx" color="#666" style="position: relative;top: 2rpx;"></uni-icons></view>
							   </navigator>
							   <view style="font-size: 24rpx; color: #666;">已支付</view>
						    </view>
							<view style="display: flex;align-items: center;grid-gap: 20rpx;">
								<view><image style="display: block;width: 160rpx;height: 160rpx;border-radius: 10rpx;" :src="item.shopurl" ></image></view>
								<view style="flex: 1;">{{item.shopname}}</view>
								<view>实付￥<text style="font-size: 36rpx;font-weight: bold;color: red;">{{item.shopmoney}}</text></view>
							</view>
							<view style="text-align: right;"><uni-tag size="mini" type="primary" text="评价" @click="openPingFen(item.id)"></uni-tag></view>
		               </view>
		            </view>
		        </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				items: ['全部'],
				current: 0,
				user:uni.getStorageSync("xm-user"),
				ordersList:[],
				
			}
		},
		onShow() {
			this.loadOreders()
		},
		methods: {
			loadOreders(){
				this.$request.get("/shop/getDingItem",{
					userid:this.user.id
				}).then(res => {
					this.ordersList = res.result
				})
			},
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
			},
			openPingFen(dingdanid){
				uni.navigateTo({
					url:'/pages/pinjia/pinjia?dingdanid='+dingdanid
				})
			}
			
		}
	}
</script>

<style>

</style>
