<template>
    <div class="table">
        
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 分类管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button class="addbtn" type="primary" icon="el-icon-circle-plus-outline" @click="addEdit()">新增分类</el-button>
            </div>
            <el-table :data="typeList" border class="table" ref="multipleTable">
                
                <el-table-column prop="categoryName" label="分类名称">
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
                    <el-input v-model="addType.categoryName" class="mr10"></el-input>
                    <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addDomain">新增二级分类</el-button>
                </el-form-item>
                <el-form-item label="分类权重">
                    <el-input v-model="addType.categorySort"></el-input>
                </el-form-item>
                <el-form-item label="分类状态">
                    <el-radio-group v-model="addType.categoryStatus">
                        <el-radio :label="2">首页显示</el-radio>
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="0">关闭</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="分类图标">
                    <el-upload
                    class="uploadCom"
                    action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                    ref="upload"
                    :headers="headers"
                    list-type="picture-card"
                    :on-exceed="handleExceed"
                    :on-success="uploadSuccess"
                    :limit="1"
                        >
                        <i slot="default" class="el-icon-plus"></i>
                        <div slot="file" slot-scope="{file}">
                            <img
                                class="el-upload-list__item-thumbnail"
                                :src="file.url" alt=""
                            >
                            <span class="el-upload-list__item-actions">
                                <span
                                class="el-upload-list__item-preview"
                                @click="handlePictureCardPreview(file.url)"
                                >
                                <i class="el-icon-zoom-in"></i>
                                </span>
                                <span
                                v-if="!disabled"
                                class="el-upload-list__item-delete"
                                @click="uploadRemove(file)"
                                >
                                <i class="el-icon-delete"></i>
                                </span>
                            </span>
                        </div>
                    </el-upload>
                </el-form-item>
                <!-- 循环二级分类 -->
                <el-form-item v-for="(domain, index) in addType.children" :label="'二级分类' + index" :key="domain.key">
                    <div>
                        <el-input v-model="domain.categoryName" class="mr10 mb10"></el-input>
                    <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addDomainThird(index)" class="mb10">新增三级分类</el-button>
                    <el-button @click.prevent="removeDomain(domain)">删除</el-button>
                    </div>
                    <!-- 循环三级分类 -->
                    <el-form-item v-for="(domain2, index2) in addType.children[index].children" :label="'三级分类' + index2" :key="domain2.key">
                        <el-input v-model="domain2.categoryName" class="mr10"></el-input>
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
                    <el-input v-model="editType.categoryName" class="mr10" @input="firstChange"></el-input>
                    <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addEditDomain">新增二级分类</el-button>
                </el-form-item>
                <el-form-item label="分类权重">
                    <el-input v-model="editType.categorySort" @input="firstChange"></el-input>
                </el-form-item>
                <el-form-item label="分类状态">
                    <el-radio-group v-model="editType.categoryStatus" @input="firstChange">
                        <el-radio :label="2">首页显示</el-radio>
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="0">关闭</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="分类图标">
                    <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="editIcon!=''">
                        <div tabindex="0" class="el-upload-list__item is-success">
                            <div data-v-452ce00c="">
                                <img data-v-452ce00c="" :src="editType.ico" alt="" class="el-upload-list__item-thumbnail">
                                <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                    <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(editType.ico)">
                                        <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                    </span>
                                    <span data-v-452ce00c="" class="el-upload-list__item-delete" @click='delDefaultLoad'>
                                        <i data-v-452ce00c="" class="el-icon-delete"></i>
                                    </span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <el-upload
                    class="uploadCom"
                    action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                    ref="upload"
                    :headers="headers"
                    list-type="picture-card"
                    :on-exceed="handleExceed"
                    :on-success="uploadEditSuccess"
                    :limit="1"
                        >
                        <i slot="default" class="el-icon-plus"></i>
                        <div slot="file" slot-scope="{file}">
                            <img
                                class="el-upload-list__item-thumbnail"
                                :src="file.url" alt=""
                            >
                            <span class="el-upload-list__item-actions">
                                <span
                                class="el-upload-list__item-preview"
                                @click="handlePictureCardPreview(file.url)"
                                >
                                <i class="el-icon-zoom-in"></i>
                                </span>
                                <span
                                v-if="!disabled"
                                class="el-upload-list__item-delete"
                                @click="uploadRemove(file)"
                                >
                                <i class="el-icon-delete"></i>
                                </span>
                            </span>
                        </div>
                    </el-upload>
                </el-form-item>
                <!-- 循环二级分类 -->
                <el-form-item v-for="(domain, index) in editType.children" :label="'二级分类' + index" :key="domain.key" v-show="domain.typeNum!=-1">
                    <div>
                        <el-input v-model="domain.categoryName" class="mr10 mb10" @input="secNameChange(domain,index)"></el-input>
                        <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addEditDomainThird(index)" class="mb10">新增三级分类</el-button>
                        <el-button @click.prevent="removeEditDomain(domain)">删除</el-button>
                    </div>
                    <!-- 循环三级分类 -->
                    <el-form-item v-for="(domain2, index2) in editType.children[index].children" :label="'三级分类' + index2" :key="domain2.key" v-show="domain2.typeNum!=-1">
                        <el-input v-model="domain2.categoryName" class="mr10" @input="thiNameChange(domain2,index2,index)"></el-input>
                        <el-button @click.prevent="removeEditDomainThird(domain2,index2,index)">删除</el-button>
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
                //新增商品分类
                addType:{
                    categoryName:'',
                    categorySort:'',
                    ico:'',
                    categoryStatus:1,
                    children:[]
                },
                //编辑商品分类
                editType:{
                    
                },
                editIcon:'',
                typeList:[],
                addVisible: false,
                editVisible: false,
                delId:'',
                delIndex:'',
                delVisible: false,
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
                axiosRequest('product/InsertProductCategory',this.addType,'post').then((res)=>{
                    this.reload()
                    this.addVisible = false
                    this.$message.success(`添加成功`);
                })
            },
            //保存编辑内容
            editTypeBt(){
                console.log(this.editType)
                axiosRequest('product/updateProductCategory',this.editType,'post').then((res)=>{
                    this.$message.success('修改成功')
                    this.editVisible=false
                })
            },
            //查询分类列表
            getTypeList(){
                axiosRequest('product/getProductCategoryList','','post').then((res)=>{
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
                    categoryName: '',
                    children:[]
                });
            },
            addDomainThird(index){
                this.addType.children[index].children.push({
                    categoryName: ''
                });
            },
            removeDomain(item) { 
                var index = this.addType.children.indexOf(item)
                if (index !== -1) {
                this.addType.children.splice(index, 1)
                }
            },
            removeDomainThird(item,num2,num) {
                var index = this.addType.children[num].children.indexOf(item)
                if (index !== -1) {
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
            removeEditDomain(item) { 
                 var index = this.editType.children.indexOf(item)
                if (index !== -1) {
                    if(item.id){
                        this.editType.children[index].typeNum='-1'
                    }
                    //this.editType.children.splice(index, 1)
                }
            },
            removeEditDomainThird(item,num2,num) {
                var index = this.editType.children[num].children.indexOf(item)
                if (index !== -1) {
                    if(item.id){
                        this.editType.children[num].children[index].typeNum='-1'
                    }
                    //this.editType.children[num].children.splice(index, 1)                      
                }

            },       
            // 添加分类按钮
            addEdit(){
                //this.$router.push('/typeedit')
                this.addVisible=true
            },
            // 列表编辑按钮
            handleEdit(index, row) {
                this.editType=row
                this.editType.typeNum=0
                this.editIcon=row.ico
                this.editVisible = true; 
            },
            handleDelete(index,parameter) {
                this.delIndex=index
                this.delId=parameter
                this.delVisible = true;
               
            },
            // 确定删除
            deleteRow(){
                axiosRequest('product/deleteProductCategory',{
                    'id':this.delId
                },'post').then((res)=>{
                    this.$message.success('删除成功');
                    this.delVisible = false;
                    this.typeList.splice(this.delIndex,1)
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
        width: 100%;
        font-size: 14px;
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
