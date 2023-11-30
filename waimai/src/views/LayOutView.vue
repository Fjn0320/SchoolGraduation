<template>
 
    <el-container style="min-height: 100vh">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246);border-right-color: transparent">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
        </el-aside>

      <el-container>

        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
        </el-header>
  
        <el-main>
          <!-- 表示当前子路由会在此页面显示 -->
          <router-view @refreshUser="getUser"/>
        </el-main>

      </el-container>
    </el-container>

  
</template>

<script>

import Aside from '@/components/Aside.vue'
import Header from '@/components/Header.vue'
import request from '/src/until/request.js'
export default {
  name: 'LayOutView',
  components: {
    Aside,Header
  },
  data(){
    return{
      sideWidth: 200,
      isCollapse: false,
      logoTextShow: true,
      collapseBtnClass: 'el-icon-s-fold',
      user: {},
    }
  },
  created(){//从后台获取最新的数据
    this.getUser()
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser(){
      let username = localStorage.getItem("honey-user")?JSON.parse(localStorage.getItem("honey-user")).username:""
      console.log(username);
      //从后台获取数据
      return (request.get("/user/getUserData/" + username)).then(res => {
        console.log(res);
        this.user = res.result
      })
    }
    
  }
  }

</script>


