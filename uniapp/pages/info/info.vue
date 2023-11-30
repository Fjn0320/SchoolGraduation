<template>
	<view style="padding: 20rpx;">
			<view class="box">
				<uni-forms ref="form" :rules="rules" :modelValue="form" label-width="140rpx" label-align="righe">
					<uni-forms-item label="头像" name="imgurl">
						<uni-file-picker  image-styles="imageStyles" del-icon="false" :disable-preview="true" 
							  v-model="imgurl" limit="1" file-mediatype="image" @select="handleAvatarUploadSuccess"></uni-file-picker>
					</uni-forms-item>
					
					<uni-forms-item label="账号" name="username"  >
						<uni-easyinput  v-model="form.username" disable />
					</uni-forms-item>
					<uni-forms-item label="密码" name="password" >
						<uni-easyinput  v-model="form.password"  />
					</uni-forms-item>
					<uni-forms-item label="邮箱" name="email">
						<uni-easyinput  v-model="form.email"  />
					</uni-forms-item>
					<uni-forms-item label="电话" name="phone">
						<uni-easyinput  v-model="form.phone"  />
					</uni-forms-item>
					<uni-forms-item label="地址" name="address">
						<uni-easyinput  v-model="form.address"  />
					</uni-forms-item>
					<uni-forms-item label="昵称" name="uniname">
						<uni-easyinput  v-model="form.uniname"  />
					</uni-forms-item>
					
					<uni-forms-item >
						<button type="primary" size="mini" @click="save">保存</button>
					</uni-forms-item>
				</uni-forms>
			</view>
		</view>
</template>

<script>
	export default {
		data() {
			return {
				id:uni.getStorageSync("xm-user").id,
				imgurl:[],
				form:{
					
				},
				user:uni.getStorageSync("xm-user"),
				imageStyles:{
					width:80,
					height:80,
					border:{
						color:"#eee",
						width:"1px",
						style:'solid',
						radius:'50%'
					}
				},
			}
		},
		onLoad() {
			this.form = JSON.parse(JSON.stringify(this.user)),
			this.imgurl =[{url:this.form.imgurl}] 
		},
		methods: {
			save(){
				this.$request.post('/user/userUpdate',this.form).then(res => {
					if(res.code === 200){
						uni.showToast({
							icon:"success",
							title:"修改成功"
						})
						uni.setStorageSync("xm-user",this.form)
					}else{
						uni.showToast({
							icon:"error",
							title:res.message
						})
					}
				})
			},
			handleAvatarUploadSuccess(e){
				console.log(e)
				let _this = this
				const filePath = e.tempFilePaths[0]
				uni.uploadFile({
					url:_this.$baseUrl + '/file/upload',
					filePath:filePath,
					name:"file",
					success(res) {
						let url = JSON.parse(res.data || '{}').result
						console.log(url)
						_this.form.imgurl = url
					}
				})
			}
		}
	}
</script>

<style>

</style>
