<template>
	<view style="padding: 20rpx;">
		<view style="margin-bottom: 110rpx;">
			<view v-for="item in addressList" :key="item.id" class="box" style="margin-bottom: 15rpx;" @click="selectAddress(item.id)">
				<view style="margin-bottom: 15rpx;">地址：{{item.address}}</view>
				<view style="color: #888;">
					<text>姓名：{{item.user}}</text>
					<text style="margin-left: 20rpx;">电话：{{item.phone}}</text>
				</view>
				<view style="text-align: right;">
					<uni-icons type="compose" size="16" @click="updateAddress(item.id)"></uni-icons>
					<uni-icons type="trash" size="16" style="margin-left: 20rpx;" @click="deleteAddress(item)"></uni-icons>
				</view>
			</view>
		</view>
		
		
		<button type="primary"  style="background-color: dodgerblue;border-color: dodgerblue;
		position: fixed;bottom: 10rpx;width: 100%;left: -3rpx;" @click="addadress">新增收货地址</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:uni.getStorageSync("xm-user"),
				addressList:[],
			}
		},
		onShow(option){
			this.load()
		},
		methods: {
			selectAddress(addressId){
				let orders = uni.getStorageSync("xm-orders") || {}
				orders.addressId = addressId
				uni.setStorageSync("xm-orders",orders)
				uni.navigateBack()
			},
			load(){
				this.$request.get("/address/selectAll",{
					userid:this.user.id
				}).then(res => {
					this.addressList = res.result
				})
			},
			deleteAddress(item){
				this.$request.post("/address/deleteAddress/"+item.id).then(res => {
					if(res.code === 200){
						uni.showToast({
							icon:'success',
							title: "删除成功"
						})
						this.load()
					}else{
						uni.showToast({
							icon:'error',
							title: res.message
						})
					}
				})
			},
			updateAddress(addressId){
				uni.navigateTo({
					url:'/pages/addAdress/addAdress?addressId='+ addressId
				})
			},
			addadress(){
				uni.navigateTo({
					url:'/pages/addAdress/addAdress'
				})
			}
			
		}
	}
</script>

<style>

</style>
