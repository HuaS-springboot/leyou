<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 退换货订单
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="conditon.orderNo" placeholder="订单号" class="handle-input mr10"></el-input>
        <el-input v-model="conditon.nickName" placeholder="账户名" class="handle-input mr10"></el-input>
        <el-input v-model="conditon.title" placeholder="商品名" class="handle-input mr10"></el-input>

        <el-button class="mr10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button
          class="addbtn"
          type="primary"
          icon="el-icon-circle-plus-outline"
          @click="exportOrder"
        >导出订单</el-button>
      </div>
      <el-table
        :data="orderList"
        border
        class="table"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="orderNo" label="订单号"></el-table-column>
        <el-table-column prop="productName" label="商品名称"></el-table-column>
        <el-table-column prop="nickName" label="下单账户"></el-table-column>
        <el-table-column prop="payChannel" label="支付方式">
          <template slot-scope="scope">
            <span v-if="scope.row.payChannel=='WX'">微信</span>
            <span v-if="scope.row.payChannel=='zfb'">支付宝</span>
          </template>
        </el-table-column>
        <el-table-column prop="productAmountTotal" label="应收"></el-table-column>
        <el-table-column prop="orderAmountTotal" label="实收"></el-table-column>
        <el-table-column prop="createTime" width="160" label="下单时间"></el-table-column>
        <el-table-column prop="payTime" width="160" label="支付时间"></el-table-column>
        <el-table-column prop="orderStatus" label="订单状态">
          <template slot-scope="scope">
            <span v-if="scope.row.orderStatus==5">未发货退款</span>
            <span v-if="scope.row.orderStatus==6">已发货退款</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200" align="left" :formatter="formatter">
          <template slot-scope="scope">
            <!-- 退款   驳回   详情-->
            <el-button
              class="typeIcon red"
              type="text"
              icon="el-icon-sort-up"
              @click="scope.row.orderStatus==5?confirmRefund(scope.$index,scope.row):confirmRefunded(scope.$index,scope.row)"
            ></el-button>
            <el-button
              class="typeIcon red"
              type="text"
              icon="el-icon-close"
              @click="scope.row.orderStatus==6?rejected(scope.$index,scope.row):reject(scope.$index,scope.row)"
            ></el-button>
            <!-- 详情 -->
            <el-button
              class="typeIcon red"
              type="text"
              icon="el-icon-edit"
              @click="goOrderDetail(scope)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="this.total"
          :page-size="this.conditon.getPageSize"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import request from "../../../utils/request";
import { axiosRequest } from "@/api/index";
import { pageNum } from "../../common/pageNum";
import { objToArr, formatDateTime } from "@/components/common/common";
const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  name: "roletable",
  data() {
    return {
      status: "",
      type: "",
      payType: "",
      //当前行的id
      id: "",
      // 订单状态修改
      editForm: {
        orderId: "",
        price: "",
        remark: "",
        status: "",
        logisticsNo: "",
        logisticsTye: ""
      },
      // 订单筛选条件
      conditon: {
        orderNo: "",
        nickName: "",
        title: "",
        type: "",
        payType: "",
        startTime: "",
        endTime: "",
        status: "",
        getPageNum: "1",
        getPageSize: pageNum.getPageSize
      },
      logisticsType: "",
      //选中的id集合
      ids: "",
      orderList: [],
      total: 0,
      orderStatus: [
        { value: "0", lable: "未发货退款" },
        { value: "1", lable: "已发货退款" }
      ],
      dictionaryPayType: objToArr(
        JSON.parse(window.localStorage.getItem("dictionary")).pay_type
      ),
      //快递类型
      dictionarylogisticsType: objToArr(
        JSON.parse(window.localStorage.getItem("dictionary")).express_company
      ),
      timeType: [
        { value: "0", lable: "下单时间" },
        { value: "1", lable: "付款时间" },
        { value: "2", lable: "发货时间" },
        { value: "3", lable: "完成时间" }
      ]
    };
  },
  created() {
    this.getOrderList();
  },
  computed: {},
  methods: {
    //获取订单列表
    getOrderList() {
      this.orderList = [];
      axiosRequest("order/getOrderList", this.conditon, "post").then(res => {
        if (res.list) {
          for (let i = 0; i < res.list.length; i++) {
            if (res.list[i].orderStatus == 5 || res.list[i].orderStatus == 6) {
              this.orderList.push(res.list[i]);
            }
          }
        }
        // this.orderList = res.list;
        this.total = res.total;
      });
    },
    //订单状态修改
    orderStatusEdit() {
      axiosRequest("order/updateOrderStatus", this.editForm, "post").then(
        res => {
          console.log(res);
          return res;
        }
      );
    },
    //搜索
    search() {
      this.getOrderList();
    },
    //导出订单
    exportOrder() {
      if (this.ids) {
        this.$confirm("确定导出订单?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            this.getOrderList();
            window.location.href =
              request.SERVER + "order/exportOrderExcel?ids=" + this.ids;
            this.$message({
              type: "success",
              message: "导出成功!"
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消导出"
            });
          });
      } else {
        this.$message("请至少选择一条数据！");
      }
    },
    //获取选中的当前行数据中的id
    handleSelectionChange(val) {
      if (val) {
        for (let i = 0; i < val.length; i++) {
          this.ids += val[i].orderId + ",";
        }
      } else {
        this.ids = "";
      }
    },
    //未发货退款-确认退款
    confirmRefund(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 7;
      this.orderStatusEdit();
      this.getOrderList();
    },
    //未发货退款-驳回
    reject(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 1;
      this.orderStatusEdit();
      this.getOrderList();
    },
    //已发货退款-确认退款
    confirmRefunded(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 7;
      this.orderStatusEdit();
      this.getOrderList();
    },
    //已发货退款-驳回
    rejected(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 2;
      this.orderStatusEdit();
      this.getOrderList();
    },
    // 订单详情
    goOrderDetail(scope) {
      axiosRequest(
        "order/getOrderDetailsList",
        { id: scope.row.orderId },
        "post"
      ).then(res => {
        console.log(res);
        if (res) {
          window.localStorage.setItem("order_detail", JSON.stringify(res));
        }
        this.$router.push({
          path: "/orderdetail"
        });
      });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.getPageNum = val;
      this.getOrderList();
    },
    formatter(row, column) {
      return row.address;
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
</style>
