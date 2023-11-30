<template>
    <div>
          <div style="margin: 10px 0">
            <el-input v-model="username" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"></el-input>
            <!-- <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>
            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"></el-input> -->
            <el-button @click="selectUser" class="ml-5" type="primary">搜索</el-button>
            <el-button @click="rest" type="warning">重置</el-button>
          </div>
  
          <div style="margin: 10px 0">
            <el-button type="primary" @click="addUser">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger" @click="toggleSelection">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-upload action="http://localhost:8081/wm/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
              <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" style="margin-left: 10px;">导出 <i class="el-icon-top"></i></el-button>
          </div>
          <!-- 
            header-cell-style 设置头部居中
            cell-style 设置单元格内容居中
            header-cell-class-name:设置表头颜色
             :row-key="getRowKeys"//行数据的 Key,能够拿到对应的选中的id并且返回出去
             :reserve-selection="true"//仅对table列有效，类型为 Boolean，为 true 则会在数据更新之后保留之前选中的数据（需指定 row-key）
           -->
          <el-table 
              ref="multipleTable"
              :data="tableData"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
              border stripe 
              :header-cell-class-name="headerStyle"
              @selection-change="handleSelectionChange"
              :row-key="getRowKeys"
              
              >
              <!-- 复选框添加 -->
              <el-table-column
              :reserve-selection="true"
                type="selection"
                width="55">
              </el-table-column>
            <el-table-column prop="id" label="序号" width="50">
            </el-table-column>
            <el-table-column  label="头像" width="100">
              <template  slot-scope="scope">
                <el-image
                v-if="scope.row.imgurl"
                  style="width: 50px; height: 50px"
                  :src="scope.row.imgurl"
                  :error="error(scope.row.imgurl)"
                  ></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="username" label="账号" width="100">
            </el-table-column>
            <el-table-column prop="password" label="密码" width="100">
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="100">
            </el-table-column>
            <el-table-column prop="uniname" label="名字" width="140">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="140">
            </el-table-column>
            <el-table-column prop="phone" label="电话" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址" width="120">
            </el-table-column>
            <el-table-column label="操作"  width="200" align="center">
              <template slot-scope="scope">
                <el-button type="success" @click="userEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                <el-button type="danger" @click="openDeleteUser(scope.row.id)">删除 <i class="el-icon-remove-outline"></i></el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页控件 -->
          <div style="padding: 10px 0; text-align:center">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 15, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>

          <!-- 添加用户框 -->
          <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="form" :label-width="formLabelWidth" size="small">

              <el-upload
                class="avatar-uploader"
                :headers="{token: usertoke.token}"
                action="http://localhost:8081/wm/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <img v-if="form.imgurl" :src="form.imgurl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>


              <el-form-item label="账号" v-model="userlength" >
                <el-input v-model="form.username" autocomplete="off" placeholder="请输入账号，不能输入汉字，最长八位数" maxLength="8" ></el-input>
              </el-form-item>
              <el-form-item label="密码" >
                <el-input v-model="form.password" autocomplete="off" placeholder="请输入密码" maxLength="8"></el-input>
              </el-form-item>
              <el-form-item label="角色">
                <el-select v-model="form.nickname" placeholder="请选择" autocomplete="off" >
                  <el-option
                    v-for="item in roles"
                    :key="item.name"
                    :label="item.name"
                    :value="item.flag">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="名字" >
                <el-input v-model="form.uniname" autocomplete="off" placeholder="请输入名字"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" >
                <el-input v-model="form.email" autocomplete="off" placeholder="请输入邮箱"></el-input>
              </el-form-item>
              <el-form-item label="电话" >
                <el-input v-model="form.phone" autocomplete="off" placeholder="请输入电话"></el-input>
              </el-form-item>
              <el-form-item label="地址" >
                <el-input v-model="form.address" autocomplete="off" placeholder="请输入地址"></el-input>
              </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="saveUser">确 定</el-button>
            </div>
          </el-dialog>

          <!-- 删除时的对话框 -->
          <el-dialog
              title="提示"
              :visible.sync="centerDialogVisible"
              width="30%"
              center>
              <span>确定删除该数据？</span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteUser">确 定</el-button>
              </span>
        </el-dialog>

        <!-- 多选的时候的对话框 -->
        <el-dialog
          title="提示"
          :visible.sync="dialogVisibleAll"
          width="30%"
          >
          <span>确定删除数据？</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisibleAll = false">取 消</el-button>
            <el-button type="primary" @click="deleteAllUer">确 定</el-button>
          </span>
        </el-dialog>

    </div>
