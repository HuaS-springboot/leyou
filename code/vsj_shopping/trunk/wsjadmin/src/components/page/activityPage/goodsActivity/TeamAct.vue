<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 拼团活动
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="condition.title" placeholder="活动名称" class="handle-input mr10 mb10"></el-input>
        <el-input v-model="condition.nickName" placeholder="参与商品" class="handle-input mr10 mb10"></el-input>
        <el-select v-model="condition.status" placeholder="活动状态" class="mr10 handle-select mb10">
          <el-option v-for="item in options" :key="item.key" :label="item.key" :value="item.value"></el-option>
        </el-select>
        <el-button class="mr10 mb10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <label class="mr10 mb10" style="color:#333;margin-left:20px;">活动时间</label>
        <div class="timeChoose mr10 mb10">
          <el-date-picker
            v-model="queryTime"
            type="datetimerange"
            :picker-options="pickerOptions"
            format="yyyy-MM-dd HH:mm:ss"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
          ></el-date-picker>
        </div>
        <el-button class="mr10 " type="primary" icon="el-icon-search" @click="addTeam">发起拼团活动</el-button>
      </div>
      <el-table
        :data="tableData.list"
        border
        class="table"
        ref="multipleTable"
      >
        <el-table-column prop="activityName" label="活动名称"></el-table-column>
        <el-table-column prop="nickName" label="发起人"></el-table-column>
        <el-table-column prop="startTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="productName" label="参与商品"></el-table-column>
        <!-- <el-table-column prop="address" label="原价"></el-table-column>
        <el-table-column prop="address" label="拼团价"></el-table-column>
        <el-table-column prop="address" label="成团人数"></el-table-column> -->
        <el-table-column prop="participateNumber" label="参与人数"></el-table-column>
        <el-table-column prop="activityStatus" label="状态">
          <template slot-scope="scope">
            <span v-if="scope.row.activityStatus===0">未开始</span>
            <span v-if="scope.row.activityStatus===1">进行中</span>
            <span v-if="scope.row.activityStatus===2">已结束</span>
          </template>
        </el-table-column>
        <el-table-column prop="activityStatus" label="操作" width="150" align="left" >
          <template slot-scope="scope">
            <!-- 下架 编辑  库存管理  删除 -->
            <el-button class="typeIcon" type="text" icon="el-icon-check" v-show='scope.row.activityStatus===0' @click="changeStatus(scope.row,'1')"></el-button>
            <el-button class="typeIcon" type="text" icon="el-icon-close" v-show='scope.row.activityStatus===1' @click="changeStatus(scope.row,'2')"></el-button>
            <el-button class="typeIcon" type="text" icon="el-icon-edit"   @click="editActive(scope.row)"></el-button>
            <el-button class="typeIcon red" type="text" icon="el-icon-delete" v-show='scope.row.activityStatus!=1' @click="delActive(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="tableData.total"
          :page-size="condition.pageSize"
        ></el-pagination>
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
  </div>
</template>
<script>
import { axiosRequest } from "@/api/index";
import { pageNum } from "@/components/common/pageNum";
import { objToArr } from "@/components/common/common";
const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  name: "roletable",
  inject:['reload'],
  data() {
    return {
      tableData: [],
      condition: {
        type: 0, //活动类型 0=团购 1=秒杀
        title: "", //活动名
        productName: "", //商品名
        status: "", //状态  0-未开始  1-进行中  2-已结束
        startTime: "",
        endTime: "",
        pageNum: 1,
        pageSize: pageNum.getPageSize
      },
      queryTime: [],
      delId:'',
      editVisible: false,
      delVisible: false,
      options: [],
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      },
      /************************************* */
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.options = objToArr(
        JSON.parse(localStorage.getItem("dictionary")).activity_status
      );
      axiosRequest("activity/getActivityList", this.condition, "post").then(
        res => {
          console.log(res);
          this.tableData = res;
        }
      );
    },
    //搜索
    search() {
      this.condition.pageNum = 1;
      this.condition.startTime = this.queryTime[0];
      this.condition.endTime = this.queryTime[1];
      this.getList();
    },

    //发起团购
    addTeam() {
      sessionStorage.removeItem('activeId')
      this.$router.push("./addteam");
    },
    //改变活动状态
    changeStatus(value,status){
      let index=this.tableData.list.indexOf(value)
      this.$set(this.tableData.list[index],'activityStatus',status)
      axiosRequest('activity/updateActivity',{
        id:value.id,
        status:status
      },'post').then((res)=>{ 
        this.reload()
      })
    },
    //编辑活动
    editActive(value){
      this.$router.push('/addteam')
      sessionStorage.setItem('activeId',value.id)
    },
    //删除活动
    delActive(value){
      this.delVisible=true
      this.delId=value.id
      this.delValue=value
    },
    // 分页导航
    handleCurrentChange(val) {
      this.condition.pageNum = val;
      this.getList();
    },
    // 确定删除
    deleteRow() {
      axiosRequest('activity/delActivity',{
        id:this.delId,
        activityType:'0'
      },'post').then((res)=>{
        let index=this.tableData.list.indexOf(this.delValue)
        this.tableData.list.splice(index,1)
        this.$message.success("删除成功");
        this.delVisible = false;
      })
      
    }






    /*********************************************************** */

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
.handle-box {
  margin-bottom: 10px;
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
.mb10 {
  margin-bottom: 15px;
}
</style>
