
<template>
  <div>
    <div class="crumbs">
    </div>
    <div class="container">
      <div class="form-box mallSetting">
        <el-form :model="form"  ref="mallsetting" label-width="160px" label-position="left">
          <el-form-item label="是否支持支付购买">
            <el-radio-group v-model="form.isCanBuy">
              <el-radio label="1">开启</el-radio>
              <el-radio label="0">关闭</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="L1（普通用户）">
            <el-input v-model="form.level1.day" style="width:150px">
              <template slot="append">天</template>
            </el-input>/
            <el-input v-model="form.level1.money" style="width:150px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="L2（普通会员) ">
            <el-input v-model="form.level2.day" style="width:150px">
              <template slot="append">天</template>
            </el-input>/
            <el-input v-model="form.level2.money" style="width:150px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="L3（高级会员）">
            <el-input v-model="form.level3.day" style="width:150px">
              <template slot="append">天</template>
            </el-input>/
            <el-input v-model="form.level3.money" style="width:150px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">保存设置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--<el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>-->
  </div>

</template>

<script>
import { axiosRequest } from "@/api/index";
import { arrToObj, objToArr } from "@/components/common/common";
//import { rules } from "../../../common/rule";
import "@/assets/css/upload.css";
export default {
  name: "baseform",
  data: function() {
    return {
      form: {
        isCanBuy: "1",
        level1: {
          id: '',
          money: '',
          day: ''
        },
        level2: {
          id: '',
          money: '',
          day: ''
        },
        level3: {
          id: '',
          money: '',
          day: ''
        }
      },

      headers: {
        token: localStorage.getItem("token"),
        userId: localStorage.getItem("userId"),
        platformCode: this.$store.state.platformCode
      }
    };
  },
  created() {
    this.getMallSetting();
  },
  methods: {
    //获取商城配置
    getMallSetting() {
    	let then=this;
      //获取当前配置项  给this.form赋值
       axiosRequest('admin/user/MaterialPackage','','POST').then((res)=>{
       	
                    //this.$message.success('提交成功！');
                   	let data=res.list;
                   	data.forEach(row=>{
                   		if(row.levelId=='1'){
                   			Object.assign(then.form.level1,row)
                   		}
                   		if(row.levelId=='2'){
                   			Object.assign(then.form.level2,row)
                   		}
                   		if(row.levelId=='3'){
                   			Object.assign(then.form.level3,row)
                   		}
                   		if(row.status=='0'||row.status==null){
                   			then.form.isCanBuy='0';
                   		}
                   	});
                   		
               });
    },

    //保存配置
    onSubmit() {
      let then=this;
      let arr=[];
      arr.push(then.form.level1);
      arr.push(then.form.level2);
      arr.push(then.form.level3);
      let obj={'status':then.form.isCanBuy,'memberPackageList':arr};
      console.log(JSON.stringify(obj));
      axiosRequest('admin/user/updateMemberPackage',obj,'POST').then((res)=>{
                    this.$message.success('提交成功！');
                    then.getMallSetting();
               });
               
      //form为当前配置项 再进行保存
    }
  }
};
</script>
<style scoped>
.mallSetting {
  width: 100%;
}
.mallSetting .el-input {
  width: 40%;
}
</style>
