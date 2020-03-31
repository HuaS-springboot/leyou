<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 商品管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-tabs v-model="message">
                <el-tab-pane :label="`商品信息`" name="tab1" >
                    <el-form :model="goodsDetail" ref="goodsDetail" label-width="160px" label-position="left" class="addGoods1">
                        <el-form-item label="商品名称">
                            <el-input v-model="goodsDetail.productName"></el-input>
                        </el-form-item>
                        <el-form-item label="排位权重">
                            <el-input v-model="goodsDetail.productSort"></el-input>
                            (数值越大越靠前，最大值为9，默认为0，按照创建时间排序)
                        </el-form-item>
                        <el-form-item label="商品分类" class="addGoodsType">
                            <el-cascader class="mr10" v-model='goodsDetail.goodsType' :options="typeList"  clearable placeholder="请选择商品种类" @change="typeChange" ></el-cascader>
                        </el-form-item>
                        <el-form-item label="商品图片">
                            <div class="el-upload-list el-upload-list--picture-card uploadCom" v-show="goodsDetail.productImage.length>0">
                                <div tabindex="0" v-for="(item,index) in goodsDetail.productImage" :key="item" class="el-upload-list__item is-success" >
                                    <div data-v-452ce00c="">
                                        <img data-v-452ce00c="" :src="item" alt="" class="el-upload-list__item-thumbnail">
                                        <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                            <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(item)">
                                                <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                            </span>
                                            <span data-v-452ce00c="" class="el-upload-list__item-delete" >
                                                <i data-v-452ce00c="" class="el-icon-delete" @click="delBanner(item,index)"></i>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            
                            <el-upload
                                class="uploadCom"
                                action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                                ref="uploadBanner"
                                :headers="headers"
                                list-type="picture-card"
                                :limit=5
                                :on-exceed="exceedBanner"
                                :on-success="bannerSuccess"
                                :multiple=true
                                :file-list="bannerList"
                                :show-file-list=false
                                >
                                    <i slot="default" class="el-icon-plus"></i>
                                    <div slot="file" slot-scope="{file}">
                                        <img
                                            class="el-upload-list__item-thumbnail"
                                            :src="file.url" alt=""
                                        >
                                        <span class="el-upload-list__item-actions" @click="changPosition">
                                            <span
                                            class="el-upload-list__item-preview"
                                            @click.stop="handlePictureCardPreview(file.url)"
                                            >
                                            <i class="el-icon-zoom-in"></i>
                                            </span>
                                            <span
                                            v-if="!disabled"
                                            class="el-upload-list__item-delete"
                                            @click.stop="bannerRemove(file)"
                                            >
                                            <i class="el-icon-delete"></i>
                                            </span>
                                        </span>
                                    </div>
                                </el-upload>
                        </el-form-item>
                        <el-form-item label="是否支持退换货">
                            <el-radio-group v-model="goodsDetail.isExchange">
                                <el-radio :label="1">支持</el-radio>
                                <el-radio :label="0">不支持</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="市场售价">
                            <el-input v-model="goodsDetail.price"></el-input>
                            (作为实际售价的比较价格，只做展示使用，实际售价以规格为主)
                        </el-form-item>
                        <el-form-item label="商品重量(kg)">
                            <el-input v-model="goodsDetail.weight"></el-input>
                        </el-form-item>
                        <el-form-item label="起售时间">
                            <el-date-picker v-model="goodsDetail.saleTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期时间" default-time="12:00:00"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="分享图片">
                            <div class="el-upload-list el-upload-list--picture-card uploadCom" >
                                <div tabindex="0" class="el-upload-list__item is-success" v-shoe="goodsDetail.shareImage!=''">
                                    <div data-v-452ce00c="">
                                        <img data-v-452ce00c="" :src="goodsDetail.shareImage" alt="" class="el-upload-list__item-thumbnail">
                                        <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                            <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(goodsDetail.shareImage)">
                                                <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                            </span>
                                            <span data-v-452ce00c="" class="el-upload-list__item-delete" >
                                                <i data-v-452ce00c="" class="el-icon-delete" @click="delShare(goodsDetail.shareImage)"></i>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                           <el-upload
                            class="uploadCom"
                            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                            ref="uploadShare"
                            :headers="headers"
                            list-type="picture-card"
                            :on-exceed="handleExceed"
                            :on-success="shareSuccess"
                            :limit="1"
                            >
                                <i slot="default" class="el-icon-plus"></i>
                                <div slot="file" slot-scope="{file}">
                                    <img
                                        class="el-upload-list__item-thumbnail"
                                        :src="file.url" alt=""
                                    >
                                    <span class="el-upload-list__item-actions">
                                        <span
                                        class="el-upload-list__item-preview"
                                        @click="handlePictureCardPreview(file.url)"
                                        >
                                        <i class="el-icon-zoom-in"></i>
                                        </span>
                                        <span
                                        v-if="!disabled"
                                        class="el-upload-list__item-delete"
                                        @click="shareRemove(file)"
                                        >
                                        <i class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </el-upload>
                        </el-form-item>
                        <el-form-item label="分享标题">
                            <el-input v-model="goodsDetail.shareTitle"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitGoods">下一步</el-button>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
                <el-tab-pane :label="`商品属性`" name="tab2" :disabled='tab2Abled'>
                    <el-table :data="extensionData" style="width: 50%" border >
                        <el-table-column prop="extensionKey" label="属性名称" width="240" >
                            <template scope="scope">
                                <el-input size="small"  v-model="scope.row.extensionKey" placeholder="请输入属性名称"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="extensionValue" label="属性值">
                            <template scope="scope">
                                <el-input size="small"  v-model="scope.row.extensionValue" placeholder="请输入属性值"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column  label="操作" width="100px">
                            <template scope="scope">
                                <el-button type="info" round size="mini" @click="delkey(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-button type="primary" @click="addkey" class="mt10">添加属性</el-button>
                    <br>
                    <el-button type="primary" @click="submitTab2" class="mt10">下一步</el-button>
                </el-tab-pane>
                <el-tab-pane :label="`商品详情`" name="tab3" :disabled='tab3Abled'>
                    <quill-editor ref="myTextEditor" v-model="editContent" :options="editorOption"></quill-editor>
                    <el-button class="editor-btn mt10" type="primary" @click="submitTab3">下一步</el-button>
                </el-tab-pane>
                <el-tab-pane :label="`规格定价`" name="tab4" :disabled='tab4Abled'>
                    <el-form class="marketPrice" >            
                        <el-form-item label="统一零售价" >
                            <el-input v-model="salePrice" @input="salePriceChange"></el-input>
                        </el-form-item>      
                        <el-form-item label="定价规格">
                            <el-radio-group v-model="priceType" @change='priceTypeChange'>
                                <el-radio :label="0">统一定价</el-radio>
                                <el-radio :label="1">按规格定价</el-radio>
                            </el-radio-group>
                        </el-form-item>         
                        <el-form-item>
                            <el-button type="primary" @click="addEdit()">添加规格</el-button>
                        </el-form-item>
                        <el-form-item v-show="specsList.length>0">
                            <el-table :data="specsList" border  class="guigeEdit">
                                <el-table-column prop="attrJson2" label="规格" width="180px"> </el-table-column>
                                <el-table-column prop="productStock" label="库存" width="200px"> 
                                    <template scope="scope">
                                        <el-input size="small"  v-model="scope.row.productStock" placeholder="请输入库存"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="productPrice" label="价格(￥)" width="200px"> 
                                    <template scope="scope">
                                        <el-input size="small"  v-model="scope.row.productPrice" placeholder="请输入价格" :disabled='specsPrice'></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="saleNum" label="销量(￥)" width="200px"> 
                                    <template scope="scope">
                                        <el-input size="small"  v-model="scope.row.saleNum" placeholder="请输入销量"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column width="80" label="无库存是否可销售">
                                    <template slot-scope="props" >
                                        <span class="tran_box">
                                        <el-checkbox v-model="props.row.isSell">是</el-checkbox>
                                        </span>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-form-item>
                        <el-form-item>
                            
                            <el-button type="primary" @click="submitTab4">下一步</el-button>
                        </el-form-item>
                    </el-form>      
                </el-tab-pane>
                <el-tab-pane :label="`分润管理`" name="tab5" :disabled='tab5Abled'>
                    <el-form ref="form" :model="form" label-width="160px" label-position="left">
                        <el-form-item label="分销模式" style="float:left" v-if="trwardType==1">
                            <el-radio-group v-model="fxType" >
                                <el-radio :label="1">开启</el-radio>
                                <el-radio :label="0">关闭</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="经销商极差模式" style="float:left" v-if="trwardType==2">
                            <el-radio-group v-model="jcType">
                                <el-radio :label="1">开启</el-radio>
                                <el-radio :label="0">关闭</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <br><br>
                        <!-- 分销独立配置 -->
                        <el-form-item label="独立配置" v-show="fxType==1&&trwardType==1" class="mt10">  
                            <el-radio-group v-model="fxSpecial" @change="fxSpecialChange">
                                <el-radio :label=1>开启</el-radio>
                                <el-radio :label=0>关闭</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        
                        <div class="form-box mt10" v-show="fxType==1&&trwardType==1">
                            <el-form ref="fxDefault" :model="fxDefault" label-width="160px" label-position="left" :disabled="fxDisabled" >
                                <el-form-item label="分销等级">
                                    <el-radio-group v-model="fxDefault.level">
                                        <el-radio :label=1>一级分销</el-radio>
                                        <el-radio :label=2>二级分销</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="一级分销模式">
                                    <el-radio-group v-model="fxDefault.firstLevelModel">
                                        <el-radio :label=0>按固定金额(￥)</el-radio>
                                        <el-radio :label=1>按固定比例(%)</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="一级分销提成" class="mt20">
                                    <el-input v-model="fxDefault.firstLevelValue"></el-input>
                                </el-form-item>
                                <el-form-item label="二级分销模式" v-show="fxDefault.level==2">
                                    <el-radio-group v-model="fxDefault.secondLevelModel">
                                        <el-radio :label=0>按固定金额(￥)</el-radio>
                                        <el-radio :label=1>按固定比例(%)</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="二级分销提成" class="mt20" v-show="fxDefault.level==2">
                                    <el-input v-model="fxDefault.secondLevelValue"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        
                        <!-- 极差模式独立配置-->
                        <el-form-item label="独立配置" v-show="jcType==1&&trwardType==2" class="mt10">
                            <el-radio-group v-model="jcSpecial" @change="jcSpecialChange">
                                <el-radio :label=1>开启</el-radio>
                                <el-radio :label=0>关闭</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <div class="form-box mt10" v-show="jcType==1&&trwardType==2">
                            <el-form ref="jcDefaultForm" :model="jcDefaultForm" label-width="160px" label-position="left" :disabled="jcDisabled">
                                <el-form-item label="奖励提成模式">
                                    <el-radio-group v-model="jcDefaultForm.bonusUnits">
                                        <el-radio :label="1">按固定金额(￥)</el-radio>
                                        <el-radio :label="0">按固定比例(%)</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                
                                <el-table :data="jcDefault"  border  class="mt20" >
                                    <el-table-column prop="levelName" label="经销商等级" width="240" >
                                    </el-table-column>
                                    <el-table-column label="提成比例">
                                        <template scope="scope">
                                            <el-input size="small" v-model="scope.row.bonusNum" placeholder="请输入百分比或者固定金额"></el-input>
                                        </template>
                                    </el-table-column>
                                </el-table>
                                
                                <el-form-item label="经销商平级奖励">
                                    <el-radio-group v-model="jcDefaultForm.openPeers">
                                        <el-radio :label="1">开启</el-radio>
                                        <el-radio :label="0">关闭</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="平级奖励模式" v-show='jcDefaultForm.openPeers==1'>
                                    <el-radio-group v-model="jcDefaultForm.perrsUnits">
                                        <el-radio :label="1">按固定金额(￥)</el-radio>
                                        <el-radio :label="0">按固定比例(%)</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-table :data="jcDefault"  border  class="mt10 " v-show='jcDefaultForm.openPeers==1'>
                                    <el-table-column prop="levelName" label="经销商等级" width="240"></el-table-column>

                                    <el-table-column label="提成比例" width="359">
                                        <template scope="scope">
                                            <label for="">奖励层级</label>
                                            <el-input size="small"  v-model="scope.row.peersHierarchy" placeholder="请输入百分比或者固定金额"></el-input>
                                            <label for="">金额</label>
                                            <el-input size="small"  v-model="scope.row.peersNum" placeholder="请输入百分比或者固定金额"></el-input>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-form>
                        </div>
                        
                            
                        
                    </el-form>
                    <el-button type="primary" style="display:block" class="mt10" @click="submitTab5">下一步</el-button>
                </el-tab-pane>
                <el-tab-pane :label="`配送模板`" name="tab6" :disabled='tab6Abled'>
                    <el-radio v-model="radio" label="1" class="mt10">选择模板</el-radio>
                    <el-radio v-model="radio" label="2" class="mt10">自定义配送模板</el-radio>
                    <br>
                    <!-- 选择模板 -->
                    <el-select v-model="expressValue" placeholder="请选择" class="mt10" v-show="this.radio==1">
                        <el-option
                        v-for="item in expressOptions"
                        :key="item.configuration.parentDist.name"
                        :label="item.configuration.parentDist.name"
                        :value="item.id">
                        </el-option>
                    </el-select>

                    <!-- 自定义配送模板 -->
                    <el-form ref="addvalueParent" class="expressEdit mt10" :model="addvalueParent" label-width="160px" label-position="left" v-show="this.radio==2">
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
                    <br>
                    <el-button type="primary" @click="submitTab6" class="mt10">下一步</el-button>
                </el-tab-pane>
                <el-tab-pane :label="`首页推荐`" name="tab7" :disabled='tab7Abled'>
                    <el-checkbox v-model="bannerCheck">设置到首页轮播</el-checkbox>
                    <el-checkbox v-model="proCheck">设置到首页推荐</el-checkbox>
                    <el-form ref="goodsBnnerUrl" :model="goodsBnnerUrl" label-width="120px" label-position="left" v-show="this.bannerCheck==true" class="mt30">
                        <el-form-item label="轮播图图片" >
                            <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="goodsBnnerUrl.bannerUrl!=''">
                                <div tabindex="0" class="el-upload-list__item is-success">
                                    <div data-v-452ce00c="">
                                        <img data-v-452ce00c="" :src="goodsBnnerUrl.bannerUrl" alt="" class="el-upload-list__item-thumbnail">
                                        <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                            <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(goodsBnnerUrl.bannerUrl)">
                                                <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                            </span>
                                            <span data-v-452ce00c="" class="el-upload-list__item-delete" @click='delDefaultBanner'>
                                                <i data-v-452ce00c="" class="el-icon-delete"></i>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <el-upload
                            class="uploadCom"
                            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                            ref="uploadBanner"
                            :headers="headers"
                            list-type="picture-card"
                            :on-exceed="handleExceed"
                            :on-success="bannerSetSuccess"
                            :limit="1"
                            >
                                <i slot="default" class="el-icon-plus"></i>
                                <div slot="file" slot-scope="{file}">
                                    <img
                                        class="el-upload-list__item-thumbnail"
                                        :src="file.url" alt=""
                                    >
                                    <span class="el-upload-list__item-actions">
                                        <span
                                        class="el-upload-list__item-preview"
                                        @click="handlePictureCardPreview(file.url)"
                                        >
                                        <i class="el-icon-zoom-in"></i>
                                        </span>
                                        <span
                                        v-if="!disabled"
                                        class="el-upload-list__item-delete"
                                        @click="bannerRemove(file)"
                                        >
                                        <i class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </el-upload>
                        </el-form-item>
                    </el-form>
                    
                    <el-form ref="goodsBnnerUrl" :model="goodsBnnerUrl" label-width="120px" label-position="left" v-show="this.proCheck==true" class="mt30">
                        <el-form-item label="首页推荐图片" >
                            <div class="el-upload-list el-upload-list--picture-card uploadCom" v-if="goodsBnnerUrl.recommUrl!=''">
                                <div tabindex="0" class="el-upload-list__item is-success">
                                    <div data-v-452ce00c="">
                                        <img data-v-452ce00c="" :src="goodsBnnerUrl.recommUrl" alt="" class="el-upload-list__item-thumbnail">
                                        <span data-v-452ce00c="" class="el-upload-list__item-actions">
                                            <span data-v-452ce00c="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(goodsBnnerUrl.recommUrl)">
                                                <i data-v-452ce00c="" class="el-icon-zoom-in"></i>
                                            </span>
                                            <span data-v-452ce00c="" class="el-upload-list__item-delete" @click='delDefaultRecomm'>
                                                <i data-v-452ce00c="" class="el-icon-delete"></i>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <el-upload
                            class="uploadCom"
                            action="http://172.16.0.231:8751/api/v1/upload/uploadFile"
                            ref="uploadRecomm"
                            :headers="headers"
                            list-type="picture-card"
                            :on-exceed="handleExceed"
                            :on-success="recommSuccess"
                            :limit="1"
                            >
                                <i slot="default" class="el-icon-plus"></i>
                                <div slot="file" slot-scope="{file}">
                                    <img
                                        class="el-upload-list__item-thumbnail"
                                        :src="file.url" alt=""
                                    >
                                    <span class="el-upload-list__item-actions">
                                        <span
                                        class="el-upload-list__item-preview"
                                        @click="handlePictureCardPreview(file.url)"
                                        >
                                        <i class="el-icon-zoom-in"></i>
                                        </span>
                                        <span
                                        v-if="!disabled"
                                        class="el-upload-list__item-delete"
                                        @click="recommRemove(file)"
                                        >
                                        <i class="el-icon-delete"></i>
                                        </span>
                                    </span>
                                </div>
                            </el-upload>
                        </el-form-item>
                    </el-form>
                    <el-button  type="primary"  v-show="this.proCheck==true||this.bannerCheck==true" @click="submitTab7">完成设置</el-button>
                </el-tab-pane>
            </el-tabs>
        </div>

        <!-- 查看大图 -->
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
        <!-- 编辑弹出框 -->
        <el-dialog title="规格编辑" :visible.sync="editVisible" width="40%" class="typeEdit">
            <el-form  label-width="110px" label-position="left">
                <el-form-item label="主规格名称">
                    <el-input class="specsName" v-model="specsMainName"></el-input>
                </el-form-item>
                <el-form-item label="主规格值">
                    <el-tag :key="tag" v-for="tag in specsMain"  closable :disable-transitions="false" @close="handleClose(tag,'main')"> {{tag}} </el-tag>
                    <el-input class="input-new-tag" v-if="inputMainVisible" v-model="specsMainInput" ref="saveMainTagInput" size="small" @keyup.enter.native="handleInputConfirm('main')" @blur="handleInputConfirm('main')"></el-input>
                    <el-button v-else class="button-new-tag" size="small" @click="showInput('main')">+ 添加规格值</el-button>
                </el-form-item>
                <el-form-item label="副规格名称">
                    <el-input class="specsName" v-model="specsSecondName"></el-input>
                </el-form-item>
                <el-form-item label="副规格">
                    <el-tag :key="tag" v-for="tag in specsSecond"  closable :disable-transitions="false" @close="handleClose(tag,'second')"> {{tag}} </el-tag>
                    <el-input class="input-new-tag" v-if="inputSecondVisible" v-model="specsSecondInput" ref="saveSecondTagInput" size="small" @keyup.enter.native="handleInputConfirm('second')" @blur="handleInputConfirm('second')"></el-input>
                    <el-button v-else class="button-new-tag" size="small" @click="showInput('second')">+ 添加规格值</el-button>
                </el-form-item>
                
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="addSpecs">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
    </div>
