<template>
	<view style="padding: 20rpx;">
		<view class="box" style="background-color: antiquewhite; padding: 10rpx 20rpx;">
			<uni-icons type="sound" size="16" style="position: relative;top: 3rpx;color: orange;"></uni-icons>
			<text>温馨提示：适量点餐，避免浪费</text>
		</view>
		
		<navigator url="/pages/address/address" class="box" style="margin: 20rpx 0;position: relative;top: 4rpx;color: dodgerblue; ">
			<view v-if="addressId">
				<view style="margin-bottom: 10rpx; display: flex;">
					<view style="flex: 1;">地址：{{address.address}}</view>
					<uni-icons type="right" color="#666"></uni-icons>
				</view>
				<view style="color: #888;margin-bottom: 20rpx;">
					<text>姓名：{{address.user}}</text>
					<text style="margin-left: 20rpx;">电话：{{address.phone}}</text>
				</view>
				<view style="display: flex;">
					<view style="flex: 1;">
						平台配送
					</view>
					<view style="flex: 1;color: dodgerblue;text-align: right;">30分钟送达</view>
				</view>
			</view>
			
			<view v-else>
				<uni-icons type="plus" size="15" ></uni-icons>
				<text style="margin-left: 5rpx; color: dodgerblue;">请添加收货地址</text>
			</view>
		</navigator>
		
		<view class="box" style="margin: 20rpx 0;">
			<view style="margin-bottom: 20rpx;color: #666; font-size: 36rpx;">{{shopinfo.shoping}}</view>
			<!-- 购物车 -->
			<view>
				<view style="display: flex;grid-gap: 20rpx;margin-bottom: 20rpx;" v-for="item in getCartList" :key="item.id">
					<view style="width: 140rpx; height: 140rpx;">
						<image :src="item.shopPojo.shopurl" style="width: 100%;height: 100%;display: block;border-radius: 4rpx;"></image>
					</view>
					
					<view style="flex: 1; display: flex;flex-direction: column;justify-content: space-between;">
						<view>{{item.shopPojo.shopname}}</view>
						<view style="color: #888;">X {{item.num}}</view>
					</view>
					<view style="width: 150rpx;text-align: right;color: red;padding-top: 10rpx;">
						￥{{item.shopPojo.shopmoey}}
					</view>
				</view>
			</view>
			<!-- 显示优惠和总金额信息 -->
			<view v-if="amount">
				<view style="text-align: right;">
					<text>已优惠</text>
					<text style="color: red;">￥ {{amount.discount}}</text>
					<text style="margin-left: 20rpx;">小计</text>
					<text style="color: red;font-size: 36rpx; font-weight: bold;">￥ {{amount.actual}}</text>
				</view>
			</view>
			<!-- 购物车商品和金额信息 -->
			
			<!-- 商家承诺 -->
			<view class="box" style="margin: 20rpx 0;">
				<view style="display: flex; margin-bottom: 10rpx;">
					<view style="flex: 1;font-weight: bold;">商家承诺</view>
					<view style="flex: 1;text-align: right;color: #666;">售后无忧 · 食无忧</view>
				</view>
				<view style="display: flex; margin-bottom: 10rpx;">
					<view style="flex: 1;font-weight: bold;">隐私保护</view>
					<view style="flex: 1;text-align: right;color: #666;">号码保护 · 隐私无忧</view>
				</view>
				<view style="display: flex; margin-bottom: 10rpx;">
					<view style="flex: 1;font-weight: bold;">备注</view>
					<navigator url="/pages/orderComment/orderComment" style="flex: 1;text-align: right;color: #666;">
						<text v-if="comment">{{comment}}</text>
						<text v-else>请填写口味偏好</text>
						<uni-icons type="right"
					 color="#999" style="position: relative; top: 2rpx;"></uni-icons></navigator>
				</view>
			</view>
			<!-- 商家承诺 -->
			
			<view class="box" style="margin: 20rpx 0;">
				<uni-data-checkbox v-model="value" :localdata="range" @change="change"></uni-data-checkbox>
			</view>
			
			<!-- 提交按钮 -->
			<view v-if="amount" class="box" style="position: fixed;bottom: 0%;width: 100%;left: -40rpx;">
				<view style="text-align: right;" >
					<text>已优惠</text>
					<text style="color: red;">￥ {{amount.discount}}</text>
					<text style="margin-left: 20rpx;">小计</text>
					<text style="color: red;font-size: 36rpx; font-weight: bold;">￥ {{amount.actual}}</text>
					<button size="mini" type="primary" style="margin-left: 20rpx; background-color: deepskyblue;
					border-color: deepskyblue;position: relative;top: 4rpx;" @click="addOrder()">支付</button>
				</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				shopid:0,//商家id
				addressId:0,//地址id
				comment:'',//获取备注数据
				user:uni.getStorageSync('xm-user'),
				getCartList:[],//获取订单信息里面包括商品信息和商店信息
				amount:[],
				address:[],//获取地址信息
				shopinfo:[],//商店信息
				amount:[],//计算金额
				
				value: 0,
				range: [{"value": 0,"text": "支付宝"	},{"value": 1,"text": "微信"}]
			}
		},
		onShow() {
			//获取缓存数据
			let orders = uni.getStorageSync("xm-orders")
			this.shopid = orders.shopid || 0
			this.addressId = orders.addressId || 0
			this.comment = orders.comment || ""
			this.loadCart()
			this.getAddress()
		},
		methods: {
			addOrder(){//提交订单
				if(!this.addressId){
					uni.showToast({
						icon:"error",
						title:"请选择地址"
					})
				}else{
					this.$request.post("/shop/addDingDTO",{
						shopid:this.shopid,
						comment:this.comment,
						user:uni.getStorageSync('xm-user').username,
						address:this.address.address,
						phone:this.address.phone
					}).then(res => {
						if(res.code == 200){
							uni.showToast({
								icon:"success",
								title:"下单成功"
							})
							//清除缓存
							uni.removeStorageSync("xm-orders")
							uni.navigateBack()
						}else{
							uni.showToast({
								icon:"error",
								title:res.message
							})
						}
					})
					
				}
				
				
			},
			//从选择地址后从网络存储地址id然后获取地址信息
			getAddress(){
				this.$request.get("/address/selectById",{
					id:this.addressId//商家id
				}).then(res => {
					this.address = res.result || []
				})
			},
			loadCart(){//刷新购物车,根据用户id来查询
				this.$request.get("/cart/selectAllCart",{
						userid:this.user.id,
						businessid:this.shopid,
					}).then(res => {
						this.getCartList = res.result
						console.log(this.getCartList.length)
						if(this.getCartList.length>0){
							this.shopinfo = this.getCartList[0].shopInfoPojo
						}
					
				})
				
				this.$request.get("/cart/calc",{
					userid:this.user.id,
					businessid:this.shopid,
				}).then(res => {
					this.amount = res.result
				})
				
			},
		}
	}
</script>

<style>

</style>
