<template>
    <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #669fef">
      <div style="display: flex; background-color: white; width: 30%; border-radius: 5px; overflow: hidden;background-image:linear-gradient(to bottom right, #FC466B , #3F5EFB);">
        <div style="flex: 1; display: flex; align-items: center; justify-content: center">
          <el-form :model="userForm" style="width: 50%" :rules="rules" ref="registerRef">
            <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">欢迎注册后台管理系统</div>

            <el-form-item prop="username">
              <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" @input="inName" v-model="userForm.username"  maxlength="8"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="userForm.password" @input="inPass" maxlength="8"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPass">
              <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请确认密码" v-model="userForm.confirmPass"></el-input>
            </el-form-item>
            <el-form-item prop="nickname" >
                <el-select v-model="userForm.nickname" placeholder="请选择" prefix-icon="el-icon-edit" >
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item prop="uniname">
              <el-input prefix-icon="el-icon-message" size="medium" placeholder="请输入名字" v-model="userForm.uniname"></el-input>
            </el-form-item>
            <el-form-item prop="email">
                <el-input prefix-icon="el-icon-message" size="medium" placeholder="请输入邮箱" v-model="userForm.email"></el-input>
              </el-form-item>
              <el-form-item prop="phone">
                <el-input prefix-icon="el-icon-phone" size="medium"  placeholder="请输入电话" v-model="userForm.phone"></el-input>
              </el-form-item>
              <el-form-item prop="address">
                <el-input prefix-icon="el-icon-location" size="medium"  placeholder="请确认地址" v-model="userForm.address"></el-input>
              </el-form-item>
            <el-form-item>
              <el-button type="primary" style="width: 100%" @click="register">注 册</el-button>
            </el-form-item>
            <div style="display: flex">
              <div style="flex: 1">已经有账号了？请 <span style="color: #6e77f2; cursor: pointer" @click="$router.push('/login')">登录</span></div>
            </div>
          </el-form>
        </div>
      </div>
  
    </div>
  </template>
<script>
import ValidCode from '@/components/ValidCode'
import request from '/src/until/request.js'

    export default {
        name: "RegisterView",
        components:{
            ValidCode
        },
        data(){
           // 验证码校验
            const validatePassword = (rule, confirmPass, callback) => {
              if (confirmPass === '') {
                callback(new Error('请确认密码'))
              } else if (confirmPass !== this.userForm.password) {
                callback(new Error('两次输入的密码不一致'))
              } else {
                callback()
              }
            };
            return {
                userForm: {
                    username:'',
                    password:'',
                    confirmPass:'',
                    nickname: '',
                    uniname:'',
                    email:'',
                    phone:'',
                    address: '',
                    imgurl:''
                },
 
                options: [{//当管理者添加选项的时候出现  ---为实现功能，当为超级管理员是会出现三个选项，普通管理者只能出现两个 商家和用户
                    value: '管理员',
                    label: '管理员'
                  }, {
                    value: '商家',
                    label: '商家'
                  }],
                rules: {
                    username: [
                        { required: true, message: '请输入账户', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                    ],
                    nickname: [
                        { required: true, message: '请选择', trigger: 'blur' },
                    ],
                    uniname: [
                        { required: true, message: '请输入名字', trigger: 'blur'},
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur'},
                    ],
                    phone: [
                        { required: true, message: '请输入电话', trigger: 'blur'},
                    ],
                    address: [
                        { required: true, message: '请输入地址', trigger: 'blur'},
                    ],
                    confirmPass: [
                      { validator: validatePassword, trigger: 'blur' }
                    ],
                }
            }
        },
        methods:{
            register(){
              this.$refs['registerRef'].validate((valid) => {
                    if (valid) {
                      // 验证通过
                      console.log(this.userForm);
                      request.post('/login/register', this.userForm).then(res => {
                        console.log(res);
                        if (res.code == '200') {
                          this.$router.push('/login')
                          this.$message.success('注册成功')
                        } else {
                          this.$message.error(res.message)
                        }
                      })
                    }
                  })
                },
            // 现在文本校验
            inName(data){
                //有缺陷当输入----这个时会显示
                this.userForm.username = data.replace(/[\W]/g,'')
            },
            inPass(data){
                //有缺陷当输入----这个时会显示
                this.userForm.password = data.replace(/[\W]/g,'')
            }
          }
        }

</script>

<style>

</style>