<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 会员等級</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

           
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="levelName" label="等级名称" >
                </el-table-column>
                <el-table-column prop="sort" label="等级权重" >
                </el-table-column>
               
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button class="typeIcon" type="text" icon="el-icon-edit " @click="handleUpdata(scope.row)"></el-button>
                        <el-button class="typeIcon red" type="text" icon="el-icon-delete"  @click="handleDelete(scope.row)"></el-button>
                    </template>
                </el-table-column>
               
               
            </el-table>
            <div style="margin-top: 20px">
        
        <el-button @click="insertVisible=true">添加等级</el-button>
        
      </div>
      
             <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 列表添加、修改弹窗 -->
    <el-dialog width="40%" title="修改等级"  :visible.sync="editVisible">
      <el-form :model="form" ref="form" label-width="120px">
        <el-row>

          <el-col :span="20">
            <el-form-item label="等级名称" prop="vaildDay">
              <el-input v-model="form.levelName" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="20">
            <el-form-item label="等级权重" prop="number">
              <el-input v-model="form.sort" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAttribute">确 定</el-button>
      </div>
    </el-dialog>
        
        <!--新增彈窗-->
        <el-dialog width="40%" title="添加等级"  :visible.sync="insertVisible">
      <el-form :model="insert" ref="insert" label-width="120px">
        <el-row>

          <el-col :span="20">
            <el-form-item label="等级名称" prop="vaildDay">
              <el-input v-model="insert.levelName" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="20">
            <el-form-item label="等级权重" prop="number">
              <el-input v-model="insert.sort" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="insertVisible = false">取 消</el-button>
        <el-button type="primary" @click="insertAttribute">确 定</el-button>
      </div>
    </el-dialog>
            <div style="padding:20px;font-size:16px;color:#666">级别总数:{{total}}</div>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" :page-size="this.pageSize" :pager-count="7" layout="prev, pager, next" :total="this.total">
                </el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    import request from '../../../../utils/request';
    import {axiosRequest} from '../../../../api/index'
    import {pageNum} from '../../../common/pageNum'
    const cityOptions =['账户管理', '角色管理', '商品管理', '财务审批'];
    export default {
        name: 'roletable',
        inject:['reload'],
        data() {
            return {
              editVisible: false,
              delVisible: false,
              insertVisible: false,
                tableData: [],
                pageNum : 1,
                pageSize : 0,
                total : 100,
                form : {
                levelName:'',
                sort:'',},
                delArr:[],
                id : 0,
                insert : {levelName:'',
                sort:'',
                },
               
//              
                
//             
                multipleSelection: [],
            }
        },
        created() {
            this.getLevel();
            this.pageSize = pageNum.getPageSize;
            this.getMemberList();
        },
        computed: {

        },
        methods: {
        	saveAttribute(){
        		this.editVisible = false;
        		var insertFrom = {
        			id : this.id,
                    levelName : this.form.levelName,
                    sort : this.form.sort
                };
                axiosRequest('admin/user/updateUserLevel',insertFrom,'POST').then((res)=>{
                	this.reload();
                    this.$message.success('修改成功！');
                })
               
        	},
        	
            deleteRow(){
                let then=this;
    	     let ids=this.delArr.join(',');
             let obj={'ids':ids};
             axiosRequest('admin/user/deleteUserLevel',obj,'POST').then((res)=>{
             	      this.reload();
                      this.$message.success('刪除成功！');
                      then.delVisible=false;
            });
             },
            //删除单个商品
            handleDelete(row) {
                this.delArr=[];
				this.delArr.push(row.id);
                this.delVisible = true;
            },
            
            handleUpdata(row){
            	let then=this;
            	this.form.levelName = row.levelName;
            	this.form.sort=row.sort;
            	this.id = row.id;
            	this.editVisible = true; 
            },
            //搜索会员列表
            selectMemberList(){
                this.parentId = '';
                this.getMemberList();
            },
           
            //获取会员等级列表
            getMemberList(){
                var selectForm = {
                    pageNum : this.pageNum,
                    pageSize : pageNum.getPageSize,

                };
                axiosRequest('admin/user/getUserLevelList',selectForm,'POST').then((res)=>{
                    this.tableData = res.list;
                    this.total = res.total;
                })
            },
            //获取用户等级
            getLevel(){
                axiosRequest('admin/user/getUserLevelList','','POST').then((res)=>{
                    for(var i=0;i<res.list.length;i++){
                        this.options[i+1] = {
                            value:res.list[i].id,
                            label:res.list[i].levelName
                        };
                    }
                })
            },
            //等级变化
            leverChange(value){
                console.log(value)
                this.levelId=value
                this.getMemberList()
            },
            insertAttribute(){
          	this.insertVisible = false;
//      		var insertFrom = {
//      			id : this.id,
//                  levelName : this.form.levelName,
//                  sort : this.form.sort
//              };
                axiosRequest('admin/user/addUserLevel',this.insert,'POST').then((res)=>{
                    this.reload();
                    this.$message.success('提交成功！');
                })
               
          },
            // 分页导航
            handleCurrentChange(val) {
                this.pageNum = val;
                this.getMemberList();
            },

            handleSelectionChange(val) {
                this.multipleSelection = val;
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
        color: #DCDFE6;
    }
    .qzlabel{
        display: inline-block;
        color: #666;
        margin-right: 15px;
        margin-left: 15px;
    }
    .el-radio{
        margin-right: 10px;
    }
    .handle-select {
        width: 120px;
    }
    .handle-inputSmall{
        width: 100px;
        display: inline-block;
    }
    .handle-input {
        width: 200px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
    .mr10{
        margin-right: 10px;
    }
    .mr5{
        margin-right: 5px;
    }
    .addbtn{
        position: absolute;
        right: 5%;
    }
    .typeIcon{
        font-size: 16px;
        padding: 0 3px;
    }
    .simpleHandle{
        margin-top: 15px;
    }
    .timeChoose{
        display: inline;
        
    }
    .uploadA{

    }
</style>