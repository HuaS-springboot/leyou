<template>
	<div class="table">
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-cascades"></i> 素材管理
				</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<div class="handle-box">
				<!-- 搜索导航栏 -->
				<el-input v-model="conditon.content" placeholder="素材内容" class="handle-input mr10"></el-input>

				<el-select v-model="conditon.oneCateId" placeholder="一级分类" class="mr10 handle-select">
					<el-option v-for="item in materialType" :key="item.value" :label="item.key" :value="item.value"></el-option>
				</el-select>
				<el-select v-model="conditon.twoCateId" placeholder="二级分类" class="mr10 handle-select">
					<el-option v-for="item in materialType" :key="item.value" :label="item.key" :value="item.value"></el-option>
				</el-select>
				<el-select v-model="conditon.threeCateId" placeholder="三级分类" class="mr10 handle-select">
					<el-option v-for="item in materialType" :key="item.value" :label="item.key" :value="item.value"></el-option>
				</el-select>
				<el-select v-model="conditon.status" placeholder="状态" class="mr10 handle-select">
					<el-option v-for="item in materialStatus" :key="item.value" :label="item.key" :value="item.value"></el-option>
				</el-select>
				<el-button class="mr10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
				<br />
				<!-- 新增分类-->
				<el-button class="addbtn" type="primary" icon="el-icon-circle-plus-outline" @click="insertMaterial">新增素材</el-button>
			</div>
			<br />
			<el-table :data="tableData.list" border class="table" ref="multipleTable" @select='chStatus'>
			            <el-table-column type="selection" width="55" align="center"></el-table-column>
			            <el-table-column prop="sort"  label="排序权重" width="80"></el-table-column>
			            <el-table-column prop="image"  label="图片">
			            	<template slot-scope="scope">
			            	<div class="tableImg" :style="{backgroundImage: 'url(' + scope.row.image.split(',')[0] + ')'}" @click="handlePictureCardPreview( scope.row.image)"></div>
			            	</template>
			            </el-table-column>
			            
			            <el-table-column prop="title" label="文案"></el-table-column>
			            <el-table-column prop="collectionNum" label="收藏量"></el-table-column>
			            <el-table-column prop="nickName" label="发布人"></el-table-column>
			            <el-table-column prop="status" :formatter="formatter" label="状态">
			            	
			            </el-table-column>
			            
			            
			            <el-table-column label="操作" :formatter="formatter" align="center">
			                <template slot-scope="scope">
			                    <!-- 审批 编辑  删除 -->
			                    <el-button type="status" v-show="scope.row.status=='0'" round size="mini" @click="materialCheck(scope.row)">审核</el-button>
								<el-button type="info" round size="mini" @click="editDetail(scope.row.id)">编辑</el-button>
								<el-button type="info" round size="mini" @click="handleDelete(scope.row)">删除</el-button>
								</template>
			            </el-table-column>
						
			        </el-table>
			
			<div class="simpleHandle">
			        <el-button type="primary" plain @click="batchCheck()">批量审核</el-button>
			        <el-button type="primary" plain @click="changeStatus(-1)">批量删除</el-button>
			    </div>
				
			    <!-- 分页 -->
			<div class="pagination">
			    <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="tableData.total" :page-size='conditon.pageSize'>
			    </el-pagination>
			</div>
		</div>

		
		<!-- 删除提示框 -->
		<el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
		    <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
		    <span slot="footer" class="dialog-footer">
		        <el-button @click="delVisible = false">取 消</el-button>
		        <el-button type="primary" @click="deleteRow">确 定</el-button>
		    </span>
		</el-dialog>
		<!-- 图片放大 -->
		<!-- <el-dialog :visible.sync="dialogVisible">
		    <img width="100%" :src="dialogImageUrl" alt="">
		</el-dialog>-->
	</div> 
</template>

