<template>
	<view style="padding: 40rpx;">
		<view style="padding: 20rpx; margin: 50rpx 0; background-color:  #fff; box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);border-radius: 20rpx;">
			<view style="margin: 30rpx; font-size: 40rpx;">欢迎登录</view>
			<uni-forms ref="form" :modelValue="form" :rules="rules" validate-trigger="blur">
				<uni-forms-item name="username" required >
					<uni-easyinput prefixIcon="person" v-model="form.username" type="text" placeholder="请输入账号"/>
				</uni-forms-item>
				<uni-forms-item name="password" required>
					<uni-easyinput prefixIcon="locked" v-model="form.password" type="text" placeholder="请输入密码" />
				</uni-forms-item>
				<uni-forms-item>
					<button style="background-color: #ffd100; border-color: #ffd100; line-height: 70rpx; height: 70rpx ;" @click="login()">登录</button>
				</uni-forms-item>
				<uni-forms-item>
					<view style="text-align: right; ">还没有账号，去<navigator style="display: inline-block; color: dodgerblue;
					margin-left: 4rpx;" url="/pages/register/register">注册</navigator></view>
				</uni-forms-item>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form:{},
				rules: {
					username: {
						rules:[
							{
								required: true,
								errorMessage: '请填写账号',
							},
							{
								minLength: 3,
								maxLength: 5,
								errorMessage: '账号长度在 {minLength} 到 {maxLength} 个字符'
							},
						],
					},
					password: {
						rules:[
							{
								required: true,
								errorMessage: '请填写账号',
							},
							{
								minLength: 3,
								maxLength: 5,
								errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符'
							},
						],
					}
				}
			}
		},
		methods: {
			login(){
				this.$refs.form.validate().then((res)=>{
					console.log(this.form)
					this.$request.post("/login/login",this.form).then(res =>{
						console.log(res)
						if(res.result != null){
							if(res.result.nickname !== "ROLE_USER"){
								uni.showToast({
									icon: 'error',
									title: "无法登录，权限不为用户"
								})
							}else if(res.code == 200){
								uni.showToast({
									icon: 'success',
									title: "登陆成功"
								})
								//存储用户信息到浏览器里面
								uni.setStorageSync("xm-user",res.result)
								uni.switchTab({
									url:'/pages/index/index'
								})
							}else{
								uni.showToast({
									icon: 'error',
									title: res.message
								})
							}
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
				
			},
			
		}
	}
</script>

<style>

</style>
