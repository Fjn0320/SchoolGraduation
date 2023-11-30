<template>
	<view style="padding: 20rpx;">
		<view class="box" style="display: flex;">
			<view style="flex: 1; display: flex;flex-direction: column;justify-content: space-between;">
				<view style="font-size: 36rpx; font-weight: bold;">{{shopinfoList.shoping}}</view>
				<view>
					<text style="padding-right: 10rpx;border-right: 2rpx solid #ccc;">平台配送</text>
					<text style="padding: 0 10rpx;border-right:2rpx solid #ccc">免费配送</text>
					<text style="padding: 0 10rpx">30分钟送达</text>
				</view>
				<view>
					地址：{{shopinfoList.adress}}
				</view>
				<view>
					联系电话：{{shopinfoList.phone}}
				</view>
			</view>
			<view style="width: 200rpx;height: 200rpx;">
				<image :src="shopinfoList.imgurl" style="width: 100%;height: 100%;border-radius: 50%;"></image>
			</view>
		</view>
		<!-- 商家信息结束 -->
		
		<!-- 商家分类信息 -->
		<view style="margin: 20rpx 0;">
			<view style="background-color: #fff;display: flex;">
				<view style="background-color: #ffd100;padding: 10rpx;border-radius: 10rpx;overflow: hidden;margin-bottom: 10rpx;">全部商品</view>
				<view style="padding: 10rpx;">评价</view>
			</view>
			<!-- 分类展示 -->
			<view style="display: flex; background-color: #fff;border-radius: 10rpx;padding: 10rpx;">
				<view style="width:200rpx;text-align: center;" >
					<view  v-for="item in shopsortList" :key="item.id" 
					:class="{ 'categroy-sort' : item.id === activeCategoryId}"
					class="categroy-item" @click="getSortMenu(item.id)">{{item.shopsot}} </view>
			</view>
			<!-- 商品列表开始 -->
			<scroll-view scroll-y="true" style="height: 59vh">
				<view style="padding: 20rpx; ">
					<view style="display: flex; grid-gap: 20rpx; margin-bottom: 10rpx;"  v-for="item in shopSortMenu" :key="item.id">
						<view style="width: 200rpx; height: 200rpx;">
							<image :src="item.shopurl" style="width: 200rpx;height: 200rpx; border-radius: 20rpx; display: block;"></image>
						</view>
						<view style="flex: 1; display: flex;  flex-direction: column;justify-content: space-between;">
							<view style="font-size: 32rpx;font-weight: bold;">{{item.shopname}}</view>
							<view>不得不说，嘎嘎好吃</view>
							<view style="margin-bottom: 10rpx;">
								<text class="min-button">7折</text>
								<text style="font-size: 24rpx; margin-left: 20rpx;" >已售 30</text>
							</view>
							<view> 
								<text style="text-decoration: line-through;">￥{{item.shopprice}}</text>
								<text style="color: orangered;">￥{{item.shopmoey}}</text>
								<text class="min-button" style="margin-left: 5rpx;">到手价</text>
							</view>
							<view>
								<text class="min-btn-fill" @click="addCart(item)">选购</text>
							</view>
						</view>
					</view>
				</view>
				<!-- 商品列表结束 -->
			</scroll-view>
			
			</view>
		</view>
		
		<!-- 购物车弹窗 -->
		<uni-goods-nav :fill="true" :options="options" :button-group="customButtonGroup" @click="onClick" @buttonClick="buttonClick" style="margin-top: 20px;" />
	
		<uni-popup ref="popup" type="bottom" background-color="#fff">
			<scroll-view style="max-height: 70vh;" scroll-y="true">
				<view style="padding: 40rpx 40rpx 280rpx 40rpx;">
					 <view style="text-align: right; margin-bottom: 10rpx;color: #999;">
						 <uni-icons v-if="getCartList.length" style="po(sition: relative; top: 4rpx;" type="trash" size="16" color="999" ></uni-icons>
						<text @click="deleteAll">
							清空
						</text>
					</view>
					<view v-for="(item,index) in getCartList" :key="index" style="display: flex;margin-bottom: 20rpx;" v-if="item.shopPojo">
						<view style="width: 100rpx;height: 100rpx;">
							<image style="width: 100%;height: 100%; display: inline-block;" :src="item.shopPojo.shopurl"></image>
						</view>
						<view style="flex: 1;margin-left: 20rpx;display: flex;flex-direction: column;justify-content: space-around;">
							<view style="flex: 1;">{{item.shopPojo.shopname}}</view>
							<view style="flex: 1;color: red;display: flex;align-items: flex-end;">
								<view style="flex: 1;">￥{{item.shopPojo.shopmoey}}</view>
								<view style="flex: 1;display: flex;justify-content: right;">
									<uni-number-box :min="1" v-model="item.num" @change="updateCart(item)"></uni-number-box>
								</view>
							</view>
						</view>
					</view>
					<view style="margin-top: 40rpx;text-align: right;" v-if="amount.actual">总金额：<text style="color: red;">￥{{amount.actual}}</text></view>
					<view style="margin-top: 10rpx;text-align: right;" v-if="amount.amount">总金额：<text style="color: red;">￥{{amount.amount}}</text></view>
					<view style="margin-top: 10rpx;text-align: right;" v-if="amount.discount">总金额：<text style="color: red;">￥{{amount.discount}}</text></view>
				</view>
			</scroll-view>
		</uni-popup>
	
	</view>
