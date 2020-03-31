<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 批量发货
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="form-box">
        <el-form ref="form" label-width="160px" label-position="left">
          <ul class="message">
            <li>功能简介:</li>
            <li>1、订单管理中筛选下载未发货订单</li>
            <li>2、下载批量发货导入模板到本地电脑</li>
            <li>3、按照格式添加对应订单的订单号</li>
            <li>4、选择快递公司</li>
            <li>5、上传修改好的模板导入</li>
            <br />
            <li>注意事项:</li>
            <li>1、最多支持X条订单导入</li>
            <li>2、模板不可自行修改</li>
            <li>3、导入尽量避免在高峰期进行，以免发生未知错误</li>
            <br />
            <br />
          </ul>

          <el-form-item label="快递公司" label-width="120px">
            <!-- 一级分类-->
            <el-select
              v-model="fileData.logisticsType"
              placeholder="快递公司"
              class="mr10 handle-select"
            >
              <el-option v-for="item in expressCompany" :key="item.value" :value="item.key"></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="文件上传" label-width="120px">
            <el-upload
              class="upload-demo"
              drag
              action="http://jsonplaceholder.typicode.com/api/posts/"
              multiple
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将文件拖到此处，或
                <em>点击上传</em>
              </div>
            </el-upload>
          </el-form-item>-->

          <el-form-item>
            <el-button type="primary" @click="downLoad">下载导入模板</el-button>
            <el-button type="primary" @click="importFile">确认导入</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <el-dialog title="导入模板" :visible.sync="importVisible" width="40%" class="typeEdit">
      <el-upload class="upload" drag :action="uploadUrl" :data="fileData" >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          只能上传 Excel 格式的文件
          <em>点击上传</em>
        </div>
      </el-upload>
    </el-dialog>
  </div>
</template>
<script>
import { objToArr } from "@/components/common/common";
import request from "../../../utils/request";
export default {
  name: "baseform",
  data: function() {
    return {
      expressCompany: objToArr(
        JSON.parse(window.localStorage.getItem("dictionary")).express_company
      ),
      uploadUrl: request.SERVER + "order/importExcel",
      importVisible: false,
      //快递公司
      fileData: {
        logisticsType: ""
      },
    };
  },
  created() {
    this.getData();
  },
  methods: {
    downLoad() {
      window.location.href = request.SERVER + "order/exportExcel";
    },
    importFile() {
      if (this.fileData.logisticsType) {
        this.importVisible = true;
      } else {
        this.$message("请先选择快递类型");
      }
    }
  }
};
</script>
<style>
.message {
  font-size: 14px;
  color: #606266;
}
.message li {
  list-style: none;
}
.upload {
  width: 500px;
}
</style>
