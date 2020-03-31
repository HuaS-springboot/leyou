<template>
	<div class="container">
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 分类管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button class="addbtn"  type="primary" icon="el-icon-circle-plus-outline" @click="addEdit()">新增分类</el-button>
            </div>
            <el-table :data="typeList" border class="table" ref="multipleTable">
                
                <el-table-column prop="cateName" class='typewidth' label="分类名称">
                </el-table-column>
                
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button class="typeIcon" type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)"></el-button>
                        <el-button class="typeIcon red" type="text" icon="el-icon-delete"  @click="handleDelete(scope.$index,scope.row.id)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 新增弹出框 -->
        <el-dialog title="编辑" :visible.sync="addVisible" width="40%" class="typeEdit" >
            <el-form ref="addType" :model="addType" label-width="120px" label-position="left">
                <el-form-item label="分类名称">
                    <el-input v-model="addType.cateName" class="mr10"></el-input>
                    <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addDomain">新增二级分类</el-button>
                </el-form-item>
                
                <!-- 循环二级分类 -->
                <el-form-item v-for="(domain, index) in addType.children" :label="'二级分类' + index" :key="domain.key">
                    <div>
                        <el-input v-model="domain.cateName" class="mr10 mb10"></el-input>
                    <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addDomainThird(index)" class="mb10">新增三级分类</el-button>
                    <el-button @click.prevent="removeDomain(domain)">删除</el-button>
                    </div>
                    <!-- 循环三级分类 -->
                    <el-form-item v-for="(domain2, index2) in addType.children[index].children" :label="'三级分类' + index2" :key="domain2.key">
                        <el-input v-model="domain2.cateName" class="mr10"></el-input>
                        <el-button @click.prevent="removeDomainThird(domain2,index2,index)">删除</el-button>
                    </el-form-item>
                    
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="addTypeEdit">确 定</el-button>
            </span>
        </el-dialog>
        
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%" class="typeEdit" >
            <el-form ref="editType" :model="editType" label-width="120px" label-position="left">
                <el-form-item label="分类名称">
                    <el-input v-model="editType.cateName" class="mr10" @input="firstChange"></el-input>
                    <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addEditDomain">新增二级分类</el-button>
                </el-form-item>
                
                <!-- 循环二级分类 -->
                <el-form-item v-for="(domain, index) in editType.children" :label="'二级分类' + index" :key="domain.key" v-if="domain.typeNum!=-1">
                    <div>
                        <el-input v-model="domain.cateName" class="mr10 mb10" @input="secNameChange(domain,index)"></el-input>
                        <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addEditDomainThird(index)" class="mb10">新增三级分类</el-button>
                        <el-button @click="removeEditDomain(domain,index)">删除</el-button>
                    </div>
                    <!-- 循环三级分类 -->
                    <el-form-item v-for="(domain2, index2) in editType.children[index].children" :label="'三级分类' + index2" :key="domain2.key" v-if="domain2.typeNum!=-1">
                        <el-input v-model="domain2.cateName" class="mr10" @input="thiNameChange(domain2,index2,index)"></el-input>
                        <el-button @click="removeEditDomainThird(domain2,index2,index)">删除</el-button>
                    </el-form-item>
                    
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="editTypeBt">确 定</el-button>
            </span>
        </el-dialog>
        
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>


        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>

        
     </div>
    </div>
</template>

