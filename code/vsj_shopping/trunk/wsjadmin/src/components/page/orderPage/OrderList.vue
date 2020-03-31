<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 订单列表
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="conditon.orderNo" placeholder="订单号" class="handle-input mr10"></el-input>
        <el-input v-model="conditon.nickName" placeholder="账户名" class="handle-input mr10"></el-input>
        <el-input v-model="conditon.title" placeholder="商品名" class="handle-input mr10"></el-input>

        <el-button class="mr10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <br />
        <br />
        <!-- 订单状态-->
        <el-select
          v-model="status"
          placeholder="订单状态"
          :clearable="true"
          class="mr10 handle-select"
          @change="orderSearch"
          @clear="clearData"
        >
          <el-option v-for="item in dictionaryOrder" :key="item.value" :value="item.key"></el-option>
        </el-select>
        <!-- 支付方式-->
        <el-select
          v-model="payType"
          placeholder="支付方式"
          :clearable="true"
          class="mr10 handle-select"
          @change="payTypeSearch"
          @clear="clearData"
        >
          <el-option v-for="item in dictionaryPayType" :key="item.value" :value="item.key"></el-option>
        </el-select>
        <!-- 时间类型-->
        <el-select
          v-model="type"
          placeholder="时间类型"
          class="mr10 handle-select"
          @change="typeChange"
        >
          <el-option v-for="item in timeType" :key="item.lable" :value="item.lable"></el-option>
        </el-select>
        <!-- 开始时间和结束时间 -->
        <div class="timeChoose">
          <el-date-picker
            v-model="dateType"
            format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            @change="dateChange"
          ></el-date-picker>
        </div>

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
            <span v-if="scope.row.orderStatus==0">待付款</span>
            <span v-if="scope.row.orderStatus==1">待发货</span>
            <span v-if="scope.row.orderStatus==2">待收货</span>
            <span v-if="scope.row.orderStatus==3">已签收</span>
            <span v-if="scope.row.orderStatus==4">已结算</span>
            <span v-if="scope.row.orderStatus==5">未发货退款</span>
            <span v-if="scope.row.orderStatus==6">已发货退款</span>
            <span v-if="scope.row.orderStatus==7">已退款</span>
            <span v-if="scope.row.orderStatus==8">已关闭</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="left" :formatter="formatter" min-width="300">
          <template slot-scope="scope">
            <!-- 关闭 -->
            <el-button
              v-if="scope.row.orderStatus==0"
              class="typeIcon"
              type="text"
              icon="el-icon-circle-close"
              @click="closeOrder(scope.$index, scope.row)"
            ></el-button>
            <!-- 确认付款 -->
            <el-button
              v-if="scope.row.orderStatus==0"
              class="typeIcon red"
              type="text"
              icon="el-icon-circle-check"
              @click="confirmPay(scope.$index,scope.row)"
            ></el-button>
            <!-- 修改价格 -->
            <el-button
              v-if="scope.row.orderStatus==0"
              class="typeIcon red"
              type="text"
              icon="el-icon-edit-outline"
              @click="editPrice(scope.$index,scope.row)"
            ></el-button>
            <!-- 发货 -->
            <el-button
              v-if="scope.row.orderStatus==1"
              class="typeIcon red"
              type="text"
              icon="el-icon-position"
              @click="sendGoods(scope.$index,scope.row)"
            ></el-button>
            <!-- 退款 -->
            <el-button
              v-if="scope.row.orderStatus==1 || scope.row.orderStatus==2"
              class="typeIcon red"
              type="text"
              icon="el-icon-sort-up"
              @click="refund(scope.$index,scope.row)"
            ></el-button>
            <!-- 取消发货 -->
            <el-button
              v-if="scope.row.orderStatus==2"
              class="typeIcon red"
              type="text"
              icon="el-icon-document-delete"
              @click="sendOutGoods(scope.$index,scope.row)"
            ></el-button>
            <!-- 确认收货 -->
            <el-button
              v-if="scope.row.orderStatus==2"
              class="typeIcon red"
              type="text"
              icon="el-icon-document-checked"
              @click="receiveGoods(scope.$index,scope.row)"
            ></el-button>
            <!-- 结算 -->
            <el-button
              v-if="scope.row.orderStatus==3"
              class="typeIcon red"
              type="text"
              icon="el-icon-coin"
              @click="settlement(scope.$index,scope.row)"
            ></el-button>
            <!-- 确认退款 -->
            <el-button
              v-if="scope.row.orderStatus==5||scope.row.orderStatus==6"
              class="typeIcon red"
              type="text"
              icon="el-icon-finished"
              @click="scope.row.orderStatus==5?confirmRefund(scope.$index,scope.row):confirmRefunded(scope.$index,scope.row)"
            ></el-button>
            <!-- 驳回 -->
            <el-button
              v-if="scope.row.orderStatus==6||scope.row.orderStatus==5"
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

    <!-- 编辑弹出框 -->
    <el-dialog title="修改价格" :visible.sync="editVisible" width="40%" class="typeEdit">
      <el-form :model="editForm" label-width="110px" label-position="left">
        <el-form-item label="实收">
          <el-input v-model="editForm.price" class="mr10"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 发货弹出框 -->
    <el-dialog title="发货" :visible.sync="rufundVisible" width="40%" class="typeEdit">
      <el-form :model="editForm" label-width="110px" label-position="left">
        <el-form-item label="快递类型">
          <el-select v-model="editForm.logisticsType" placeholder="快递类型" class="mr10 handle-select">
            <el-option v-for="item in dictionarylogisticsType" :key="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="快递单号">
          <el-input v-model="editForm.logisticsNo" class="mr10"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="rufundVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRufund()">确 定</el-button>
      </span>
    </el-dialog>
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
      dictionaryOrder: objToArr(
        JSON.parse(window.localStorage.getItem("dictionary")).order_status
      ),
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
      ],
      operation: [],
      operationStatus: [
        {
          status: 0,
          value: [
            { value: "0", lable: "关闭" },
            { value: "1", lable: "确认付款" },
            { value: "2", lable: "修改价格" }
          ]
        },
        {
          status: 1,
          value: [{ value: "0", lable: "发货" }, { value: "1", lable: "退款" }]
        },
        {
          status: 2,
          value: [
            { value: "0", lable: "取消发货" },
            { value: "1", lable: "确认发货" },
            { value: "2", lable: "退款" }
          ]
        },
        {
          status: 3,
          value: [{ value: "0", lable: "结算" }]
        },
        {
          status: 4,
          value: []
        },
        {
          status: 5,
          value: [
            { value: "0", lable: "确认退款" },
            { value: "1", lable: "驳回" }
          ]
        }
      ],
      value: {
        0: "",
        1: ""
      },
      tableData: [],
      multipleSelection: [],
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      rufundVisible: false,
      form: {
        name: "",
        date: "",
        address: ""
      },
      radio: "",
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
      dateType: []
    };
  },
  created() {
    this.getOrderList();
  },
  computed: {},
  methods: {
    //获取订单列表
    getOrderList() {
      axiosRequest("order/getOrderList", this.conditon, "post").then(res => {
        this.orderList = res.list;

        this.total = res.total;
      });
    },
    //清空选项
    clearData() {
      (this.conditon = {
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
      }),
        this.getOrderList();
    },
    //搜索
    search() {
      this.getOrderList();
    },
    //订单状态的查询
    orderSearch(data) {
      switch (data) {
        case "未付款":
          this.conditon.status = "0";
          break;
        case "待发货":
          this.conditon.status = "1";
          break;
        case "待收货":
          this.conditon.status = "2";
          break;
        case "已签收":
          this.conditon.status = "3";
          break;
        case "已结算":
          this.conditon.status = "4";
          break;
        case "未发货退款":
          this.conditon.status = "5";
          break;
        case "已发货退款":
          this.conditon.status = "6";
          break;
        case "已退款":
          this.conditon.status = "7";
          break;
        case "已关闭":
          this.conditon.status = "8";
          break;
      }
      this.getOrderList();
    },
    //支付方式的查询
    payTypeSearch(data) {
      switch (data) {
        case "微信":
          this.conditon.payType = "WX";
          break;
        case "支付宝":
          this.conditon.payType = "ZFB";
          break;
      }
      this.getOrderList();
    },
    //时间类型的查询
    typeChange(data) {
      switch (data) {
        case "下单时间":
          this.conditon.type = "0";
          break;
        case "付款时间":
          this.conditon.type = "1";
          break;
        case "发货时间":
          this.conditon.type = "2";
          break;
        case "完成时间":
          this.conditon.type = "3";
          break;
      }
      this.getOrderList();
    },
    // 开始时间和结束时间
    dateChange(data) {
      console.log(data, 2000);
      this.conditon.startTime = formatDateTime(data[0]);
      this.conditon.endTime = formatDateTime(data[1]);
      this.getOrderList();
    },
    //导出订单
    exportOrder() {
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
    orderStatusEdit() {
      axiosRequest("order/updateOrderStatus", this.editForm, "post").then(
        res => {
          console.log(res);
        }
      );
    },
    //关闭订单
    closeOrder(index, data) {
      this.$confirm("是否确定关闭该订单?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: "false"
      })
        .then(action => {
          if (action == "confirm") {
            this.editForm.orderId = data.orderId;
            this.editForm.status = "8";
            let code = this.orderStatusEdit();
            console.log(code);
            if (code == 200) {
              this.$message({
                type: "success",
                message: "关闭成功!"
              });
            }
          }
        })
        .catch(err => {
          if (err == "cancel") {
            this.$message({
              type: "info",
              message: "已取消关闭"
            });
          }
        });
    },
    //确认付款
    confirmPay(index, data) {
      this.$confirm("是否确确认付款?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(action => {
          if (action == "confirm") {
            this.editForm.orderId = data.orderId;
            this.editForm.status = 1;
            this.orderStatusEdit();
            this.$message({
              type: "success",
              message: "付款成功!"
            });
          }
        })
        .catch(err => {
          if (err == "cancle") {
            this.$message({
              type: "info",
              message: "已取消付款"
            });
          }
        });
    },
    //修改价格
    editPrice(index, data) {
      this.editForm.orderId = data.orderId;
      this.editVisible = true;
    },
    // 价格修改
    saveEdit() {
      this.editVisible = false;
      this.$message.success("修改成功");
      this.orderStatusEdit();
      console.log(this.editForm.price);
    },
    //发货
    sendGoods(index, data) {
      this.editForm.orderId = data.orderId;
      console.log(this.dictionarylogisticsType);
      this.rufundVisible = true;
    },
    saveRufund() {
      this.rufundVisible = false;
      this.editForm.status = 2;
      this.orderStatusEdit();
    },
    //待发货退款
    refund(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 7;
      this.orderStatusEdit();
    },
    //取消发货
    sendOutGoods(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 1;
      this.orderStatusEdit();
    },
    //确认收货
    receiveGoods(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 3;
      this.orderStatusEdit();
    },
    //待收货 退款
    refundForSended() {
      this.editForm.status = 7;
      this.orderStatusEdit();
    },
    //结算
    settlement(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 4;
      this.orderStatusEdit();
    },
    //未发货退款-确认退款
    confirmRefund(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 7;
      this.orderStatusEdit();
    },
    //未发货退款-驳回
    reject(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 1;
      this.orderStatusEdit();
    },
    //已发货退款-确认退款
    confirmRefunded(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 7;
      this.orderStatusEdit();
    },
    //已发货退款-驳回
    rejected(index, data) {
      this.editForm.orderId = data.orderId;
      this.editForm.status = 2;
      this.orderStatusEdit();
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
          // query: {
          //   list: JSON.stringify(res),
          // }
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
.orderOperation {
  width: 100px;
  margin-right: 10px;
}
</style>
