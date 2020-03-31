<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 等级管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button class="addbtn" type="primary" icon="el-icon-circle-plus-outline" @click="handlerAdd()">新增等级</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="levelName" label="等级名称" ></el-table-column>
                <el-table-column prop="sort" label="等级权重" ></el-table-column>
                <el-table-column prop="isDefault" label="默认等级">
                    <template slot-scope="props" type='index'>
                        <span class="tran_box">
                            <el-checkbox v-model="props.row.isDefault" @change="changeLever(props.row)">默认等级</el-checkbox>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button class="typeIcon" type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)"></el-button>
                        <el-button class="typeIcon red" type="text" icon="el-icon-delete"  @click="handleDelete(scope.$index, scope.row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
            
        </div>

        <!-- 新增弹出框 -->
        <el-dialog title="新增" :visible.sync="addVisible" width="40%" class="typeEdit">
            <el-form :model="editForm"   label-width="110px" label-position="left">
                <el-form-item label="等级名称" >
                    <el-input v-model="editForm.levelName" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="等级权重" >
                    <el-input v-model="editForm.sort" class="mr10"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit()">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%" class="typeEdit">
            <el-form :model="editForm"   label-width="110px" label-position="left">
                <el-form-item label="等级名称" >
                    <el-input v-model="editForm.levelName" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="等级权重" >
                    <el-input v-model="editForm.sort" class="mr10"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit()">确 定</el-button>
            </span>
        </el-dialog>
        
        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow()">确 定</el-button>
            </span>
        </el-dialog>
        

    </div>
</template>

<script>
    import {axiosRequest} from '@/api/index'
    import { addUserLever,getUserLever } from '@/api/index';
    import {rules} from '@/components/common/rule'
    export default {
        name: 'roletable',
        data(){
            return {
                //修改等级
                editForm:{
                    id:'',
                    levelName:'',
                    sort:''
                },
                tableData: [],
                addVisible:false,
                editVisible: false,
                delVisible: false,
                idx: -1,
                id: -1,
                multipleSelection:[],
                oldId:'',
            }
        },
        created() {
            this.getAllLevel()
        },
        methods: {
            //查询会员等级列表
            getAllLevel(){
                axiosRequest('memeber/findAllLevel','','POST').then((res)=>{
                    this.tableData = res;
                    for(let i=0;i<res.length;i++){
                        if(this.tableData[i].isDefault==1){
                            this.tableData[i].isDefault=true
                            this.oldId=this.tableData[i].id
                        }else{
                            this.tableData[i].isDefault=false
                        }
                    }
                })
            },
            handleDelete(index, row) {
                this.idx = index;
                this.id = row.id;
                this.delVisible = true;
            },
            // 确定删除
            deleteRow(){
                axiosRequest('memeber/deleteLevelById',{id:this.id},'POST').then((res)=>{
                    this.delVisible = false;
                    this.tableData.splice(this.idx,1);
                })
            },
            
            // 编辑
            handleEdit(index, row) {
                this.editForm.id = row.id;
                this.editForm.levelName = row.levelName;
                this.editForm.sort = row.sort;
                this.editForm.isDefault = row.isDefault;
                this.editVisible = true;
            },
            // 保存添加会员等级
            saveEdit() {
                axiosRequest('memeber/editMemberLevel',this.editForm,'POST').then((res)=>{
                    this.addVisible =false,
                    this.editVisible = false,
                    this.getAllLevel();
                })
                
            },
            //设置默认等级
            changeLever(row){
                axiosRequest('memeber/editDefaultLevel',{
                    id:row.id,
                    oldId:this.oldId
                },'post').then((res)=>{
                    this.oldId=row.id
                    for(let i=0;i< this.tableData.length;i++){
                        this.tableData[i].isDefault=false
                        if(this.tableData[i].id==this.oldId){
                            this.tableData[i].isDefault=true
                        }
                    }
                    this.$message.success('提交成功');
                })  
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },

            handlerAdd(){
                this.editForm.id = null;
                this.editForm.levelName = null;
                this.editForm.sort = null;
                this.editForm.isDefault = null;
                this.addVisible = true;
            }
            

        }
    }

</script>

<style scoped>
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
    .mr10{
        margin-right: 10px;
    }
    .addbtn{
        position: relative;
        margin-bottom: 15px;
    }
    .typeIcon{
        font-size: 16px;
        padding: 0 3px;
    }
    

    
</style>
