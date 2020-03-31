<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 商品列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">
                <el-input v-model="condition.title" placeholder="请输入要查询的商品名称" class="handle-input mr10 mb10" ></el-input>
                <el-input v-model="condition.priceMin" placeholder="价格区间" class="handle-inputSmall mr5 mb10" ></el-input>—
                <el-input v-model="condition.priceMax" placeholder="价格区间" class="handle-inputSmall mr10 mb10" ></el-input>
                <el-button class="mr10 mb10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                <el-cascader class="mr10 mb10" :options="typeList" :props="{ checkStrictly: true }" clearable placeholder="商品分类" @change="typeChange" ></el-cascader>

                <!-- 上架状态-->
                <el-select v-model="value" placeholder="上架状态" class="mr10 handle-select mb10" @change='stateChange'>
                    <el-option
                    v-for="item in type_status"
                    :key="item.key"
                    :label="item.key"
                    :value="item.value">
                    </el-option>
                </el-select>
                <el-button class="addbtn mb10" type="primary" icon="el-icon-circle-plus-outline" @click="addGoods()">新增商品</el-button>
            </div>
            <el-table :data="tableData.list" border class="table" ref="multipleTable" @select='chStatus'>
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="productImage" label="图片"  width="70px">
                    <template slot-scope="scope">
                        <div class="tableImg" :style="{backgroundImage: 'url(' + scope.row.productImage.split(',')[0] + ')'}" @click="handlePictureCardPreview( scope.row.productImage)"></div>
    　　　　　　　　  </template>
                </el-table-column>
                <el-table-column prop="productSort" label="排序权重" sortable>
                </el-table-column>
                <el-table-column prop="productName" label="商品名称" >
                </el-table-column>
                <el-table-column prop="price" label="统一价格" >
                </el-table-column>
                <el-table-column prop="totalStock" label="库存" >
                </el-table-column>
                <el-table-column prop="saleNum" label="销量" >
                </el-table-column>
                <el-table-column prop="publishStatus" label="状态" >
                    <template slot-scope="scope">
    　　　　　　　　　   <span v-if="scope.row.publishStatus=== 0">下架</span>
    　　　　　　　　　　 <span v-if="scope.row.publishStatus=== 1">上架</span>
    　　　　　　　　  </template>
                </el-table-column>
                
                
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <!-- 下架 编辑  库存管理  删除 -->
                        <el-button class="typeIcon" type="text" icon="el-icon-sort" @click="lowerShelf(scope.row)" ></el-button>
                        <el-button class="typeIcon" type="text" icon="el-icon-edit" @click="editDetail(scope.row)"></el-button>
                        <el-button class="typeIcon" type="text" icon="el-icon-shopping-cart-1" @click="stockEdit(scope.row)"></el-button>
                        <el-button class="typeIcon red" type="text" icon="el-icon-delete"  @click="handleDelete(scope.row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="simpleHandle">
                <el-button type="primary" plain @click="toggleSelection()">全选</el-button>
                <el-button type="primary" plain @click="toggleSelection(tableData.list)">反选</el-button>
                <el-button type="primary" plain @click="changeStatus(1)">批量上架</el-button>
                <el-button type="primary" plain @click="changeStatus(0)">批量下架</el-button>
                <el-button type="primary" plain @click="changeWeight()">权重批量修改</el-button>
                <el-button type="primary" plain @click="changeStatus(-1)">批量删除</el-button>
            </div>
            <!-- 分页 -->
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="tableData.total" :page-size='condition.pageSize'>
                </el-pagination>
            </div>
        </div>
        <!-- 库存编辑框 -->
        <el-dialog title="编辑库存" :visible.sync="stockVisible" width="761px" center>
            <el-table :data="guigeData" border  class="guigeEdit">
                <el-table-column prop="attrJson2" label="规格" width="180px"> </el-table-column>
                <el-table-column prop="productStock" label="库存" width="150px"> 
                    <template scope="scope">
                        <el-input size="small" v-show="scope.row.productStock" v-model="scope.row.productStock" placeholder="请输入属性值"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="productPrice" label="价格(￥)" width="150px"> 
                    <template scope="scope">
                        <el-input size="small" v-show="scope.row.productPrice" v-model="scope.row.productPrice" placeholder="请输入属性值"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="saleNum" label="销量" width="150px"> 
                    <template scope="scope">
                        <el-input size="small"  v-model="scope.row.saleNum" placeholder="请输入属性值"></el-input>
                    </template>
                </el-table-column>
                <el-table-column width="80" label="无库存是否可销售">
                    <template slot-scope="props" >
                        <span class="tran_box">
                            <el-checkbox v-model="props.row.isSell" >是</el-checkbox>
                        </span>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="stockVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEditSrock">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 权重批量修改编辑框 -->
        <el-dialog title="编辑权重" :visible.sync="weightVisible" width="451px" center>
            <el-table :data="weightList" border  class="guigeEdit">
                <el-table-column prop="productName" label="商品名" width="180"> </el-table-column>
                <el-table-column prop="productSort" label="权重" width="220"> 
                    <template scope="scope">
                        <el-input size="small" v-show="true" v-model="scope.row.productSort" placeholder="请输入权重值"></el-input>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="weightVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEditWeight">确 定</el-button>
            </span>
        </el-dialog>     
        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 图片放大 -->
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>




    </div>
