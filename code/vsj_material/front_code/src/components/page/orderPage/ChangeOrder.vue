<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 退换货订单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">
                <el-input v-model="select_word" placeholder="订单号" class="handle-input mr10" ></el-input>
                <el-input v-model="select_word" placeholder="账户名" class="handle-input mr10" ></el-input>
                <el-input v-model="select_word" placeholder="商品名" class="handle-input mr10" ></el-input>
                
                <el-button class="mr10" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                <br/><br/>
                <!-- 订单状态-->
                <el-select v-model="value" placeholder="订单状态" class="mr10 handle-select">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <!-- 支付方式-->
                <el-select v-model="value" placeholder="支付方式" class="mr10 handle-select">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <!-- 上架状态-->
                <el-select v-model="value" placeholder="时间类型" class="mr10 handle-select">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <div class="timeChoose">
                    <el-date-picker v-model="value2" type="datetimerange" :picker-options="pickerOptions" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right">
                    </el-date-picker>
                </div>
                

                <el-button class="addbtn" type="primary" icon="el-icon-circle-plus-outline" >导出订单</el-button>
            </div>
            <el-table :data="data" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="address" label="订单号" >
                </el-table-column>
                <el-table-column prop="address" label="商品名称" >
                </el-table-column>
                <el-table-column prop="address" label="下单账户" >
                </el-table-column>
                <el-table-column prop="address" label="支付方式" width="100">
                </el-table-column>
                <el-table-column prop="address" label="应收" width="100">
                </el-table-column>
                <el-table-column prop="address" label="实收" width="100">
                </el-table-column>
                <el-table-column prop="address" label="下单时间" >
                </el-table-column>
                <el-table-column prop="address" label="支付时间" >
                </el-table-column>
                <el-table-column prop="address" label="订单状态" width="100">
                </el-table-column>
                
                
                
                <el-table-column label="操作" width="200" align="left" :formatter="formatter">
                    <template slot-scope="scope">
                        <!-- 下架 编辑  库存管理  删除 -->
                        <el-button type="info" round size="mini">信息按钮</el-button>
                        <el-button type="info" round size="mini">信息按钮</el-button>
                        
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="1000">
                </el-pagination>
            </div>
        </div>
        
        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    
    const cityOptions =['账户管理', '角色管理', '商品管理', '财务审批'];
    export default {
        name: 'roletable',
        data() {
            return {
                tableData: [],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                editVisible: false,
                delVisible: false,
                form: {
                    name: '',
                    date: '',
                    address: ''
                },
                radio: '',
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
                value:"",
                idx: -1,
                id: -1,
                pickerOptions: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                value2: '',
                // 
                checkAll: false,
                
                cities: cityOptions,
                isIndeterminate: true
            }
        },
        created() {
            this.getData();
        },
        computed: {
            data() {
                return this.tableData.filter((d) => {
                    let is_del = false;
                    for (let i = 0; i < this.del_list.length; i++) {
                        if (d.name === this.del_list[i].name) {
                            is_del = true;
                            break;
                        }
                    }
                    if (!is_del) {
                        if (d.address.indexOf(this.select_cate) > -1 &&
                            (d.name.indexOf(this.select_word) > -1 ||
                                d.address.indexOf(this.select_word) > -1)
                        ) {
                            return d;
                        }
                    }
                })
            }
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                
            },
            //添加商品
            addGoods(){
                this.$router.push('/addgoods')
            },
            handleCheckAllChange(val) {
                this.checkedCities = val ? cityOptions : [];
                this.isIndeterminate = false;
            },
            handleCheckedCitiesChange(value) {
                let checkedCount = value.length;
                this.checkAll = checkedCount === this.cities.length;
                this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
                this.idx = index;
                this.id = row.id;
                this.form = {
                    id: row.id,
                    name: row.name,
                    date: row.date,
                    address: row.address
                }
                this.editVisible = true;
            },
            handleDelete(index, row) {
                this.idx = index;
                this.id = row.id;
                this.delVisible = true;
            },
            // delAll() {
            //     const length = this.multipleSelection.length;
            //     let str = '';
            //     this.del_list = this.del_list.concat(this.multipleSelection);
            //     for (let i = 0; i < length; i++) {
            //         str += this.multipleSelection[i].name + ' ';
            //     }
            //     this.$message.error('删除了' + str);
            //     this.multipleSelection = [];
            // },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 保存编辑
            saveEdit() {
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx+1} 行成功`);
                if(this.tableData[this.idx].id === this.id){
                    this.$set(this.tableData, this.idx, this.form);
                }else{
                    for(let i = 0; i < this.tableData.length; i++){
                        if(this.tableData[i].id === this.id){
                            this.$set(this.tableData, i, this.form);
                            return ;
                        }
                    }
                }
            },
            // 确定删除
            deleteRow(){
                this.$message.success('删除成功');
                this.delVisible = false;
                if(this.tableData[this.idx].id === this.id){
                    this.tableData.splice(this.idx, 1);
                }else{
                    for(let i = 0; i < this.tableData.length; i++){
                        if(this.tableData[i].id === this.id){
                            this.tableData.splice(i, 1);
                            return ;
                        }
                    }
                }
            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
        color: #DCDFE6;
    }
    .qzlabel{
        display: inline-block;
        color: #666;
        margin-right: 15px;
        margin-left: 15px;
    }
    .el-radio{
        margin-right: 10px;
    }
    .handle-select {
        width: 120px;
    }
    .handle-inputSmall{
        width: 100px;
        display: inline-block;
    }
    .handle-input {
        width: 200px;
        display: inline-block;
    }
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
    .mr5{
        margin-right: 5px;
    }
    .addbtn{
        position: absolute;
        right: 5%;
    }
    .typeIcon{
        font-size: 16px;
        padding: 0 3px;
    }
    .simpleHandle{
        margin-top: 15px;
    }
    .timeChoose{
        display: inline;
        
    }
</style>
