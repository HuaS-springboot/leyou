<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 配送模板</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button class="addbtn" type="primary" icon="el-icon-circle-plus-outline" @click="addEdit()">新增模板</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" >
                <el-table-column prop="name" label="模板名称" ></el-table-column>
                <el-table-column prop="model" label="计费方式" >
                    <template slot-scope="scope">
    　　　　　　　　　    <span v-if="scope.row.model=== 0">按件计费</span>
                        <span v-if="scope.row.model=== 1">按重量计费</span>
    　　　　　　　　  </template>
                </el-table-column>
                <el-table-column prop="firstHeavyCost" label="首重(首件)价格" ></el-table-column>
                <el-table-column prop="nextHeavyCost" label="续重(续件)价格" ></el-table-column>

                <el-table-column width="80" label="是否默认">
                    <template slot-scope="props" >
                        <span class="tran_box">
                        <el-checkbox v-model="props.row.selected">是</el-checkbox>
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
        <el-dialog title="编辑" :visible.sync="addVisible" width="50%" class="typeEdit2">
            <el-form ref="addvalueParent" :model="addvalueParent" label-width="120px" label-position="left">
                <el-form-item label="模板名称" >
                    <el-input v-model="addvalueParent.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="计费方式">
                    <el-radio-group v-model="addvalueParent.model">
                        <el-radio :label="0">按件计费</el-radio>
                        <el-radio :label="1">按重量计费</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="首件数量" v-show="addvalueParent.model==0">
                    <el-input v-model="addvalueParent.firstHeavy" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="首件价格" v-show="addvalueParent.model==0">
                    <el-input v-model="addvalueParent.firstHeavyCost" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续件数量" v-show="addvalueParent.model==0">
                    <el-input v-model="addvalueParent.nextHeavy" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续件费用" v-show="addvalueParent.model==0">
                    <el-input v-model="addvalueParent.nextHeavyCost" class="mr10"></el-input>
                </el-form-item>
                
                <el-form-item label="首重重量(kg)"  v-show="addvalueParent.model==1">
                    <el-input v-model="addvalueParent.firstHeavy" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="首重价格"  v-show="addvalueParent.model==1">
                    <el-input v-model="addvalueParent.firstHeavyCost" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续重重量(kg)"  v-show="addvalueParent.model==1">
                    <el-input v-model="addvalueParent.nextHeavy" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续重价格"  v-show="addvalueParent.model==1">
                    <el-input v-model="addvalueParent.nextHeavyCost" class="mr10"></el-input>
                </el-form-item>
                <el-form-item
                    v-for="(domain,index) in addvalueChild"
                    :label="'地区价格'+index "
                    :key="domain.key"
                    :rules="{required: true, message: '域名不能为空', trigger: 'blur'}"
                >
                    <el-form-item label="地区" class="addCity" label-width="80px">
                          <el-cascader v-model="domain.cityCode" :options="cityList" :props="props" collapse-tags clearable >
                          </el-cascader>
                    </el-form-item>
                    <el-form-item label="首件数量" v-show="addvalueParent.model==0"  label-width="120px">
                        <el-input v-model="domain.firstHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="首件价格" v-show="addvalueParent.model==0" label-width="120px">
                        <el-input v-model="domain.firstHeavyCost" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续件数量" v-show="addvalueParent.model==0" label-width="120px">
                        <el-input v-model="domain.nextHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续件费用" v-show="addvalueParent.model==0" label-width="120px">
                        <el-input v-model="domain.nextHeavyCost" class="mr10"></el-input>
                        <el-button @click.prevent="removeDomain(domain)">删除</el-button>
                    </el-form-item>
                    
                    <el-form-item label="首重重量"  v-show="addvalueParent.model==1" label-width="120px">
                        <el-input v-model="domain.firstHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="首重价格"  v-show="addvalueParent.model==1" label-width="120px">
                        <el-input v-model="domain.firstHeavyCost" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续重重量"  v-show="addvalueParent.model==1" label-width="120px">
                        <el-input v-model="domain.nextHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续重价格"  v-show="addvalueParent.model==1" label-width="120px">
                        <el-input v-model="domain.nextHeavyCost" class="mr10"></el-input>
                        <el-button @click.prevent="removeDomain(domain)">删除</el-button>
                    </el-form-item>
                </el-form-item>
                <el-form-item>              
                    <el-button type="primary" plain @click="addDomain">新增地区价格</el-button>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="addSave">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%" class="typeEdit2">
            <el-form ref="form" :model="form" label-width="120px" label-position="left">
                <el-form-item label="模板名称" >
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="计费方式">
                    <el-radio-group v-model="radio">
                        <el-radio label="3">按件计费</el-radio>
                        <el-radio label="6">按重量计费</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="首件数量" v-show="radio==3">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="首件价格" v-show="radio==3">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续件数量" v-show="radio==3">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续件费用" v-show="radio==3">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                
                <el-form-item label="首重重量"  v-show="radio==6">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="首重价格"  v-show="radio==6">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续重重量"  v-show="radio==6">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续重价格"  v-show="radio==6">
                    <el-input v-model="form.name" class="mr10"></el-input>
                </el-form-item>
                <el-form-item
                    v-for="(domain,index) in dynamicValidateForm.domains"
                    :label="'地区价格'+index "
                    :key="domain.key"
                    :rules="{required: true, message: '域名不能为空', trigger: 'blur'}"
                >
                    <el-form-item label="地区" class="addCity" label-width="120px">
                        <el-cascader  :options="cityList" :props="props" collapse-tags clearable >
                        </el-cascader>
                    </el-form-item>
                    <el-form-item label="首件数量" v-show="radio==3"  label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="首件价格" v-show="radio==3" label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续件数量" v-show="radio==3" label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续件费用" v-show="radio==3" label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                        <el-button @click.prevent="removeDomain(domain)">删除</el-button>
                    </el-form-item>
                    
                    <el-form-item label="首重重量"  v-show="radio==6" label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="首重价格"  v-show="radio==6" label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续重重量"  v-show="radio==6" label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续重价格"  v-show="radio==6" label-width="120px">
                        <el-input v-model="form.name" class="mr10"></el-input>
                        <el-button >删除</el-button>
                    </el-form-item>
                </el-form-item>
                <el-form-item>              
                    <el-button type="primary" plain >新增地区价格</el-button>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" >确 定</el-button>
            </span>
        </el-dialog>

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
    import {axiosRequest} from '@/api/index'
    import {pageNum} from '@/components/common/pageNum'


    export default {
        name: 'roletable',
        inject:['reload'],
        data(){
            return {
                tableData: [],
                condition:{
                    'pageNum':1,
                    'pageSize':pageNum.getPageSize
                },
                addVisible:false,
                editVisible:false,
                addValue:{
                
                },
                addvalueParent:{
                    "firstHeavy":'',
                    "firstHeavyCost": '',
                    "model":0,
                    "name":"",
                    "nextHeavy":'',
                    "nextHeavyCost":'',
                    "provinceCode": 0
                },
                addvalueChild:[],
                cityList:[],
                props:{
                    label:'name',
                    value:'code',
                    children:'children',
                    multiple:true,
                    expandTrigger:'hover',
                    emitPath:false
                },
                delId:'',
                delIndex:'',


                dynamicValidateForm:[],

                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                delVisible: false,
                form: {
                    name: '',
                    date: '',
                    address: ''
                },
                options: [{
                    value: '选项1',
                    label: '按件计费'
                    }, {
                    value: '选项2',
                    label: '按重量计费'
                    }
                ],
                idx: -1,
                id: -1,
                
                province:'',
                sheng: '',
                shi: '',
                shi1: [],
                qu: '',
                qu1: [],
                city:'',
                block:'',
                value:"",
                radio:'3'
            }
        },
        created() {
            this.getExpressList()
            
        },
        methods: {
            //获取运费模板列表
            getExpressList(){
                axiosRequest('product/getDistTemplateList',this.condition,'post').then((res)=>{
                    this.tableData=res
                })
                axiosRequest('product/getSysAreas','','post').then((res)=>{
                    this.cityList=res
                    for(let i in this.cityList){
                        for(let  j in this.cityList[i].children){
                            this.cityList[i].children[j].children=''
                        }
                    }
                })
            },

            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
           
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            // 添加
            addEdit(){
                //this.$router.push('/typeedit')
                this.addVisible=true
                
            },
            //保存配送模板
            addSave(){
                
                for(let i=0;i<this.addvalueChild.length;i++){
                    if(typeof(this.addvalueChild[i].cityCode)=='object'){
                        this.addvalueChild[i].cityCode=this.addvalueChild[i].cityCode.join(',')
                    }
                }
                axiosRequest('product/insertDistTemplate',{
                    'parentDist':this.addvalueParent,
                    'children':this.addvalueChild
                },'post').then((res)=>{
                    this.addVisible=false
                    this.$message.success('添加成功')
                    this.reload()
                })
            },
            // 编辑
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
                console.log(this)
            },
            handleDelete(index, row) {
                this.delIndex = index;
                this.delId = row.id;
                this.delVisible = true;
            },            
            // 确定删除
            deleteRow(){
                axiosRequest('product/deleteDistTemplate',{
                    id:this.delId
                },'post').then((res)=>{
                    this.$message.success('删除成功');
                    this.delVisible = false;
                    this.tableData.splice(this.delIndex,1)
                })
            },
            //
            removeDomain(item) {
                var index = this.addvalueChild.indexOf(item)
                if (index !== -1) {
                this.addvalueChild.splice(index, 1)
                }
            },
            addDomain() {
                this.addvalueChild.push({
                    "cityCode": '',
                    "firstHeavy": '',
                    "firstHeavyCost": '',
                    "model":this.addvalueParent.model,
                    "nextHeavy": '',
                    "nextHeavyCost": '',
                    "provinceCode": ''
                });
            }
        }
    }

</script>

<style scoped>
   
    .el-input{
        width: 50%;
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
    .addbtn{
        position: relative;  
        margin-bottom: 15px;
    }
    .typeIcon{
        font-size: 16px;
        padding: 0 3px;
    }
    
    .mrselect{
        width: 50%;
    }
    .addCity .el-select,.addCity .el-input{
        width: 140px;
    }
</style>
