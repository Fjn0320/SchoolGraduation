<template>
    <div>
        <el-card style="width: 500px;">
            
            <el-descriptions  class="margin-top" title="个人信息" :column="1"  border>
              <template slot="extra">
                <el-button type="primary" size="small" @click="openUpdateInfo">操作</el-button>
              </template>
      
              <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-s-custom"></i>
                    头像
                  </template>
                  <img :src="tableForm.imgurl" class="avatar">
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user"></i>
                  账号
                </template>
                  {{ tableForm.username }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-mobile-phone"></i>
                  昵称
                </template>
                {{ tableForm.nickname }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-location-outline"></i>
                  名字
                </template>
                {{ tableForm.uniname }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-tickets"></i>
                  电话
                </template>
                {{ tableForm.phone }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-office-building"></i>
                  联系地址
                </template>
                {{ tableForm.address }}
              </el-descriptions-item>
              <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-office-building"></i>
                    邮箱
                  </template>
                  {{ tableForm.email }}
                </el-descriptions-item>
            </el-descriptions>
          </el-card>


          <!-- 更改用户信息 -->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form label-width="80px" size="small">
              
            <el-upload
                class="avatar-uploader"
                :headers="{token: user.token}"
                action="http://localhost:8081/wm/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <img v-if="form.imgurl" :src="form.imgurl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
      
            <el-form-item label="账号">
              <el-input v-model="form.username" autocomplete="off" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
              <el-select v-model="form.nickname" placeholder="请选择" autocomplete="off" >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="名字">
              <el-input v-model="form.uniname" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="电话">
              <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址">
              <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>

    </div>

    
  </template>
  
  <script>
import request from '/src/until/request.js'
  export default {
    name: "Person",
    data() {
      return {
        form: {},
        tableForm:[],
        dialogFormVisible:false,
        user: localStorage.getItem("honey-user") ? JSON.parse(localStorage.getItem("honey-user")) : {},
        options: [{
                    value: '商家',
                    label: '商家'
                  }],
      }
    },
    created() {
      this.getUser().then(res => {
        console.log(res)
        this.tableForm = res
        this.form = res
        console.log("this.tableForm:");
        console.log(this.tableForm);
        console.log("this.form:");
        console.log(this.form);
      })
    },
    methods: {
      async getUser() {
        return (await request.get("/user/getUserData/" + this.user.username)).result
      },
      save() {
        console.log(this.form);
        request.post("/user/userUpdate", this.form).then(res => {
          if (res.code == 200) {
            this.$message.success("保存成功")
            //触发父级更新User
            this.$emit("refreshUser")
            this.dialogFormVisible=false
          } else {
            this.$message.error("保存失败")
          }
        })
      },
      handleAvatarSuccess(res) {
        console.log(res);
        this.form.imgurl = res.result
      },
      openUpdateInfo(){//打开更改信息栏
        this.dialogFormVisible = true
      }
    }
  }
  </script>
  
  <style>
  .avatar-uploader {
    text-align: center;
    padding-bottom: 10px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 138px;
    height: 138px;
    line-height: 138px;
    text-align: center;
  }
  .avatar {
    width: 138px;
    height: 138px;
    display: block;
  }
  </style>