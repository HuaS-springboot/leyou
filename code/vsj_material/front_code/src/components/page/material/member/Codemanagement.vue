
<template>
  <div class="table">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 兑换码管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">

      <div class="handle-box">
        <el-input v-model="code" placeholder="兑换码" class="handle-input mr10"></el-input>

        <el-input v-model="nickName" placeholder="用户名" class="handle-input mr10"></el-input>
        <el-input v-model="telPhone" placeholder="手机号" class="handle-input mr10"></el-input>
        <!--  <el-input v-model="select_word" placeholder="openid" class="handle-input mr10" ></el-input> -->
        <!--<el-select v-model="levelId" placeholder="等级" class="mr10 handle-select" @change="leverChange" :clearable="true">
                    <el-option
                    v-for="item in options"
                    :key="item.value"status
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>-->
          <el-select v-model="form.status" placeholder="使用状态" class="mr10 handle-select" @change="leverChange" :clearable="true">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-button class="mr10" type="primary" icon="el-icon-search" @click="selectCodeList">搜索</el-button><br />
        <br />

      </div>
      <el-table tooltip-effect="dark" :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" label="序号">
        </el-table-column>
        <el-table-column prop="code" label="兑换码">
        </el-table-column>
        <el-table-column prop="levelId" label="类型">
        </el-table-column>
        <el-table-column prop="levelDay" label="有效天数">
        </el-table-column>
        <el-table-column prop="effectiveTime" label="生效时间">
        </el-table-column>
        <el-table-column prop="status" label="使用状态">
        </el-table-column>
        <el-table-column prop="nickName" label="使用人">
        </el-table-column>
        <el-table-column prop="useTime" label="使用时间">
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button class="typeIcon red" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px">
        <el-button @click="deleteSelects">批量删除</el-button>
        <el-button @click="editVisible=true">生成兑换码</el-button>
      </div>
      <!-- 删除提示框 -->
      <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
        <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="delVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteRow">确 定</el-button>
        </span>
      </el-dialog>

      <div style="padding:20px;font-size:16px;color:#666">会员总数:{{total}}</div>
      <div class="pagination">
        <el-pagination background @current-change="handleCurrentChange" :page-size="this.pageSize" :pager-count="7" layout="prev, pager, next" :total="this.total">
        </el-pagination>
      </div>
    </div>

    <!-- 列表添加、修改弹窗 -->
    <el-dialog width="40%" title="生成兑换码" :before-close="handleBeforeClose" :visible.sync="editVisible">
      <el-form :model="form" ref="form" label-width="120px">
        <el-row>
          <el-col :span="20">
            <el-form-item label="兑换类型" >
              <el-select v-model="levelId" placeholder="兑换类型" class="mr10 handle-select" :clearable="true">
                <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="有效天数" >
              <el-input v-model="form.levelDay" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="生效时间" >
              <el-date-picker v-model="form.effectiveTime" type="datetime" placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
           <el-form-item label="失效时间" >
              <el-date-picker v-model="form.invalidTime" type="datetime" placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="生成数量" >
              <el-input v-model="form.count" auto-complete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAttribute">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "../../../../utils/request";
import { axiosRequest } from "../../../../api/index";
import { pageNum } from "../../../common/pageNum";
import { formatDate } from "../../../common/common";

