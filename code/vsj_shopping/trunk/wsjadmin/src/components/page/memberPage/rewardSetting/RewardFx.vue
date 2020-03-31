<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 分销模式</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="160px" label-position="left">
                    <el-form-item label="分销等级">
                        <el-radio-group v-model="form.level">
                            <el-radio :label=1>一级分销</el-radio>
                            <el-radio :label=2>二级分销</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="一级分销模式">
                        <el-radio-group v-model="form.firstLevelModel">
                            <el-radio :label=0>按固定金额(￥)</el-radio>
                            <el-radio :label=1>按固定比例(%)</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="一级分销提成" class="mt20">
                        <el-input v-model="form.firstLevelValue"></el-input>
                    </el-form-item>
                    <el-form-item label="二级分销模式" v-show="form.level==2">
                        <el-radio-group v-model="form.secondLevelModel">
                            <el-radio :label=0>按固定金额(￥)</el-radio>
                            <el-radio :label=1>按固定比例(%)</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="二级分销提成" class="mt20"  v-show="form.level==2">
                        <el-input v-model="form.secondLevelValue"></el-input>
                    </el-form-item>
                                        
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">保存设置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        

    </div>
</template>

<script>
    import {axiosRequest} from '@/api/index'
    import {getFxDefault} from '@/components/common/common'
    
    export default {
        name: 'baseform',
        data: function(){
            return {
                form: {
                    "firstLevelModel": 0,    //一级分销模式 0=按固定金额；1=按固定比例
                    "firstLevelValue": 0,    //一级分销提成比例
                    "level": 0,              //分销等级 1=一级分销；2=二级分销
                    "productId": 0,          //商品id
                    'id':0,                  //配置id
                    "secondLevelModel": 0,   //二级分销模式 0=按固定金额；1=按固定比例
                    "secondLevelValue": 0,   //二级分销提成比例
                }
            }
        },
        created(){
           this.getFxDefault()
        },
        methods: {
            getFxDefault(){
                axiosRequest('product/getStageDistr',{'id':'0'},'post').then((res)=>{
                    this.form=res
                })
            },
            onSubmit(){
                this.form.id=0
                this.form.productId=0
                axiosRequest('product/insertStageDistr',this.form,'post').then((res)=>{
                    this.$message.success('修改成功')
                })
            }
        }
        

    }
</script>
<style scoped>
    .mt20{
        margin-top: 20px;
    }
</style>
