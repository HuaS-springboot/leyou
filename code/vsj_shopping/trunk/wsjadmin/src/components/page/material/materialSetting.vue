<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 配置管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-form ref="form" :model="materialSetting" label-width="80px">
        <el-form-item label="是否开启">
          <el-radio-group v-model="materialSetting.material_switch">
            <el-radio label="1">开启</el-radio>
            <el-radio label="0">关闭</el-radio>
          </el-radio-group>
        </el-form-item>
        <br />
        <!-- 选择模板 -->
        <el-form-item label="限制级别">
          <el-select
            v-model="limit"
            placeholder="请选择"
            v-show="materialSetting.material_switch==1"
            @change="levelChnage"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit()">保存设置</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../../../utils/request";
import { axiosRequest } from "@/api/index";
import { pageNum } from "../../common/pageNum";
import { objToArr, formatDateTime, arrToObj } from "@/components/common/common";
const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  name: "roletable",
  data() {
    return {
      form: {},
      limit: "",
      options: [],
      materialSetting: {
        material_switch: "1",
        material_level_id: null
      }
    };
  },
  created() {
    this.getMallSetting();
    this.getMemeberLevel();
  },
  computed: {},
  methods: {
    //获取商城配置
    getMallSetting() {
      axiosRequest(
        "system/query",
        {
          type: "5"
        },
        "post"
      ).then(res => {
        this.materialSetting = arrToObj(res);
        console.log(this.materialSetting, 10000000000);
      });
    },
    //查询会员等级
    getMemeberLevel() {
      axiosRequest("memeber/findAllLevel", "", "POST").then(res => {
        for (var i = 0; i < res.length; i++) {
          this.options[i] = {
            value: res[i].id,
            label: res[i].levelName
          };
        }
        console.log(this.options,666666)
        this.options.find(item => {
          if (item.value == this.materialSetting.material_level_id) {
            this.limit = item.label;
          }
        });
      });
    },
    levelChnage(data) {
      console.log(this.materialSetting, 133);
      this.options.find(item => {
        if (item.label == data) {
          this.materialSetting.material_level_id = item.value;
        }
      });
    },
    //保存配置
    onSubmit() {
      axiosRequest(
        "system/update",
        { vsjSysConfigList: objToArr(this.materialSetting) },
        "post"
      ).then(res => {
        this.$message.success("保存成功！");
      });
    }
  }
};
</script>

<style scoped>
.mt10 {
  margin-top: 20px;
}
</style>
