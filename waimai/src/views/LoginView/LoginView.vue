<template>
    <div style="height: 100vh; display: flex; align-items: center; justify-content: center;background-color:#0f9876">
        <div style="display: flex; background-color: white; width: 50%; border-radius: 5px; overflow: hidden;">
            <div style="flex:1;">
                <img src="@/assets/image.png" alt="" style="width: 100%;">
            </div>
            <!--  
                align-items: center;水平居中
            -->
            <div style="flex: 1;display:flex;align-items: center;justify-content: center;">
                <el-form :model="user" :rules="rules" ref="loginRef" style="width: 80%;" >
                    <div style="font-size: 20px; font-weight: bold; text-align: center;margin-bottom:20px">欢迎登录后台管理系统</div>
                    <el-form-item prop="username">
                        <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" @input="inUserName" v-model="user.username" maxlength="8"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" @input="inPassWord" v-model="user.password" maxlength="8"></el-input>
                    </el-form-item>
                    <el-form-item prop="code">
                        <div style="display: flex;">
                            <el-input prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" placeholder="请输入验证码" v-model="user.code"></el-input>
                            <div style="flex: 1;margin-left:20px;height: 36px">   
                                <ValidCode @input="createValidCode"/>
                            </div>
                        </div>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" style="width: 100%;" @click="login">登录</el-button>
                    </el-form-item>
                    <div style="display: flex;">
                        <div style="flex: 1;">还没账号？请<span style="color: #0f9876;cursor:pointer" @click="$router.push('/register')" >注册</span></div>
                        <div style="flex: 1; text-align:right"><span style="color: #0f9876;cursor:pointer">忘记密码</span></div>
                    </div>
         
                </el-form>
            </div>
        </div>
    </div>
</template>
<script>
import ValidCode from '@/components/ValidCode'
import request from '/src/until/request.js'
import {setRoutes} from '@/router'

    export default {
        name: "LoginView",
        components:{
            ValidCode
        },
        data(){
            //验证码进行验证
            /*
                分析：当验证的输入框的prop的值必须跟我们设置的表单的值一样（必须一样）
            */
            var validateCode = (rule, value, callback) => {
                    if (value === '') {
                        console.log("---"+value);
                        callback(new Error('请输入验证码'))
                    } else if(value.toLowerCase() !== this.code){//如果用户填写的验证码不等于验证码传过来的code
                        console.log("---"+value);
                        callback(new Error('验证码错误'))
                    }else{
                        callback()
                    }
                }
            return {
                user: {
                    username:'',
                    password:'',
                    code: '',//用户输入的code 验证码
                },
                code: '',//是传过来的Code值就是验证码传过来的值，然后进行验证
                rules: {
                    username: [
                        { required: true, message: '请输入账户', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                    ],
                    code: [
                        { validator: validateCode, trigger: 'blur' },
                  
                    ]
                }
            }
        },
        methods:{
            login(){
                //验证验证码
                this.$refs['loginRef'].validate((valid) => {
                    if (valid) {
                    //验证通过
                    request.post("/login/login",this.user).then(res =>{
                        if(res.result.nickname === "ROLE_USER"){
                            this.$router.push('/')
                            this.$message.success("无权登录")
                        }else if(res.code == '200'){
                            this.$router.push('/')
                            this.$message.success("登录成功")
                            localStorage.setItem("honey-user",JSON.stringify(res.result))//存储用户信息到浏览器里面
                            localStorage.setItem("menu",JSON.stringify(res.result.menu))
                            //动态设置当前用户的路由
                            setRoutes()
                        }else{
                            this.$message.success(res.message)
                        }
                        console.log("登陆成功后得到res");
                        console.log(res);
                    })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
               
            },
            createValidCode(data){//这个是设置验证码时获取验证码，并将传过来的验证码进行赋值，之后好验证
                this.code=data.toLowerCase()
                console.log(data);
            },
            // 现在文本校验
            inUserName(data){
                //有缺陷当输入----这个时会显示
                this.user.username = data.replace(/[\W]/g,'')
            },
            inPassWord(data){
                //有缺陷当输入----这个时会显示
                this.user.password = data.replace(/[\W]/g,'')
            }

        }
    }
</script>

<style>

</style>