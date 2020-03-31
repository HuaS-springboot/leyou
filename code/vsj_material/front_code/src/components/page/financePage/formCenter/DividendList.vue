<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 订单分成明细统计</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">
                <el-input v-model="conditon.nickName" placeholder="姓名" class="handle-input mr10 mb10"  ></el-input>
                <el-select v-model="conditon.id" placeholder="会员等级" class="mr10 handle-select mb10" @change='stateChange'>
                    <el-option
                    v-for="item in options"
                    :key="item.levelName"
                    :label="item.levelName"
                    :value="item.id">
                    </el-option>
                </el-select>
                <el-input v-model="conditon.orderNo" placeholder="订单号" class="handle-input mr10 mb10" ></el-input>
                <el-button class="mr10 mb10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                <el-button class="mr10 mb10" type="primary" icon="el-icon-search" @click="exportList">导出Excel表格</el-button>
                
                <label class="mr10 mb10" style="color:#333;margin-left:20px;">订单时间</label>
                <div class="timeChoose mb10">
                    <el-date-picker v-model="queryTime" type="datetimerange" 
                    :picker-options="pickerOptions" format="yyyy-MM-dd HH:mm:ss" 
                    range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right">
                    </el-date-picker>
                </div>
            </div>
            <el-table :data="formData.list" border class="table" ref="multipleTable" >
                <el-table-column prop="nickName" label="会员名称" >
                </el-table-column>
                <el-table-column prop="levelName" label="会员等级" >
                </el-table-column>
                <el-table-column prop="orderNo" label="订单号" >
                </el-table-column>
                <el-table-column prop="productAmountTotal" label="订单金额" >
                </el-table-column>
                <el-table-column prop="orderAmountTotal" label="订单实付款" >
                </el-table-column>
                <el-table-column prop="createTime" label="订单时间" >
                </el-table-column>
                <el-table-column prop="operationMoney" label="分红金额" >
                </el-table-column>
                <el-table-column prop="incomeSource" label="分红方式" >
                </el-table-column>
                <el-table-column prop="stockDater" label="结算时间" >
                </el-table-column>
                
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="formData.pages" :page-size='conditon.pageSize'>
                </el-pagination>
            </div>
        </div>
        

    </div>
</template>

<script>
    import request from '../../../../utils/request';
    import {pageNum} from '@/components/common/pageNum'
    import {axiosRequest} from '@/api/index'
    export default {
        name: 'roletable',
        data() {
            return {
                //查询条件
                conditon:{
                    'nickName':"",
                    'id':'',
                    'startTime':'',
                    'endTime':'',
                    'orderNo':'',
                    'pageNum':1,
                    'pageSize':pageNum.getPageSize
                },
                formData:[],
                queryTime:[],
                options: [],
                pickerOptions: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                        }
                    }]
                },
            }
        },
        created() {
            this.getList()
            this.getAllLevel()
        },
        methods: {

            //获取列表           
            getList(){
                axiosRequest('financial/getOrderReportCenterByName',this.conditon,'post').then((res)=>{
                    this.formData=res
                    console.log(this.formData)
                })
            },
            //查询会员等级列表
            getAllLevel(){
                axiosRequest('memeber/findAllLevel','','POST').then((res)=>{
                    this.options=res
                    console.log(res)
                })
            },
            search(){
                this.conditon.pageNum=1
                this.conditon.startTime=this.queryTime[0]
                this.conditon.endTime=this.queryTime[1]
                this.getList()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.conditon.pageNum=val
                this.getList()
            },
            stateChange(){
                this.conditon.pageNum = 1;
                this.getList()
            },
            exportList(){
                console.log(1)
                if(this.formData.pages==0){
                    this.$message.error('当前条件的订单数为0')
                }else{
                    window.location.href=request.SERVER+'financial/exportOrderReportListExcel?nickName='
                                                    +this.conditon.nickName+'&orderNo='+this.conditon.orderNo+'&id='+this.conditon.id+'&startTime='
                                                    +this.conditon.startTime+'&endTime='+this.conditon.endTime
                }
            },
           
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
    .mb10{
        margin-bottom: 15px;
    }
</style>
