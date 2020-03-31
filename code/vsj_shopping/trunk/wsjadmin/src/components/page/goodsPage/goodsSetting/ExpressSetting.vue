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
            <el-table :data="tableData.list" border class="table" ref="multipleTable" >
                <el-table-column prop="configuration.parentDist.name" label="模板名称" ></el-table-column>
                <el-table-column prop="configuration.parentDist.model" label="计费方式" >
                    <template slot-scope="scope">
    　　　　　　　　　    <span v-if="scope.row.configuration.parentDist.model=== 0">按件计费</span>
                        <span v-if="scope.row.configuration.parentDist.model=== 1">按重量计费</span>
    　　　　　　　　  </template>
                </el-table-column>
                <el-table-column prop="configuration.parentDist.firstHeavyCost" label="首重(首件)价格" ></el-table-column>
                <el-table-column prop="configuration.parentDist.nextHeavyCost" label="续重(续件)价格" ></el-table-column>
                <el-table-column prop="status" label="默认等级">
                    <template slot-scope="props" type='index'>
                        <span class="tran_box">
                            <el-checkbox v-model="props.row.status" @change="changeDefault(props.row)">默认等级</el-checkbox>
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
                >
                    <el-form-item label="地区" class="addCity" label-width="80px">
                        <el-cascader v-model="domain.cityDist" :options="cityList" :props="props" collapse-tags clearable >
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
            <el-form :model="form" ref="form"  label-width="120px" label-position="left">
                <el-form-item label="模板名称" >
                    <el-input v-model="form.configuration.parentDist.name" class="mr10"></el-input> 
                </el-form-item>
                <el-form-item label="计费方式">
                    <el-radio-group v-model="form.configuration.parentDist.model">
                        <el-radio :label="0">按件计费</el-radio>
                        <el-radio :label="1">按重量计费</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="首件数量" v-show="form.configuration.parentDist.model===0">
                    <el-input v-model="form.configuration.parentDist.firstHeavy" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="首件价格" v-show="form.configuration.parentDist.model==0">
                    <el-input v-model="form.configuration.parentDist.firstHeavyCost" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续件数量" v-show="form.configuration.parentDist.model==0">
                    <el-input v-model="form.configuration.parentDist.nextHeavy" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续件费用" v-show="form.configuration.parentDist.model==0">
                    <el-input v-model="form.configuration.parentDist.nextHeavyCost" class="mr10"></el-input>
                </el-form-item>
                
                <el-form-item label="首重重量"  v-show="form.configuration.parentDist.model==1">
                    <el-input v-model="form.configuration.parentDist.firstHeavy" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="首重价格"  v-show="form.configuration.parentDist.model==1">
                    <el-input v-model="form.configuration.parentDist.firstHeavyCost" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续重重量"  v-show="form.configuration.parentDist.model==1">
                    <el-input v-model="form.configuration.parentDist.nextHeavy.nextHeavyCost" class="mr10"></el-input>
                </el-form-item>
                <el-form-item label="续重价格"  v-show="form.configuration.parentDist.model==1">
                    <el-input v-model="form.configuration.parentDist.nextHeavyCost" class="mr10"></el-input>
                </el-form-item>
                <el-form-item
                    v-for="(domain,index) in form.configuration.children"
                    :label="'地区价格'+index "
                    :key="domain.key"
                    :rules="{required: true, message: '域名不能为空', trigger: 'blur'}"
                >
                    <el-form-item label="地区" class="addCity" label-width="120px">
                        <el-cascader  :options="cityList" :props="props" v-model="domain.cityDist" collapse-tags clearable >
                        </el-cascader>
                    </el-form-item>
                    <el-form-item label="首件数量" v-show="domain.model==0"  label-width="120px">
                        <el-input v-model="domain.firstHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="首件价格" v-show="domain.model==0" label-width="120px">
                        <el-input v-model="domain.firstHeavyCost" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续件数量" v-show="domain.model==0" label-width="120px">
                        <el-input v-model="domain.nextHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续件费用" v-show="domain.model==0" label-width="120px">
                        <el-input v-model="domain.nextHeavyCost" class="mr10"></el-input>
                        <el-button @click.prevent="removeForm(domain)">删除</el-button>
                    </el-form-item>
                    
                    <el-form-item label="首重重量"  v-show="domain.model==1" label-width="120px">
                        <el-input v-model="domain.firstHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="首重价格"  v-show="domain.model==1" label-width="120px">
                        <el-input v-model="domain.firstHeavyCost" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续重重量"  v-show="domain.model==1" label-width="120px">
                        <el-input v-model="domain.nextHeavy" class="mr10"></el-input>
                    </el-form-item>
                    <el-form-item label="续重价格"  v-show="domain.model==1" label-width="120px">
                        <el-input v-model="domain.nextHeavyCost" class="mr10"></el-input>
                        <el-button @click.prevent="removeForm(domain)">删除</el-button>
                    </el-form-item>
                </el-form-item>
                <el-form-item>              
                    <el-button type="primary" plain >新增地区价格</el-button>
                </el-form-item>
            </el-form>
            <!-- "   -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="editSubmit">确 定</el-button>
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
    import {unique1} from '@/components/common/common'

    export default {
        name: 'roletable',
        inject:['reload'],
        data(){
            return {
                tableData: [],
                condition:{
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
                    emitPath:false,
                },
                delId:'',
                delIndex:'',
                oldId:'',
                dynamicValidateForm:[],
                delVisible: false,
                form: {
                    configuration:{
                        'parentDist':{
                            'name':'',
                            'firstHeavy':'',
                            'firstHeavyCost':'',
                            'model':'',
                            'nextHeavyCost':'',
                            'nextHeavy':'',
                        },
                        'children':[{
                            cityDist:[{
                                cityCode:[],
                                province:''
                            }],
                            firstHeavy: '',
                            firstHeavyCost: '',
                            model: '',
                            nextHeavy: '',
                            nextHeavyCost: '',
                            provinceCode: ""
                        }]
                    },
                    id:'',
                    status:''
                },
                radio:''
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
                    this.tableData.list.forEach(item=>{
                        item.configuration=JSON.parse(item.configuration)
                        if(item.status==1){
                            item.status=true
                            this.oldId=item.id
                        }else{
                            item.status=false
                        }
                    }) 
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
            changeDefault(row){
                axiosRequest('product/updateDistDefault',{
                    id:row.id,
                    oldId:this.oldId
                },'post').then((res)=>{
                    this.oldId=row.id
                    for(let i=0;i< this.tableData.length;i++){
                        this.tableData[i].status=false
                        if(this.tableData[i].id==this.oldId){
                            this.tableData[i].status=true
                        }
                    }
                    this.$message.success('提交成功');
                })  
            },
            // 添加
            addEdit(){
                //this.$router.push('/typeedit')
                this.addVisible=true
            },
            //保存配送模板
            addSave(){
                for(let i=0;i<this.addvalueChild.length;i++){
                    let provinceChoose=[]
                    let cityChoose=[]
                    for(let k=0;k<this.addvalueChild[i].cityDist.length;k++){
                        provinceChoose.push(this.addvalueChild[i].cityDist[k].substring(0,2))        
                    }
                    provinceChoose=unique1(provinceChoose)
                    for(let l=0;l<provinceChoose.length;l++){
                        cityChoose.push({
                            'province':provinceChoose[l]+'0000',
                            'cityCode':[]
                        })
                        for(let j=0;j<this.addvalueChild[i].cityDist.length;j++){
                            if(this.addvalueChild[i].cityDist[j].substring(0,2)==cityChoose[l].province.substring(0,2)){
                                cityChoose[l].cityCode.push(this.addvalueChild[i].cityDist[j])
                            }
                        }
                    }
                    this.addvalueChild[i].cityDist=cityChoose
                }
                axiosRequest('product/insertDistTemplate',{
                    'configuration':JSON.stringify({
                        'parentDist':this.addvalueParent,
                        'children':this.addvalueChild
                    })
                    
                },'post').then((res)=>{
                    this.addVisible=false
                    this.$message.success('添加成功')
                    this.reload()
                })
            },
            // 编辑
            handleEdit(index, row) {                
                this.editVisible = true;
                this.idx = index;
                this.id = row.id;
                this.form = {
                    id: row.id,
                    configuration:row.configuration,
                    status:row.status
                }
                this.form.configuration.children.forEach(item=>{
                    let cityListEdit=[]
                    item.cityDist.forEach(item1=>{
                        item1.cityCode.forEach(item2=>{
                            cityListEdit.push(item2)
                        })
                    })
                    item.cityDist=cityListEdit
                })
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
                    this.tableData.list.splice(this.delIndex,1)
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
                    "cityDist": '',
                    "firstHeavy": '',
                    "firstHeavyCost": '',
                    "model":this.addvalueParent.model,
                    "nextHeavy": '',
                    "nextHeavyCost": '',
                    "provinceCode": ''
                });
            },
            //编辑商品删除
            removeForm(item){
                var index = this.form.configuration.children.indexOf(item)
                if (index !== -1) {
                    this.form.configuration.children.splice(index, 1)
                }
            },
            //保存编辑
            editSubmit(){
                this.form.configuration.children.forEach(item=>{
                    let provinceChoose=[]
                    let cityChoose=[]
                    item.cityDist.forEach(item1=>{
                        provinceChoose.push(item1.substring(0,2))
                    })
                    provinceChoose=unique1(provinceChoose)
                    provinceChoose.forEach(item2=>{
                        cityChoose.push({
                            'province':item2+'0000',
                            'cityCode':[]
                        })
                        
                    })
                    cityChoose.forEach(item4=>{
                        item.cityDist.forEach(item3=>{
                            if(item3.substring(0,2)==item4.province.substring(0,2)){
                                item4.cityCode.push(item3)
                            }
                        })
                        
                    })
                    
                    item.cityDist=cityChoose
                })
                
                axiosRequest('product/updateDistTemplate',{
                    'id':this.form.id,
                    'configuration':JSON.stringify(this.form.configuration)
                    
                },'post').then((res)=>{
                    this.editVisible=false
                    this.$message.success('修改成功')
                    this.reload()
                })
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
