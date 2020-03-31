<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 推荐商品设置</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="120px" label-position="left">
                    
                    <el-form-item  label="推荐商品图片">
                        <el-upload class="upload-demo" drag action="http://jsonplaceholder.typicode.com/api/posts/" multiple>
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
                        </el-upload>
                    </el-form-item>
                    <draggable element="ul" v-model="list" class="bannerList">
                        <li  v-for="item in list" :key="item.name" :index="item.name" :style="item.color" @click="handleEdit()">{{item.name}}
                            <el-button type="info" icon="el-icon-close" circle></el-button>
                            <el-button type="info" icon="el-icon-zoom-in" circle></el-button>
                        </li>
                    </draggable>
                

                    <el-form-item>
                        <el-button  type="primary" @click="onSubmit">保存设置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="40%" class="typeEdit">
            <el-form ref="form" :model="form" label-width="110px" label-position="left">
                <el-select v-model="value" placeholder="一级分类" class="mr10 handle-select">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <!-- 二级分类-->
                <el-select v-model="value" placeholder="二级分类" class="mr10 handle-select">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <el-input v-model="select_word" placeholder="请输入要查询的商品名称" class="handle-input mr10"  ></el-input>
                <el-button class="mr10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            </el-form>
            <br>
            <el-table :data="data" border class="table" ref="multipleTable" @selection-change="handleSelectionChange" >
                <el-table-column prop="address" label="商品名称" >
                </el-table-column>
                <el-table-column prop="address" label="一级分类" >
                </el-table-column>
                <el-table-column prop="address" label="二级分类" >
                </el-table-column>
                <el-table-column label="操作"  align="center">
                    <template slot-scope="scope">
                        <!-- 下架 编辑  库存管理  删除 -->
                        <el-button class="typeIcon" type="text" icon="el-icon-star-off" @click="handleDelete(scope.$index, scope.row)">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    
    
    import draggable from 'vuedraggable'
    export default {
        name: 'baseform',
        components: {
            //调用组件
            draggable,
        },
        data: function(){
            return {
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: true,
                    type: ['步步高'],
                    resource: '小天才',
                    desc: '',
                    options: []
                },
                options: [{
                    value: '选项1',
                    label: '黄金糕'
                    }, {
                    value: '选项2',
                    label: '双皮奶'
                    }, {
                    value: '选项3',
                    label: '蚵仔煎'
                    }, {
                    value: '选项4',
                    label: '龙须面'
                    }, {
                    value: '选项5',
                    label: '北京烤鸭'
                    }
                ],
                list:[
                    {
                    id: 1,
                    name: ' ',
                    color:{
                        "background-color":"red"
                    }
                    },
                    {
                    id: 2,
                    name: ' ',
                    color:{
                        "background-color":"palevioletred"}
                    },
                    {
                    id: 3,
                    name: ' ',
                    color:{
                        "background-color":"black"},
                    },
                    {
                    id: 4,
                    name: ' ',
                    color:{
                        "background-color":"blue"}
                    },
                    {
                    id: 5,
                    name: ' ',
                    color:{
                        "background-color":"yellow"},
                    },
                    {
                    id: 6,
                    name: ' ',
                    color:{
                        "background-color":"green"},
                    },
                ],
                editVisible: false,
                delVisible: false,
            }
        },
        created(){
            this.getData()
        },
        methods: {
            // 获取 easy-mock 的模拟数据
            getData() {
                
            },
            onSubmit() {
                this.$message.success('提交成功！');
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                mallSetting({
                    "type": 1
                }).then((res) => {
                    console.log(res)
                    //this.tableData = res.list;
                })
            },
            // 编辑
            handleEdit() {

               
                this.editVisible = true;
            },
        },
        

    }
</script>
<style scoped>
    .form-box{
        width: 100%;
    }

    li{
        list-style: none;
    }
    .bannerList{
        padding: 20px 0;
        display: block;
        width: 100%;
        overflow: hidden;
    }
    .mr10{
        margin-right: 10px;
    }
    .bannerList li{
        float:left;
        width: 200px;
        height: 120px;
        margin-left: 20px; 
        margin-bottom: 10px;
        list-style: none;
        border-radius: 10px;
        position: relative;
    }
    .bannerList li button,.bannerList li button:active,.bannerList li button:focus,.bannerList li button:hover{
        color: #fff;
        border: 0;
        background: rgba(0, 0, 0, 0.8);
    }
    .bannerList li button:nth-of-type(1){
        position: absolute;
        right: -12.5px;
        top: -12.5px
    }
    .bannerList li button:nth-of-type(2){
        position: absolute;
        right: 17px;
        top: -12.5px;
    }
    .handle-input{
        margin-top: 10px;
        width: 180px;
    }

</style>
