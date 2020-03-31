<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 会员列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">
                <el-input v-model="nickName" placeholder="会员名称" class="handle-input mr10" ></el-input>
               <!-- <el-input v-model="orderNO" placeholder="订单号" class="handle-input mr10" ></el-input>-->
                <el-input v-model="telPhone" placeholder="手机号" class="handle-input mr10" ></el-input>
               <!--  <el-input v-model="select_word" placeholder="openid" class="handle-input mr10" ></el-input> -->
                <el-select v-model="levelId" placeholder="等级" class="mr10 handle-select" @change="leverChange" :clearable="true">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <el-button class="mr10" type="primary" icon="el-icon-search" @click="selectMemberList">搜索</el-button><br/>
                <br/>
                
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
              
               <el-table-column prop="headUrl" label="用户头像" > 
               	<template slot-scope='scope'> 
	               <!--<div class="sub-title"></div>
	               <div class="demo-basic--circle">
			        <div class="block"><el-avatar shape="square" :size="100" :src="scope.row.headUrl" v-if='scope.row.headUrl'></el-avatar></div>
			      </div>-->
			      <img :src="scope.row.headUrl" alt="" class="tableimg"/>
			      </template>
			   </el-table-column>
 
                <el-table-column prop="nickName" label="姓名" >
                </el-table-column>
                <el-table-column prop="telPhone" label="手机号码">
                </el-table-column>
                <el-table-column prop="joinTime" label="加入时间">
                </el-table-column>
                <el-table-column prop="levelName" label="会员等级">
                </el-table-column>
                <el-table-column prop="expiredTime" label="会员过期时间" >
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button class="typeIcon" type="text" icon="el-icon-edit" @click="handleUpdata(scope.row)">编辑</el-button>
                        <!--<el-button class="typeIcon red" type="text" icon="el-icon-delete"  @click="handleDelete(scope.row)"></el-button>-->
                    </template>
                </el-table-column>
               
              
            </el-table>
            <!-- 列表添加、修改弹窗 -->
    <el-dialog width="40%" title="修改会员信息"  :visible.sync="UpdataVisible">
      <el-form :model="form" ref="form" label-width="120px">

        <!--<el-row>

          <el-col :span="20">-->
            <el-form-item label="头像" prop="vaildDay">
              <!--el-input v-model="form.headUrl" auto-complete="off"></el-input>-->
              <el-upload
				  class="uploadCom"
				  action="http://172.16.0.233:8888/api/v1/upload/uploadFile"
				  :show-file-list="false"
				  :headers="headers"
				  :on-success="handleAvatarSuccess"
				  :before-upload="beforeAvatarUpload">
				  <img v-if="form.headUrl" :src="form.headUrl" class="avatar">
				  <i v-else class="el-icon-plus avatar-uploader-icon" id="iconStyle"></i>
				</el-upload>
            </el-form-item>

            

          <!--<el-col :span="20">-->
            <el-form-item label="姓名" prop="number">
              <el-input v-model="form.nickName" auto-complete="off"></el-input>
            </el-form-item>
          <!--</el-col>
          <el-col :span="20">-->
            <el-form-item label="手机号码" prop="number">
              <el-input v-model="form.telPhone" auto-complete="off"></el-input>
            </el-form-item>
          <!--</el-col>
          <el-col :span="20">-->
            <el-form-item label="加入时间" prop="number">
              <el-input v-model="form.joinTime" auto-complete="off"></el-input>
            </el-form-item>
          <!--</el-col>-->
          <el-form-item label="会员等级">
        <el-select v-model="form.levelId" placeholder="请选择会员等级">
         <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
        </el-select>
        </el-form-item>
          <!--<el-col :span="20">-->
            <el-form-item label="会员过期时间" prop="number">
              <el-input v-model="form.expiredTime" auto-complete="off"></el-input>
            </el-form-item>
          <!--</el-col>-->
       <!-- </el-row>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAttribute">确 定</el-button>
      </div>
    </el-dialog>

        
            <div style="padding:20px;font-size:16px;color:#666">会员总数:{{total}}</div>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" :page-size="this.pageSize" :pager-count="7" layout="prev, pager, next" :total="this.total">
                </el-pagination>
            </div>
        </div>