</template>
<script>
import request from '/src/until/request.js'

    export default {
        name:"ShowUserView",
        data(){
            return {
                usertoke: localStorage.getItem("honey-user")?JSON.parse(localStorage.getItem("honey-user")):null,
                tableData:[], 
                total:0,
                pageNum:1,
                pageSize:5,
                username:"",
                headerStyle: 'headerStyle',//设置表头颜色
                dialogFormVisible:false, //这个是设置对话框是否显示
                form:{ //表单
                    username:"",
                    password:"",
                    nickname:"",
                    uniname:"",
                    email:"",
                    phone:"",
                    address:"",
                    imgurl:""
                },
                formLabelWidth: '80px',//对话框里表单名称的长度
                ifSaveUpdate: true ,//true是sava方法，false是update方法
                centerDialogVisible:false, //打开删除对话框 默认为false
                deleteId:0,//是传过来的id号，默认为0
                multipleSelection: [],//存放选中的复选框的
                dialogVisibleAll: false,//删除时出现的对话框
                userlength:'',//账号输入的信息
                maxLength:8,//设置输入的长度为多少
                roles:[]//存放
              }
        },
        created(){
            this.showPageUser()
        },
        methods:{
            showPageUser(){
                request.get("/user/getPageAllUser",{
                    params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username
                    }
                })
                .then(res => {
                    console.log("获取的列表");
                    console.log(res);
                    if (res.result==null) {
                      this.$message.error("无数据")
                    }else{
                      this.tableData = res.result.data
                      this.total = res.result.total
                      console.log(this.tableData);
                    }
            
                }),
                request.get("/role/getRole").then(res =>{
                  console.log("获取全部的role");
                  console.log(res);
                  this.roles = res
                })
            },
            error(res){
              console.log(res);
            },
            saveUser(){//提交数据用post请求
                if(this.ifSaveUpdate){
                    request.post("/user/userInsert",this.form).then(res => {
                    console.log("提交的数据");
                    console.log(res);
                    if(res){
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.showPageUser()
                    }else{
                    this.$message.success("保存失败")
                    }
                })
                }else{
                    request.post("/user/userUpdate",this.form).then(res => {
                    console.log("提交的数据");
                    console.log(res);
                    if(res){
                    this.$message.success("修改成功")
                    this.dialogFormVisible = false
                    this.showPageUser()
                    }else{
                    this.$message.success("修改失败")
                    }
                })
                }
            },
            deleteUser(){//删除数据
              request.post("/user/userDelete/"+this.deleteId).then(res => {
                  console.log("删除的数据");
                  console.log(res);
                  if(res){
                    this.$message.success("删除成功")
                    this.centerDialogVisible = false
                    this.showPageUser()
                  }else{
                    this.$message.success("删除失败")
                  }
              })
            },
            openDeleteUser(id){//打开删除对话框
              this.deleteId = id
              this.centerDialogVisible = true
              console.log(this.deleteId);
              console.log(id);
            },
            userEdit(row){//打开编辑
              this.form = row
              this.dialogFormVisible = true
              this.ifSaveUpdate = false
            },
            addUser(){//t添加数据打开对话框表单
            this.dialogFormVisible = true
            this.ifSaveUpdate = true
            this.form = {
                username:"",
                password:"",
                nickname:"",
                uniname:"",
                email:"",
                phone:"",
                address:"",
                imgurl:""
            }
            },
            selectUser(){
              console.log("搜索的名字："+this.username);
              this.showPageUser()
            },
            rest(){//搜索栏重置
              this.username = ''
              this.showPageUser()
            },
            handleSizeChange(pageSize){//传输的页码
            this.pageSize = pageSize
            this.showPageUser()
            },
            handleCurrentChange(pageNum){//传输的数量
            this.pageNum = pageNum
            this.showPageUser()
            },
            handleSelectionChange(val){//当复选框选中时，将选中的进入数组中
            this.multipleSelection = val.map((item) => item.id);;
            console.log(this.multipleSelection);
            },
            toggleSelection(){//打开批量删除的对话框
            this.dialogVisibleAll=true
            },
            deleteAllUer(){//打开批量删除的对话，点击确定按钮
            request.post("/user/userAllDelete",this.multipleSelection).then(res =>{
                console.log("删除的数据");
                console.log(res);
                if(res){
                this.$message.success("批量删除成功")
                this.dialogVisibleAll = false
                this.showPageUser()
                }else{
                this.$message.success("批量删除失败")
                }
            })
            },
            getRowKeys(row) {//这个是在复选时，换到下一页，上一页的所选的数据不丢失
            return row.id;
            //将id return出去
            },
            exp(){//导出文件生成EXCEL文件
              if (this.usertoke) {
                window.open("http://localhost:8081/wm/user/export")
              }
             
            },
            handleExcelImportSuccess() {//导入文件
                this.$message.success("导入成功")
                this.showPageUser()
            },
            handleAvatarSuccess(res) {
              console.log(res);
              this.form.imgurl = res.result
      },
        }
    }
</script>
<style>
.headerStyle {
  background: #ffd04b!important;
}
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