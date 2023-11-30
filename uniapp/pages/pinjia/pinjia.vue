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
				dingdanid:0,
				shopid:0,
				comment:''
			}
		},
		onLoad(option) {
			this.dingdanid = option.dingdanid
			console.log(this.dingdanid)
		},
		methods: {
			save(){
				console.log(this.comment)
					this.$request.post("/shop/addPingFen?id="+this.dingdanid +"&comment="+this.comment).then(res => {
						if(res.code === 200){
							uni.showToast({
								icon: 'success',
								title: "评价成功"
							})
						}else{
							uni.showToast({
								icon: 'error',
								title: res.message
							})
						}
					})
				}
			}
		
	}
</script>

<style>

</style>
