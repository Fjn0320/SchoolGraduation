<template>
	<view style="padding: 40rpx;">
		<view style="padding: 20rpx; margin: 50rpx 0; background-color:  #fff; box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);border-radius: 20rpx;">
			<view style="margin: 30rpx; font-size: 40rpx;">注册页面</view>
			<uni-forms ref="form" :modelValue="form" :rules="rules" validate-trigger="blur">
				<uni-forms-item name="username" required >
					<uni-easyinput prefixIcon="person" v-model="form.username" type="text" placeholder="请输入账号"/>
				</uni-forms-item>
				<uni-forms-item name="password" required>
					<uni-easyinput prefixIcon="locked" v-model="form.password" type="text" placeholder="请输入密码" />
				</uni-forms-item>
				<uni-forms-item>
					<button style="background-color: #ffd100; border-color: #ffd100; line-height: 70rpx; height: 70rpx ;" @click="register()">注册</button>
				</uni-forms-item>
				<uni-forms-item>
					<view style="text-align: right; ">已有账号，去<navigator style="display: inline-block; color: dodgerblue;
					margin-left: 4rpx;" url="/pages/login/login">登录</navigator></view>
				</uni-forms-item>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form:{
					nickname:"ROLE_USER"
				},
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
			register(){
				this.$refs.form.validate().then((res)=>{
					this.$request.post("/login/register",this.form).then(res =>{
						if(res.code === '200'){
							uni.showToast({
								icon: 'success',
								title: "注册成功"
							})
							//存储用户信息到浏览器里面
							setTimeout(()=>{
								uni.navigateTo({
									url:'/pages/login/login'
								},500)
							})
							
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