</template>

<script>
    import "@/assets/css/upload.css";
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import { quillEditor } from 'vue-quill-editor';
    import {axiosRequest} from '@/api/index'
    import draggable from 'vuedraggable'
    import {getGoodsType,arrToObj,null2str,objToArr,unique1} from '../../../common/common'
    export default {
        name: 'tabs',
        components: {
            //调用组件
            draggable,
            quillEditor,
        },
        data() {
            return {
                headers:{
                    "token":localStorage.getItem('token'),
                    "userId":localStorage.getItem("userId"),
                    "platformCode":this.$store.state.platformCode
                }, 
                dialogImageUrl:'',
                dialogVisible: false,
                proId:0,     //商品id
                //配送模板
                expressOptions:[],
                //tab1
                //商品详情
                goodsDetail:{
                    isExchange:1,      //默认支持退换货
                    productImage:[]
                },
                goodsType:['','',''],
                typeList:getGoodsType(),//商品分类
                dialogImageUrl:'',//查看大图url
                bannerList:[],
                dialogVisible: false,  
                disabled: false,
                //tab2
                extensionData: [{
                    productId:sessionStorage.getItem('editGoodsId'),
                    extensionKey: '',
                    extensionValue: '',
                    type: '0'
                }],
                //tab3
                editContent:'',
                //tab4
                specsMainName:'',//主规格名
                specsSecondName:'',//副规格名
                specsMain:[],//主规格值数组
                specsSecond:[],//副规格值数组
                specsMainInput:'',//主规格添加input
                specsSecondInput:'',//副规格添加input
                inputMainVisible:false, 
                inputSecondVisible:false,
                editVisible:false,//规格编辑框
                specsList:[],//最终规格数组
                specsPrice:true,//规格价格是否不可编辑
                salePrice:'',//统一零售价
                priceType:0,
                //tab5
                trwardType:2,   //奖励模式是否开启，奖励模式  0-不启用奖励模式  1-启用分销模式  2-启用极差模式
                fxType:1,      //分销模式开关  0-关闭   1-启用
                jcType:1,      //极差模式开关  0-关闭   1-启用
                fxSpecial:0,   //分销模式是否可编辑开关   0-否  1-是
                jcSpecial:0,   //极差模式是否可编辑开关   0-否  1-是
                fxDisabled:true,   //分销模式是否不可编辑  true-不可编辑  false-可编辑
                jcDisabled:true,   //分销模式是否不可编辑  true-不可编辑  false-可编辑
                fxDefault:{},      //分销模式相关配置比例
                jcDefault:{},      //极差模式相关配置比例
                jcDefaultForm:{},
                form:{},
                //tab6
                expressValue:"",
                radio:"1",
                expressId:'',
                cityList:'',
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
                props:{
                    label:'name',
                    value:'code',
                    children:'children',
                    multiple:true,
                    expandTrigger:'hover',
                    emitPath:false
                },
                //tab7
                bannerUrl:'',
                recommUrl:'',
                goodsBnnerUrl:{
                    bannerUrl:'',
                    recommUrl:''
                },
                bannerCheck:false,
                proCheck:false,
                message: 'tab1',  //当前tabs
                tab2Abled:true,   //tabs是否禁用
                tab3Abled:true,
                tab4Abled:true,
                tab5Abled:true,
                tab6Abled:true,
                tab7Abled:true,
                list:[],
                publishStatus:'0',
                value:"",
                content: '',
                editorOption: {
                    placeholder: '请输入商品的图文详情'
                },
            }
        },
        created() {
            this.getGoodsDetail()
            this.getExpress()
        },
        methods: {
            //判断是新建商品还是编辑商品
            getGoodsDetail(){   //编辑商品               
                if(sessionStorage.getItem('editGoodsId')){
                    this.proId=sessionStorage.getItem("editGoodsId")
                    axiosRequest('product/getProductDetail',{
                        'id':sessionStorage.getItem('editGoodsId')
                    },'post').then((res)=>{
                        this.goodsDetail=res
                        this.fxType=this.goodsDetail.isDealer
                        this.jcType=this.goodsDetail.isDealer
                        this.fxSpecial=this.goodsDetail.isCommission
                        if(res.productImage==""){
                            this.goodsDetail.productImage=[]    
                        }else{
                            this.goodsDetail.productImage=res.productImage.split(',')
                        }
                        //商品分类
                        if(res.threeCategoryId!=''){
                            this.goodsType[2]=res.threeCategoryId
                        }
                        if(res.twoCategoryId!=''){
                            this.goodsType[1]=res.twoCategoryId
                        }
                        if(res.oneCategoryId!=''){
                            this.goodsType[0]=res.oneCategoryId
                        }
                        this.goodsDetail.goodsType=this.goodsType
                        if(this.fxSpecial==1){
                            this.fxDisabled=false
                        }
                        this.jcSpecial=this.goodsDetail.isCommission
                        if(this.jcSpecial==1){
                            this.jcDisabled=false
                        }
                        this.editContent=res.describesion
                        this.expressValue=res.templateId
                        this.specsList=res.specsList
                        if(this.specsList.length>0){
                            for(let i=0;i<this.specsList.length;i++){
                                this.specsList[i].attrJson2=this.specsList[i].attrJson.replace(/[&\|\\\*^%$#@,{}:'"\-]/g,"");
                            }
                            for(let i=0;i<this.specsList.length;i++){
                                this.specsList[i].attrJson2=this.specsList[i].attrJson.replace(/[&\|\\\*^%$#@,{}:'"\-]/g,"");
                                for(let j=1;j<this.spacsList.length;j++){
                                    if(this.specsList[i].productPrice==this.specsList[j].productPrice){
                                        this.specsPrice=true
                                        this.priceType=0
                                        this.salePrice=this.specsList[i].productPrice
                                    }else{
                                        this.specsPrice=false
                                        this.priceType=1
                                        break;
                                    }
                                }
                            }
                            
                        }
                        
                        
                    })
                }else{     //新增商品

                }
                //查询系统的奖励模式
                axiosRequest('system/query',{
                    type:"2",
                },"post").then((res)=>{
                    this.trwardType=arrToObj(res).bounty_mode
                    if(this.trwardType==1){  //查询分销模式的系统配置
                        axiosRequest('product/getStageDistr',{'id':this.proId},'post').then((res)=>{
                            this.fxDefault=res
                        })
                        
                    }else if(this.trwardType==2){ //查询极差模式的系统配置
                        axiosRequest('product/getStageSchemaList',{'productId':this.proId},'post').then((res)=>{
                            //设置默认值
                            if(res[0].perrsUnits===null||res[0].perrsUnits===''){
                                res[0].perrsUnits=1
                            }
                            if(res[0].openPeers===null||res[0].openPeers===''){
                                res[0].openPeers=0
                            }
                            if(res[0].bonusUnits===null||res[0].bonusUnits===''){
                                res[0].bonusUnits=1
                            }
                            this.jcDefault=null2str(res)
                            this.jcDefaultForm=this.jcDefault[0]
                            
                        })
                    }
                })
            },
            // ////////////   tab1部分 begin
            //商品分类
            typeChange(event){
                console.log(event)
                if(event.length==1){
                    this.goodsDetail.oneCategoryId=event[0]
                }else if(event.length==2){
                    this.goodsDetail.oneCategoryId=event[0]
                    this.goodsDetail.twoCategoryId=event[1]
                }else if(event.length==3){
                    this.goodsDetail.oneCategoryId=event[0]
                    this.goodsDetail.twoCategoryId=event[1]
                    this.goodsDetail.threeCategoryId=event[2]
                }
            },
            //配送模板
            getExpress(){
                axiosRequest('product/getDistTemplateList',{
                    'pageNum':1,
                    'pageSize':10
                },'post').then((res)=>{
                    this.expressOptions=res.list
                    this.expressOptions.forEach(item=>{
                        item.configuration=JSON.parse(item.configuration)
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
            changPosition(){
                console.log(1)
            },
            //图片放大
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
            //删除banner图
            delBanner(file,index){
                let index1=this.bannerList.indexOf(file)
                this.bannerList.splice(index1,1)
                this.goodsDetail.productImage.splice(index,1)
            },
            //删除分享图片
            delShare(){
                this.$refs.uploadShare.clearFiles();
                this.goodsDetail.shareImage=''
            },
            handleExceed(files) {
                this.$message.warning(`分享图片最多上传一张`);
            },
            exceedBanner(files){
                this.$message.warning(`商品轮播图最多上传五张`);
            },
            //banner图片上传
            bannerSuccess(file){
                this.goodsDetail.productImage.push(file.data.url)
            },
            //文件上传
            shareSuccess(file) {
                this.goodsDetail.shareImage=file.data.url
            },
            //删除logo图
            shareRemove(file) {
                this.$refs.uploadShare.clearFiles();
            },
            //tabs 切换相关函数
            tabClick(tab){
                console.log(tab)
            },
            //保存设置
            submitGoods(){
                console.log(this.goodsDetail)
                this.goodsDetail.productImage=this.goodsDetail.productImage.join(',')
                if(sessionStorage.getItem('editGoodsId')){
                    axiosRequest('product/updateProduct',this.goodsDetail,'post').then((res)=>{
                        this.extensionData[0].productId=sessionStorage.getItem('editGoodsId')
                        this.proId=sessionStorage.getItem('editGoodsId')
                        this.$message.success('保存成功')
                        this.getextensionData()
                        this.tab2Abled=false
                        this.message='tab2'
                        console.log(this.extensionData)
                    })
                }else{
                    axiosRequest('product/insertProduct',this.goodsDetail,'post').then((res)=>{
                        sessionStorage.setItem('editGoodsId',res.productId)
                        this.extensionData[0].productId=res.productId
                        this.proId=res.productId
                        this.$message.success('保存成功')
                        this.getextensionData()
                        this.tab2Abled=false
                        this.message='tab2'
                        console.log(this.extensionData)
                    })
                }
                
            },
            // ////////////   tab1部分 end
            // ////////////   tab2部分 begin
            // 获取属性
            getextensionData(){
                axiosRequest('product/getExtensionList',{
                    id:this.proId
                },'post').then((res)=>{
                    this.extensionData=res.list
                })
            },
            //添加属性
            addkey(){
                this.extensionData.push({
                    productId:sessionStorage.getItem('editGoodsId'),
                    extensionKey: '',
                    extensionValue: '',
                    type: '0'
                })
                console.log(this.extensionData)
            },
            //删除属性
            delkey(index){
                var num = this.extensionData.indexOf(index)
                if (num !== -1) {
                    axiosRequest('product/deleteExtension',{
                        id:this.extensionData[num].id
                    },'post').then((res)=>{
                        this.extensionData.splice(num, 1)
                    })    
                }
                
            } , 
            //保存tab2
            submitTab2(){
                console.log(this.extensionData)
                if(this.extensionData.length==0){
                    this.$message.error('请填写商品的扩展属性')
                }else{
                    if(sessionStorage.getItem('editGoodsId')){
                        axiosRequest('product/updateExtension',{"productExtensionList":this.extensionData},'post').then((res)=>{
                            this.$message.success('保存成功')
                            this.tab3Abled=false
                            this.message='tab3'
                        })
                    }else{
                        axiosRequest('product/insertProductExtension',{"productExtensionList":this.extensionData},'post').then((res)=>{
                            this.$message.success('保存成功')
                            this.tab3Abled=false
                            this.message='tab3'
                        })
                    }
                }
                
                
            },
            // ////////////   tab2部分 end
            // ////////////   tab3部分 begin
            submitTab3(){
                console.log(this.editContent)
                axiosRequest('product/updateProduct',{
                    "productId":sessionStorage.getItem('editGoodsId'),
                    'describesion':this.editContent
                },'post').then((res)=>{
                    this.$message.success('保存成功')
                    this.tab4Abled=false
                    this.message='tab4'
                })
            },
            // ////////////   tab3部分 end
            // ////////////   tab4部分 begin
            // 规格编辑
            addEdit(){
                this.editVisible=true
                this.specsMainName=objToArr(JSON.parse(this.specsList[0].attrJson))[0].key
                this.specsSecondName=objToArr(JSON.parse(this.specsList[0].attrJson))[1].key
                this.specsList.forEach(item=>{
                    this.specsMain.push(objToArr(JSON.parse(item.attrJson))[0].value)
                    this.specsSecond.push(objToArr(JSON.parse(item.attrJson))[1].value)
                })
                this.specsMain=unique1(this.specsMain)
                this.specsSecond=unique1(this.specsSecond)
            },
            //监听规格的增加
            handleInputConfirm(type) {
                if(type=='main'){//主规格新增
                    let specsMainInput = this.specsMainInput;
                    if (specsMainInput) {
                    this.specsMain.push(specsMainInput);
                    }
                    this.inputMainVisible = false;
                    this.specsMainInput = '';
                }else if(type=='second'){//副规格新增
                    let specsSecondInput = this.specsSecondInput;
                    if (specsSecondInput) {
                    this.specsSecond.push(specsSecondInput);
                    }
                    this.inputSecondVisible = false;
                    this.specsSecondInput = '';
                }
                
            },
            handleClose(tag,type) {
                if(type=='main'){
                    this.specsMain.splice(this.specsMain.indexOf(tag), 1);
                }else if(type=='second'){
                    this.specsSecond.splice(this.specsSecond.indexOf(tag), 1);
                }
                
            },
            showInput(type) {
                if(type=='main'){//主规格新增
                    this.inputMainVisible = true;
                    this.$nextTick(_ => {
                        this.$refs.saveMainTagInput.$refs.input.focus();
                    });
                }else if(type=='second'){//副规格新增
                    this.inputSecondVisible = true;
                    this.$nextTick(_ => {
                        this.$refs.saveSecondTagInput.$refs.input.focus();
                    });      
                }
            },
            //保存规格编辑窗口里面的规格
            addSpecs(){
                this.specsList=[]
                for(let i=0;i<this.specsMain.length;i++){
                    for(let j=0;j<this.specsSecond.length;j++){
                        let spec={}
                        spec[this.specsMainName]=this.specsMain[i]
                        spec[this.specsSecondName]=this.specsSecond[j]

                        this.specsList.push({
                            attrJson:JSON.stringify(spec),
                            attrJson2:JSON.stringify(spec).replace(/[&\|\\\*^%$#@,{}:'"\-]/g,""),
                            isnullSell:'2',
                            isSell:false,
                            productId:sessionStorage.getItem('editGoodsId'),
                            productPrice:this.salePrice,
                            productStock:0,
                            saleNum:0
                        })
                    }   
                }
                this.editVisible=false
            },
            //定价规则改变
            priceTypeChange(value){
                console.log(value)
                if(value==0){//统一定价
                    this.specsPrice=true
                    for(let i=0;i<this.specsList.length;i++){
                        this.specsList[i].productPrice=this.salePrice
                    }
                }else if(value==1){// 按规格定价
                    this.specsPrice=false
                }
            },
            //统一零售价更改
            salePriceChange(){
                if(this.specsPrice==true){
                    for(let i=0;i<this.specsList.length;i++){
                        this.specsList[i].productPrice=this.salePrice
                    }
                }
            },
            //规格保存
            submitTab4(){
                if(this.specsList.length==0){
                    this.$message.error('请添加至少一种规格')
                }else if(this.specsList[0].productPrice==''){
                    this.$message.error('请填写商品价格')
                }else if(this.specsList.length>0){
                    // if(this.specsList[0].productPrice==""){
                    //     this.$message.error('请将规格价格以及库存填写完整')
                    // }else{
                        axiosRequest('product/insertProductSpecs',{'specsList':this.specsList},'post').then((res)=>{
                            this.$message.success('保存成功')
                            if(this.trwardType==0){//系统配置未开启奖励模式
                                this.tab6Abled=false
                                this.message='tab6'
                            }else{
                                this.tab5Abled=false
                                this.message='tab5'
                                if(this.trwardType==1){//开启分销商模式
                                    this.fxType=1
                                }else if(this.trwardType==2){//开启极差模式
                                    this.jcType=1
                                }
                            }
                            
                        })
                    //}
                } 
            },
            // ////////////   tab4部分 end
            //////////////    tab5部分 begin
            fxSpecialChange(value){  //分销模式编辑状态修改
                if(value==0){
                    this.fxDisabled=true
                }else if(value==1){
                    this.fxDisabled=false
                }
            },
            jcSpecialChange(value){   //极差模式编辑状态修改
                if(value==0){
                    this.jcDisabled=true
                }else if(value==1){
                    this.jcDisabled=false
                }
            },
            submitTab5(){
                if(this.trwardType==1){  //保存分销模式的系统配置
                    if(this.fxSpecial==1){//开启了独立配置
                        this.fxDefault.isCommission=1
                    }
                    else{
                        this.fxDefault.isCommission=0
                    }
                    this.fxDefault.isDealer=this.fxType
                    this.fxDefault.productId=this.proId
                    axiosRequest('product/insertStageDistr',this.fxDefault,'post').then((res)=>{
                        this.$confirm('是否立即上架商品', '提示', {
                            confirmButtonText: '上架商品',
                            cancelButtonText: '继续编辑',
                            type: 'info'
                        }).then(() => {
                            axiosRequest('product/updateProduct',{
                                productId:this.proId,
                                publishStatus:1   
                            },'post').then((res)=>{
                                this.$message.success('上架成功')
                                this.tab6Abled=false
                                this.message='tab6'              
                            })
                        }).catch(() => {
                            this.$message.success('保存成功')
                            this.tab6Abled=false
                            this.message='tab6'          
                        });
                        
                    })
                }else if(this.trwardType==2){ //保存极差模式的系统配置
                    if(this.jcSpecial==1){//开启了独立配置
                        for(let i=1;i<this.jcDefault.length;i++){
                            if(this.jcDefault[i].perrsUnits===null||this.jcDefault[i].perrsUnits===''){
                                this.jcDefault[i].perrsUnits=1
                            }
                            if(this.jcDefault[i].openPeers===null||this.jcDefault[i].openPeers===''){
                                this.jcDefault[i].openPeers=0
                            }
                            if(this.jcDefault[i].bonusUnits===null||this.jcDefault[i].bonusUnits===''){
                                this.jcDefault[i].bonusUnits=1
                            } 
                        }
                        console.log(this.jcDefault)
                        axiosRequest('product/insertStageSchema',{
                            'isCommission':this.jcSpecial,
                            'isDealer':this.jcType,
                            'productId':this.proId,
                            'schemaRequestList':this.jcDefault
                            },'post').then((res)=>{
                                this.$confirm('是否立即上架商品', '提示', {
                                    confirmButtonText: '上架商品',
                                    cancelButtonText: '继续编辑',
                                    type: 'info'
                                }).then(() => {
                                    axiosRequest('product/updateProduct',{
                                        productId:this.proId,
                                        publishStatus:1   
                                    },'post').then((res)=>{
                                        this.$message.success('上架成功')
                                        this.tab6Abled=false
                                        this.message='tab6'              
                                    })
                                }).catch(() => {
                                    this.$message.success('保存成功')
                                    this.tab6Abled=false
                                    this.message='tab6'          
                                });
                        })
                    }   
                    else{
                        this.$confirm('是否立即上架商品', '提示', {
                            confirmButtonText: '上架商品',
                            cancelButtonText: '继续编辑',
                            type: 'info'
                        }).then(() => {
                            axiosRequest('product/updateProduct',{
                                productId:this.proId,
                                publishStatus:1   
                            },'post').then((res)=>{
                                this.$message.success('上架成功')
                                this.tab6Abled=false
                                this.message='tab6'              
                            })
                        }).catch(() => {
                            this.tab6Abled=false
                            this.message='tab6'          
                        });
                    }
                } 
            },
            /////////////     tab5部分 end
            /////////////     tab6部分 beigin
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
                    "model":0,
                    "nextHeavy": '',
                    "nextHeavyCost": '',
                    "provinceCode": ''
                });
            },
            submitTab6(){
                console.log(this.radio)
                if(this.radio==1){         //选择已有模板   
                    axiosRequest('product/updateProduct',{
                        productId:this.proId,
                        templateId:this.expressValue   
                    },'post').then((res)=>{
                        this.$message.success('保存成功！');
                        this.tab7Abled=false
                        this.message='tab7'
                    })
                }else if(this.radio==2){   //选择新增模板
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
                        this.expressValue=res.id
                        axiosRequest('product/updateProduct',{
                            productId:this.proId,
                            templateId:this.expressValue   
                        },'post').then((res)=>{
                            this.$message.success('保存成功！');
                            this.tab7Abled=false
                            this.message='tab7'
                        })
                    })
                    
                }
                this.getIsBanner()
            },

            /////////////     tab6部分 end
            /////////////     tab7部分 begin
            // loding图上传成功
            //bannerUrl:'',
            // recommUrl:''
            bannerSetSuccess(file){
                this.bannerUrl=""
                this.goodsBnnerUrl.bannerUrl=file.data.url
            },
            // loding图上传成功
            recommSuccess(file){
                this.recommUrl=""
                this.goodsBnnerUrl.recommUrl=file.data.url
            },
            //删除load图
            bannerRemove(file) {
                
                this.$refs.uploadBanner.clearFiles();
                this.goodsBnnerUrl.bannerUrl=""
            },
            //删除logo图
            recommRemove(file) {
                this.$refs.uploadRecomm.clearFiles();
                this.goodsBnnerUrl.recommUrl=""
            },
            //删除load默认图片
            delDefaultBanner(){
                console.log(1)
                this.goodsBnnerUrl.bannerUrl=""
            }, 
            //删除load默认图片
            delDefaultRecomm(){
                this.goodsBnnerUrl.recommUrl=""
            },
            //图片放大
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file;
                this.dialogVisible = true;
            },
            //查询商品是否关联到轮播以及首页推荐
            getIsBanner(){
                axiosRequest('product/getMasteImage',{
                    'productId':this.proId,
                },'post').then(res=>{
                    if(res==null){
                        console.log(1)
                    }else{
                        if(res.length==2){
                            this.bannerCheck=true
                            this.proCheck=true
                            res.forEach(item=>{
                                if(item.isMaster==0){
                                    this.goodsBnnerUrl.bannerUrl=item.picUrl
                                    this.goodsBnnerUrl.bannerId=item.id
                                }else if(item.isMaster==1){
                                    this.goodsBnnerUrl.recommUrl=item.picUrl
                                    this.goodsBnnerUrl.recommId=item.id
                                }    
                            })
                        }
                        if(res.length==1){
                            if(res[0].isMaster==0){
                                this.bannerCheck=true
                                this.goodsBnnerUrl.bannerUrl=res[0].picUrl
                            }else if(res[0].isMaster==1){
                                this.proCheck=true
                                this.goodsBnnerUrl.recommUrl=res[0].picUrl
                            }

                        }
                        console.log(this.goodsBnnerUrl)
                    }
                })
            },
            submitTab7(){
                if(sessionStorage.getItem('editGoodsId')){
                    if(this.goodsBnnerUrl.bannerUrl!=''&&this.goodsBnnerUrl.recommUrl==''){
                        axiosRequest('product/updateProductImage',{
                            imgRequestList:[{
                                id:this.goodsBnnerUrl.bannerId,
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.bannerUrl,
                                isMaster:0,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                    }else if(this.goodsBnnerUrl.bannerUrl==''&&this.goodsBnnerUrl.recommUrl!=''){
                        axiosRequest('product/updateProductImage',{
                            imgRequestList:[{
                                id:this.goodsBnnerUrl.recommId,
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.recommUrl,
                                isMaster:1,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                    }else if(this.goodsBnnerUrl.bannerUrl!=''&&this.goodsBnnerUrl.recommUrl!=''){
                        axiosRequest('product/updateProductImage',{
                            imgRequestList:[{
                                id:this.goodsBnnerUrl.bannerId,
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.bannerUrl,
                                isMaster:0,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                        axiosRequest('product/updateProductImage',{
                            imgRequestList:[{
                                id:this.goodsBnnerUrl.recommId,
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.recommUrl,
                                isMaster:1,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                    }
                }else{
                    if(this.goodsBnnerUrl.bannerUrl!=''&&this.goodsBnnerUrl.recommUrl==''){
                        axiosRequest('product/insertProductImage',{
                            imgRequestList:[{
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.bannerUrl,
                                isMaster:0,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                    }else if(this.goodsBnnerUrl.bannerUrl==''&&this.goodsBnnerUrl.recommUrl!=''){
                        axiosRequest('product/insertProductImage',{
                            imgRequestList:[{
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.recommUrl,
                                isMaster:1,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                    }else if(this.goodsBnnerUrl.bannerUrl!=''&&this.goodsBnnerUrl.recommUrl!=''){
                        axiosRequest('product/insertProductImage',{
                            imgRequestList:[{
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.bannerUrl,
                                isMaster:0,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                        axiosRequest('product/insertProductImage',{
                            imgRequestList:[{
                                productId:this.proId,
                                picDesc:'',
                                picUrl:this.goodsBnnerUrl.recommUrl,
                                isMaster:1,
                                picStatus:1
                                }
                            ]
                        },'post').then((res)=>{
                            this.getPublicStatus()
                        })
                    }
                }    
                

            },
            getPublicStatus(){
                axiosRequest('product/getProductDetail',{
                    'id':sessionStorage.getItem('editGoodsId')
                },'post').then((res)=>{
                    if(res.publishStatus==0){
                        this.$confirm('是否立即上架商品', '提示', {
                            confirmButtonText: '上架商品',
                            cancelButtonText: '返回列表',
                            type: 'info'
                        }).then(() => {
                            axiosRequest('product/updateProduct',{
                                productId:this.proId,
                                publishStatus:1   
                            },'post').then((res)=>{
                                this.$message.success('上架成功')  
                                this.$router.push('/goodslist')         
                            })
                        }).catch(() => {
                            this.$router.push('/goodslist') 
                        });
                    }else{
                        this.$message.success('商品修改成功')
                    }
                    
                })
            },
            /////////////     tab7部分 end











            

            
            
            
            
            handleRead(index) {
                const item = this.unread.splice(index, 1);
                console.log(item);
                this.read = item.concat(this.read);
            },
            handleDel(index) {
                const item = this.read.splice(index, 1);
                this.recycle = item.concat(this.recycle);
            },
            handleRestore(index) {
                const item = this.recycle.splice(index, 1);
                this.read = item.concat(this.read);
            },
             
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

    .el-upload--text .el-icon-upload{
        margin-top: 10px;
    }

    .addGoods1 .el-upload-dragger,.addGoods1 .el-upload--text{
        width: 220px ;
        height: 110px;
    }
    .el-form-item--mini.el-form-item, .el-form-item--small.el-form-item{
        margin-bottom: 10px;
    }
    
    .addGoods1 .el-input{
        width: 30%;
    }
    .addGoodsType .el-input{
        width: 142.5%;
    }
    .mt10{
        margin-top: 20px;
    }
    .mt30{
        margin-top: 30px;
    }
    
    .mr10{
        margin-right: 15px;
    }
    
    .expressEdit .el-input{
        width: 30%;
    }
    .expressEdit .expressPrice .el-input{
        width: 80%;
    }
    .addCity .el-select,.addCity .el-input{
        width: 140px;
    }
    .marketPrice .el-input{
        width: 20%;
    }
    .marketPrice label{
        width: 100px;
        text-align: left;
    }
    .guigeEdit .el-input{
        width: 80%;
    }
      .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  .mb10{
      margin-bottom: 10px;
  }
  .specsName{
      width: 45%;
  }
  .uploadCom>ul{
        display: none;
    }
</style>