</template>

<script>
	export default {
		data() {
			return {
				shopid:0,//商家id
				shopMneuList:[],//获取商家菜单信息
				shopinfoList:[],//商家信息列表
				shopsortList:[],//商家分类信息
				username:'',//获取账号名
				activeCategoryId:0,
				shopSortMenu:[],//所分类中的菜单
				options:[//购物车组件
					{
						icon: 'cart',
						text: '购物车',
						info: 0
					}
				],
				customButtonGroup:[//立即下单组件
					{
						text: '立即下单',
						backgroundColor: 'linear-gradient(90deg, #FE6035, #EF1224)',
						color: '#fff'
					}
				],
				user:uni.getStorageSync('xm-user'),
				getCartList:[],//获取该用户购物车的数据
				amount:[]
				
			}
		},
		onShow() {
			let allPage = getCurrentPages()//获取当前页面栈的实例
			let lastPages = allPage.length - 1//获取倒数第二个元素的索引
			let option = allPage[lastPages].options//获得上个页面传过来的参数
			this.shopid  = option.shopid
			this.username = option.username
			this.load()
			this.loadCart()
		},
		methods: {
			load(){
				  //根据id查询到店主名，然后根据店主名查到相应的店铺里面的菜单信息
				this.$request.get("/shop/selectById/"+this.shopid).then(res => {
					this.shopMneuList = res.result
				})
				
				//商家信息列表根据id
				this.$request.get("/shop/getShopInfoId/"+this.shopid).then(res => {
					this.shopinfoList = res.result
				})
				
				//商家菜品分类列表，根据账号名去查
				this.$request.get("/shop/getShopSort/"+this.username).then(res => {
					this.shopsortList = res.result
					this.activeCategoryId = this.shopsortList[0].id
					this.getSortMenu(this.activeCategoryId)
					// console.log(this.shopsortList)
					// console.log(this.activeCategoryId)
				})
			},
			//根据分类和username获取相应的数据
			getSortMenu(selectid){
				this.activeCategoryId=selectid
				this.$request.get("/shop/getAllMenu",{
					id:selectid
				}).then(res=>{
					this.shopSortMenu = res.result
					console.log(res)
				})
			},
			addCart(item){//添加购物车
				this.$request.post("/cart/add",{
					shopid:item.id,//商品id
					num:1,//商品数量
					userid:this.user.id,//用户id
					businessid:this.shopid//传入商家id
				}).then(res => {
					if(res.code === 200){
						uni.showToast({
							icon:'success',
							title: "加入成功"
						})
						this.loadCart()
					}else{
						uni.showToast({
							icon:'error',
							title: res.message
						})
					}
				})
			},
			loadCart(){//刷新购物车,根据用户id来查询
				this.$request.get("/cart/selectAllCart",{
					userid:this.user.id,
					businessid:this.shopid,
					}).then(res => {
					this.getCartList = res.result
					this.options[0].info = this.getCartList.length
					console.log(this.getCartList)
				})
				
				this.$request.get("/cart/calc",{
					userid:this.user.id,
					businessid:this.shopid,
				}).then(res => {
					this.amount = res.result
				})
				
			},
			onClick(){//点击购物车图标
				this.$refs.popup.open()
			},
			updateCart(item){
				this.$request.post("/cart/updateCart",item).then(res => {
					if(res.code === 200){
						this.loadCart()
					}else{
						uni.showToast({
							icon:'error',
							title: res.message
						})
					}
				})
			},
			deleteAll(){
				console.log(this.user.id)
				console.log(this.shopid)
				this.$request.post("/cart/deleteCart/"+this.user.id+"/"+this.shopid).then(res => {
					if(res.code === 200){
						uni.showToast({
							icon:'success',
							title: "清空购物车"
						})
						this.loadCart()
					}else{
						uni.showToast({
							icon:'error',
							title: res.message
						})
					}
				})
			},
			buttonClick(){
				if(this.getCartList.length === 0){
					uni.showToast({
						icon:"error",
						title:"请选择商品"
					})
					return
				}
				
				let orders = uni.getStorageSync("xm-orders") || {}
				orders.shopid = this.shopid || 0
				uni.setStorageSync("xm-orders",orders)
				console.log(uni.getStorageSync("xm-orders"))
				uni.navigateTo({
					url:'/pages/comfirm/comfirm'
				})
			}
			
		},
			
	}
</script>

<style>
.categroy-sort{
	background-color: #ffd100;
	color: orange;
	border-left: 5rpx solid orange;
}
.categroy-item{
	padding: 15rpx;
	background-color: #eee;
}
.min-button{
	border: 2rpx solid orangered;
	padding: 0 4rpx;
	font-size: 22rpx;
	color: orangered; 
	border-radius: 5rpx;
	font-weight: bold;
}
.min-btn-fill{
	background-color: orange;
	padding: 0 4rpx;color: #fff; 
	font-size: 24rpx;
	border-radius: 5rpx;
}
</style>
