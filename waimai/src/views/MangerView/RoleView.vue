<template>
    <div>
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
            <el-table-column prop="id" label="序号" >
            </el-table-column>
            <el-table-column prop="name" label="名称" >
            </el-table-column>
            <el-table-column prop="flag" label="标识" >
            </el-table-column>
            <el-table-column prop="description" label="描述" >
            </el-table-column>
            <el-table-column label="操作"  align="center">
              <template slot-scope="scope">
                <el-button type="info"  @click="selectMenu(scope.row.id)">分配菜单<i class="el-icon-menu"></i></el-button>
                <!-- <el-button type="success" @click="userEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                <el-button type="danger" @click="openDeleteUser(scope.row.id)">删除 <i class="el-icon-remove-outline"></i></el-button> -->
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

          <!-- 分别通过default-expanded-keys和default-checked-keys设置默认展开和默认选中的节点。需要注意的是，此时必须设置node-key，其值为节点数据中的一个字段名，该字段在整棵树中是唯一的。 -->
          <el-dialog title="菜单分配" :visible.sync="menuDialogVis" width="30%">
            <el-tree
              ref="tree"
              :props="props"
              node-key="id"
              :data="menData"
              show-checkbox
           
              :default-expanded-keys="expends"
              :default-checked-keys="checks">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ data.id }}{{ data.name }}</span>
         </span>
              >
            </el-tree>
            <div slot="footer" class="dialog-footer">
              <el-button @click="menuDialogVis = false">取 消</el-button>
              <el-button type="primary" @click="saveRolMenu">确 定</el-button>
            </div>
          </el-dialog>
          <!-- 添加用户框 -->
          <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="form" :label-width="formLabelWidth" size="small">

              <el-form-item label="名称"  >
                <el-input v-model="form.name" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item label="标识" >
                <el-input v-model="form.flag" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="描述" >
                <el-input v-model="form.password" autocomplete="off"></el-input>
              </el-form-item>
              

            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="saveUser">确 定</el-button>
            </div>
          </el-dialog>

          <!-- 删除时的对话框 -->
          <!-- <el-dialog
              title="提示"
              :visible.sync="centerDialogVisible"
              width="30%"
              center>
              <span>确定删除该数据？</span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteUser">确 定</el-button>
              </span>
        </el-dialog> -->

        <!-- 多选的时候的对话框 -->
        <!-- <el-dialog
          title="提示"
          :visible.sync="dialogVisibleAll"
          width="30%"
          >
          <span>确定删除数据？</span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisibleAll = false">取 消</el-button>
            <el-button type="primary" @click="deleteAllUer">确 定</el-button>
          </span>
        </el-dialog> -->
    </div>
</template>
<script>
import request from '/src/until/request.js'

    export default {
        name:"RoleView",
        data(){
            return {
                tableData:[], 
                total:0,
                pageNum:1,
                pageSize:5,
                name:"",
                headerStyle: 'headerStyle',//设置表头颜色
                dialogFormVisible:false, //这个是设置对话框是否显示
                form:{ //表单
                    name:"",
                    description:"",
                    flag:"",
                },
                formLabelWidth: '80px',//对话框里表单名称的长度
                ifSaveUpdate: true ,//true是sava方法，false是update方法
                centerDialogVisible:false, //打开删除对话框 默认为false
                deleteId:0,//是传过来的id号，默认为0
                multipleSelection: [],//存放选中的复选框的
                dialogVisibleAll: false,//删除时出现的对话框
                menData:[],
                menuDialogVis:false,
                props:{
                  label: 'name',
                },
                roleId:0,
                expends:[],
                checks:[],
                roleFlag: '',
                ids:[],
                keys:[]//半选和全选的
              }
        },
        created(){
            this.showPageUser()
        },
        methods:{
          
            showPageUser(){
                request.get("/role/getPageAllUser",{
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
                      console.log(this.tableData);
                    }
            
                })
            },
            async selectMenu(roleId){
              this.roleId = roleId
              
              this.menuDialogVis=true

              //请求菜单数据
              request.get("/menu/getAllMenu")
                .then(res => {
                      this.menData = res.result
                    console.log(res);
                    //   // 把后台返回的菜单数据处理成 id数组
                    this.expends = this.menData.map(item => item.id)
                    }
                )

                request.get("/role/roleMenu/" + this.roleId).then(res => {
                  console.log(res);
                    this.checks = res.result
                    console.log("checks:");
                      console.log(this.checks);
                  })


            },
            saveRolMenu(){
              console.log(this.$refs.tree.getHalfCheckedKeys());
              const keys1 = this.$refs.tree.getHalfCheckedKeys()
              const keys2 = this.$refs.tree.getCheckedKeys()
              keys2.forEach(element => {
                keys1.push(element)
              });
              
              console.log(keys1);
             
              request.post("/role/roleMenu/"+this.roleId,keys1).then(res =>{
                if (res.code == 200) {
                  this.$message.success("绑定成功")
                  this.menuDialogVis = false

                  // // 操作管理员角色后需要重新登录
                  // if (this.roleFlag === 'ROLE_ADMIN') {
                  //   this.$store.commit("logout")
                  // }

                } else {
                  this.$message.error(res.message)
                }
              })
            },
            saveUser(){//提交数据用post请求
                if(this.ifSaveUpdate){
                    request.post("/role/roleMenu",this.form).then(res => {
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
                //     request.post("/role/userUpdate",this.form).then(res => {
                //     console.log("提交的数据");
                //     console.log(res);
                //     if(res){
                //     this.$message.success("修改成功")
                //     this.dialogFormVisible = false
                //     this.showPageUser()
                //     }else{
                //     this.$message.success("修改失败")
                //     }
                // })
                }
            },
            deleteUser(){//删除数据
              request.post("/role/userDelete/"+this.deleteId).then(res => {
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
            request.post("/role/userAllDelete",this.multipleSelection).then(res =>{
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