</template>

<script>
    import {axiosRequest} from '@/api/index'
    import {pageNum} from '@/components/common/pageNum'
    import { arrToObj,objToArr,getGoodsType } from '@/components/common/common';
    const cityOptions =['账户管理', '角色管理', '商品管理', '财务审批'];
    export default {
        name: 'roletable',
        inject:['reload'],
        data() {
            return {
                //商品查询条件
                condition:{
                    'title':"",
                    'oneCateId':'',
                    'twoCateId':'',
                    'threeCateId':'',
                    'status':'',
                    'priceMax':'',
                    'priceMin':'',
                    'pageNum':1,
                    'pageSize':pageNum.getPageSize
                },
                //数据列表
                tableData: [],
                dialogImageUrl:'',
                dialogVisible: false,
                disabled: false,
                guigeData:[],
                weightData:[],
                stockVisible:false,
                weightVisible:false,
                typeList:getGoodsType(),
                weightList:[],//修改的权重list
                type_status:[{
                    'value':0,
                    'key':'下架'
                },{
                    'value':1,
                    'key':'上架'
                }],
                value:'',
                delVisible: false,
            }
        },
        created() {
            this.getGoodsList();
        },
        computed: {
            data() {
                
            }
        },
        methods: {
            //获取商品、商品分类列表
            getGoodsList(){
                //获取商品列表
                axiosRequest('product/getProductList',this.condition,'post').then((res)=>{
                    for(let i=0;i<res.list.length;i++){
                        res.list[i].selectStatus=false
                    }
                    this.tableData=res
                })
            },
 
            //图片放大
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
            // 搜索
            search(a){
                this.condition.pageNum=1
                this.getGoodsList()
            },
            //分类查询
            typeChange(event){
                console.log(event)
                if(event.length==1){
                    this.condition.oneCateId=event[0]
                }else if(event.length==2){
                    this.condition.twoCateId=event[1]
                }else if(event.length==3){
                    this.condition.threeCateId=event[2]
                }else if(event.length==0){
                    this.condition.oneCateId=""
                    this.condition.twoCateId=""
                    this.condition.threeCateId=""
                }
                this.condition.pageNum = 1;
                this.getGoodsList()
            },
            //商品上架状态查询
            stateChange(event){
                this.condition.status=this.value
                this.condition.pageNum = 1;
                this.getGoodsList()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.condition.pageNum = val;
                this.getGoodsList();
            },
            //全选,反选
            toggleSelection(rows){
                if(rows){//反选
                    rows.forEach(row=>{
                        row.selectStatus=!row.selectStatus
                        console.log(row)
                        this.$refs.multipleTable.toggleRowSelection(row);
                    })
                }else{//全选
                    let statusNum=0
                    this.tableData.list.forEach(row=>{
                        if(row.selectStatus==false){
                            statusNum++
                        }
                    })
                    if(statusNum==0){
                        this.tableData.list.forEach(row=>{
                            row.selectStatus=false
                        })
                    }else{
                        this.tableData.list.forEach(row=>{
                            row.selectStatus=true
                        })
                    }
                    this.$refs.multipleTable.toggleAllSelection();
                }
            },
            //上下架删除商品函数
            changStatus(query){
                axiosRequest('product/updateProStatus',query,'post').then((res)=>{
                    this.reload()
                })
            },
            //批量上下架
            changeStatus(status){
                let ids=[]
                this.tableData.list.forEach(row=>{
                    if(row.selectStatus==true){
                        ids.push(row.productId)
                    }
                })
                if(ids.length==0){
                    this.$message.error('请选择需要操作的商品')
                }else if(ids.length>0){
                    if(status==-1){
                        this.delIds=ids.join(',')
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
            // 确定删除
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
                this.delIds=row.productId
                this.delVisible = true; //删除提示框 
            },
            //下架单个商品
            lowerShelf(row){
                if(row.publishStatus==0){//下架改上架
                    this.changStatus({
                        "ids":row.productId,
                        "status":1
                    })
                }else if(row.publishStatus==1){//下架改上架
                    this.changStatus({
                        "ids":row.productId,
                        "status":0
                    })
                }
            },
            chStatus(selection,row){
                row.selectStatus=!row.selectStatus
            },
            //规格编辑
            stockEdit(row){
                this.stockVisible=true
                axiosRequest('product/getProductDetail',{
                    'id':row.productId
                },'post').then((res)=>{
                    res.specsList.forEach(function(item,index){
                        let attrJson=item.attrJson.replace(/[&\|\\\*^%$#@,{}:'\-]/g,"")
                        res.specsList[index].attrJson2=attrJson
                        if(item.isnullSell==1){
                            item.isSell=true
                        }else if(item.isnullSell==2){
                            item.isSell=false
                        }
                    })
                    this.guigeData=res.specsList
                })
            },
            //保存规格修改信息
            saveEditSrock(){
                this.guigeData.forEach(function(item,index){
                    if(item.isSell==true){
                        item.isnullSell=1
                    }else if(item.isSell==false){
                        item.isnullSell=2
                    }
                })
                axiosRequest('product/updateProductSpecs',{'specsList':this.guigeData},'post').then((res)=>{
                    this.$message.success('修改成功')
                    this.stockVisible=false
                })
            },
            //编辑商品信息
            editDetail(row){
                sessionStorage.setItem("editGoodsId",row.productId)
                this.$router.push('/addgoods')
            },
            //批量修改权重
            changeWeight(){
                this.weightList=[]
                this.weightVisible=true
                for(let i=0;i<this.tableData.list.length;i++){
                    if(this.tableData.list[i].selectStatus==true){
                        let weightLi={
                            "productId":this.tableData.list[i].productId,
                            "productName":this.tableData.list[i].productName,
                            "productSort":this.tableData.list[i].productSort,
                            
                        }
                        this.weightList.push(weightLi)
                    }
                }
            },
            //保存权重修改结果
            saveEditWeight(){
                if(this.weightList.length==0){
                    this.$message.error('请选择需要操作的商品')
                }else{
                    axiosRequest('product/updateProductBatch',{
                        'productList':this.weightList
                    },'post').then((res)=>{
                        this.weightVisible=false
                        this.$message.success('修改成功');
                        this.reload()
                    })
                }
                
            },


            //添加商品
            addGoods(){
                sessionStorage.removeItem('editGoodsId')
                this.$router.push('/addgoods')
            },


 
            
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 10px;
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
        position: relative;
    }
    .typeIcon{
        font-size: 16px;
        padding: 0 3px;
    }
    .simpleHandle{
        margin-top: 15px;
    }
    .tableImg{
        width: 50px;
        height: 50px;
        background-size: cover;
        border-radius: 6px;
    }
    .mb10{
        margin-bottom: 15px
    }
</style>
