<template>
	<view style="padding: 20rpx;">
		<view class="box">
			<uni-forms ref="form" :rules="rules" :modelValue="form" label-width="140rpx" label-align="right">
				<uni-forms-item label="备注" name="comment">
					<uni-easyinput type="textarea" v-model="comment" placeholder="请输入" />
				</uni-forms-item>
				
				<uni-forms-item >
					<button type="primary" size="mini" @click="save">确认</button>
				</uni-forms-item>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				comment:''
			}
		},
		methods: {
			save(){
				let orders = uni.getStorageSync("xm-orders")
				orders.comment = this.comment 
				uni.setStorageSync("xm-orders",orders)
				uni.navigateBack()
			}
		}
	}
</script>

<style>

</style>
