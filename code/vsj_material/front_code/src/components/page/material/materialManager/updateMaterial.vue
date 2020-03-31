<template>
	<div>
		<div class="container">
			<div class="form-box"  >
				<el-form ref="mallsetting" :model="mallsetting" label-width="160px" label-position="left">
		
					<div class="crumbs">
						<el-breadcrumb separator="/">
							<el-breadcrumb-item><i class="el-icon-lx-calendar"></i>新增素材</el-breadcrumb-item>
						</el-breadcrumb>
					</div>
		           <br />
				    <br />
					<div class="center">
						<el-form-item label="排序权重" prop="sort">
						    <el-input v-model="mallsetting.material.sort"></el-input>
						</el-form-item>
										
						<el-form-item label="文案标题" prop="title">
							<el-input v-model="mallsetting.material.title"></el-input>
						</el-form-item>
										
						<el-form-item label="状态">
							<el-select v-model="mallsetting.material.status" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.key" :value="item.value" :label="item.key">
								</el-option>
							</el-select>
						</el-form-item>
		
						<el-form-item label="资源类型">
							<div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="shopping_logo!=''">
								<div tabindex="0" class="el-upload-list__item is-success">
									<div data-v-452ce00c="">
										<img data-v-452ce00c="" :src="mallsetting.material.image" alt="" class="el-upload-list__item-thumbnail">
										<span data-v-452ce00c="" class="el-upload-list__item-actions">
											<span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(mallsetting.shopping_logo)">
												<i data-v-452ce00c="" class="el-icon-zoom-in"></i>
											</span>
											<span data-v-452ce00c="" class="el-upload-list__item-delete" @click='delDefaultLogo'>
												<i data-v-452ce00c="" class="el-icon-delete"></i>
											</span>
										</span>
									</div>
								</div>
							</div>
							<el-upload
							class="uploadCom"
							action="http://172.16.0.225:8888/api/v1/admin/systeme"
							ref="uploadLogo"
							:headers="headers"
							list-type="picture-card"
							:on-exceed="handleExceed"
							:on-success="logoSuccess"
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
										@click="logoRemove(file)"
										>
										<i class="el-icon-delete"></i>
										</span>
									</span>
								</div>
							</el-upload>
							
						</el-form-item>
				
				
						<el-form-item label="文案内容">
							<el-input
							  type="textarea"
							  :autosize="{ minRows: 2, maxRows: 4}"
							  placeholder="请输入内容"
							  v-model="mallsetting.material.content">
							</el-input>
						</el-form-item>
										
						<el-form-item label="下载量" prop="downloadNum">
							<el-input v-model="mallsetting.material.downloadNum"></el-input>
						</el-form-item>
						<el-form-item label="收藏量" prop="collectionNum">
						    <el-input v-model="mallsetting.material.collectionNum"></el-input>
						</el-form-item>
						<el-form-item label="分类" >
							<el-select v-model="mallsetting.material.oneCateId" placeholder="请选择" class="typewidth" :change="oneselect(mallsetting.material.oneCateId)">
								<el-option v-for="item in oneType" :key="item.cateName" :value="item.id" :label="item.cateName">
								</el-option>
							</el-select>
							<el-select v-model="mallsetting.material.twoCateId" placeholder="请选择" class="typewidth" :change="twoselect(mallsetting.material.twoCateId)">
								<el-option v-for="item in twoType" :key="item.cateName" :value="item.id" :label="item.cateName">
								</el-option>
							</el-select>
							
							<el-select v-model="mallsetting.material.threeCateId" placeholder="请选择" class="typewidth">
								<el-option v-for="item in threeType" :key="item.cateName" :value="item.id" :label="item.cateName">
							</el-option>
							</el-select>
						</el-form-item>
		
						<el-form-item>
							<el-button type="primary" @click="onSubmit">保存</el-button>
							<el-button @click="returnMaterial">取消</el-button>
						</el-form-item>
		
					</div>
		
				</el-form>
			</div>
		</div>
	</div>
</template>

