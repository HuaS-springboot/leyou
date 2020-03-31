<template>
    <div class="fenxiao">
        <div class="form-box">
            <el-form ref="form" :model="form" label-width="160px" label-position="left">
                <el-form-item label="分销等级">
                    <el-radio-group v-model="form.resource">
                        <el-radio label="一级分销"></el-radio>
                        <el-radio label="二级分销"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="分销模式">
                    <el-radio-group v-model="form.resource">
                        <el-radio label="按固定金额(￥)"></el-radio>
                        <el-radio label="按固定比例(%)"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="一级分销提成" class="mt20">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="二级分销提成" class="mt20">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>                   
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">保存设置</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
    import {axiosRequest} from '@/api/index'
    export default {
        name: 'fenxiao',
        data: function(){
            return {
                form: {
                    id:'',
                    level:'',
                    firstLevelModel:'',
                    first_level_value: '',
                    second_level_model: '',
                    second_level_value: ''
                }
            }
        },
        created(){
            this.getData()
        },
        methods: {
            getData() {

            },
            onSubmit(){
                
            }
        }
    }
</script>
<style scoped>
    .mt20{
        margin-top: 20px;
    }
</style>
