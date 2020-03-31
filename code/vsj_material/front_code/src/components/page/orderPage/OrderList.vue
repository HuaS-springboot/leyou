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
          v-model="type"
          placeholder="订单状态"
          class="mr10 handle-select"
          @change="orderSearch"
        >
          <el-option v-for="item in dictionaryOrder" :key="item.value" :value="item.key"></el-option>
        </el-select>
        <!-- 支付方式-->
        <el-select
          v-model="payType"
          placeholder="支付方式"
          class="mr10 handle-select"
          @change="payTypeSearch"
        >
          <el-option v-for="item in dictionaryPayType" :key="item.value" :value="item.key"></el-option>
        </el-select>
        <!-- 时间类型-->
        <el-select v-model="dateTime" placeholder="时间类型" class="mr10 handle-select">
          <el-option v-for="item in timeType" :key="item.lable" :value="item.lable"></el-option>
        </el-select>
        <div class="timeChoose">
          <el-date-picker
            v-model="value2"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
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
            <span v-if="scope.row.payChannel==2">微信</span>
            <span v-if="scope.row.payChannel==1">支付宝</span>
          </template>
        </el-table-column>
        <el-table-column prop="productAmountTotal" label="应收"></el-table-column>
        <el-table-column prop="orderAmountTotal " label="实收"></el-table-column>
        <el-table-column prop="address" label="下单时间"></el-table-column>
        <el-table-column prop="payTime" label="支付时间"></el-table-column>
        <el-table-column prop="orderStatus" label="订单状态">
          <template slot-scope="scope">
            <span v-if="scope.row.orderStatus==0">代付款</span>
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
            <!-- 下架 编辑  库存管理  删除 -->
            <el-select
              v-model="value"
              placeholder="操作"
              class="orderOperation"
              @focus="getFocus(scope)"
              @change="getCurrenData"
            >
              <el-option
                v-for="item in operation"
                :key="item.label"
                :label="item.label"
                :value="item.lable"
              ></el-option>
            </el-select>
            <el-button type="info" round size="mini" @click="goOrderDetail(scope)">订单详情</el-button>
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
  </div>
</template>

