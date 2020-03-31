<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 升级条件</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-tabs v-model="message" class="leverDadd">
                <el-tab-pane :label="`VIP`" name="first">

                    <div class="" v-for="item in condition" :key="item.index" >
                        <div v-if="item.index!=0">
                            <el-divider></el-divider>
                            <label class="mr20">订单关系组合</label>
                            <el-radio v-model="radio" label="1">或</el-radio>
                            <el-radio v-model="radio" label="2">且</el-radio>
                            <el-divider></el-divider>
                        </div>
                        <div class="mt20">
                            <el-select class="mr60" v-model="value" placeholder="请选择">
                                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
                            </el-select>
                            <label class="mr20">关系组合</label>
                            <el-radio v-model="radio" label="1">或</el-radio>
                            <el-radio v-model="radio" label="2">且</el-radio>
                        </div>
                        <ul class="conditionUl">
                            <li class="mt20">
                                <el-checkbox label="复选框 A">启用</el-checkbox>
                                <label for="input">消费金额(￥)</label> <el-input v-model="input" placeholder="请输入达标消费金额"></el-input>
                                 
                            </li>
                            <li class="mt20">
                                <el-checkbox label="复选框 A">启用</el-checkbox>
                                <label for="input1">消费笔数</label><el-input v-model="input1" placeholder="请输入达标消费笔数"></el-input>
                                 
                            </li>
                            <li class="mt20">
                                <el-checkbox label="复选框 A">启用</el-checkbox>
                                <label for="cascader">购买商品</label>   <el-cascader :options="options2" :props="props" clearable collapse-tags class="goodsList" placeholder="请选择商品种类"></el-cascader>
                                &nbsp;&nbsp;<label for="input2" class="ml15px">商品消费笔数(￥)</label><el-input v-model="input2" placeholder="请输入达标消费笔数"></el-input>
                                 
                            </li>
                            <li class="mt20">
                                <el-checkbox label="复选框 A">启用</el-checkbox>
                                <label for="input">结算分红金额(￥)</label><el-input v-model="input3" placeholder="请输入达标分红金额"></el-input>
                                 
                            </li>
                        </ul>
                        
                    </div>
                    <el-button type="primary" class="mt20" @click="addConditon">添加升级条件</el-button>





                </el-tab-pane>
                <el-tab-pane :label="`店主`" name="second">
                    <el-button type="primary">添加升级条件</el-button>
                </el-tab-pane>
                <el-tab-pane :label="`经销商`" name="third">
                    <el-button type="primary">添加升级条件</el-button>
                </el-tab-pane>
            </el-tabs>
        </div>  
    </div>
</template>

<script>
    export default {
        name: 'tabs',
        data() {
            return {
                condition:[
                    {
                        orderType:"",
                        greatCondition:true,
                        smlConditon:false,
                        index:"0",   
                        conditionPrice:{
                            price:"1000",
                            open:false
                        },
                        conditionNum:{
                            price:"1000",
                            open:false
                        },
                        conditionGoods:{
                            goodsType:['111','23'],
                            goodsNum:"200",
                            open:false
                        },
                        conditionRed:{
                            price:"1000",
                            open:false
                        }
                    },
                ],
                input:"",
                input1:"",
                input2:"",
                input3:"",
                checked1:true,
                checked2:false,
                message: 'first',
                radio:"1",
                options: [{
                value: '选项1',
                label: '个人订单'
                }, {
                value: '选项2',
                label: '二级订单'
                }],
                value: '',
                props: { multiple: true },
                options2: [{
                    value: 1,
                    label: '东南',
                    children: [{
                        value: 2,
                        label: '上海',
                        children: [
                            { value: 3, label: '普陀' },
                            { value: 4, label: '黄埔' },
                            { value: 5, label: '徐汇' }
                            ]
                        }, {
                            value: 7,
                            label: '江苏',
                            children: [
                            { value: 8, label: '南京' },
                            { value: 9, label: '苏州' },
                            { value: 10, label: '无锡' }
                            ]
                        }, {
                            value: 12,
                            label: '浙江',
                            children: [
                            { value: 13, label: '杭州' },
                            { value: 14, label: '宁波' },
                            { value: 15, label: '嘉兴' }
                            ]
                        }
                    ]
                }, {
                    value: 17,
                    label: '西北',
                    children: [{
                            value: 18,
                            label: '陕西',
                            children: [
                            { value: 19, label: '西安' },
                            { value: 20, label: '延安' }
                            ]
                        }, {
                            value: 21,
                            label: '新疆维吾尔族自治区',
                            children: [
                            { value: 22, label: '乌鲁木齐' },
                            { value: 23, label: '克拉玛依' }
                            ]
                        }
                    ]
                }]
                
            }
        },
        methods: {
            addConditon(){
                this.condition.push({
                    orderType:"",
                    greatCondition:true,
                    smlConditon:false,
                    index:this.condition.length,   
                    conditionPrice:{
                        price:"1000",
                        open:false
                    },
                    conditionNum:{
                        price:"1000",
                        open:false
                    },
                    conditionGoods:{
                        goodsType:['111','23'],
                        goodsNum:"200",
                        open:false
                    },
                    conditionRed:{
                        price:"1000",
                        open:false
                    }
                })
                console.log(this.condition)
            }
        },
        computed: {
            unreadNum(){
                return this.unread.length;
            }
        }
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

