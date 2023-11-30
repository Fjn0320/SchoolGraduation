<template>
     <div>
        <div style="margin: 10px 0">
            <el-input v-model="shopname" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"></el-input>
            <el-button @click="selectUser" class="ml-5" type="primary">搜索</el-button>
            <el-button @click="rest" type="warning">重置</el-button>
          </div>
    
          <div style="margin: 10px 0">
            <el-button type="primary" @click="addShop">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <!-- <el-button type="danger" @click="toggleSelection">批量删除 <i class="el-icon-remove-outline"></i></el-button> -->
          </div>
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
        
              <el-table-column
              :reserve-selection="true"
                type="selection"
                width="55">
              </el-table-column>
            <el-table-column prop="id" label="序号" >
            </el-table-column>
            <el-table-column  label="图像">
              <template  slot-scope="scope">
                <el-image
                v-if="scope.row.shopurl"
                  style="width: 50px; height: 50px"
                  :src="scope.row.shopurl"
                  :error="error(scope.row.shopurl)"
                  ></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="shopname" label="商品名字" >
            </el-table-column>
            <el-table-column prop="shopprice" label="商品价格" >
            </el-table-column>
            <el-table-column prop="shopping" label="店名" >
            </el-table-column>
            <el-table-column prop="shopsort" label="分类" >
            </el-table-column>
            <el-table-column label="操作"  width="200" align="center">
              <template slot-scope="scope">
                 <el-button type="success" @click="shopEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
               <el-button type="danger" @click="openDeleteShop(scope.row.id)">删除 <i class="el-icon-remove-outline"></i></el-button>
              </template>
            </el-table-column>
          </el-table>

      
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

         
          <el-dialog title="菜品信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="form"  size="small">

              <el-upload
                class="avatar-uploader"
                :headers="{token: usertoke.token}"
                action="http://localhost:8081/wm/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <img v-if="form.shopurl" :src="form.shopurl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>


              <el-form-item label="商品名字" >
                <el-input v-model="form.shopname" autocomplete="off"  ></el-input>
              </el-form-item>
              <el-form-item label="商品价格" >
                <el-input v-model="form.shopprice" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item label="店名" >
                <el-input v-model="form.shopping" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item label="分类" >
                <el-input v-model="form.shopsort" autocomplete="off" ></el-input>
              </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="saveShop">确 定</el-button>
            </div>
          </el-dialog>

          <el-dialog
              title="提示"
              :visible.sync="centerDialogVisible"
              width="30%"
              center>
              <span>确定删除该数据？</span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteShop">确 定</el-button>
              </span>
        </el-dialog>

   
       <!--  <el-dialog
          title="提示"
          :visible.sync="dialogVisibleAll"
          width="30%"
          >
          <span>确定删除数据？</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisibleAll = false">取 消</el-button>
            <el-button type="primary" @click="deleteAllShop">确 定</el-button>
          </span>
        </el-dialog> -->
    </div>  


</template>
<script>
import request from '/src/until/request.js'

export default {
        name:"ShopView",
        data() {
            return{
                usertoke: localStorage.getItem("honey-user")?JSON.parse(localStorage.getItem("honey-user")):null,
                username:localStorage.getItem("honey-user")?JSON.parse(localStorage.getItem("honey-user")).username:"",
                shopname:"",
                tableData:[], 
                total:0,
                pageNum:1,
                pageSize:5,
                headerStyle: 'headerStyle',//设置表头颜色
                multipleSelection: [],//存放选中的复选框的
                centerDialogVisible:false, //打开删除对话框 默认为false
                dialogFormVisible:false,//这个是设置对话框是否显示
                ifSaveUpdate: true ,//true是sava方法，false是update方法
                form:{
                  username:this.username
                },
                deleteId:0
            }
        },
        created(){
          this.showShop()
         
        },
        methods: {
          showShop(){
            console.log(this.usertoke);
            request.get("/shop/getPageAllShop",{
              params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    shopname:this.shopname,
                    }
            }).then(res => {
              console.log("获取的列表");
                    console.log(res);
                    if (res.result==null) {
                      this.$message.error("无数据")
                    }else{
                      this.tableData = res.result.data
                      this.total = res.result.total
                      console.log(this.tableData);
                    }
            })
          },
          addShop(){
            this.dialogFormVisible = true
            this.ifSaveUpdate = true
            this.form = {
              username:this.username
            }
          },
          saveShop(){
            if(this.ifSaveUpdate){
                    console.log(this.form);
                    request.post("/shop/insertShop",this.form).then(res => {
                    console.log("提交的数据");
                    console.log(res);
                    if(res){
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.showShop()
                    }else{
                    this.$message.success("保存失败")
                    }
                })
                 }
                 else{
                    request.post("/shop/shopUpdate",this.form).then(res => {
                    console.log("提交的数据");
                    console.log(res);
                    if(res){
                    this.$message.success("修改成功")
                    this.dialogFormVisible = false
                    this.showShop()
                    }else{
                    this.$message.success("修改失败")
                    }
                })
                }
          },
          openDeleteShop(id){//打开删除对话框
              this.deleteId = id
              this.centerDialogVisible = true
              console.log(this.deleteId);
              console.log(id);
            },
          deleteShop(){
            request.post("/shop/shopDelete/"+this.deleteId).then(res => {
                  console.log("删除的数据");
                  console.log(res);
                  if(res){
                    this.$message.success("删除成功")
                    this.centerDialogVisible = false
                    this.showShop()
                  }else{
                    this.$message.success("删除失败")
                  }
            })
          },
          shopEdit(row){//打开编辑
              this.form = row
              this.dialogFormVisible = true
              this.ifSaveUpdate = false
            },
          handleSizeChange(pageSize){//传输的页码
            this.pageSize = pageSize
            this.showShop()
          },
          handleCurrentChange(pageNum){//传输的数量
            this.pageNum = pageNum
            this.showShop()
          },
          selectUser(){
              console.log("搜索的名字："+this.shopname);
              this.showShop()
            },
          rest(){//搜索栏重置
              this.shopname = ''
              this.showShop()
          },
          handleSelectionChange(val){//当复选框选中时，将选中的进入数组中
            this.multipleSelection = val.map((item) => item.id);;
            console.log(this.multipleSelection);
            },
            getRowKeys(row) {//这个是在复选时，换到下一页，上一页的所选的数据不丢失
            return row.id;
            //将id return出去
            },
          selectUser(){
            console.log("搜索的名字："+this.username);
            this.showShop()
          },
          handleAvatarSuccess(res) {
              console.log(res);
              this.form.shopurl = res.result
          },
          error(res){
              console.log(res);
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