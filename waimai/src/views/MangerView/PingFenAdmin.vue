<template>
    <div>
       <div style="margin: 10px 0">
           <el-input v-model="shopname" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"></el-input>
           <el-button @click="selectShop" class="ml-5" type="primary">搜索</el-button>
           <el-button @click="rest" type="warning">重置</el-button>
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
           <el-table-column prop="comment" label="评论" >
           </el-table-column>
           <el-table-column prop="goname" label="下单人" >
           </el-table-column>
           <el-table-column prop="shopname" label="商店" >
           </el-table-column>
           <el-table-column label="操作"  width="200" align="center">
             <template slot-scope="scope">
                <el-button type="danger" @click="openDeletePing(scope.row.id)">删除 <i class="el-icon-remove-outline"></i></el-button>
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

         <el-dialog
             title="提示"
             :visible.sync="centerDialogVisible"
             width="30%"
             center>
             <span>确定删除该数据？</span>
             <span slot="footer" class="dialog-footer">
               <el-button @click="centerDialogVisible = false">取 消</el-button>
               <el-button type="primary" @click="deletePing">确 定</el-button>
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
       name:"PingFenAdmin",
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
               
               deleteId:0
           }
       },
       created(){
         this.showPingFen()
        
       },
       methods: {
        showPingFen(){
           console.log(this.usertoke);
           request.get("/ping/getPageAllPing",{
             params: {
                   pageNum: this.pageNum,
                   pageSize: this.pageSize,
                   shopname:this.shopname
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
         openDeletePing(id){//打开删除对话框
             this.deleteId = id
             this.centerDialogVisible = true
             console.log(this.deleteId);
             console.log(id);
           },
           deletePing(){
           request.post("/ping/pingDelete/"+this.deleteId).then(res => {
                 console.log("删除的数据");
                 console.log(res);
                 if(res){
                   this.$message.success("删除成功")
                   this.centerDialogVisible = false
                   this.showPingFen()
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
           this.showPingFen()
         },
         handleCurrentChange(pageNum){//传输的数量
           this.pageNum = pageNum
           this.showPingFen()
         },
         selectShop(){
             console.log("搜索的名字："+this.shopname);
             this.showPingFen()
           },
         rest(){//搜索栏重置
             this.shopname = ''
             this.showPingFen()
         },
         handleSelectionChange(val){//当复选框选中时，将选中的进入数组中
           this.multipleSelection = val.map((item) => item.id);;
           console.log(this.multipleSelection);
           },
           getRowKeys(row) {//这个是在复选时，换到下一页，上一页的所选的数据不丢失
           return row.id;
           //将id return出去
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