const cityOptions = ["账户管理", "角色管理", "商品管理", "财务审批"];
export default {
  name: "roletable",
  inject:['reload'],
  data() {
    return {
      delVisible: false,
      editVisible: false,
      delArr:[],
      delIds: "",
      //生成兑换券参数
      form: {
        status:"",
        levelId: "",
        levelDay: "",
        effectiveTime: "",
        count: "",
        invalidTime:""
      },
      tableData: [],
      pageNum: 1,
      pageSize: 0,
      total: 100,
      nickName: "",
      telPhone: "",
      levelId: "",
      code:"",
      options2:[
      {
          value: "",
          label: "全部"
        }
      ],
      options: [
        {
          value: "",
          label: "全部"
        },
        {
          value: "1",
          label: "已使用"
        },
        {
          value: "0",
          label: "未使用"
        },
      ],

      multipleSelection: []
    };
  },
  created() {
    this.pageSize = pageNum.getPageSize;
    this.getCodeList();
    this.getLevel();
  },
  computed: {},
  methods: {
    handleBeforeClose(done) {
      done();
    },
    deleteRow() {
    	let then=this;
    	let ids=this.delArr.join(',');
       let obj={'ids':ids};
       axiosRequest('admin/conversion/deleteMaterialConversion',obj,'POST').then((res)=>{
       	              this.reload();
                      this.$message.success('刪除成功！');
                      then.delVisible=false;
//                  if(res.code=='200'){
//                  	then.$message.success(res.message);
//                  	then.delVisible=false;
//                  	then.getCodeList();
//                  	return;
//                  }else{
//                  	then.$message.fail(res.message);
//                  	then.delVisible=false;
//                  	return;
//                  }
               });
                 
    },
    //删除单个商品
    handleDelete(row) {
//    row.id;
        this.delArr=[];
				this.delArr.push(row.id);
        this.delVisible = true; //删除提示框
      //alert(row.id);
//    let obj={'ids':row.id};
//     axiosRequest('admin/conversion/deleteMaterialConversion',obj,'POST').then((res)=>{
//                  this.$message.success('提交成功！');
//              }),
//             this.getCodeList()
    },
    //搜索兑换码列表
    selectCodeList() {
      this.parentId = "";
      this.getCodeList();
    },

    //获取兑换码列表
    getCodeList() {
      var selectForm = {
        pageNum: this.pageNum,
        pageSize: pageNum.getPageSize,
        nickName: this.nickName,
        telPhone: this.telPhone,
        levelId: this.form.levelId,
        status:this.form.status,
        code:this.code
      };
      //请求成功赋值

//    for (let i = 0; i < 20; i++) {
//      this.tableData.push({
//        id: i,
//        type: "11",
//        validTime: "1223",
//        codeNo: 2324234,
//        vaildDay: 2331,
//        status: "2342",
//        usePerson: "32423",
//        usedTime: "243"
//      });
//    }
//    this.total = 20;
           axiosRequest("admin/conversion/getMaterialConversionList", selectForm, "POST").then((res) => {
           	console.log(res);
             this.tableData = res.list;
             this.total = res.total;
           });
    },

    // 分页导航
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getCodeList();
    },

    handleSelectionChange(val) {
    	let then=this;
    	then.delArr=[];
    	if(val!=null&&val.length>0){
    		val.forEach(row=>{
    			then.delArr.push(row.id);
    		})
    	}
     	
      this.multipleSelection = val;
    },
    //批量删除
    deleteSelects() {
    	let then=this;
    	/*let ids=[]
                this.tableData.list.forEach(row=>{
                    if(row.selectStatus==true){
                        ids.push(row.productId)
                    }
                })*/
      then.delIds=then.delArr.join(',');
//    console.log(JSON.stringify(then.delIds));
       let obj={'ids':then.delIds};
       axiosRequest('admin/conversion/deleteMaterialConversion',obj,'POST').then((res)=>{
       	             this.reload();
                    this.$message.success('提交成功！');
                })
//     this.getCodeList()
    },
    getLevel(){
                axiosRequest('admin/user/getUserLevelList','','POST').then((res)=>{
                    for(var i=0;i<res.list.length;i++){
                        this.options2[i+1] = {
                            value:res.list[i].id,
                            label:res.list[i].levelName
                        };
                    }
                })
            },
                leverChange(value){
                console.log(value)
                this.form.status=value
                this.getCodeList()
            },
    /**
     *@description 保存兑换券生成
     * */
    saveAttribute() {
      this.editVisible = false;
        		var insertFrom = {
                    levelId: this.levelId,
                    levelDay: this.form.levelDay,
                    effectiveTime: formatDate(this.form.effectiveTime),
                    count: this.form.count,
                    invalidTime:formatDate(this.form.invalidTime)
                };
                axiosRequest('admin/conversion/insertMaterialConversion',insertFrom,'POST').then((res)=>{
                	   this.reload();
                    this.$message.success('提交成功！');
                })
               
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
.uploadA {
}
</style>
