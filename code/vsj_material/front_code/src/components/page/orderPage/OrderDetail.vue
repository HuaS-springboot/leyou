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
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="收货地址" class="wid50">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="收货电话" class="wid50">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label class="wid50"></el-form-item>
          <el-form-item label="订单应收" class="wid50">
            <el-input v-model="form.productAmountTotal" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="订单优惠" class="wid50">
            <el-input v-model="form.name" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="订单状态" class="wid50">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="订单实收" class="wid50">
            <el-input v-model="form.orderAmountTotal"></el-input>
          </el-form-item>
          <el-form-item label="支付方式" class="wid50">
            <el-input v-model="form.name" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label class="wid50"></el-form-item>
          <el-form-item label="奖励详情" class="wid90">
            <el-table :data="memberList" border class="table" ref="multipleTable">
              <el-table-column prop="orderNo" label="会员"></el-table-column>
              <el-table-column prop="orderNo" label="会员级别"></el-table-column>
              <el-table-column prop="orderNo" label="奖励金额"></el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="用户备注" class="wid50">
            <el-input type="textarea" v-model="form.desc" size="medium "></el-input>
          </el-form-item>
          <el-form-item label="商户备注" class="wid50">
            <el-input type="textarea" v-model="form.desc" size="medium "></el-input>
          </el-form-item>
          <el-form-item label="订单商品" class="wid90">
            <el-table :data="goodsList" border class="table" ref="multipleTable">
              <el-table-column prop="orderNo" label="商品名称"></el-table-column>
              <el-table-column prop="orderNo" label="商品规格"></el-table-column>
              <el-table-column prop="orderNo" label="数量"></el-table-column>
              <el-table-column prop="orderNo" label="单价"></el-table-column>
              <el-table-column prop="orderNo" label="商品总价"></el-table-column>
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
    <el-dialog title="订单轨迹" :visible.sync="stepVisible" width="40%" >
      <el-steps direction="vertical" :active="0" space="70px">
        <el-step title="已打印订单" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="已出库" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="已接受" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="订单完毕" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="订单完毕" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="订单完毕" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="订单完毕" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="订单完毕" description="这是一段很长很长很长的描述性文字"></el-step>
        <el-step title="订单完毕" description="这是一段很长很长很长的描述性文字"></el-step>
      </el-steps>
    </el-dialog>
    </div>
  </div>
</template>

<script>
import { axiosRequest } from "@/api/index";
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
      memberList: [],
      goodsList: [],
      form: {
        orderNo: "",
        createTime: "",
        productName: "",
        nickName: "",
        payTime: "",
        productAmountTotal: "",
        orderAmountTotal: "",

        name: "这里是订单号",
        region: "",
        date1: "",
        date2: "",
        delivery: true,
        type: ["步步高"],
        resource: "小天才",
        desc: "",
        options: []
      },
      stepVisible: false
    };
  },
  created() {
    this.getListData();
  },
  methods: {
    //获取传过来的参数
    getListData() {
      let listData = JSON.parse(this.$route.query.list);
      this.form = listData;
      this.goodsList = this.form.orderDetail;
    },
    //订单轨迹
    orderSteps() {
      this.stepVisible = true;
    },
    cancel(){

    },
    //保存修改
    onSubmit() {
      this.$message.success("提交成功！");
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
