<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 升级条件</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-tabs v-model="message" class="leverDadd" @tab-click='changeTabs'>
                <el-tab-pane v-for="lever in leverList" :key="lever.id" :label="lever.levelName" :name="lever.levelName">
                    <div class="" v-for="(item ,index) in condition" :key="index" >
                        <div v-if="index!=0">
                            <el-divider></el-divider>
                            <label class="mr20">与上级关系组合</label>
                            <el-radio v-model="item.parentRelation" :label="0">或</el-radio>
                            <el-radio v-model="item.parentRelation" :label="1">且</el-radio>
                            <el-divider></el-divider>
                        </div>
                        <div class="mt20">
                            <el-select class="mr60" v-model="item.type" placeholder="请选择">
                                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
                            </el-select>
                            <label class="mr20">关系组合</label>
                            <el-radio v-model="item.currentRelation" :label="0">或</el-radio>
                            <el-radio v-model="item.currentRelation" :label="1">且</el-radio>
                        </div>
                        <ul class="conditionUl">
                            <li class="mt20">
                                <el-checkbox label="复选框 A" v-model="item.data.rulesMoney.status==1" @change="changeCheck('rulesMoney',item.data.rulesMoney.status,index)">启用</el-checkbox>
                                <label for="input">消费金额(￥)</label> 
                                <el-input v-model="item.data.rulesMoney.totalMoney" placeholder="请输入达标消费金额"></el-input>
                            </li>
                            <li class="mt20">
                                <el-checkbox label="复选框 A" v-model="item.data.rulesOrderCount.status==1" @change="changeCheck('rulesOrderCount',item.data.rulesOrderCount.status,index)">启用</el-checkbox>
                                <label for="input1">消费笔数</label>
                                <el-input v-model="item.data.rulesOrderCount.orderCount" placeholder="请输入达标消费笔数"></el-input>
                            </li>
                            <li class="mt20">
                                <el-checkbox label="复选框 A" v-model="item.data.rulesProductCount.status==1" @change="changeCheck('rulesProductCount',item.data.rulesProductCount.status,index)">启用</el-checkbox>    
                                <label for="input2">商品消费笔数</label>
                                <el-input v-model="item.data.rulesProductCount.count" placeholder="请输入达标消费笔数"></el-input>   
                                <label for="cascader">商品种类</label>
                                <el-input v-model="item.data.rulesProductCount.productId" v-show='false'></el-input>
                                <el-button @click="changeEdit(item.data.rulesProductCount.productId,index)" >修改商品</el-button>
                            </li>
                            <li class="mt20">
                                <el-checkbox label="复选框 A" v-model="item.data.rulesDividend.status==1" @change="changeCheck('rulesDividend',item.data.rulesDividend.status,index)">启用</el-checkbox>
                                <label for="input">结算分红金额(￥)</label>
                                <el-input v-model="item.data.rulesDividend.dividendMoney" placeholder="请输入达标分红金额"></el-input>                                 
                            </li>
                        </ul>
                        
                    </div>
                    <el-button type="primary" class="mt20" @click="delCondition">删除条件</el-button>
                    <el-button type="primary" class="mt20" @click="addCondition">添加升级条件</el-button>
                    <br/><br/>
                    <el-button type="primary" class="mt20" @click="saveCondition">保存设置</el-button>
                </el-tab-pane>
            </el-tabs>
        </div>
        <!-- 选择商品组件 -->
        <vChoosegood :chooseIdArr='chooseIdList' v-if='editVisible' @saveChoose='saveChoose' @closeChoose='closeChoose'></vChoosegood>
    </div>
</template>