<script>
	import { axiosRequest } from '@/api/index';
	import { arrToObj,objToArr } from '@/components/common/common';
	import {rules} from '../../../common/rule';
	import "@/assets/css/upload.css";
	export default {
	    name: 'baseform',
	    data: function(){
	        return {
	            //配置，默认启动页关闭，分类导航开启
	            mallsetting:{
	                "loading_page_switch":0,
					'material':{
						title:"",
						image:"",
						content:"",
						sort:'',
						collectionNum:'',
						downloadNum:'',
						sysUsrId:'',
						status:'',
						oneCateId:'',
						twoCateId:'',
						threeCateId:'', 
						id:''
						
						
					}
	
	            },
	            expressApi:[{value:1,key:'已审核'},{value:0,key:'未审核'}],
				oneType:[],
				twoType:[],
				threeType:[],
				allType:[],
	            loading_page_path:'',
	            shopping_logo:'',
	            headers:{
	                "token":localStorage.getItem('token'),
	                "userId":localStorage.getItem("userId"),
	                "platformCode":this.$store.state.platformCode
	            }, 
	            rules:rules.mallSetting,
	            dialogImageUrl:'',
	            dialogVisible: false,
	            disabled: false,
	            
	
	        }
	    },
	    created(){
	     this.getMaterialType()
		 this.mallsetting.material.id=this.$route.query.id;
		 console.log(this.$route.query.id)
		 this.getMaterialDetail(this.$route.query.id)
	    },
	    methods: {
	        //获取等级分类
	       getMaterialType(){
	            axiosRequest('admin/materialCategory/findMaterialCategory',{
	            },'post').then((res)=>{
	              this.allType=res;
				  if(this.allType!=null){
					   this.oneType=this.allType
				  }

	            })
	            
	        },
			getMaterialDetail(id){
				console.log(id)
				axiosRequest("material/getMaterialList", {id:id}, "post").then(res => {
					console.log(res)
					this.mallsetting.material = res.list[0];
					
				});
			},
	        handleExceed(files, fileList) {
	            this.$message.warning(`图片最多上传一张`);
	        },
	        //保存修改信息
	        onSubmit() { 

	            this.$refs.mallsetting.validate((valid) => {
	                if (valid) {
						this.mallsetting.material.sysUsrId=window.localStorage.getItem("userId")
						var arr = new Array();
						arr.push(this.mallsetting.material)
						console.log(arr)
	                    axiosRequest('material/updateMaterial',{vsjMaterialRequestList:arr},'post').then((res)=>{
							this.$router.push('/materiallist')
	                        this.$message.success('保存成功！');
							
	                    })        
	                    
	                } else {
	                    console.log('error submit!!');
	                    return false;
	                }
	            });
	        },
			//二级分类提示下拉框
			oneselect(oneCateId){
				for(var a in this.allType){
              	if(this.allType[a].id==oneCateId){
				this.twoType=this.allType[a].children;
				
				}
				 }
			},
			//三级分类下拉框
			twoselect(twoCateId){
				for(var a in this.twoType){
				if(this.twoType[a].id==twoCateId){	
				this.threeType=this.twoType[a].children;
					}
				 }
			},
			returnMaterial(){
				this.$router.push('/materiallist')
				},
	        // loding图上传成功
	        loadingSuccess(file){
	            this.loading_page_path=""
	            this.mallsetting.loading_page_path=file.data.url
	        },
	        //文件上传
	        logoSuccess(file) {
	            this.mallsetting.shopping_logo=file.data.url
	        },
	        //删除load图
	        loadingRemove(file) {
	            this.$refs.uploadLoad.clearFiles();
	            this.mallsetting.loading_page_path=""
	        },
	        //删除logo图
	        logoRemove(file) {
	            this.$refs.uploadLogo.clearFiles();
	            this.mallsetting.shopping_logo=""
	        },
	        //删除load默认图片
	        delDefaultLoad(){
	            this.loading_page_path=""
	        },
	        //删除logo默认图片
	        delDefaultLogo(){
	            this.shopping_logo=""
	        },
	        //图片放大
	        handlePictureCardPreview(file) {
	            this.dialogImageUrl = file;
	            this.dialogVisible = true;
	        },

	    },
	    
	
	}
</script>

<style scoped>
	
	.mallSetting{
	    width: 100%;
	}
	.mallSetting .el-input{
	    width: 60%;
	}
	.typewidth{
		
		width: 120px;
		margin: 10px;
	}
</style>
