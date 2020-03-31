<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 会员列表
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="condition.nickName" placeholder="姓名" class="handle-input mr10"></el-input>
        <el-select
          v-model="condition.levelId"
          placeholder="会员等级"
          class="mr10 handle-select"
          :clearable="true"
          @clear="clearData"
        >
          <el-option
            v-for="item in financial"
            :key="item.lable"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button class="mr10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>

        <label class="mr10" style="color:#333;margin-left:20px;">提现时间</label>
        <div class="timeChoose">
          <el-date-picker
            v-model="queryTime"
            type="datetimerange"
            format="yyyy-MM-dd HH:mm:ss"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            @change="timeChange"
          ></el-date-picker>
        </div>
        <el-select
          v-model="condition.status"
          placeholder="审核状态"
          class="ml10 handle-select"
          :clearable="true"
          @clear="clearStatus"
          @change="checkStatusChange"
        >
          <el-option
            v-for="item in checkStatus"
            :key="item.lable"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column prop="nickName" label="会员名称"></el-table-column>

        <el-table-column prop="levelId" label="会员等级">
          <template slot-scope="scope">
            <span v-if="scope.row.levelId==0">VIP</span>
            <span v-if="scope.row.levelId==1">代理商</span>
            <span v-if="scope.row.levelId==2">加盟商</span>
            <span v-if="scope.row.levelId==3">股东</span>
          </template>
        </el-table-column>
        <el-table-column prop="withdrawAmount" label="提现金额"></el-table-column>
        <el-table-column prop="createTime" label="提现时间"></el-table-column>
        <el-table-column prop="status" label="审核状态">
          <template slot-scope="scope">
            <span v-if="scope.row.status==0">待审批</span>
            <span v-if="scope.row.status==1">审批通过</span>
            <span v-if="scope.row.status==2">驳回</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="160" align="left" :formatter="formatter">
          <template slot-scope="scope">
            <!-- 下架 编辑  库存管理  删除 -->
            <el-button
              v-if="scope.row.status==1"
              type="info"
              round
              size="mini"
              @click="approve(scope.$index,scope.row)"
            >审批</el-button>
            <el-button
              v-if="scope.row.status==1"
              type="info"
              round
              size="mini"
              @click="reject(scope.$index,scope.row)"
            >驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="this.total"
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
import request from "../../../../utils/request";
import { pageNum } from "../../../common/pageNum";
import { objToArr, formatDateTime } from "@/components/common/common";
const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  name: "roletable",
  data() {
    return {
      condition: {
        pageNum: 1,
        pageSize: pageNum.getPageSize,
        nickName: "",
        levelId: "",
        startTime: "",
        endTime: "",
        type: 1,
        status: null
      },
      total: 0,
      queryTime: [],
      financial: [
        { value: "0", label: "VIP" },
        { value: "1", label: "代理商" },
        { value: "2", label: "加盟商" },
        { value: "3", label: "股东" }
      ],
      checkStatus: [
        { value: "0", label: "待审批" },
        { value: "1", label: "审批通过" },
        { value: "2", label: "驳回" }
      ],
      /************************************************************** */
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
    this.search();
  },
  computed: {
    data() {
      return this.tableData.filter(d => {
        let is_del = false;
        for (let i = 0; i < this.del_list.length; i++) {
          if (d.name === this.del_list[i].name) {
            is_del = true;
            break;
          }
        }
        if (!is_del) {
          if (
            d.address.indexOf(this.select_cate) > -1 &&
            (d.name.indexOf(this.select_word) > -1 ||
              d.address.indexOf(this.select_word) > -1)
          ) {
            return d;
          }
        }
      });
    }
  },
  methods: {
    //查询
    search() {
      axiosRequest("financial/getReviewList", this.condition, "post").then(
        res => {
          console.log(res);
          this.tableData = res.list;
          this.total = res.total;
        }
      );
    },
    //审批或驳回
    review() {
      axiosRequest("financial/review", this.condition, "post").then(res => {
        console.log(res);
      });
    },
    //清除选择框的值
    clearData() {
      this.condition.levelId = "";
    },
    clearStatus() {
      this.condition.status = "";
    },
    checkStatusChange(data) {
      this.condition.status = data;
      this.search();
    },
    //开始结束时间监听
    timeChange(data) {
      this.condition.startTime = formatDateTime(data[0]);
      this.condition.endTime = formatDateTime(data[1]);
      this.search();
    },
    //审批
    approve(index, data) {
      this.$confirm("是否确认通过审批?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true
      })
        .then(action => {
          if (action == "confirm") {
            this.condition = data;
            this.condition.status = 1;
            this.review();

            this.$message({
              type: "success",
              message: "审批通过!"
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消审批"
          });
        });
    },
    //驳回
    reject(index, data) {
      this.$confirm("是否驳回?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true
      })
        .then(action => {
          if (action == "confirm") {
            this.condition = data;
            this.condition.status = 2;
            this.review();

            this.$message({
              type: "success",
              message: "驳回成功!"
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消驳回"
          });
        });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getDataList();
    },
    // 获取 easy-mock 的模拟数据
    getData() {},
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
    handleDelete(index, row) {
      this.idx = index;
      this.id = row.id;
      this.delVisible = true;
    },
    // delAll() {
    //     const length = this.multipleSelection.length;
    //     let str = '';
    //     this.del_list = this.del_list.concat(this.multipleSelection);
    //     for (let i = 0; i < length; i++) {
    //         str += this.multipleSelection[i].name + ' ';
    //     }
    //     this.$message.error('删除了' + str);
    //     this.multipleSelection = [];
    // },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 保存编辑
    saveEdit() {
      this.editVisible = false;
      this.$message.success(`修改第 ${this.idx + 1} 行成功`);
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
    },
    // 确定删除
    deleteRow() {
      this.$message.success("删除成功");
      this.delVisible = false;
      if (this.tableData[this.idx].id === this.id) {
        this.tableData.splice(this.idx, 1);
      } else {
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].id === this.id) {
            this.tableData.splice(i, 1);
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
.ml10 {
  margin-left: 10px;
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