<script>
	import request from "../../../../utils/request";
	import {
		axiosRequest
	} from "@/api/index";
	import {
		pageNum
	} from "../../../common/pageNum";
	import {
		objToArr,
		bus
	} from "@/components/common/common";
	const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
	export default {
		name: "roletable",
		data() {
			return {
				type: "",
				payType: "",
				dateTime: "",
				//当前行的id
				delIds: "",
				// 价格修改
				editForm: {
					id: "",
					price: ""
				},
				// 订单筛选条件
				conditon:{
				    'content':"",
				    'oneCateId':'',
				    'twoCateId':'',
				    'status':'',
				    'pageNum':1,
				    'pageSize':7
				},
				//选中的id集合
				ids: "",
				statusShow:[],
				total: 0,
				materialStatus:[
				  { value: "", key: "全部" },
                  { value: "0", key: "未审核" },
				  { value: "1", key: "已审核" }],
				materialType:[
					{ value: "", key: "全部" },
					{ value: "1", key: "一级分类" },
					{ value: "2", key: "二级分类" },
					{ value: "3", key: "三级分类" }],
				tableData: [],
				cur_page: 1,
				multipleSelection: [],
				select_cate: "",
				select_word: "",
				del_list: [],
				is_search: false,
				editVisible: false,
				delVisible: false,
				stockVisible:false,
				weightVisible:false,
				form: {
					name: "",
					date: "",
					address: ""
				},
				radio: "",

				value: "",
				idx: -1,
				id: -1,
				value2: "",
				checkAll: false,
				cities: cityOptions,
				isIndeterminate: true
			};
		},
		created() {
			this.getMaterialList();
		},
		computed: {},
		methods: {
			//获取订单列表
			getMaterialList() {
				var a=this;
				axiosRequest("material/getMaterialList", this.conditon, "post").then(res => {
					console.log(res)
					this.tableData = res;
				});
			},
			//搜索
			search(a){
			    this.conditon.pageNum=1
			    this.getMaterialList()
			},
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
			materialCheck(row){
				
			},
			// 确定删除
			deleteRow(){
			    let ids=[]
			    this.tableData.list.forEach(row=>{
			        if(row.selectStatus==true){
			            ids.push(row.id)
			        }
			    })
			    this.changStatus({
			       "ids":this.ids,
			       'status':'-1'
			    })
				this.delVisible=false
			    this.$message.success('删除成功');
			    this.getMaterialList()
			},
			//删除单个素材
			handleDelete(row) {
				console.log(row.id)
			    this.ids=row.id
			    this.delVisible = true; //删除提示框 
			},
			chStatus(selection,row){

				row.selectStatus=!row.selectStatus
				console.log(row.selectStatus)
			},
			//对列的格式化操作
			formatter(row, column) {
			  return row.status=='0'?'未审核':'已审核'
			},
			handleCurrentChange(val) {
			     this.conditon.pageNum = val;
			     this.getMaterialList();
			},
			//删除素材函数
			changStatus(query){
				console.log(query)
			    axiosRequest('material/deleteMaterial',query,'post').then((res)=>{
			         this.getMaterialList();
			    })
			},
			 //批量删除
            changeStatus(status){
                let ids=[]
                this.tableData.list.forEach(row=>{
                    if(row.selectStatus==true){
                        ids.push(row.id)
                    }
                })
                if(ids.length==0){
                    this.$message.error('请选择需要操作的素材')
                }else if(ids.length>0){
                    if(status==-1){
                        this.ids=ids.join(',')
                        this.delVisible = true; //删除提示框 
                    }else{
                        this.changStatus({
                            "ids":ids.join(','),
                            'status':status
                        })
                        this.$message.success('操作成功');
                    }
                }

            },
			//添加页面跳转
			insertMaterial(){
				this.$router.push("/addMaterial");
			},
			//修改页面跳转
			editDetail(a){
				console.log(a)
				this.$router.push({
					path:"/updateMaterial",
					query:{
					id:a
				    }
				});
				
			},
			//单个审核
			materialCheck(row){
				
				var params={status:1,id:row.id};
				var arr = new Array();
				arr.push(params);
				axiosRequest('material/updateMaterial',{vsjMaterialRequestList:arr},'post').then((res)=>{
				    this.getMaterialList();
					this.$message.success('操作成功');
				})
		
           },
		   batchCheck(){
			  this.tableData.list.forEach(row=>{
				   console.log(row.status)
				   console.log(row.selectStatus)
			      if(row.selectStatus==true){
			         row.status=1;
					 console.log("====================")
					 console.log(row.status)
			      }
				  console.log(row.status)
			  })
			  axiosRequest('material/updateMaterial',{vsjMaterialRequestList:this.tableData.list},'post').then((res)=>{
			    this.getMaterialList();
			  	this.$message.success('操作成功');
			  })
			  } 
		   }
		
	};
</script>
<style scoped>
	.handle-box {
		margin-bottom: 20px;
		color: #dcdfe6;
	}

	.qzlabel {
		display: inline-block;
		color: #666;
		margin-right: 15px;
		margin-left: 15px;
	}

	.el-radio {
		margin-right: 10px;
	}

	.handle-select {
		width: 120px;
	}

	.handle-inputSmall {
		width: 100px;
		display: inline-block;
	}

	.handle-input {
		width: 200px;
		display: inline-block;
	}

	.del-dialog-cnt {
		font-size: 16px;
		text-align: center;
	}

	.table {
		width: 100%;
		font-size: 14px;
	}

	.red {
		color: #ff0000;
	}

	.mr10 {
		margin-right: 10px;
	}

	.mr5 {
		margin-right: 5px;
	}

	.addbtn {
		position: absolute;
		right: 5%;
	}

	.typeIcon {
		font-size: 16px;
		padding: 0 3px;
	}

	.simpleHandle {
		margin-top: 15px;
	}

	.timeChoose {
		display: inline;
	}

	.orderOperation {
		width: 100px;
		margin-right: 10px;
	}
</style>
