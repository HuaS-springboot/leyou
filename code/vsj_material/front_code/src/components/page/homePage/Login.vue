<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">用户登录</div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.userName" placeholder="用户名">
            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="密码"
            v-model="ruleForm.pswd"
            @keyup.enter.native="submitForm('ruleForm')"
          >
            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {axiosRequest} from "@/api/index.js";
export default {
  data: function() {
    return {
      ruleForm: {
        userName:"",
        pswd: ""
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        pswd: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  created(){
  },
  methods: {
    submitForm(formName) {
    	
      this.$refs[formName].validate(valid => {
        if (valid) {
        	
          axiosRequest('loginSysUser',{ 
            'userName':this.ruleForm.userName,
            'pswd':this.ruleForm.pswd},"post").then((res)=>{

            	
            
              localStorage.setItem("dictionary",JSON.stringify(res.sysDictionary))
              localStorage.setItem("userId",res.id)
              localStorage.setItem('nickName',res.nickName)
              localStorage.setItem("token",res.token)
              localStorage.setItem('shopName','素材')
              localStorage.setItem("userName",res.userName)

             /* console.log(objToArr(JSON.parse(window.localStorage.getItem("dictionary")).material_type)); */
             
             if(res!=null){

           			    this.$router.push('/')
             }
             	

          })
       } else {
          console.log("error submit!!");
              return false;
             }
        
      });
    }
  }
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(/assets/img/login-bg.jpg);
  background-size: 100%;
}
.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}
.ms-content {
  padding: 30px 30px;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}
.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>