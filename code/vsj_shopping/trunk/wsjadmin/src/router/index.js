import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '微视角' },
            children:[
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/homePage/Dashboard.vue'], resolve),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/accounttable',
                    component: resolve => require(['../components/page/systemPage/userSetting/AccountTable.vue'], resolve),
                    meta: { title: '账户管理' }
                },
                {
                    path: '/roletable',
                    component: resolve => require(['../components/page/systemPage/userSetting/RoleTable.vue'], resolve),
                    meta: { title: '角色管理' }
                },
                {
                    path: '/mallsetting',
                    component: resolve => require(['../components/page/systemPage/systemSetting/MallSetting.vue'], resolve),
                    meta: { title: '商城装修' }
                },
                {
                    path: '/systemsetting',
                    component: resolve => require(['../components/page/systemPage/systemSetting/SystemSetting.vue'], resolve),
                    meta: { title: '系统参数' }
                },
                {
                    path: '/paysetting',
                    component: resolve => require(['../components/page/systemPage/systemSetting/PaySetting.vue'], resolve),
                    meta: { title: '支付参数' }
                },
                {
                    path: '/othersetting',
                    component: resolve => require(['../components/page/systemPage/systemSetting/OtherSetting.vue'], resolve),
                    meta: { title: '第三方api参数' }
                },
                
                {
                    path: '/typetable',
                    component: resolve => require(['../components/page/goodsPage/goodsSetting/TypeTable.vue'], resolve),
                    meta: { title: '分类管理' }
                },
                {
                    path: '/goodslist',
                    component: resolve => require(['../components/page/goodsPage/goodsSetting/GoodsList.vue'], resolve),
                    meta: { title: '商品列表' }
                },
                {
                    path: '/addgoods',
                    component: resolve => require(['../components/page/goodsPage/goodsSetting/AddGoods.vue'], resolve),
                    meta: { title: '商品管理' }
                },
                {
                    path: '/expresssetting',
                    component: resolve => require(['../components/page/goodsPage/goodsSetting/ExpressSetting.vue'], resolve),
                    meta: { title: '配送模板' }
                },
                {
                    path: '/bannersetting',
                    component: resolve => require(['../components/page/goodsPage/goodsProduct/BannerSetting.vue'], resolve),
                    meta: { title: '轮播图设置' }
                },
                {
                    path: '/recomsetting',
                    component: resolve => require(['../components/page/goodsPage/goodsProduct/RecomSetting.vue'], resolve),
                    meta: { title: '推荐商品设置' }
                },
                {
                    path: '/orderlist',
                    component: resolve => require(['../components/page/orderPage/OrderList.vue'], resolve),
                    meta: { title: '订单管理' }
                },
                {
                    path: '/delivergoods',
                    component: resolve => require(['../components/page/orderPage/DeliverGoods.vue'], resolve),
                    meta: { title: '批量发货' }
                },
                {
                    path: '/changeorder',
                    component: resolve => require(['../components/page/orderPage/ChangeOrder.vue'], resolve),
                    meta: { title: '退换货订单' }
                },
                {
                    path: '/waitorder',
                    component: resolve => require(['../components/page/orderPage/Waitorder.vue'], resolve),
                    meta: { title: '待发货订单' }
                },
                {
                    path: '/memberlist',
                    component: resolve => require(['../components/page/memberPage/memerSetting/MemberList.vue'], resolve),
                    meta: { title: '会员管理' }
                },
                {
                    path: '/leversetting',
                    component: resolve => require(['../components/page/memberPage/memerSetting/LeverSetting.vue'], resolve),
                    meta: { title: '等级管理' }
                },
                {
                    path: '/leveradd',
                    component: resolve => require(['../components/page/memberPage/memerSetting/LeverAdd.vue'], resolve),
                    meta: { title: '升级条件' }
                },
                {
                    path: '/relationsetting',
                    component: resolve => require(['../components/page/memberPage/relationSetting/RelationSetting.vue'], resolve),
                    meta: { title: '关系设置' }
                },
                {
                    path: '/rewardfx',
                    component: resolve => require(['../components/page/memberPage/rewardSetting/RewardFx.vue'], resolve),
                    meta: { title: '分销模式' }
                },
                {
                    path: '/rewardJc',
                    component: resolve => require(['../components/page/memberPage/rewardSetting/RewardJc.vue'], resolve),
                    meta: { title: '经销商极差模式' }
                },
                {
                    path: '/financialaudit',
                    component: resolve => require(['../components/page/financePage/auditCenter/FinancialAudit.vue'], resolve),
                    meta: { title: '提现审核' }
                },
                {
                    path: '/dividendlist',
                    component: resolve => require(['../components/page/financePage/formCenter/DividendList.vue'], resolve),
                    meta: { title: '订单分成明细' }
                },
                {
                    path: '/salelist',
                    component: resolve => require(['../components/page/financePage/formCenter/SaleList.vue'], resolve),
                    meta: { title: '商品销量统计' }
                },
                {
                    path: '/seckillact',
                    component: resolve => require(['../components/page/activityPage/goodsActivity/SeckillAct.vue'], resolve),
                    meta: { title: '秒杀活动' }
                },
                {
                    path: '/teamact',
                    component: resolve => require(['../components/page/activityPage/goodsActivity/TeamAct.vue'], resolve),
                    meta: { title: '团购活动' }
                },
                {
                    path: '/addseckill',
                    component: resolve => require(['../components/page/activityPage/goodsActivity/AddSeckill.vue'], resolve),
                    meta: { title: '发起秒杀活动' }
                },
                {
                    path: '/addteam',
                    component: resolve => require(['../components/page/activityPage/goodsActivity/AddTeam.vue'], resolve),
                    meta: { title: '发起团购活动' }
                },
                {
                    path: '/orderdetail',
                    component: resolve => require(['../components/page/orderPage/OrderDetail.vue'], resolve),
                    meta: { title: '订单详情' }
                },
                {
                    path: '/memberdetail',
                    name:"memberdetail",
                    component: resolve => require(['../components/page/memberPage/memerSetting/MemberDetail.vue'], resolve),
                    meta: { title: '会员详情' }
                },
                /******素材推广******* */
                {
                    path: '/shopActive',
                    name:"shopActive",
                    component: resolve => require(['../components/page/material/shopActive.vue'], resolve),
                    meta: { title: '素材管理' }
                },
                {
                    path: '/materialSetting',
                    name:"materialSetting",
                    component: resolve => require(['../components/page/material/materialSetting.vue'], resolve),
                    meta: { title: '会员详情' }
                },
                {
                    path: '/demo',
                    name:"demo",
                    component: resolve => require(['../components/page/other/demo.vue'], resolve),
                    meta: { title: '会员详情'}
                },
                
                
                {
                    path: '/404',
                    component: resolve => require(['../components/page/other/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../components/page/other/403.vue'], resolve),
                    meta: { title: '403' }
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/homePage/Login.vue'], resolve)
        },
        // {
        //     path: '*',
        //     redirect: '/404'
        // }
    ]
})