<script>
    import {axiosRequest} from '@/api/index'
    import {getGoodsType } from '@/components/common/common';
    import vChoosegood from "@/components/common/chooseGood"
    export default {
        name: 'tabs',
        components:{
            vChoosegood
        },
        data() {
            return {
                //选择商品
                conditionGoods:{
                    
                },
                tableData:[],
                typeList:getGoodsType(),
                chooseIdList:[],
                chooseMes:'选择商品',  
                conditionNum:0,    //条件计数
                // 升级条件
                condition:[
                    {
                        id: '',
                        levelId: 1,
                        parentId:0 ,       //上级Id，首层为0
                        parentRelation: 0,     //与上级的关系 0=或；1=与，首层为空
                        currentRelation: 0,    //当前升级规则 0=或；1=与
                        type:'',  
                        data:{
                            "rulesMoney":{ 
                                "totalMoney":'',
                                "status": '0'
                            },   
                            "rulesProductCount": {
                                "productId": '',
                                "count": '',
                                "status": 0
                            }, 
                            "rulesOrderCount": {
                                "orderCount": '',
                                "status": 0
                            },      
                            "rulesDividend": {
                                "dividendMoney": '',
                                "status": 0
                            }  
                        }
                    },
                ],
                editVisible:false,
                index:'0',
                leverList:[],
                input:"",
                input1:"",
                input2:"",
                input3:"",
                checked1:true,
                checked2:false,
                message: '',
                radio:"1",
                options: [
                    {
                        value: '0',
                        label: '个人订单'
                    }, {
                        value: '1',
                        label: '一级订单'
                    }, {
                        value: '2',
                        label: '二级订单'
                    }
                ],
                
            }
            
        },
        created(){
            this.getMemberLever() 
        },
        methods: {
            //获取会员等级
            getMemberLever(){
                axiosRequest(this.$URL.QUERY_MEMBER_LEVEL_LIST,'','post').then((res)=>{
                    this.leverList=res
                    this.message=this.leverList[0].levelName
                    this.getUpGrade()
                })
            },
            changeCheck(key,value,index){
                if(value==0){
                    this.condition[index].data[key].status=1
                }else if(value==1){
                    this.condition[index].data[key].status=0
                }
                
            },
            //获取升级条件
            getUpGrade(){
                axiosRequest(this.$URL.QUERY_MEMBER_UPGRADE,{
                    levelId:this.leverList[this.index].id
                },'post').then((res)=>{
                    
                    if(res.length>0){
                        this.condition=res
                        for(let i =0;i<this.condition.length;i++){
                            if(this.condition[i].type==0){
                                this.condition[i].type='个人订单'
                            }else if(this.condition[i].type==1){
                                this.condition[i].type='一级订单'
                            }else if(this.condition[i].type==2){
                                this.condition[i].type='二级订单'
                            }
                            if(this.condition[i].data==''){
                                this.condition[i].data={
                                    "rulesMoney":{ 
                                        "totalMoney":'',
                                        "status": '0'
                                    },   
                                    "rulesProductCount": {
                                        "productId": '',
                                        "count": '',
                                        "status": 0
                                    }, 
                                    "rulesOrderCount": {
                                        "orderCount": '',
                                        "status": 0
                                    },      
                                    "rulesDividend": {
                                        "dividendMoney": '',
                                        "status": 0
                                    } 
                                }
                            }else{
                                this.condition[i].data=JSON.parse(this.condition[i].data)
                            }
                        }
                    }
                    
                    console.log(this.condition)
                })
            },
            //切换tabs
            changeTabs(value){
                this.index=value.index
                this.message=this.leverList[this.index].levelName
                this.condition=[
                    {
                        id: '',
                        levelId: this.leverList[this.index].id,
                        parentId: 0,       //上级Id，首层为0
                        parentRelation: 0,     //与上级的关系 0=或；1=与，首层为空
                        currentRelation: 0,    //当前升级规则 0=或；1=与
                        type:'',  
                        data:{
                            "rulesMoney":{ 
                                "totalMoney":'',
                                "status": '0'
                            },   
                            "rulesProductCount": {
                                "productId": '',
                                "count": '',
                                "status": 0
                            }, 
                            "rulesOrderCount": {
                                "orderCount": '',
                                "status": 0
                            },      
                            "rulesDividend": {
                                "dividendMoney": '',
                                "status": 0
                            }  
                        }
                    },
                ]
               
                this.getUpGrade()
            },
            addCondition(){
                this.condition.push({
                        id: '',
                        levelId: this.leverList[this.index].id,
                        parentId: this.condition[this.condition.length-1].id,       //上级Id，首层为0
                        parentRelation: 0,     //与上级的关系 0=或；1=与，首层为空
                        currentRelation: 0,    //当前升级规则 0=或；1=与
                        type:'',  
                        data:{
                            "rulesMoney":{ 
                                "totalMoney":'',
                                "status": '0'
                            },   
                            "rulesProductCount": {
                                "productId": '',
                                "count": '',
                                "status": 0
                            }, 
                            "rulesOrderCount": {
                                "orderCount": '',
                                "status": 0
                            },      
                            "rulesDividend": {
                                "dividendMoney": '',
                                "status": 0
                            }  
                        }
                    })
            },
            //删除升级条件
            delCondition(){
                if(this.condition.length==1){
                    if(this.condition[this.condition.length-1].id!=''){
                        axiosRequest(this.$URL.DELETE_MEMBER_UPGRADE,{
                            'id':this.condition[this.condition.length-1].id
                        },'post').then((res)=>{
                            
                        })
                    }
                    this.condition=[
                    {
                        id: '',
                        levelId: this.leverList[this.index].id,
                        parentId: 0,       //上级Id，首层为0
                        parentRelation: 0,     //与上级的关系 0=或；1=与，首层为空
                        currentRelation: 0,    //当前升级规则 0=或；1=与
                        type:'',  
                        data:{
                            "rulesMoney":{ 
                                "totalMoney":'',
                                "status": '0'
                            },   
                            "rulesProductCount": {
                                "productId": '',
                                "count": '',
                                "status": 0
                            },
                            "rulesOrderCount": {
                                "orderCount": '',
                                "status": 0
                            },      
                            "rulesDividend": {
                                "dividendMoney": '',
                                "status": 0
                            }  
                        }
                    },
                ]
                }else{
                    if(this.condition[this.condition.length-1].id!=''){
                        axiosRequest(this.$URL.DELETE_MEMBER_UPGRADE,{
                            'id':this.condition[this.condition.length-1].id
                        },'post').then((res)=>{
                            
                        })
                    }
                    this.condition.splice(this.condition.length-1,1)
                    
                }
            },
            //保存升级条件
            saveCondition(){
                this.condition.forEach(item=>{
                    if(item.type=="个人订单"){
                        item.type=0
                    }else if(item.type=="一级订单"){
                        item.type=1
                    }else if(item.type=="二级订单"){
                        item.type=2
                    }
                })
                axiosRequest(this.$URL.EDIT_MEMBER_UPGRADE,{
                    memberUpgradeList:this.condition,
                },'post').then((res)=>{
                    this.$message.success("保存成功")
                })
            },
            // 选择商品的相关方法
            changeEdit(item,index){
                this.editVisible=true
                this.chooseIdList=item.split(',')
                this.conditionNum=index
            },
            closeChoose(){
                this.editVisible=false
            },
            saveChoose(value){
                this.chooseIdList=[]
                this.chooseIdList=value
                if(this.chooseIdList.length>1){
                    this.condition[this.conditionNum].data.rulesProductCount.productId=this.chooseIdList.join(',')
                }else{
                    this.condition[this.conditionNum].data.rulesProductCount.productId=this.chooseIdList
                }
                this.editVisible=false
            },
        },
        
    }

</script>

<style>
.message-title{
    cursor: pointer;
}
.handle-row{
    margin-top: 30px;
}
.leverDadd label{
    font-size: 14px;
    color: #606266;
}
.mr10{
    margin-right: 10px;
}
.mr20{
    margin-right: 20px;
}
.mr60{
    margin-right: 60px; 
}
.ml30{
    margin-left: 30%;
}
.conditionUl .ml15px{
    margin-left: 15px;
}
.mt20{
    margin-top: 20px;
}
.conditionUl{
    width: 100%;
    display: flex;
    flex-wrap: wrap;
}
li{
    list-style: none;
}
.conditionUl>li{
    width: 100%;
}
.conditionUl>li .el-input{
    width: 20%;
    float: left;
}
.conditionUl>li .goodsList{
    width:20%;
    float: left;
}
.conditionUl>li .goodsList>.el-input{
    width: 100%;
}
.conditionUl>li label{
    float: left;
    width: 10%;
    
    text-align: center;
    line-height: 32px;
}
</style>

