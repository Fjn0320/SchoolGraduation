<template>
	<view style="padding: 20rpx;">
		<view class="box">
			<uni-forms ref="form" :modelValue="form" :rules="rules"  label-width="180rpx" label-align="right" >
				<uni-forms-item label="地址信息" name="address" required>
					<uni-easyinput type="text" v-model="form.address" placeholder="请输入地址"></uni-easyinput>
				</uni-forms-item>
				<uni-forms-item label="联系人" name="user" required>
					<uni-easyinput type="text" v-model="form.user" placeholder="请输入联系人"></uni-easyinput>
				</uni-forms-item>
				<uni-forms-item label="联系电话" name="phone" required>
					<uni-easyinput type="text" v-model="form.phone" placeholder="请输入电话"></uni-easyinput>
				</uni-forms-item>
				
				<uni-forms-item >
					<button type="primary" size="mini" @click="saveAddress">确定</button>
				</uni-forms-item>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userToken:uni.getStorageSync("xm-user"),
				form:{
					userid:uni.getStorageSync("xm-user").id
				},
				rules: {
					address: {
						rules:[
							{
								required: true,
								errorMessage: '请填写地址',
							}
						]
					},
					user: {
						rules:[
							{
								required: true,
								errorMessage: '请填写联系人',
							}
						]
					},
					phone: {
						rules:[
							{
								required: true,
								errorMessage: '请填写联系电话',
							}
						]
					}
					
				},
				
			}
		},
		onLoad(option){
			let addressId = option.addressId
			if(addressId>0){
				this.$request.get("/address/selectById",{
					id:addressId
				}).then(res => {
					this.form = res.result
				})
			}
			
		},
		methods: {
			saveAddress(){
				this.$refs.form.validate().then((res)=>{
					console.log(this.form)
					this.$request.request({
						method:'POST',
						url: this.form.id ? '/address/updateAddress' : '/address/add',
						data: this.form
					}).then(res =>{
							if(res.code == 200){
								uni.showToast({
									icon: 'success',
									title: "添加成功"
								})
								uni.navigateBack()
							}else{
								uni.showToast({
									icon: 'error',
									title: res.message
								})
							}
					})
				}).catch((err)=>{
					console.log("表单校验失败",err)
				})
			}
			
		}
	}
</script>

<style>

</style>
