<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 会员订单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">
                <el-input v-model="nickName" placeholder="会员名称" class="handle-input mr10" ></el-input>
                <el-input v-model="orderNO" placeholder="订单号" class="handle-input mr10" ></el-input>
                <el-input v-model="telPhone" placeholder="手机号" class="handle-input mr10" ></el-input>
               <!--  <el-input v-model="select_word" placeholder="openid" class="handle-input mr10" ></el-input> -->
                
                <el-button class="mr10" type="primary" icon="el-icon-search" @click="selectMemberList">搜索</el-button><br/>
                <br/>
                <!-- 等级-->
                <el-select v-model="levelId" placeholder="等级" class="mr10 handle-select" @change="leverChange" :clearable="true">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <!--<label class="mr10" style="color:#333;margin-left:20px;">加入时间</label>
                <div class="timeChoose">
                    <el-date-picker v-model="queryTime" type="datetimerange" :picker-options="pickerOptions" 
                    format="yyyy-MM-dd HH:mm:ss"
                    range-separator="至" 
                    start-placeholder="开始日期" end-placeholder="结束日期" align="right">
                    </el-date-picker>
                </div>-->
               <!-- <el-button class="addbtn" type="primary" icon="el-icon-circle-plus-line" @click="exportList">导出会员列表</el-button>-->
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="orderNo" label="订单号" >
                </el-table-column>
                <el-table-column prop="nickName" label="会员名称" >
                </el-table-column>
                <el-table-column prop="telPhone" label="手机号">
                </el-table-column>
                <el-table-column prop="payAmount" label="支付金额">
                </el-table-column>
                <el-table-column prop="createTime" label="支付时间">
                </el-table-column>
                <el-table-column prop="levelName" label="会员等级">
                </el-table-column>
                <el-table-column prop="expiredTime" label="会员过期时间" width = "180px">
                </el-table-column>
                <!--<el-table-column prop="carryBalance" label="可提现余额" > </el-table-column>
                <el-table-column prop="freezeBalance" label="冻结余额" > </el-table-column>
                <el-table-column label="操作" width="200" align="left" >
                    <template slot-scope="scope">
                        <el-button type="info" round size="mini" @click="selectLowerList(scope.row)">查看下级</el-button>
                        <el-button type="info" round size="mini" @click="goMemberDetail(scope.row)">用户详情</el-button>
                    </template>
                </el-table-column>-->
            </el-table>
            <div style="padding:20px;font-size:16px;color:#666">会员总数:{{total}}</div>
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
        data() {
            return {
              
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
//              parentId:'',
                orderNO:'',
//              queryTime:[],
//              pickerOptions: {
//                  shortcuts: [{
//                      text: '最近一周',
//                      onClick(picker) {
//                      const end = new Date();
//                      const start = new Date();
//                      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
//                      picker.$emit('pick', [start, end]);
//                      }
//                  }, {
//                      text: '最近一个月',
//                      onClick(picker) {
//                      const end = new Date();
//                      const start = new Date();
//                      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
//                      picker.$emit('pick', [start, end]);
//                      }
//                  }, {
//                      text: '最近三个月',
//                      onClick(picker) {
//                      const end = new Date();
//                      const start = new Date();
//                      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
//                      picker.$emit('pick', [start, end]);
//                      }
//                  }]
//              },
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
                    orderNo : this.orderNO,
                    telPhone : this.telPhone,
                    levelId : this.levelId,
//                  startTime : this.queryTime[0],
//                  endTime : this.queryTime[1],
//                  parentId : this.parentId
                };
                axiosRequest('/admin/order/getMemberOrderList',selectForm,'POST').then((res)=>{
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
//          exportList(){
//              if(this.total==0){
//                  this.$message.error('当前条件的会员数为0')
//              }else{
//                  window.location.href=request.SERVER+'memeber/exportMemberListExcel?nickName='
//                                                  +this.nickName+'&orderNO='+this.orderNO+'&telPhone='
//                                                  +this.telPhone+'&levelId='+this.levelId+'&startTime='
//                                                  +this.queryTime[0]+'&endTime='+this.queryTime[1]
//              }
//          },
            //查看用户下级
//          selectLowerList(row){
//              this.pargeNum = 1; 
//              this.nickName = '';
//              this.telPhone = '';
//              this.levelId = '';
//              this.queryTime = '';
//              this.parentId = row.id;
//              this.orderNO = row.nickName;
//              this.getMemberList();
//          },
            //
            // 分页导航
            handleCurrentChange(val) {
                this.pageNum = val;
                this.getMemberList();
            },
            // 用户详情
//          goMemberDetail(val){
//              console.log(val);
//              //this.$router.push({name:'memberdetail',params:{member:val}})
//              sessionStorage.setItem('memberInfo',JSON.stringify(val))
//              this.$router.push('/memberdetail')
//          },
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