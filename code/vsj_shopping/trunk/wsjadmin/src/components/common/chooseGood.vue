<template >

    <div id="chooseGoods">
        <div class="handle-box">
            <div class="el-dialog__header">
                <span class="el-dialog__title">选择商品</span>
                <button type="button" aria-label="Close" class="el-dialog__headerbtn" @click="cancel">
                    <i class="el-dialog__close el-icon el-icon-close"></i>
                </button>
            </div>
            <div>
                <div>
                    <el-cascader class="mb10" :options="typeList" :props="{ checkStrictly: true }" clearable placeholder="商品分类" @change="typeChange" ></el-cascader>
                </div>
                <el-table :data="tableData.list" border class="table mt20" ref="multipleTable" row-key="productId" @select='chStatus'>
                    <el-table-column prop="selectStatus" :reserve-selection="true" type="selection" width="55" align="center"></el-table-column>
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
                </el-table>
                
                <!-- 分页 -->
                <div class="pagination">
                    <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="tableData.total" :page-size='tableData.pageSize'>
                    </el-pagination>
                </div>
            </div>
            <div>
                <span slot="footer" class="chooseFooter">
                    <el-button @click="cancel">取 消</el-button>
                    <el-button type="primary" @click="saveEdit()">确 定</el-button>
                </span>
            </div>
        </div>
        
    </div>
</template>
<script>
    import {axiosRequest} from '@/api/index'
    import {pageNum} from '@/components/common/pageNum'
    import {getGoodsType } from '@/components/common/common';
    export default{
        name:"chooseGood",
        props:{
            'chooseIdArr':{
                type:Array,     //数组必传
                required:true
            },
            'isSingle':{
                type:Boolean,     
                required:false
            }
            
        },
        data:function(){
            return{
                tableData:[],
                conditionGoods:{
                    'oneCateId':'',
                    'twoCateId':'',
                    'threeCateId':'',
                    'pageNum':1,
                    'pageSize':pageNum.getPageSize
                },
                chooseArr:[],
                chooseGoodsName:'', //商品的名字
                conditionNum:0,    //条件计数
                typeList:getGoodsType(),
            }
        },
        created(){
            this.chooseArr=[]
            //处理下控制的情况
            if(this.chooseIdArr.length==1&&this.chooseIdArr[0]==''){
                this.chooseArr=[]
            }else{
                this.chooseArr=JSON.parse(JSON.stringify(this.chooseIdArr))
            }
            this.getGoodsList()
        },
        methods:{
            //获取商品、商品分类列表
            getGoodsList(){
                //获取商品列表
                axiosRequest('product/getProductList',this.conditionGoods,'post').then((res)=>{
                    this.tableData=res
                    this.$refs.multipleTable.clearSelection()
                    for(let i=0;i<res.list.length;i++){
                        res.list[i].selectStatus=false
                        for(let j=0;j<this.chooseArr.length;j++){
                            if(this.chooseArr[j]==res.list[i].productId){
                                this.$refs.multipleTable.toggleRowSelection(res.list[i],true)
                                res.list[i].selectStatus=true
                            }
                            
                        }
                    }
                })
            },
            //分类查询
            typeChange(event){
                if(event.length==1){
                    this.conditionGoods.oneCateId=event[0]
                }else if(event.length==2){
                    this.conditionGoods.twoCateId=event[1]
                }else if(event.length==3){
                    this.conditionGoods.threeCateId=event[2]
                }else if(event.length==0){
                    this.conditionGoods.oneCateId=""
                    this.conditionGoods.twoCateId=""
                    this.conditionGoods.threeCateId=""
                }
                this.conditionGoods.pageNum = 1;
                this.getGoodsList()
            },
            chStatus(selection,row){
                if(this.isSingle==true&&selection.length>1){
                    this.$message.error('只能选择一个商品')
                    this.$refs.multipleTable.clearSelection()
                }else{
                    row.selectStatus=!row.selectStatus
                    if(row.selectStatus==true){
                        this.chooseArr.push(row.productId)
                        this.chooseGoodsName=row.productName
                    }else if(row.selectStatus==false){
                        for(let i=0;i<this.chooseArr.length;i++){
                            if(this.chooseArr[i]==row.productId){
                                this.chooseArr.splice(i,1)
                            }
                        }
                    }
                }
                
            },
            // 分页导航
            handleCurrentChange(val) {
                this.conditionGoods.pageNum = val;
                this.getGoodsList();
            },
            saveEdit(){
                this.$emit('saveChoose',this.chooseArr,this.chooseGoodsName)
            },
            cancel(){
                this.$emit('closeChoose')
            }
        }
    }
</script>
<style scoped>
    #chooseGoods{
        position: fixed;
        z-index: 999999;
        top: 0;
        right: 0;
        bottom: 0;
        left:0;
        /* width: 100vw;
        height: 100vh; */
        background-color: rgba(0, 0,0,0.2);
        padding:0;
    }   
    .handle-box{
        position: relative;
        z-index: 9999999;
        padding:0 20px ;
        margin-top: 15vh;
        width: 80%;
        left: 10%;
        height: auto;
        background-color: #fff;;
        box-shadow: 0 1px 3px rgba(0,0,0,.3);
        
        border-radius: 2px;
    }
    .handle-box>div:nth-of-type(2){
        padding: 20px 0;
    }
    .handle-box>div:nth-of-type(3){
        padding: 20px 0;
    }
    .mb10{
        margin-bottom:15px;
    }
    .mt20{
        margin-bottom:20px;
    }
    .chooseGoods{
        background-color: #fff;
        padding: 30px 20px;
    } 
</style>