<script>
import request from "../../../utils/request";
import { axiosRequest } from "@/api/index";
import { pageNum } from "../../common/pageNum";
import { objToArr, bus } from "@/components/common/common";
const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  name: "roletable",
  data() {
    return {
      type: "",
      payType: "",
      dateTime: "",
      //当前行的id
      id: "",
      // 价格修改
      editForm: {
        id: "",
        price: ""
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
      timeType: [
        { value: "", lable: "全部" },
        { value: "", lable: "下单时间" },
        { value: "", lable: "付款时间" },
        { value: "", lable: "操作时间" },
        { value: "", lable: "发货时间" },
        { value: "", lable: "完成时间" },
        { value: "", lable: "结算时间" }
      ],
      operation: [],
      operationStatus: [
        {
          status: 0,
          value: [
            { value: "", lable: "关闭" },
            { value: "", lable: "确认付款" },
            { value: "", lable: "修改价格" }
          ]
        },
        {
          status: 1,
          value: [{ value: "", lable: "发货" }, { value: "", lable: "退款" }]
        },
        {
          status: 2,
          value: [
            { value: "", lable: "取消发货" },
            { value: "", lable: "确认发货" },
            { value: "", lable: "退款" }
          ]
        },
        {
          status: 3,
          value: [{ value: "", lable: "结算" }]
        },
        {
          status: 4,
          value: []
        },
        {
          status: 5,
          value: [
            { value: "", lable: "确认退款" },
            { value: "", lable: "驳回" }
          ]
        }
      ],

      tableData: [],
      cur_page: 1,
      multipleSelection: [],
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      form: {
        name: "",
        date: "",
        address: ""
      },
      radio: "",

      value: "",
      idx: -1,
      id: -1,
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
      value2: "",
      //
      checkAll: false,

      cities: cityOptions,
      isIndeterminate: true
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
    //搜索
    search() {
      this.getOrderList();
    },
    //订单状态的查询
    orderSearch(data) {
      switch (data) {
        case "未付款":
          this.conditon.type = "0";
          break;
        case "待发货":
          this.conditon.type = "1";
          break;
        case "待收货":
          this.conditon.type = "2";
          break;
        case "已签收":
          this.conditon.type = "3";
          break;
        case "已结算":
          this.conditon.type = "4";
          break;
        case "未发货退款":
          this.conditon.type = "5";
          break;
        case "已发货退款":
          this.conditon.type = "6";
          break;
        case "已退款":
          this.conditon.type = "7";
          break;
        case "已关闭":
          this.conditon.type = "8";
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
    //根据当前状态判断操作的显示内容
    getFocus(scope) {
      //获取行的 orderId
      this.id = scope.row.orderId;

      if (scope.row.orderStatus == 0) {
        this.operation = this.operationStatus[0].value;
      } else if (scope.row.orderStatus == 1) {
        this.operation = this.operationStatus[1].value;
      } else if (scope.row.orderStatus == 2) {
        this.operation = this.operationStatus[2].value;
      } else if (scope.row.orderStatus == 3) {
        this.operation = this.operationStatus[3].value;
      } else if (scope.row.orderStatus == 4) {
        this.operation = this.operationStatus[4].value;
      } else if (scope.row.orderStatus == 5) {
        this.operation = this.operationStatus[5].value;
      } else if (scope.row.orderStatus == 6) {
        this.operation = this.operationStatus[5].value;
      } else if (scope.row.orderStatus == 7) {
        this.operation = this.operationStatus[4].value;
      } else if (scope.row.orderStatus == 8) {
        this.operation = this.operationStatus[4].value;
      }
    },
    //操作列
    getCurrenData(data) {
      console.log(data, 3455566);
      switch (data) {
        case "关闭":
          this.closeOrder();
          break;
        case "确认付款":
          this.confirmPay();
          break;
        case "修改价格":
         this.editPrice();
          break;
        case "发货":
          alert(4);
          break;
        case "退款":
          alert(5);
          break;
        case "取消发货":
          this.editPrice();
          break;
        case "结算":
          alert(7);
          break;
        case "确认退款":
          alert(8);
          break;
        case "驳回":
          alert(9);
          break;
        case "确认发货":
          alert(10);
          break;
      }
    },
    //关闭订单
    closeOrder() {
      this.$confirm("是否确定关闭该订单?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: "false"
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "关闭成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消关闭"
          });
        });
    },
    //确认付款
    confirmPay() {
      this.$confirm("是否确确认付款?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "付款成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消付款"
          });
        });
    },
    //修改价格
    editPrice() {
      this.editVisible = true;
    },
    // 订单详情
    goOrderDetail(scope) {
      console.log(scope, "this.id");
      axiosRequest(
        "order/getOrderDetailsList",
        { id: scope.row.orderId },
        "post"
      ).then(res => {
        console.log(res);
        this.$router.push({
          path: "/orderdetail",
          query: { list: JSON.stringify(res) }
        });
      });
    },

    // 分页导航
    handleCurrentChange(val) {
      console.log(val);
      this.getPageNum = val;
      //this.getData();
    },

    //添加商品
    addGoods() {
      this.$router.push("/addgoods");
    },
    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.cities.length;
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleEdit(index, row) {
      this.idx = index;
      this.id = row.id;
      this.form = {
        id: row.id,
        name: row.name,
        date: row.date,
        address: row.address
      };
      this.editVisible = true;
    },

    // 价格修改
    saveEdit() {
      this.editVisible = false;
      this.$message.success("修改成功");
      console.log(this.editForm.price, "what//");
      if (this.tableData[this.idx].id === this.id) {
        this.$set(this.tableData, this.idx, this.form);
      } else {
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].id === this.id) {
            this.$set(this.tableData, i, this.form);
            return;
          }
        }
      }
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
