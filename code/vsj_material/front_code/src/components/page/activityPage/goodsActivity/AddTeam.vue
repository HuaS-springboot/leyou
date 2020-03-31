<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 发起秒杀活动</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box addSeckillForm">
                <el-form ref="form" :model="form" label-width="160px" label-position="left">
                    <el-form-item label="拼团活动名称" class="seckillTitle">
                        <el-input v-model="input2" placeholder="请输入拼团活动名称"></el-input>
                    </el-form-item>
                    <el-form-item label="拼团商品">
                        <el-cascader :options="options2" :props="props" clearable collapse-tags class="goodsList" placeholder="请选择拼团商品"></el-cascader>
                    </el-form-item>
                    <el-table :data="vipLever"  border  class="mt20">
                        <el-table-column prop="key" label="规格" width="160" ></el-table-column>
                        <el-table-column prop="num" label="库存" width="160" ></el-table-column>
                        <el-table-column label="拼团需要参入人数">
                            <template scope="scope">
                                <el-input size="small" v-show="scope.row.index" v-model="scope.row.name" placeholder="请输入百分比或者固定金额"></el-input>
                            </template>
                        </el-table-column> 
                        <el-table-column label="发起团数">
                            <template scope="scope">
                                <el-input size="small" v-show="scope.row.index" v-model="scope.row.name" placeholder="请输入百分比或者固定金额"></el-input>
                            </template>
                        </el-table-column>                     
                        <el-table-column prop="price" label="原售价" width="160" ></el-table-column>
                        <el-table-column label="拼团价">
                            <template scope="scope">
                                <el-input size="small" v-show="scope.row.index" v-model="scope.row.name" placeholder="请输入百分比或者固定金额"></el-input>
                            </template>
                        </el-table-column>                        
                    </el-table>
                    <el-form-item label="活动时间" class="mt20">
                        <el-date-picker v-model="value2" type="datetimerange" :picker-options="pickerOptions" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right">
                    </el-date-picker>
                    </el-form-item>

                    <el-form-item class="mt20">
                        <el-button type="primary" @click="onSubmit">保存设置</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>

    </div>
</template>

<script>
    import { mallSetting } from '@/api/index';
    export default {
        name: 'baseform',
        data: function(){
            return {
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: true,
                    desc: '',
                    options: []
                },
                vipLever: [{
                        key: '合伙人',
                        num:"",
                        price:"",
                        name: '',
                        index:"1",
                    }, {
                        key: '店主',
                        name: '',
                        index:"2",
                    }, {
                        key: 'VIP',
                        name: '',
                        index:"3",
                    }
                ],
            }
        },
        created(){
            this.getData()
        },
        methods: {
            onSubmit() {
                this.$message.success('提交成功！');
            },
            
        },
        

    }
</script>
<style scoped>
    .mt20{
        margin-top: 20px;
    }
    .seckillTitle .el-input{
        width: 19.7%;
    }
    .addSeckillForm{
        width: 100%;
    }
</style>