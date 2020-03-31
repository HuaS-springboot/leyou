<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 账户管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <!-- <el-button type="primary" icon="el-icon-delete" class="handle-del mr10" @click="delAll">批量删除</el-button> -->
        <!-- <el-select v-model="select_cate" placeholder="筛选省份" class="handle-select mr10">
                    <el-option key="1" label="广东省" value="广东省"></el-option>
                    <el-option key="2" label="湖南省" value="湖南省"></el-option>
        </el-select>-->
        <el-input v-model="fuzzyList" placeholder="请输入账号姓名手机号查询" class="handle-input mr10"></el-input>
        <el-button class="mr10" type="primary" icon="el-icon-search" @click="getUserList">搜索</el-button>
        <!-- 单选框 -->
        <el-select v-model="value" placeholder="角色列表" @change="searchRoleList">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button
          class="addbtn"
          type="primary"
          icon="el-icon-circle-plus-outline"
          @click="addEdit()"
        >新增账号</el-button>
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="account" label="账号" width="240"></el-table-column>
        <el-table-column prop="sysRoleModel" label="角色" width="120"></el-table-column>
        <el-table-column prop="nickName" label="姓名" width="120"></el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
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
    <el-dialog title="添加" :inline="true" :visible.sync="addVisible" width="50%">
      <el-form ref="form" :model="form" label-width="80px" label-position="left">
        <el-form-item label="姓名">
          <el-input v-model="addForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="addForm.account"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="addForm.pswd"></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="addForm.pswd"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="userName" placeholder="角色列表" @change="getRoleId">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限配置">
          <el-tree
            :data="dataManage"
            show-checkbox
            node-key="id"
            :default-checked-keys="checkedKeys"
            :default-expand-all="true"
            :props="defaultProps"
            ref="tree"
          ></el-tree>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAdd()">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :inline="true" :visible.sync="editVisible" width="50%">
      <el-form ref="form" :model="form" label-width="80px" label-position="left">
        <el-form-item label="姓名">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="form.account"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.pswd"></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="form.pswd"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roles[0].roleName" placeholder="角色列表" @change="editChangeManage">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限配置">
          <el-tree
            :data="dataManage"
            show-checkbox
            node-key="id"
            :default-checked-keys="checkedKeys"
            :default-expand-all="true"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit(form.id)">确 定</el-button>
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
export default {
  name: "accounttable",
  data() {
    return {
      fuzzyList: "", //账号/昵称/手机号
      dataManage: [], //右侧tree
      checkedKeys: [], //默认被勾选的id数组
      defaultProps: {
        children: "children",
        label: "name"
      },
      ids: "",
      total: 0,
      getPageSize: pageNum.getPageSize,
      userName: "", //添加账号的角色名
      roleId: null,
      /******************************** */
      tableData: [],
      cur_page: 1,
      multipleSelection: [],
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      addVisible: false,
      addForm: {
        // id: null,
        nickName: "",
        account: "",
        phone: "",
        pswd: "",
        createId: ""
      },
      form: {
        id: null,
        nickName: "",
        account: "",
        phone: "",
        pswd: "",
        createId: "",
        roles: [
          {
            roleName: ""
          }
        ]
      },
      options: [],
      value: "",
      idx: -1,
      id: -1
    };
  },
  created() {
    this.getUserList();
    this.getRoleList();
    this.getTreeData();
  },
  computed: {},
  methods: {
    //获取查询
    getUserList() {
      axiosRequest(
        "user/getSysUserList",
        { fuzzyList: this.fuzzyList },
        "post"
      ).then(res => {
        this.total = res.total;
        this.tableData = res.list;
        this.tableData.find(item => {
          if (item.sysRoleModel[0]) {
            item.sysRoleModel = item.sysRoleModel[0].roleName;
          }
        });
      });
    },
    //角色列表
    getRoleList() {
      axiosRequest("role/getRoleList", {}, "post").then(res => {
        console.log(res, 132);
        for (let i = 0; i < res.list.length; i++) {
          this.options[i] = {
            value: res.list[i].id,
            label: res.list[i].roleName
          };
        }
        console.log(this.options, 456);
      });
    },
    //查询条件角色列表的监听
    searchRoleList(data) {
      axiosRequest("user/getSysUserById", { id: data }, "post").then(res => {
        console.log(res, "res003");
        this.form = res;
      });
    },
    //获取treeData
    getTreeData() {
      axiosRequest("user/getPermissionList", "", "post").then(res => {
        this.dataManage = res;
      });
    },
    /************************************** */
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getUserList();
    },

    search() {
      this.is_search = true;
    },
    // 获取 easy-mock 的模拟数据
    formatter(row, column) {
      return row.address;
    },
    //新增角色
    addEdit() {
      this.addVisible = true;
    },
    // 编辑
    handleEdit(index, row) {
      axiosRequest("user/getSysUserById", { id: row.id }, "post").then(res => {
        console.log(res, "res000002");
        this.checkedKeys = [];
        for (let i = 0; i < res.roles.length; i++) {
          for (let j = 0; j < res.roles[i].permission.length; j++) {
            this.checkedKeys.push(res.roles[i].permission[i].id);
          }
        }
        this.form = res;
        this.addUser(row.id, res.roles[0].id);
      });
      this.editVisible = true;
    },
    //编辑-》切换不同角色，显示对应角色的权限
    editChangeManage(data) {
      console.log(data,'shishishishish')
      axiosRequest("role/getRoleById", { id: data }, "post").then(res => {
        console.log(res, "eeeee");
        this.checkedKeys = [];
        for (let i = 0; i < res.permission.length; i++) {
          this.checkedKeys.push(res.permission[i].id);
        }
        console.log(this.checkedKeys,'checkedKeyschange')
      });
    },
    //添加用户角色信息
    addUser(id, rolesId) {
      axiosRequest(
        "user/addUserRoleJoin",
        { id: id, roles: [{ id: this.roleId }] },
        "post"
      ).then(res => {});
    },
    //删除
    handleDelete(index, row) {
      this.idx = index;
      this.id = row.id;
      this.delVisible = true;
    },
    //复选框
    handleSelectionChange(val) {
      //   val.find(item => {
      //     this.ids += item.id + ",";
      //   });
      //   console.log(val);
    },
    // 保存编辑
    saveEdit(id) {
      console.log(id);
      if (id) {
        //编辑
      } else {
      }
      this.form.createId = window.localStorage.getItem("userId") * 1;
      this.editVisible = false;
      axiosRequest("user/editSysUser", this.form, "post").then(res => {
        console.log(res);
      });
    },
    getRoleId(data) {
      this.roleId = data;
      axiosRequest("role/getRoleById", { id: data }, "post").then(res => {
        console.log(res, "eeeee");
        this.checkedKeys = [];
        for (let i = 0; i < res.permission.length; i++) {
          this.checkedKeys.push(res.permission[i].id);
        }
      });
    },
    saveAdd() {
      // let checkNodeId;
      // let checkData = this.$refs.tree.getCheckedKeys();
      // for (let i = 0; i < checkData.length; i++) {
      //   checkNodeId = checkData[checkData.length - 1];
      // }
      this.addForm.createId = window.localStorage.getItem("userId") * 1;
      this.addVisible = false;
      axiosRequest("user/editSysUser", this.addForm, "post").then(res => {
        this.getUserList();
        console.log(res, 10002);
        this.addUser(res, this.roleId);
      });
    },
    // 确定删除
    deleteRow() {
      axiosRequest("user/delSysUser", { ids: this.id + "" }, "post").then(
        res => {
          this.getUserList();
          this.$message.success("删除成功");
        }
      );
      this.delVisible = false;
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
  position: absolute;
  right: 5%;
}
</style>
