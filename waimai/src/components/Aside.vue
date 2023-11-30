<template>
    <!-- 左边菜单 -->
        <!-- 
          active-text-color：激活菜单文字的颜色
          collapse-transition:菜单栏的折叠效果
          collapse：是否水平折叠收起菜单


         -->
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden;"
                
                 background-color="rgb(48, 65, 86)"
                 text-color="#fff"
                 active-text-color="#ffd04b"
                 :collapse-transition="false"
                 :collapse="isCollapse"
                 router
                 @select="handleSelect"
        >
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/icon.jpg" alt="" style="width: 40px; position: relative; top: 11px;margin-right: 10px;margin-top: 5px;margin-left: 10px;">
            <b style="color: white;"  v-show="logoTextShow">暮光后台管理系统</b>
          </div>
          <div v-for="item in menu" :key="item.id">
            <div v-if="item.path">
              <el-menu-item :index="item.path">
                <template slot="title">
                  <i class="el-icon-house"></i>
                  <span slot="title">{{item.name}}</span>
                </template>
              </el-menu-item>
            </div>

            <div v-else>
              <el-submenu :index="item.id+''">
                <template slot="title">
                  <i class="el-icon-menu"></i>
                  <span slot="title">{{item.name}}</span>
                </template>
              
              <div v-for="subItem in item.children" :key="subItem.id">
                <el-menu-item :index="subItem.path">
                  <template >
                    <i class="el-icon-house"></i>
                    <span slot="title">{{subItem.name}}</span>
                  </template>
                </el-menu-item>
              </div>
            </el-submenu>
            </div>

          </div>
          
          <!-- <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">系统管理</span>
            </template>
            <el-menu-item index="/user">
              <i class="el-icon-s-custom"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
            <el-menu-item index="/role">
              <i class="el-icon-s-custom"></i>
              <span slot="title">角色管理</span>
            </el-menu-item>
            <el-menu-item index="/file">
              <i class="el-icon-document"></i>
              <span slot="title">文件管理</span>
            </el-menu-item>
            <el-menu-item index="/menu">
              <i class="el-icon-document"></i>
              <span slot="title">菜单管理</span>
            </el-menu-item>
          </el-submenu> -->
    </el-menu>
</template>
<script>
export default {
    name:"Aside",
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean,
    },
    methods: {
      handleSelect(index){
        // console.log(index);
        // console.log(this.$router.options.routes);
      }
    },
    data(){
      return{
        menu:localStorage.getItem("menu")?JSON.parse(localStorage.getItem("menu")):[]
      }
    },
}

</script>

<style scoped>

</style>