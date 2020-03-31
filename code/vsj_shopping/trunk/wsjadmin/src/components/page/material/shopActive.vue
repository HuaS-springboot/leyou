<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 素材管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
          class="addbtn"
          type="primary"
          icon="el-icon-circle-plus-outline"
          @click="addmaterial"
        >添加素材</el-button>
      </div>
      <el-table
        :data="mateList"
        border
        class="table"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="content" label="内容" width="350"></el-table-column>
        <el-table-column prop="img" label="图片" min-width="500">
          <template slot-scope="scope">
            <img
              v-for="(item,i) in scope.row.img"
              :src="item"
              :key="i"
              class="head_pic"
              @click="handlePictureCardPreview(item)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="productId" label="关联商品">
          <template slot-scope="scope">
            <div
              v-for="(item,index) in productList"
              :key="index"
            >{{scope.row.productId==item.productId?item.productName:''}}</div>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序权重"></el-table-column>
        <el-table-column label="操作" align="center" :formatter="formatter" min-width="100">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button
              class="typeIcon red"
              type="text"
              icon="el-icon-edit-outline"
              @click="edit(scope.$index,scope.row)"
            ></el-button>
            <!-- 删除 -->
            <el-button
              class="typeIcon red"
              type="text"
              icon="el-icon-delete"
              @click="del(scope.$index,scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="handle-box-edit">
        <el-button
          class="addeditbtn"
          type="primary"
          icon="el-icon-circle-plus-outline"
          @click="editWeight"
        >权重修改</el-button>
      </div>
      <div class="pagination">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="this.total"
          :page-size="this.pageSize"
        ></el-pagination>
      </div>
    </div>

    <!-- 添加素材弹出框 -->
    <el-dialog title="添加素材" :visible.sync="addVisible" width="40%" class="typeEdit">
      <el-form :model="addData" label-width="110px" label-position="left">
        <el-form-item label="关联商品">
          <el-input v-model="chooseGoodsName" class="mr10">
            <el-button slot="append" icon="el-icon-search" @click="chooseProduct">选择商品</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="排序权重">
          <el-input v-model="addData.sort" class="mr10"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="addData.content" class="mr10"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
            list-type="picture-card"
            :limit="9"
            :on-preview="addhandlePictureCardPreview"
            :on-remove="handleRemove"
            :on-exceed="handleExceed"
            :on-success="addHandleAvatarSuccess"
            :headers="uploadHeaders"
            ref="addUpload"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveAdd">保 存</el-button>
        <el-button @click="addVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="40%" class="typeEdit">
      <el-form :model="editForm" label-width="110px" label-position="left">
        <el-form-item label="关联商品">
          <el-input v-model="chooseGoodsName" class="mr10">
            <el-button slot="append" icon="el-icon-search" @click="chooseProduct">选择商品</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="排序权重">
          <el-input v-model="editForm.sort" class="mr10"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="editForm.content" class="mr10"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <!-- <img v-for="(item,i) in editForm.img" :src="item" :key="i" class="edit_pic" /> -->
          <div class="img-list">
            <div class="img-content" v-for="(item,key) in editFileList" :key="key">
              <img :src="item" class="edit_pic" />
              <!-- 删除icon -->
              <!-- 放大icon -->
              <div class="layer">
                <i @click="handlePictureCardPreview(item)" class="el-icon-view"></i>
                <i @click="handleFileRemove(item)" class="el-icon-delete"></i>
              </div>
            </div>
          </div>
          <el-upload
            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
            list-type="picture-card"
            :limit="9"
            :on-preview="edithandlePictureCardPreview"
            :on-remove="edithandleRemove"
            :on-exceed="handleExceed"
            :on-success="handleAvatarSuccess"
            :headers="uploadHeaders"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveEdit">保 存</el-button>
        <el-button @click="editVisible = false">取 消</el-button>
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
    <!-- 权重批量修改编辑框 -->
    <el-dialog title="编辑权重" :visible.sync="weightVisible" width="451px" center>
      <el-table :data="weightList" border class="guigeEdit">
        <el-table-column prop="content" label="内容" width="280"></el-table-column>
        <el-table-column prop="sort" label="权重" width="120">
          <template scope="scope">
            <el-input size="small" v-show="true" v-model="scope.row.sort" placeholder="请输入权重值"></el-input>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="weightVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEditWeight">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 图片放大 -->
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>
    <vChoosegood
      :chooseIdArr="chooseIdArr"
      ref="chooseGoods"
      v-if="chooseVisible"
      @saveChoose="saveChoose"
      @closeChoose="closeChoose"
    ></vChoosegood>
  </div>
