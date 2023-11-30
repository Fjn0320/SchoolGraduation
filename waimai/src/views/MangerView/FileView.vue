<template>
    <div>

        <!-- <div style="margin: 10px 0">
            <el-input v-model="name" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"></el-input>
            <el-button @click="selectUser" class="ml-5" type="primary">搜索</el-button>
            <el-button @click="rest" type="warning">重置</el-button>
          </div> -->
  
          <div style="margin: 10px 0">
            <el-upload 
            :headers="{token: user.token}"
            action="http://localhost:8081/wm/file/upload" 
            list-type="picture"
            :file-list="fileList"
            :on-success="handleFileImportSuccess" 

            >
                <el-button type="primary" class="ml-5">上传文件<i style="margin-left:5px" class="el-icon-top"></i></el-button>
            </el-upload>
            <!-- <el-button type="danger" @click="toggleSelection">批量删除 <i class="el-icon-remove-outline"></i></el-button> -->
          </div>

          <!-- <div>

            <el-upload 
            :headers="{token: user.token}"
            action="http://localhost:8081/wm/file/d" 
            list-type="picture"
            :file-list="fileList"
            :on-success="handleFileImportSuccess" 

            >
                <el-button type="primary" class="ml-5">上传文件<i style="margin-left:5px" class="el-icon-top"></i></el-button>
            </el-upload>
          </div> -->




        <!-- <el-table 
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
            <el-table-column prop="id" label="ID" width="50">
            </el-table-column>
            <el-table-column prop="name" label="文件名称" width="140">
            </el-table-column>
            <el-table-column prop="type" label="文件类型" width="140">
            </el-table-column>
            <el-table-column prop="size" label="文件大小(kb)" width="140">
            </el-table-column>
            <el-table-column label="下载" width="140">
                <template slot-scope="scope">
                  <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column label="启用" width="140">
                <template slot-scope="scope">
                  <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
                </template>
              </el-table-column>
            <el-table-column label="操作"  width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" @click="openDeleteFile(scope.row.id)">删除 <i class="el-icon-remove-outline"></i></el-button> 
                </template>
            </el-table-column>
        </el-table> -->

        <!-- 分页控件 -->
        <!-- <div style="padding: 10px 0; text-align:center">
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
            :visible.sync="isOpenDelete"
            width="30%"
            center>
            <span>确定删除该数据？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="isOpenDelete = false">取 消</el-button>
                <el-button type="primary" @click="deleteFile">确 定</el-button>
            </span>
        </el-dialog>

            <el-dialog
            title="提示"
            :visible.sync="dialogVisibleAll"
            width="30%"
            >
            <span>确定删除数据？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteAllUer">确 定</el-button>
            </span>
            </el-dialog> -->

    </div>
              

</template>
<script>
import request from '/src/until/request.js'

    export default {
        name:"FileView",
        data(){
            return{




                user:JSON.parse(localStorage.getItem('honey-user') || '{}'),
                fileList:[],




                tableData:[],
                multipleSelection:[],//存放选中的复选框的
                total:0,//获取的总条数
                pageNum:1,//传递的是第几页
                pageSize:5,//传递的是多少
                name:"",//传的是文件名
                headerStyle: 'headerStyle',//设置表头颜色
                deleteId:0,//获取删除的id，默认为0
                isOpenDelete:false,//是否打开删除框
                dialogVisibleAll:false,//是否打开批量删除框
            }
        },
        created(){
            // this.showPageFile()
        },
        methods: {
            handleFileImportSuccess(row, file, fileList){//新增文件
                this.$message.success("新增成功")
                console.log(row, file, fileList)
                this.fileList = fileList
                // row.imgurl = file.response.result
                // console.log(row)
                // this.showPageFile()
            },
   
            showPageFile(){//显示所有数据
                request.get("/file/getPageAllFile",{
                    params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name
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
                    }
                })
            },
            openDeleteFile(id){//打开删除框,并将选取的id号传给deleteId
                this.deleteId = id
                this.isOpenDelete = true
                console.log("获取要删除的id:" + this.deleteId);
            },
            deleteFile(){//删除文件
                request.post("/file/deleteFile/"+this.deleteId).then(res => {
                    console.log("删除的数据");
                    console.log(res);
                    if(res.code == 200){
                        this.$message.success("删除成功")
                        this.isOpenDelete = false
                        this.showPageFile()
                    }else{
                        this.$message.success("删除失败")
                    }
                })
            },
            download(url){//进行下载
                window.open(url)
            },
            changeEnable(row) {//进行选择是否启动enable
                request.post("/file/update", row).then(res => {
                    console.log(res);
                    if (res.code == 200) {
                        this.$message.success("操作成功")
                        this.showPageFile()
                    }
                })
            },
            toggleSelection(){//打开批量删除框
                this.dialogVisibleAll=true
            },
            handleSelectionChange(val){//当复选框选中时，将选中的进入数组中
                this.multipleSelection = val.map((item) => item.id);;
                console.log(this.multipleSelection);
            },
            deleteAllUer(){//打开批量删除的对话，点击确定按钮
                request.post("/file/fileAllDelete",this.multipleSelection).then(res =>{
                    console.log("删除的数据");
                    console.log(res);
                    if(res){
                        this.$message.success("批量删除成功")
                        this.dialogVisibleAll = false
                        this.showPageFile()
                    }else{
                        this.$message.success("批量删除失败")
                    }
                })
            },
            handleSizeChange(pageSize){//传输的页码
                this.pageSize = pageSize
                this.showPageFile()
            },
            handleCurrentChange(pageNum){//传输的数量
                this.pageNum = pageNum
                this.showPageFile()
            },
            getRowKeys(row) {//这个是在复选时，换到下一页，上一页的所选的数据不丢失
                return row.id;
                //将id return出去
            },
            
            selectUser(){
                console.log("搜索的名字："+this.name);
                this.showPageFile()
            },
            rest(){//搜索栏重置
                this.name = ''
                this.showPageFile()
            },
            
        }
    }
</script>
<style>
.headerStyle {
    background: #ffd04b!important;
  }
</style>