<template>
	<view>
		<view v-if="showHeader" class="status" :style="{position:headerPosition,top:statusTop}"></view>
		<view v-if="showHeader" class="header" :style="{position:headerPosition,top:headerTop}">
			<view class="addr"></view>
		</view>
		<!-- 占位 -->
		<view v-if="showHeader" class="place"></view>
		<!-- 用户信息 -->
		<view class="user">
			<!-- 头像 -->
			<view class="left">
				<image :src="user.face" @tap="toSetting"></image>
			</view>
			<!-- 昵称,个性签名 -->
			<view class="right">
				<view style="display: flex;">
					<view class="username" @tap="toLogin">{{user.username}}</view>
					<view class="memberLevel" @tap="toLogin">{{user.memberLevel}}</view>
				</view>
				<view>
					<view class="memberId">{{user.memberId}}</view>
				</view>
				<view style="display: flex;">
					<view class="signature" @tap="toSetting">{{user.phone}} </view>

					<view class="signature" @tap='updatePhone'>更换</view>
				</view>
			</view>
			<!-- 二维码按钮 -->
			<view class="erweima" @tap="toMyQR">
				<view class="icon qr"></view>
			</view>
		</view>
		<!-- 我的钱包 -->
		<view class="money">
			<!-- <view class="title">我的钱包</view> -->
			<view class="list">
				<view class="box">
					<view class="balance" @tap="balanceDetail">
						<view class='fontSize'>余额</view>
						<view class='fontSize'>￥{{user.integral}}</view>
					</view>
					<view class="balance">
						<view class='fontSize'>累计收入</view>
						<view class='fontSize'>￥{{user.integral}}</view>
					</view>
					<view class="balance">
						<view class='fontSize'>代收奖励</view>
						<view class='fontSize'>￥{{user.integral}}</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 我的订单 -->
		<view class="toolbar">
			<view class="orderHeader">
				<view class="title">我的订单</view>
				<view class="allOrder">全部订单</view>
			</view>
			<view class="list">
				<view class="box" v-for="(row,index) in orderList" :key="index" @tap="toOrderList(index)">
					<view class="img">
						<image :src="row.img"></image>
					</view>
					<view class="text">{{row.text}}</view>
				</view>
			</view>
		</view>
		<!-- 服务中心 -->
		<view class="toolbar">
			<view class="title">服务中心</view>
			<view class="list">
				<view class="box" v-for="(row,index) in mytoolbarList" :key="index" @tap="toPage(row.url)">
					<view class="img">
						<image :src="row.img"></image>
					</view>
					<view class="text">{{row.text}}</view>
				</view>
			</view>
		</view>
		<!-- 占位 -->
		<view class="place-bottom"></view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				isfirst: true,
				headerPosition: "fixed",
				headerTop: null,
				statusTop: null,
				showHeader: true,
				//个人信息,
				user: {
					username: '游客1002',
					memberLevel: 'VIP4',
					memberId: '6666666',
					face: '/static/img/face.jpg',
					phone: '134123241324',
					integral: 3.52,
					balance: 0,
					cashOut: '提现',
					detail: '详情',
					detailView: '查看详情'
				},
				// 订单类型
				orderList: [{
						text: '待付款',
						icon: "fukuan"
					},
					{
						text: '待发货',
						icon: "fahuo"
					},
					{
						text: '待收货',
						icon: "shouhuo"
					},
					{
						text: '售后',
						icon: "pingjia"
					},
				],
				// 工具栏列表
				mytoolbarList: [{
						url: '../../user/keep/keep',
						text: '我的收藏',
						img: '/static/img/user/point.png'
					},
					{
						url: '../../user/coupon/coupon',
						text: '优惠券',
						img: '/static/img/user/quan.png'
					},
					{
						url: '',
						text: '新客豪礼',
						img: '/static/img/user/renw.png'
					},
					{
						url: '',
						text: '领红包',
						img: '/static/img/user/momey.png'
					},

					{
						url: '../../user/address/address',
						text: '收货地址',
						img: '/static/img/user/addr.png'
					},
					{
						url: '',
						text: '账户安全',
						img: '/static/img/user/security.png'
					},
					{
						url: '',
						text: '银行卡',
						img: '/static/img/user/bank.png'
					},
					{
						url: '',
						text: '抽奖',
						img: '/static/img/user/choujiang.png'
					},
					// {text:'客服',img:'/static/img/user/kefu.png'},
					// {text:'签到',img:'/static/img/user/mingxi.png'}

				]
			}
		},
		//下拉刷新，需要自己在page.json文件中配置开启页面下拉刷新 "enablePullDownRefresh": true
		onPullDownRefresh() {
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onPageScroll(e) {
			//兼容iOS端下拉时顶部漂移
			this.headerPosition = e.scrollTop >= 0 ? "fixed" : "absolute";
			this.headerTop = e.scrollTop >= 0 ? null : 0;
			this.statusTop = e.scrollTop >= 0 ? null : -this.statusHeight + 'px';
		},
		onLoad() {
			this.statusHeight = 0;
			// #ifdef APP-PLUS
			this.showHeader = false;
			this.statusHeight = plus.navigator.getStatusbarHeight();
			// #endif
		},
		onReady() {
			//此处，演示,每次页面初次渲染都把登录状态重置
			uni.setStorage({
				key: 'UserInfo',
				data: false,
				success: function() {},
				fail: function(e) {}
			});
		},
		onShow() {
			uni.getStorage({
				key: 'UserInfo',
				success: (res) => {
					if (!res.data) {
						if (this.isfirst) {
							//this.toLogin();
						}
						return;
					}
					this.user = res.data;
				},
				fail: (e) => {
					//this.toLogin(); 
				}
			});
		},
		methods: {
			//跟换手机号
			updatePhone() {},
			//余额详情
			balanceDetail() {
				console.log(uni)
				uni.navigateTo({
					url: '../info/info?newsid=' + newsid,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
			//消息列表
			toMsg() {
				uni.navigateTo({
					url: '../../msg/msg'
				})
			},
			toOrderList(index) {
				uni.setStorageSync('tbIndex', index);
				uni.navigateTo({
					url: '../../user/order_list/order_list?tbIndex=' + index
				})
			},
			toSetting() {
				uni.navigateTo({
					url: '../../user/setting/setting'
				})
			},
			toMyQR() {
				uni.navigateTo({
					url: '../../user/myQR/myQR'
				})
			},
			toLogin() {
				uni.showToast({
					title: '请登录',
					icon: "none"
				});
				uni.navigateTo({
					url: '../../login/login'
				})
				this.isfirst = false;
			},
			isLogin() {
				//实际应用中,用户登录状态应该用token等方法去维持登录状态.
				const value = uni.getStorageSync('UserInfo');
				if (value) {
					return true;
				}
				return false
			},
			toDeposit() {
				uni.navigateTo({
					url: '../../user/deposit/deposit'
				})
			},
			toPage(url) {
				if (!url) {
					uni.showToast({
						title: '模板未包含此页面',
						icon: "none"
					});
					return;
				}
				uni.navigateTo({
					url: url
				})
			}
		}
	}
</script>
<style lang="scss">
	page {
		position: relative;
		background-color: #fff;
	}

	.status {
		width: 100%;
		height: 0;
		position: fixed;
		z-index: 10;
		background-color: #f06c7a;
		top: 0;
		/*  #ifdef  APP-PLUS  */
		height: var(--status-bar-height); //覆盖样式
		/*  #endif  */

	}

	.header {
		width: 92%;
		padding: 0 4%;
		height: 36upx;
		display: flex;
		justify-content: flex-end;
		align-items: center;
		position: fixed;
		top: 0;
		z-index: 10;
		background-color: #f06c7a;
		/*  #ifdef  APP-PLUS  */
		top: var(--status-bar-height);

		/*  #endif  */
		.icon-btn {
			width: 120upx;
			height: 60upx;
			flex-shrink: 0;
			display: flex;

			.icon {
				color: #fff;
				width: 60upx;
				height: 60upx;
				display: flex;
				justify-content: flex-end;
				align-items: center;
				font-size: 42upx;
			}
		}
	}

	.place {
		background-color: #f06c7a;
		height: 36upx;
		/*  #ifdef  APP-PLUS  */
		margin-top: var(--status-bar-height);
		/*  #endif  */
	}

	.place-bottom {
		height: 300upx;
	}

	.user {
		width: 92%;
		padding: 0 4%;
		display: flex;
		align-items: center;
		// position: relative;
		background-color: #f06c7a;
		padding-bottom: 36upx;

		.left {
			width: 20vw;
			height: 20vw;
			flex-shrink: 0;
			margin-right: 20upx;
			border: solid 1upx #fff;
			border-radius: 100%;

			image {
				width: 20vw;
				height: 20vw;
				border-radius: 100%;
			}

		}

		.right {
			width: 100%;

			.username {
				font-size: 28upx;
				color: #fff;
				margin-right: 4%;
			}

			.memberLevel {
				font-size: 28upx;
				color: #007AFF;
			}

			.memberId {
				font-size: 28upx;
				color: #fff;
			}

			.signature {
				color: #eee;
				font-size: 28upx;
				margin-right: 5%;
			}
		}

		.erweima {
			flex-shrink: 0;
			width: 10vw;
			height: 10vw;
			margin-left: 5vw;
			border-radius: 100%;

			display: flex;
			justify-content: center;
			align-items: center;
			background: linear-gradient(to left, #fbbb37 0%, #fcf0d0 105%);

			.icon {
				color: #7b6335;
				font-size: 42upx;
			}
		}
	}

	.order {
		width: 84%;
		margin: 30upx 4% 30upx 4%;
		padding: 30upx 4% 20upx 4%;
		background-color: #fff;
		box-shadow: 0upx 0upx 25upx rgba(0, 0, 0, 0.1);
		border-radius: 15upx;

		.list {
			display: flex;
			border-bottom: solid 1upx #17e6a1;
			padding-bottom: 10upx;

			.box {
				width: 20%;

				.img {
					width: 100%;
					display: flex;
					justify-content: center;

					.icon {
						font-size: 50upx;
						color: #464646;
					}
				}

				.text {
					width: 100%;
					display: flex;
					justify-content: center;
					font-size: 28upx;
					color: #3d3d3d;
				}
			}
		}

		.balance-info {
			display: flex;
			padding: 10upx 0;

			.left {
				width: 75%;
				display: flex;

				.box {
					width: 50%;
					font-size: 28upx;

					.num {
						width: 100%;
						height: 50upx;
						display: flex;
						justify-content: center;
						align-items: flex-end;
						color: #f9a453;
					}

					.text {
						width: 100%;
						display: flex;
						justify-content: center;
						color: #3d3d3d;
						font-size: 28upx;
					}
				}
			}

			.right {
				border-left: solid 1upx #17e6a1;
				width: 25%;

				.box {

					.img {
						width: 100%;
						height: 50upx;
						display: flex;
						justify-content: center;
						align-items: flex-end;

						.icon {
							font-size: 45upx;
							color: #e78901;
						}
					}

					.text {
						width: 100%;
						display: flex;
						justify-content: center;
						font-size: 28upx;
						color: #3d3d3d;
					}
				}
			}
		}
	}

	.VIP {
		width: 84%;
		margin: -65upx auto 20upx auto;
		padding: 30upx 4%;
		background: linear-gradient(to left, #dea96d 0%, #f6d59b 100%);
		box-shadow: 0upx 0upx 25upx rgba(0, 0, 0, 0.2);
		border-radius: 15upx;
		display: flex;
		align-items: center;

		.img {
			flex-shrink: 0;
			width: 60upx;
			height: 60upx;

			image {
				width: 60upx;
				height: 60upx;
			}
		}

		.title {
			width: 100%;
			color: #796335;
			font-size: 30upx;
		}

		.tis {
			width: 100%;
			display: flex;
			justify-content: flex-end;
			color: #fcf0d0;
			font-size: 26upx;
		}
	}

	.money {
		width: 92%;
		margin: 0 4% 0 4%;
		padding: 20upx 0 20upx 0;
		background-color: #fff;
		box-shadow: 0upx 0upx 25upx rgba(0, 0, 0, 0.1);
		border-radius: 15upx;

		.title {
			padding-top: 10upx;
			margin: 0 0 10upx 3%;
			font-size: 30upx;
			height: 80upx;
			display: flex;
			align-items: center;
		}

		.list {
			display: flex;
			padding-top: 10upx;
			margin: 0 0 10upx 3%;
			font-size: 30upx;
			height: 80upx;
			align-items: center;


			.box {
				display: inline-flex;
				width: 100%;
				justify-content: space-around;

				.balance {
					width: 100%;
					flex: auto;
					text-align: center;
					border-right: 1upx solid #000;

					.fontSize {
						font-size: 26upx;
					}
				}

				:nth-of-type(3) {
					border: none;
				}

			}

		}

		.button {
			display: flex;
			padding-top: 10upx;
			margin: 0 0 10upx 3%;
			font-size: 30upx;

			.boxleft {
				display: flex;
				flex-grow: 2;
			}

			.boxright {
				display: flex;
				margin-right: 8%;
			}
		}
	}

	.toolbar {
		width: 92%;
		margin: 0 4% 0 4%;
		padding: 20upx 0 20upx 0;
		background-color: #fff;
		box-shadow: 0upx 0upx 25upx rgba(0, 0, 0, 0.1);
		border-radius: 15upx;

		.title {
			padding-top: 10upx;
			margin: 0 0 10upx 3%;
			font-size: 30upx;
			height: 80upx;
			display: flex;
			align-items: center;
		}

		.list {
			display: flex;
			align-items: center;

			.box {
				width: 25%;
				margin-bottom: 30upx;

				.img {
					width: 23vw;
					height: 10.5vw;
					display: flex;
					justify-content: center;

					image {
						width: 9vw;
						height: 9vw;
					}
				}

				.text {
					width: 100%;
					display: flex;
					justify-content: center;
					font-size: 26upx;
					color: #3d3d3d;
				}
			}
		}
	}

	.toolbar {
		width: 92%;
		margin: 0 4% 0 4%;
		padding: 0 0 20upx 0;
		background-color: #fff;
		box-shadow: 0upx 0upx 25upx rgba(0, 0, 0, 0.1);
		border-radius: 15upx;

		.orderHeader {
			padding-top: 10upx;
			margin: 0 0 10upx 3%;
			font-size: 30upx;
			height: 80upx;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.allOrder {
				margin: 5%;
				font-size: 26upx;
			}
		}

		.list {
			display: flex;
			align-items: center;

			.box {
				width: 25%;
				margin-bottom: 30upx;

				.img {
					width: 23vw;
					height: 10.5vw;
					display: flex;
					justify-content: center;

					image {
						width: 9vw;
						height: 9vw;
					}
				}

				.text {
					width: 100%;
					display: flex;
					justify-content: center;
					font-size: 26upx;
					color: #3d3d3d;
				}
			}
		}
	}
</style>