</template>

<script>
import request from "../../../utils/request";
import { axiosRequest } from "@/api/index";
import { pageNum } from "../../common/pageNum";
import { objToArr, formatDateTime } from "@/components/common/common";
import vChoosegood from "@/components/common/chooseGood";
const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  components: {
    vChoosegood
  },
  name: "roletable",
  data() {
    return {
      uploadHeaders: {
        token: localStorage.getItem("token"),
        userId: localStorage.getItem("userId"),
        platformCode: this.$store.state.platformCode
      },
      productName: "", //关联的商品名字
      uploadFileList: [], //上传的图片列表
      editFileList: [], //修改--以前上传的图片
      editFileAdd: [], //修改的时候，添加图片，先存在这，保存时再和editFileList合并
      weightList: [], //权重集合
      chooseVisible: false, //控制选择商品的显示隐藏
      chooseIdArr: [], // 传过去的商品id
      chooseGoodsName: "", //选择的商品的名字
      addData: {
        goods: "",
        sort: "",
        content: "",
        img: ""
      },
      editForm: {
        goods: "",
        sort: "",
        content: "",
        img: ""
      },
      //商品查询条件
      conditon: {
        title: "",
        oneCateId: "",
        twoCateId: "",
        status: "",
        priceMax: "",
        priceMin: "",
        pageNum: 1,
        pageSize: pageNum.getPageSize
      },
      status: "",
      type: "",
      payType: "",
      //当前行的id
      id: "",
      // 订单筛选条件
      getNum: 1,
      pageSize: pageNum.getPageSize,
      dialogImageUrl: "",
      dialogVisible: false,

      //选中的id集合
      ids: "",
      mateList: [],
      productList: [],
      total: 0,
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
      addVisible: false,
      editVisible: false,
      delVisible: false,
      weightVisible: false,
      form: {
        name: "",
        date: "",
        address: ""
      },
      radio: "",
      dateType: []
    };
  },
  created() {
    this.getProductList();
  },
  computed: {},
  methods: {
    //获取素材列表
    getDataList() {
      axiosRequest(
        "material/getMaterialList",
        { pageNum: this.getNum, pageSize: pageNum.getPageSize },
        "post"
      ).then(res => {
        this.mateList = res.list;
        for (let i = 0; i < this.mateList.length; i++) {
          if (this.mateList[i].img) {
            this.mateList[i].img = this.mateList[i].img.split(",");
          }
        }
      });
    },
    //根据id查询商品列表
    getProductList() {
      axiosRequest("product/getProductList", this.conditon, "post").then(
        res => {
          this.productList = res.list;
          this.getDataList();
        }
      );
    },
    //添加素材
    addmaterial() {
       this.uploadFileList = [];
      this.chooseGoodsName = "";
      this.addData.productId = null;
      this.addData.content = "";
      this.addData.img = "";
      this.addData.sort = "";
      this.addVisible = true;
    },
    saveAdd() {
      this.addVisible = false;
      for (let i = 0; i < this.uploadFileList.length; i++) {
        this.addData.img += this.uploadFileList[i].response.data.url;
      }
      axiosRequest("material/insertMaterial", this.addData, "post").then(
        res => {
          this.$message.success("添加成功");
          this.getDataList();
        }
      );
    },
    //选择商品
    chooseProduct() {
      this.chooseVisible = true;
    },
    saveChoose(chooseIdArr, chooseGoodsName) {
      if (chooseIdArr && chooseGoodsName) {
        if (this.addVisible == true) {
          this.addData.productId = chooseIdArr.join("");
        } else if (this.editVisible == true) {
          this.editForm.productId = chooseIdArr.join("") * 1;
        }
        this.chooseGoodsName = chooseGoodsName;
      }
      this.chooseVisible = false;
    },
    closeChoose() {
      this.chooseVisible = false;
    },
    //添加删除图片
    handleRemove(file, fileList) {
      let index = this.uploadFileList.indexOf(file);
      this.uploadFileList.splice(index, 1);
    },
    // 修改---删除刚上传的图片
    edithandleRemove(file, fileList) {
      let index = this.editFileAdd.indexOf(file);
      this.editFileAdd.splice(index, 1);
    },
    //添加的图片上传成功
    addHandleAvatarSuccess(res, file) {
      this.$message.success("上传成功！");
      this.uploadFileList.push(file);
    },
    //修改的图片上传成功
    handleAvatarSuccess(res, file) {
      this.$message.success("上传成功！");
      this.editFileAdd.push(file);
    },
    //获取选中的当前行数据中的id
    handleSelectionChange(val) {
      this.weightList = val;
    },
    //修改
    edit(index, data) {
      this.chooseGoodsName = "";
      this.editFileAdd = [];
      this.editVisible = true;
      this.editForm = data;
      this.productList.find(item => {
        if (item.productId == data.productId) {
          this.chooseGoodsName = item.productName;
        }
      });
      for (let i = 0; i < this.editForm.length; i++) {
        if (this.editForm[i].img) {
          this.editForm[i].img = this.editForm[i].img.split(",");
        }
      }
      this.editFileList = data.img;
    },
    saveEdit() {
      for (let i = 0; i < this.editFileAdd.length; i++) {
        this.editFileList.push(this.editFileAdd[i].response.data.url);
      }
      this.editForm.img = this.editFileList.join(",");
      axiosRequest(
        "material/updateMaterial",
        { materialList: [this.editForm] },
        "post"
      ).then(res => {
        this.getDataList();
        this.editVisible = false;
      });
    },
    //删除这行数据
    del(index, data) {
      this.id = data.id;
      this.delVisible = true;
    },
    deleteRow() {
      axiosRequest("material/deleteMaterial", { id: this.id }, "post").then(
        res => {
          this.$message.success("删除成功");
          this.getDataList();
        }
      );
      this.delVisible = false;
    },
    //权重批量修改
    editWeight() {
      this.weightVisible = true;
    },
    //保存权重修改结果
    saveEditWeight() {
      for (let i = 0; i < this.weightList.length; i++) {
        this.weightList[i].img = this.weightList[i].img.join("");
      }
      if (this.weightList.length == 0) {
        this.$message.error("请选择需要操作的商品");
      } else {
        axiosRequest(
          "material/updateMaterial",
          {
            materialList: this.weightList
          },
          "post"
        ).then(res => {
          this.weightVisible = false;
          this.$message.success("修改成功");
          this.getDataList();
        });
      }
    },
    //图片放大
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file;
      this.dialogVisible = true;
    },
    //修改的---删除以前上传的图片
    handleFileRemove(file) {
      let index = this.editFileList.indexOf(file);
      this.editFileList.splice(index, 1);
    },
    //点击已上传的图片的钩子函数---图片放大
    addhandlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      // this.addData.img += file.url;
      this.dialogVisible = true;
    },

    edithandlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleExceed(files, fileList) {
      this.$message.warning("最多只可以上传9张图片！");
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
.container {
  position: relative;
}
.handle-box {
  margin-bottom: 45px;
  color: #dcdfe6;
}
.handle-box-edit {
  margin-top: 20px;
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
.addeditbtn {
  position: absolute;
  left: 5%;
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
.dialog-footer {
  position: absolute;
  left: 5%;
  bottom: 5%;
}
.head_pic {
  width: 50px;
  height: 50px;
  margin-right: 5px;
}
.edit_pic {
  width: 148px;
  height: 148px;
  margin-right: 5px;
}

.img-list {
  overflow: hidden;
  width: 100%;
}
.img-list .img-content {
  position: relative;
  display: inline-block;
  width: 148px;
  height: 148px;
  padding-bottom: 5px;
  margin: 5px 20px 20px 0;
  border: 1px solid #d1dbe5;
  border-radius: 4px;
  transition: all 0.3s;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
}
.img-list .img-content img {
  display: block;
  width: 100%;
  height: 100%;
  margin: 0 auto;
  border-radius: 4px;
}
.img-list .img-content .name {
  margin-top: 10px;
}
.img-list .img-content .name > div {
  width: 90%;
  text-overflow: ellipsis;
  overflow: hidden;
  height: 25px;
  line-height: 25px;
}

.img-list .img-content:hover .layer {
  opacity: 1;
}
.img-list .img-content .layer {
  opacity: 0;
  transition: all 0.3s;
}
.img-list .img-content .layer {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  height: 148px;
  color: #fff;
  text-align: center;
  z-index: 5;
  background-color: rgba(0, 0, 0, 0.4);
}
.img-list .img-content .layer i {
  font-size: 1.6em;
  margin-top: 60px;
  margin-right: 5px;
  cursor: pointer;
}
</style>
