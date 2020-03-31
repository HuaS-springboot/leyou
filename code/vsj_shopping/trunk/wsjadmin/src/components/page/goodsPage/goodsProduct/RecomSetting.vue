<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 推荐商品设置</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div>
                <el-form ref="form" :model="form" label-width="120px" label-position="left">
                    <el-form-item  label="推荐商品图片">

                        <draggable element="li" v-model="list" class="bannerList el-upload-list el-upload-list--picture-card uploadCom" v-show='list.length>0'>
                                <li v-for='(item,index) in list' :key="index" class="el-upload-list__item is-success">
                                    <div data-v-452ce00c="">
                                        <img data-v-452ce00c="" :src="item.picUrl"  alt="" class="el-upload-list__item-thumbnail">
                                        <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                            <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(item)">
                                                <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                            </span>
                                            <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="choose(index)">
                                                <i data-v-452ce00c="" class="el-icon-edit"></i>
                                            </span>
                                            <span data-v-452ce00c="" class="el-upload-list__item-delete" @click='delDefaultLogo(index)'>
                                                <i data-v-452ce00c="" class="el-icon-delete"></i>
                                            </span>
                                            
                                        </span>
                                    </div>
                                </li>
                        </draggable>
                        <el-upload
                            class="uploadCom"
                            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                            ref="uploadLogo"
                            :headers="headers"
                            :multiple='true'
                            list-type="picture-card"
                            :limit="5"
                            :on-exceed="handleExceed"
                            :on-success="logoSuccess">
                            <i slot="default" class="el-icon-plus"></i>
                            <div slot="file" slot-scope="{file}" v-show='false'>
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
                                    class="el-upload-list__item-delete"
                                    @click="logoRemove(file)"
                                    >
                                    <i class="el-icon-delete"></i>
                                    </span>
                                </span>
                            </div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item>
                        <el-button  type="primary" @click="onSubmit">保存设置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
        <!-- 选择商品组件 -->
        <vChoosegood :isSingle=true :chooseIdArr='chooseId' v-if='editVisible' @saveChoose='saveChoose' @closeChoose='closeChoose'></vChoosegood>
    </div>
</template>
<script>
    import "@/assets/css/upload.css";
    import draggable from 'vuedraggable'
    import {axiosRequest} from '../../../../api/index'
    import vChoosegood from "@/components/common/chooseGood"
    export default {
        name: 'baseform',
        components: {
            //调用组件
            draggable,vChoosegood
        },
        data: function(){
            return {
                headers:{
                    "token":localStorage.getItem('token'),
                    "userId":localStorage.getItem("userId"),
                    "platformCode":this.$store.state.platformCode
                }, 
                shopping_logo:'',
                list:[],
                originList:[],
                form:{},
                dialogImageUrl:'',
                dialogVisible: false,
                editVisible:false,
                chooseId:[],
                editIndex:'',
            }
        },
        created(){
            this.getBannerList()
        },
        methods: {
            //获取轮播图
            getBannerList(){
                axiosRequest('product/getProcuctImageList',{
                    status:"1",
                    type:"1"
                },'post').then((res)=>{
                    this.list=res.list
                    this.originList=JSON.parse(JSON.stringify(res.list))
                })
            },
            //文件上传
            logoSuccess(file) {
                if(this.list.length>5){
                    this.$message.warning(`轮播图最多上传五张`);
                }else{
                    this.list.push({
                        "id":'',
                        "productId":'',
                        "picDesc":"",
                        "picUrl":file.data.url,
                        'isMaster':'1',
                        'picOrder':'',
                        'picStatus':'1'
                    })
                }
                console.log(file.data.url)
            },
            handleExceed(files, fileList) {
                this.$message.warning(`轮播图最多上传五张`);
            },
            //删除logo默认图片
            delDefaultLogo(index){
                this.list.splice(index,1)
                this.$refs.uploadLogo.clearFiles();
                this.shopping_logo=""
            },
            //图片放大
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
            onSubmit(){
                if(this.originList.length==0){
                    axiosRequest('product/insertProductImage',{
                        imgRequestList:this.list
                    },'post').then((res)=>{
                        this.$message.success('保存成功')
                    })
                }else{
                    if(this.list.length==0){
                        axiosRequest('product/updateProductImage',{
                            imgRequestList:this.list,
                            type:'1'
                        },'post').then((res)=>{
                            this.$message.success('保存成功')
                        })
                    }else{
                        axiosRequest('product/updateProductImage',{
                            imgRequestList:this.list
                        },'post').then((res)=>{
                            this.$message.success('保存成功')
                        })    
                    }
                }
            },
            //选择商品
            choose(index){
                this.editVisible=true
                this.chooseId[0]=this.list[index].productId
                this.editIndex=index
            },
            closeChoose(){
                this.editVisible=false
            },
            saveChoose(value){
                this.editVisible=false
                this.list[this.editIndex].productId=value[0]
            },
        },
        

    }
</script>
<style >
.uploadCom>ul{
    display: none;
}
li{
    list-style: none
}
</style>
