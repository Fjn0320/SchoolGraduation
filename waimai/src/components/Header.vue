<template>
<!-- 收缩按钮 -->
<div style="line-height: 60px; display: flex">
    <div style="flex:1; font-size: 20px;">
        <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px;" @click="collapse"></span>

       <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
          <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>

      </div>
      <el-dropdown style="width: 150px; cursor: pointer;text-align:right">
        <div style="display:inline-block;">
          <img :src="user.imgurl" alt=""
              style="width: 30px;border-radius:50%; position:relative;top:10px;right:5px  ">
              <span>{{user.uniname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
        </div>
        
        <el-dropdown-menu slot="dropdown" style="text-align:center;width:120px">
          <el-dropdown-item style="font-size: 14px; padding: 5px 0">
            <router-link to="/person">个人信息</router-link>
          </el-dropdown-item>
          <!-- 退出按钮 -->
          <el-dropdown-item @click.native="outlogin">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
</div>

</template>
<script>


export default {
    name:"Header",
    props: {
        collapseBtnClass:String,
        collapse:Function,
        user:Object
    },
    computed: {
      currentPathName() {
        return this.$store.state.currentPathName;//需要监听的数据
      }
    },
    watch: {
      currentPathName (newVal, oldVal) {
        console.log(newVal)
      }
    },
    data(){
      return {
        //获取浏览器存放的信息
        //user: localStorage.getItem("honey-user")?JSON.parse(localStorage.getItem("honey-user")):{},
      }
      
    },
    methods: {
        outlogin(){
          this.$router.push('/login')
          localStorage.removeItem("honey-user")
          localStorage.removeItem("menu")
          this.$message.success("退出成功")
        },
        
    },

}

</script>

<style scoped>

</style>