<script>
    import request from '../../../../utils/request';
    import {axiosRequest} from '@/api/index'
    import {getArrDifference} from '../../../common/common'
    import {pageNum} from '../../../common/pageNum'
    import "@/assets/css/upload.css";
    export default {
        name: 'roletable',
        inject:['reload'],
        data(){
            return {
                headers:{
                    "token":localStorage.getItem('token'),
                    "userId":localStorage.getItem("userId"),
                    "platformCode":this.$store.state.platformCode
                },
                sys_share_pic:'',
                dialogImageUrl:'',
                dialogVisible: false,
                disabled: false,
                //新增素材分类
                addType:{
                    cateName:'',
                    sort:'',
                    categoryStatus:1,
                    children:[]
                },
                //编辑素材分类
                editType:{
                    
                },
                editIcon:'',
                typeList:[],
                addVisible: false,
                editVisible: false,
                delId:'',
                delIndex:'',
                delVisible: false,
				delIds:[]
            }
        },
        created() {
            this.getTypeList()        
        },
        watch:{
           

        },
        computed: {
            
        },
        methods: {
            // 添加新分类图片上传成功
            uploadSuccess(file){
                this.addType.ico=file.data.url
            },
            //编辑图片上传成功
            uploadEditSuccess(file){
                this.editIcon=""
                this.editType.ico=file.data.url  
                this.editType.typeNum=1
            },
            //删除新分类图片图片
            uploadRemove(file) {
                this.$refs.upload.clearFiles();
            },
            //删除编辑默认图片
            delDefaultLoad(){
                this.editIcon=""
            },
            // 保存新增分类
            addTypeEdit() {
                axiosRequest('admin/materialCategory/insertMaterialCategory',this.addType,'post').then((res)=>{
                    this.reload()
                    this.addVisible = false
                    this.$message.success(`添加成功`);
                })
            },
            //保存编辑内容
            editTypeBt(){
				if(this.delIds.length>0){
					for(var a=0;a<this.delIds.length;a++){
						var param={id:this.delIds[a]}
						axiosRequest('admin/materialCategory/deleteMaterialCategory',param,'post').then((res)=>{
						})
					}
				}
                axiosRequest('admin/materialCategory/editMaterialCategroy',this.editType,'post').then((res)=>{
					this.reload()
                    this.$message.success('修改成功')
                    this.editVisible=false
                })
            },
            //查询分类列表
            getTypeList(){
                axiosRequest('admin/materialCategory/findMaterialCategory','','post').then((res)=>{
                    this.typeList=res
                })
            },
            filterTag(value, row) {
                return row.categoryStatus === value;
            },
            //图片放大
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
            handleExceed(files, fileList) {
                this.$message.warning(`LOGO图片最多上传一张`);
            },
            //修改添加二三级分类
            addDomain() {
                this.addType.children.push({
                    cateName: '',
                    children:[]
                });
            },
            addDomainThird(index){
                this.addType.children[index].children.push({
                    cateName: ''
                });
            },
            removeDomain(item,index) { 
                var index = this.addType.children.indexOf(item)
                if (index != -1) {
                this.addType.children.splice(index, 1)
				
                }
            },
            removeDomainThird(item,num2,num) {
                var index = this.addType.children[num].children.indexOf(item)
                if (index != -1) {
                    this.addType.children[num].children.splice(index, 1)
                }
            },
            //编辑二三级分类
            addEditDomain() {
                this.editType.children.push({
                    children:[]
                });

            },
            addEditDomainThird(index){
                this.editType.children[index].children.push({
                    
                });
           
            },
            removeEditDomain(item,index) { 
                var index = this.editType.children.indexOf(item)
                if (index !=-1) {
                    if(item.id){
						this.delIds.push(item.id)
                        this.editType.children[index].typeNum=-1
                    }
                  this.editType.children.splice(index, 1)
                }
				
            },
            removeEditDomainThird(item,num2,num) {
                var index = this.editType.children[num].children.indexOf(item)
                if (index !== -1) {
                    if(item.id){
						this.delIds.push(item.id)
                        this.editType.children[num].children[index].typeNum=-1
                    }
                   this.editType.children[num].children.splice(index, 1)                     
                }

            },       
            // 添加分类按钮
            addEdit(){
                this.addVisible=true
            },
            // 列表编辑按钮
            handleEdit(index, row) {
                this.editType=row
                this.editType.typeNum=0
                this.editVisible = true; 
				
            },
            handleDelete(index,parameter) {
                this.delIndex=index
                this.delId=parameter
                this.delVisible = true;
               
            },
            // 确定删除
            deleteRow(){
                axiosRequest('admin/materialCategory/deleteMaterialCategory',{
                    'id':this.delId
                },'post').then((res)=>{
                    this.$message.success('删除成功');
                    this.typeList.splice(this.delIndex,1)
					this.delVisible = false;
                })
            },

            //编辑各个部分的监听函数
            firstChange(){
                this.editType.typeNum=1
            },

            secNameChange(item,index){
                if(item.id){
                    this.editType.children[index].typeNum="1"
                }else{//新增
                    this.editType.children[index].typeNum="2"
                } 
                this.editType.children[index].categoryName=item.categoryName
            },
            thiNameChange(item,num2,num){
                if(item.id){
                    this.editType.children[num].children[num2].typeNum="1"
                }else{//新增
                    this.editType.children[num].children[num2].typeNum="2"
                } 
                this.editType.children[num].children[num2].categoryName=item.categoryName
            },






            // 分类显示在首页加星
            typeStar(){
                
            },
            
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
        }
    }

</script>
<style >
    .mb10{
        margin-bottom: 10px;
    }
    .handle-box {
        margin-bottom: 20px;
		float: right;
		margin-right:100px ;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 90%;
        font-size: 14px;
        margin-left: 40px;
    }
   
    .red{
        color: #ff0000;
    }
    .mr10{
        margin-right: 10px;
    }
    .addbtn{
        position: relative;
    }
    .typeIcon{
        font-size: 16px;
        padding: 0 3px;
    }
    .typeEdit .el-input{
        width: 50%;
    }
    .tableImg{
        width: 50px;
        height: 50px;
        background-size: cover;
        border-radius: 6px;
    }
</style>
