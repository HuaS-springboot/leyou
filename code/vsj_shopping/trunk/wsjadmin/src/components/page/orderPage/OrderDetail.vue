<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 订单详情
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="form-box" style="width:100%">
        <el-form ref="form" :model="form" label-width="120px" label-position="left">
          <!-- <div class="titleTag">订单详情</div> -->
          <el-form-item label="订单号" class="wid50">
            <el-input v-model="form.orderNo" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="下单时间" class="wid50">
            <el-input v-model="form.createTime" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="下单用户" class="wid50">
            <el-input v-model="form.nickName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="付款时间" class="wid50">
            <el-input v-model="form.payTime" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="收货人" class="wid50">
            <el-input v-model="form.realName"></el-input>
          </el-form-item>
          <el-form-item label="收货地址" class="wid50">
            <el-input v-model="form.site"></el-input>
          </el-form-item>
          <el-form-item label="收货电话" class="wid50">
            <el-input v-model="form.telPhone"></el-input>
          </el-form-item>
          <el-form-item label class="wid50"></el-form-item>
          <el-form-item label="订单应收" class="wid50">
            <el-input v-model="form.productAmountTotal" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="订单优惠" class="wid50">
            <el-input v-model="form.name" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="订单状态" class="wid50">
            <el-input v-model="form.orderStatus"></el-input>
          </el-form-item>
          <el-form-item label="订单实收" class="wid50">
            <el-input v-model="form.orderAmountTotal"></el-input>
          </el-form-item>
          <el-form-item label="支付方式" class="wid50">
            <el-input v-model="form.payChannel" :disabled="true">
              <template slot-scope="scope">
                <span v-if="scope.payChannel=='WX'">微信</span>
                <span v-if="scope.payChannel=='zfb'">支付宝</span>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label class="wid50"></el-form-item>
          <el-form-item label="奖励详情" class="wid90">
            <el-table
              :data="orderRecord"
              border
              class="table"
              ref="multipleTable"
              @row-click="goMemberDetail"
            >
              <el-table-column prop="nickName" label="会员"></el-table-column>
              <el-table-column prop="levelName" label="会员级别"></el-table-column>
              <el-table-column prop="operationMoney" label="奖励金额"></el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="用户备注" class="wid50">
            <el-input type="textarea" v-model="form.userRemark" size="medium "></el-input>
          </el-form-item>
          <el-form-item label="商户备注" class="wid50">
            <el-input type="textarea" v-model="form.shopRemark" size="medium "></el-input>
          </el-form-item>
          <el-form-item label="商品名称" class="wid50">
            <el-input v-model="form.productName" size="medium ">
              <el-button slot="append" icon="el-icon-search" @click="goGoodsDetail">查看详情</el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="订单商品" class="wid90">
            <el-table :data="goodsList" border class="table" ref="multipleTable">
              <el-table-column prop="specsId" label="商品规格"></el-table-column>
              <el-table-column prop="number" label="数量"></el-table-column>
              <el-table-column prop="productPrice" label="单价"></el-table-column>
              <el-table-column prop="subtotal" label="商品总价"></el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="orderSteps">订单轨迹</el-button>
            <el-button type="info" @click="cancel">取消</el-button>
            <el-button type="primary" @click="onSubmit">保存修改</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!-- 订单轨迹弹出框 -->
    <div style="height: 600px;">
      <el-dialog title="订单轨迹" :visible.sync="stepVisible" width="40%">
        <el-steps direction="vertical" :active="0" space="70px">
          <el-step
            v-for="item in stepList"
            :key="item.operateType"
            status="success"
            :title="item.operateType==0?'未付款':item.operateType==1?'待发货':item.operateType==2?'待收货':item.operateType==3?'已签收':item.operateType==4?'已结算':item.operateType==5?'未发货退款':item.operateType==6?'已发货退款':item.operateType==7?'已退款':item.operateType==8?'已退款':''"
            :description="item.remark+'【'+item.creteTime+'】'"
          ></el-step>
        </el-steps>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { axiosRequest } from "@/api/index";
