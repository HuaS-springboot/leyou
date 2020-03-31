<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 角色管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
          class="addbtn"
          type="primary"
          icon="el-icon-circle-plus-outline"
          @click="addRole()"
        >新增角色</el-button>
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column prop="roleName" label="角色名称"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button>
            <el-button
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="this.total"
          :page-size="this.getPageSize"
        ></el-pagination>
      </div>
    </div>

    <!-- 添加弹出框 -->
    <el-dialog title="添加" :visible.sync="addVisible" width="30%">
      <el-form ref="form" :model="addForm" label-width="80px" label-position="left">
        <el-form-item label="角色名称">
          <el-input v-model="addForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="addForm.roleCode"></el-input>
        </el-form-item>
        <el-form-item label="角色权限">
          <el-tree
            :data="dataManage"
            show-checkbox
            node-key="id"
            :default-expand-all="true"
            :props="defaultProps"
            ref="tree"
            @check-change="handleCheckChange"
          ></el-tree>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAdd">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
      <el-form ref="form" :model="form" label-width="80px" label-position="left">
        <el-form-item label="角色名称">
          <el-input v-model="form.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色权限">
          <el-tree
            :data="dataManage"
            show-checkbox
            node-key="id"
            :default-expanded-keys="[1, 2]"
            :default-checked-keys="checkedKeys"
            :default-expand-all="true"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>

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
import { axiosRequest } from "../../../../api";
import { pageNum } from "../../../common/pageNum";
const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  name: "roletable",
  data() {
    return {
      tableData: [],
      total: null,
      getPageSize:pageNum.getPageSize,
      cur_page: 1,
      multipleSelection: [],
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      addVisible: false,
      delVisible: false,
      dataManage: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      checkedKeys: [], //默认被勾选的id数组
      addForm: {
        roleCode: "",
        roleName: "",
        createId: null
      },
      form: {
        id: null,
        roleName: "",
        roleCode: "",
        createId: null
      },
      treeCheckedId: null,
      value: "",
      idx: -1,
      id: -1,
      //
      checkAll: false,
      checkedCities: ["商品管理"],
      cities: cityOptions,
      isIndeterminate: true
    };
  },
  created() {
    this.getRoleList();
    this.getTreeData();
  },
  computed: {},
  methods: {
    getRoleList() {
      axiosRequest("role/getRoleList", {}, "post").then(res => {
        this.tableData = res.list;
        this.total = res.total;
        console.log(res, "000");
      });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {},
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
    // 新增角色
    addRole() {
      this.addVisible = true;
    },
    saveAdd() {
      let checkNodeId;
      let checkData = this.$refs.tree.getCheckedKeys();
      for (let i = 0; i < checkData.length; i++) {
        checkNodeId = checkData[checkData.length - 1];
      }
      this.addForm.createId = window.localStorage.getItem("userId") * 1;
      axiosRequest("role/editRole", this.addForm, "post").then(res => {
        this.addVisible = false;
        this.getRoleList();
        console.log(res, "res.id");
        this.addRolePermission(res, checkNodeId);
      });
    },
    // 修改角色
    saveEdit() {
      this.addVisible = true;
      this.form.createId = window.localStorage.getItem("userId") * 1;
      axiosRequest("role/editRole", this.form, "post").then(res => {
        console.log(res);
      });
    },
    // 编辑
    handleEdit(index, row) {
      this.checkedKeys = [];
      for (let i = 0; i < row.permission.length; i++) {
        this.checkedKeys.push(row.permission[i].id);
      }
      axiosRequest("role/getRoleById", { id: row.id }, "post").then(res => {
        this.form = res;
        this.addRolePermission(row.id, res.permission[0].id);
        this.editVisible = true;
      });
    },
    //获取treeData
    getTreeData() {
      axiosRequest("user/getPermissionList", "", "post").then(res => {
        this.dataManage = res;
        console.log(this.dataManage, 666666);
      });
    },
    //添加角色信息
    addRolePermission(id, permissionId) {
      axiosRequest(
        "role/addRolePermission",
        { id: id, permissions: [{ id: permissionId }] },
        "post"
      ).then(res => {
        console.log(res);
      });
    },
    handleDelete(index, row) {
      this.idx = index;
      this.id = row.id;
      this.delVisible = true;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //tree
    handleCheckChange(val) {
      // this.treeCheckedId = val.id;
      // console.log(val, 135);
    },
    // 确定删除
    deleteRow() {
      axiosRequest("role/delRole", { ids: this.id }, "post").then(res => {
        console.log(res);
        this.$message.success("删除成功");
        this.delVisible = false;
      });
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
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
.addbtn {
  position: relative;
}
</style>