<!-- 图片放大 -->
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
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
            	
              dialogVisible: false,
              UpdataVisible: false,
              dialogImageUrl:'',
              delIds:'',
              
                tableData: [ ],
                pageNum : 1,
                pageSize : 0,
                total : 100,
                nickName:'',
                telPhone:'',
                levelId:'',
                options: [
                    { 
                        value:'',
                        label:'全部'
                    }
                ],
                form :{
                nickName:'',
                telPhone:'',
                levelId:'',
                headUrl:'',
                joinTime:'',
                expiredTime:''
                },
                id:'',
                multipleSelection: [],
                headers:{
                    "token":localStorage.getItem('token'),
                    "userId":localStorage.getItem("userId"),
                    "platformCode":this.$store.state.platformCode
                }
            }
        },
        created() {
            this.getLevel();
            this.pageSize = pageNum.getPageSize;
            this.getMemberList();
            this.getLevel();
        },
        computed: {

        },
        methods: {
        	
        	
        	handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
        	
        	
        	
            deleteRow(){
                let ids=[]
                this.tableData.list.forEach(row=>{
                    if(row.selectStatus==true){
                        ids.push(row.productId)
                    }
                })
                this.changStatus({
                   "ids":this.delIds,
                    'status':'-1'
                })
                this.$message.success('删除成功');

            },
            //删除单个商品
            handleDelete(row) {
                this.delIds=row.id;
                this.delVisible = true; //删除提示框 
            },
            //搜索会员列表
            selectMemberList(){
                this.parentId = '';
                this.getMemberList();
            },
           
            //获取会员列表
            getMemberList(){
                var selectForm = {
                    pageNum : this.pageNum,
                    pageSize : pageNum.getPageSize,
                    nickName : this.nickName,
//                  orderNo : this.orderNO,
                    telPhone : this.telPhone,
                    levelId : this.levelId,
//                  startTime : this.queryTime[0],
//                  endTime : this.queryTime[1],
//                  parentId : this.parentId
                };
                axiosRequest('admin/user/getUserList',selectForm,'POST').then((res)=>{
                	console.log(res.list);
                    this.tableData = res.list;
                    this.total = res.total;
                    
                })
            },
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
            handleUpdata(row){
            	let then=this;
            	this.form.headUrl = row.headUrl;
            	this.form.nickName = row.nickName;
            	this.form.telPhone = row.telPhone;
            	this.form.joinTime = row.joinTime;
            	this.form.expiredTime= row.expiredTime;
            	this.id = row.id;
            	this.UpdataVisible = true; 
            },
            // 分页导航
            handleCurrentChange(val) {
                this.pageNum = val;
                this.getMemberList();
            },
           saveAttribute(){
        		this.UpdataVisible = false;
        		var updataFrom = {
        			id : this.id,
                    headUrl : this.form.headUrl,
                    nickName : this.form.nickName,
                    telPhone : this.form.telPhone,
                    joinTime : this.form.joinTime,
                    expiredTime : this.form.expiredTime,
                    levelId : this.form.levelId,
                    
                };
                axiosRequest('admin/user/updateUser',updataFrom,'POST').then((res)=>{
                	this.reload();
                    this.$message.success('修改成功！');
                })
               
        	},
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleAvatarSuccess(res, file) {
               this.form.headUrl = res.data.url;
             },
              beforeAvatarUpload(file) {
            
             const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return  isLt2M;
      }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
        color: #DCDFE6;
    }
    /*#iconStyle{
    	margin-top: 100px;
    }*/
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
    width: 128px;
    height: 128px;
    line-height: 128px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .tableimg{
  	width: 60px;
  	height: 60px;
  }
</style>
