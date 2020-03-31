<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
            text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem,i) in subItem.subs" :key="i" :index="threeItem.index">
                                    {{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :key="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from '../common/bus';
    export default {
        data() {
            return {
                collapse: false,
                items: [
                    {
                        icon: 'el-icon-lx-home',
                        index: 'dashboard',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-setting',
                        index: '2',
                        title: '系统管理',
                        subs: [
                            {
                                index: '2-2',
                                title: '账户管理',
                                subs:[
                                    {
                                        index:'accounttable',
                                        title:'账户管理'
                                    },
                                    {
                                        index:'roletable',
                                        title:'角色管理'
                                    }
                                ]
                            },
                            {
                                index: '2-3',
                                title: '系统配置',
                                subs: [
                                    {
                                        index: 'mallsetting',
                                        title: '商城装修'
                                    },
                                    {
                                        index: 'systemsetting',
                                        title: '系统参数'
                                    },
                                    {
                                        index: 'paysetting',
                                        title: '支付参数'
                                    },
                                    {
                                        index: 'othersetting',
                                        title: '第三方api参数'
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-shopping-bag-2',
                        index: '3',
                        title: '商品管理',
                        subs:[
                            {
                                index:'typetable',
                                title:'分类管理'
                            },
                            {
                                index:'3-2',
                                title:'商品管理',
                                subs:[
                                    {
                                        index:'goodslist',
                                        title:'商品列表'
                                    },
                                    {
                                        index:'expresssetting',
                                        title:'配送模板'
                                    },
                                ]
                            },
                            {
                                index:'3-3',
                                title:'推荐商品设置',
                                subs:[
                                    {
                                        index:'bannersetting',
                                        title:'轮播图设置'
                                    },
                                    {
                                        index:'recomsetting',
                                        title:'首页推荐商品设置'
                                    },
                                ]
                            },
                        ]
                    },
                    {
                        icon: 'el-icon-tickets',
                        index: '4',
                        title: '订单管理',
                        subs:[
                            {
                                index:'orderlist',
                                title:'订单管理'
                            },
                            {
                                index:'delivergoods',
                                title:'批量发货'
                            },
                            {
                                index:'changeorder',
                                title:'退换货订单'
                            },
                            
                        ]
                    },
                    {
                        icon: 'el-icon-user',
                        index: '5',
                        title: '会员中心',
                        subs:[
                            {
                                index:'5-1',
                                title:'会员管理',
                                subs:[
                                    {
                                        index:'memberlist',
                                        title:'会员列表'
                                    },
                                    {
                                        index:'leversetting',
                                        title:'等级管理'
                                    },
                                    {
                                        index:'leveradd',
                                        title:'升级条件'
                                    },
                                    
                                ]
                            },
                            {
                                index:'relationsetting',
                                title:'关系设置',
                                

                            },
                            {
                                index:'5-3',
                                title:'奖励模式',
                                subs:[
                                    {
                                        index:'rewardfx',
                                        title:'分销模式'
                                    },
                                    {
                                        index:'rewardjc',
                                        title:'经销商极差模式'
                                    },
                                    
                                ]
                            },
                            
                        ]
                    },
                    {
                        icon: 'el-icon-money',
                        index: '6',
                        title: '财务中心',
                        subs:[
                            {
                                index:'FinancialAudit',
                                title:'审核管理',
                            },
                            {
                                index:'6-2',
                                title:'报表中心',
                                subs:[
                                    {
                                        index:'dividendlist',
                                        title:'订单分成明细'
                                    },
                                    {
                                        index:'salelist',
                                        title:'商品销量统计'
                                    },
                                    
                                ]
                            },
                            
                        ]
                    },
                    {
                        icon: 'el-icon-wind-power',
                        index: '7',
                        title: '活动中心',
                        subs:[
                            {
                                index:'7-1',
                                title:'商品活动',
                                subs:[
                                    {
                                        index:'seckillact',
                                        title:'秒杀活动'
                                    },
                                    {
                                        index:'teamact',
                                        title:'拼团活动'
                                    },
                                    
                                ]
                            },
                            
                        ]
                    },
                    {
                        icon: 'el-icon-wind-power',
                        index: '8',
                        title: '素材管理系统配置',
                        subs:[{
                                	index: 'SysConf',
                                    title: '系统配置'
                                    },
                                    {
                                    index: 'sortmanager',
                                    title: '分类管理'
                                    },
                                    {
                                    index: '8-4',
                                    title: '页面装修'
                                    },
                                    {
                                    index: 'otherAPI',
                                    title: '第三方API'
                                    },
                                    {
                                    index: 'PayParameters',
                                    title: '支付参数'}       
                        ]
                    },
                     {
						        icon: 'el-icon-wind-power',
                                index:'materialmanager',
                                title:'素材管理',
								subs:[{
									index: 'materiallist',
								    title: '素材管理'},
									{
									   index: 'materialCheck',
									   title: '审核中心'}]
								},
                          
                            {
						        icon: 'el-icon-wind-power',
                                index:'MemberCenter',
                                title:'会员中心',
								subs:[{
									index: 'materialmemberlist',
								    title: '会员列表'},
									{
									   index: 'codemanagement',
									   title: '兑换码管理'},
									   {
									   index: 'Memberspackage',
									   title: '会员套餐'},
									     {
									   index: 'Memberoftheorder',
									   title: '会员订单'},
									   {
									   index: 'Memberleve',
									   title: '会员等级'},
									   {
                                	   index: 'leverconfig',
                                       title: '等级权限配置'
                                        }
									     
								]
								}
                ]
            }
        },
        computed:{
            onRoutes(){
                return this.$route.path.replace('/','');
            }
        },
        created(){
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
            })
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom:0;
        overflow-y: scroll;
    }
    .sidebar::-webkit-scrollbar{
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse){
        width: 250px;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