import bus from "@/components/common/bus";
export default {
  name: "baseform",
  data: function() {
    return {
      options: [
        {
          value: "guangdong",
          label: "广东省",
          children: [
            {
              value: "guangzhou",
              label: "广州市",
              children: [
                {
                  value: "tianhe",
                  label: "天河区"
                },
                {
                  value: "haizhu",
                  label: "海珠区"
                }
              ]
            },
            {
              value: "dongguan",
              label: "东莞市",
              children: [
                {
                  value: "changan",
                  label: "长安镇"
                },
                {
                  value: "humen",
                  label: "虎门镇"
                }
              ]
            }
          ]
        },
        {
          value: "hunan",
          label: "湖南省",
          children: [
            {
              value: "changsha",
              label: "长沙市",
              children: [
                {
                  value: "yuelu",
                  label: "岳麓区"
                }
              ]
            }
          ]
        }
      ],
      orderRecord: [],
      goodsList: [],
      getPageNum: "",
      form: {
        orderId: "",
        orderNo: "",
        createTime: "",
        productName: "",
        site: "",
        realName: "",
        telPhone: "",
        orderStatus: "0",
        userRemark: "",
        shopRemark: "",
        nickName: "",
        payTime: "",
        productAmountTotal: "",
        orderAmountTotal: ""
      },
      stepVisible: false,
      stepList: []
    };
  },
  created() {
    this.getListData();
  },
  methods: {
    //获取传过来的参数
    getListData() {
      this.form = JSON.parse(window.localStorage.getItem("order_detail"));
      this.form.payChannel = this.form.payChannel == "WX" ? "微信" : "支付宝";
      this.form.orderStatus =
        this.form.orderStatus == "0"
          ? "待付款"
          : this.form.orderStatus == "1"
          ? "待发货"
          : this.form.orderStatus == "2"
          ? "待收货"
          : this.form.orderStatus == "3"
          ? "已签收"
          : this.form.orderStatus == "4"
          ? "已结算"
          : this.form.orderStatus == "5"
          ? "未发货退款"
          : this.form.orderStatus == "6"
          ? "已发货退款"
          : this.form.orderStatus == "7"
          ? "已退款"
          : this.form.orderStatus == "8"
          ? "已关闭"
          : "";
      this.goodsList = this.form.orderDetail;
      this.orderRecord = this.form.orderRecord;
    },
    // 会员点击跳转到会员详情
    goMemberDetail(row, event, column) {
      sessionStorage.setItem("memberDetailData", JSON.stringify(row));
      this.$router.push("/MemberDetail");
    },
    // 点击跳转到商品详情
    goGoodsDetail() {
      sessionStorage.setItem("goodsId", JSON.stringify(this.form.productId));
      this.$router.push("/AddGoods");
    },
    //查询会员详情
    queryMemberDetail() {
      axiosRequest(
        "memeber/memberDetail",
        { id: this.memberInfo.id },
        "POST"
      ).then(res => {
        this.memberDetail = res;
      });
    },
    //订单轨迹
    orderSteps() {
      this.stepVisible = true;
      axiosRequest(
        "order/selectOrderOperation",
        { orderId: this.form.orderId },
        "post"
      ).then(res => {
        console.log(res, 666);
        if (res) {
          this.stepList = res;
        }
      });
    },
    cancel() {
      this.$router.go(-1);
    },
    //保存修改
    onSubmit() {
      this.form.orderStatus =
        this.form.orderStatus == "待付款"
          ? "0"
          : this.form.orderStatus == "待发货"
          ? "1"
          : this.form.orderStatus == "待收货"
          ? "2"
          : this.form.orderStatus == "已签收"
          ? "3"
          : this.form.orderStatus == "已结算"
          ? "4"
          : this.form.orderStatus == "未发货退款"
          ? "5"
          : this.form.orderStatus == "已发货退款"
          ? "6"
          : this.form.orderStatus == "已退款"
          ? "7"
          : this.form.orderStatus == "已关闭"
          ? "8"
          : "";
      axiosRequest("order/updateOrder", this.form, "post").then(res => {
        this.getOrderDetail();
      });
    },
    getOrderDetail() {
      axiosRequest(
        "order/getOrderDetailsList",
        { id: this.form.orderId },
        "post"
      ).then(res => {
        window.localStorage.setItem("order_detail", JSON.stringify(res));
        this.form = JSON.parse(window.localStorage.getItem("order_detail"));
      });
    }
  }
};
</script>
<style scoped>
.titleTag {
  font-size: 17px;
  padding: 8px 0;
  color: #409eff;
}
.wid50 {
  width: 40%;
  margin-right: 10%;
  display: inline-block;
}
.wid90 {
  width: 90%;
  margin-right: 10%;
  display: inline-block;
}
.notice {
}
</style>
