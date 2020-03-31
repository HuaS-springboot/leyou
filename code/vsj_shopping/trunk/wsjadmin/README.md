
## components/page ##
- [x] common.js         通用方法
- [x] rule.js           表单提交验证规则
- [x] pageNum           分页的列表中的每页数量量


- [x] homePage              首页
    
- [x] systemPage            系统管理
    - [x] userSetting           账户管理
    - [x] systemSetting         系统配置
- [x] goodsPage             商品管理
    - [x] goodsSetting          商品管理
    - [x] goodsProduct          推荐商品设置
- [x] orderPage             订单管理
    
- [x] memberPage            会员中心
    - [x] memerSetting          会员管理
    - [x] relationSetting       关系设置
    - [x] rewardSetting         奖励设置
- [x] financePage           财务中心
    - [x] auditCenter           审核中心
    - [x] formCenter            报表中心
- [x] acticityPage          活动中心
    - [x] goodsActivity         商品活动



## components/page ##
- [x] AccountTable      账户管理
- [x] RoleTable         角色管理
- [x] MallSetting       商城装修
- [x] SystemSetting     系统参数
- [x] PaySetting        支付参数
- [x] TypeTable         分类管理
- [x] GoodsList         商品列表
- [x] AddGoods          添加商品编辑商品
- [x] ExpressSetting    快递模板
- [x] BannerSetting     轮播图设置
- [x] RecomSetting      推荐商品设置
- [x] OrderList         订单管理
- [x] OrderDetail       订单详情
- [x] DeliverGoods      批量发货
- [x] ChangeOrder       退换货订单
- [x] UserList          会员列表
- [x] LeverSetting      等级管理
- [x] LerverAdd         升级条件
- [x] RelationSetting   关系设置
- [x] RewardFx          分销模式
- [x] RewardJc          经销商极差模式
- [x] FinancialAudit    财务审核
- [x] DividendList      订单分成明细统计
- [x] SaleList          商品销量统计
- [x] SeckillAct        秒杀活动
- [x] TeamAct           团购活动
- [x] AddSeckill        添加秒杀活动
- [x] AddTeam           添加拼团活动
- [x] OrderDetail       订单详情
- [x] UserDetail        会员详情






<!-- 常用参数说明 -->
- [x] userId            用户id
- [x] platformCode      平台码(所有接口必传)
- [x] token             token





## 安装步骤 ##

npm install         // 安装项目依赖，等待安装完成之后，安装失败可用 cnpm 或 yarn

// 开启服务器，浏览器访问 http://localhost:8080
npm run serve

// 执行构建命令，生成的dist文件夹放在服务器下即可访问
npm run build
```
## 组件使用说明与演示 ##

### vue-schart ###
vue.js封装sChart.js的图表组件。访问地址：[vue-schart](https://github.com/linxin/vue-schart)
<p><a href="https://www.npmjs.com/package/vue-schart"><img src="https://img.shields.io/npm/dm/vue-schart.svg" alt="Downloads"></a></p>

```html
<template>
    <div>
        <schart  class="wrapper"
				:canvasId="canvasId"
				:type="type"
				:data="data"
				:options="options"
		></schart>
    </div>
</template>
	
<script>
    import Schart from 'vue-schart';        // 导入Schart组件
    export default {
        data: function(){
            return {
                canvasId: 'myCanvas',       // canvas的id
                type: 'bar',                // 图表类型
                data: [
                    {name: '2014', value: 1342},
                    {name: '2015', value: 2123},
                    {name: '2016', value: 1654},
                    {name: '2017', value: 1795},
                ],
                options: {                  // 图表可选参数
                    title: 'Total sales of stores in recent years'
                }
            }
        },
        components: {
            Schart
        }
    }
</script>
<style>
.wrapper{
	width: 7rem;
	height: 5rem;
}
</style>
```

## 其他注意事项 ##
### 一、如果我不想用到上面的某些组件呢，那我怎么在模板中删除掉不影响到其他功能呢？ ###

举个栗子，我不想用 Vue-Quill-Editor 这个组件，那我需要分四步走。

第一步：删除该组件的路由，在目录 src/router/index.js 中，找到引入改组件的路由，删除下面这段代码。

```JavaScript
{
    // 富文本编辑器组件
    path: '/editor',
    component: resolve => require(['../components/page/VueEditor.vue'], resolve) 
},
```

第二步：删除引入该组件的文件。在目录 src/components/page/ 删除 VueEditor.vue 文件。

第三步：删除该页面的入口。在目录 src/components/common/Sidebar.vue 中，找到该入口，删除下面这段代码。
	
```js
{
	index: 'editor',
	title: '富文本编辑器'
},
```

第四步：卸载该组件。执行以下命令：
	
	npm un vue-quill-editor -S

完成。

### 二、如何切换主题色呢？ ###

第一步：打开 src/main.js 文件，找到引入 element 样式的地方，换成浅绿色主题。

```javascript
import 'element-ui/lib/theme-default/index.css';    // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
```

第二步：打开 src/App.vue 文件，找到 style 标签引入样式的地方，切换成浅绿色主题。

```javascript
@import "../static/css/main.css";
@import "../static/css/color-dark.css";     /*深色主题*/
/*@import "../static/css/theme-green/color-green.css";   !*浅绿色主题*!*/
```

第三步：打开 src/components/common/Sidebar.vue 文件，找到 el-menu 标签，把 background-color/text-color/active-text-color 属性去掉即可。

## License

[MIT](https://github.com/lin-xin/vue-manage-system/blob/master/LICENSE)