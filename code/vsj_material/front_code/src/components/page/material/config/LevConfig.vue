<template>
	<div>
		<div class="container">
			<div class="form-box">
				<el-form ref="mallsetting" :model="mallsetting" label-width="160px" label-position="left">

					<div class="crumbs">
						<el-breadcrumb separator="/">
							<el-breadcrumb-item><i class="el-icon-lx-calendar"></i>等级权限配置</el-breadcrumb-item>
						</el-breadcrumb>
					</div>

					<div class="center">
						<el-form-item label="默认级别">
							<el-select v-model="mallsetting.list.user_default_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>
 
						<el-form-item label="绑定手机后级别">
							<el-select v-model="mallsetting.list.user_building_phone_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="浏览素材">
							<el-select v-model="mallsetting.list.user_browse_material_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="复制文案">
							<el-select v-model="mallsetting.list.user_copy_writing_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="一键保存">
							<el-select v-model="mallsetting.list.user_one_click_save_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="分享">
							<el-select v-model="mallsetting.list.user_share_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="收藏">
							<el-select v-model="mallsetting.list.user_collection_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>


						<el-form-item label="发布素材">
							<el-select v-model="mallsetting.list.user_release_material_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="系统体验">
							<el-radio-group v-model="mallsetting.list.user_experience_show">
								<el-radio label="0">否</el-radio>
								<el-radio label="1">是</el-radio>
							</el-radio-group>
						</el-form-item>

						<el-form-item label="体验级别">
							<el-select v-model="mallsetting.list.user_experience_level" placeholder="请选择">
								<el-option v-for="item in expressApi" :key="item.value" :value="item.key">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="体验天数" prop="user_experience_day">
							<el-input v-model="mallsetting.list.user_experience_day" id='enjonyday'></el-input>
						</el-form-item>


						<el-form-item>
							<el-button type="primary" @click="onSubmit">保存</el-button>
							<el-button>取消</el-button>
						</el-form-item>

					</div>

				</el-form>
			</div>
		</div>
	</div>
</template>

<script>
	import {
		axiosRequest
	} from '@/api/index';
	import {
		arrToObj,
		objToArr
	} from '@/components/common/common';
	import {
		rules
	} from '../../../common/rule';
	import "@/assets/css/upload.css";

	export default {
		name: 'baseform',
		data: function() {
			return {
				mallsetting: {
					compulsory_binding_phone: 0,
					list:{}
				},
				mallsetting2: [],
				expressApi: [{
					'key': 'L1',
					'value': '0'
				}, {
					'key': 'L2',
					'value': '1'
				}]
				/* objToArr(JSON.parse(localStorage.getItem('dictionary')).express_api) */,
				/* fileStorageApi: objToArr(JSON.parse(localStorage.getItem('dictionary')).file_storage),
				  */

			}
		},
		created() {
			this.getMallSetting()
		},
		methods: {
			//获取商城配置
			 getMallSetting(){
			     axiosRequest('admin/system/getSysConfigList',{
			         type:"5"
			     },'post').then((res)=>{
			         this.mallsetting.list = arrToObj(res) 
					 console.log(this.mallsetting.list)
			        
			     }) 
		},
		onSubmit() {
			
			axiosRequest('admin/system/updateSysConfig', {
				'sysConfigList': objToArr(this.mallsetting.list)
			}, 'post').then((res) => {
				this.$message.success('提交成功！');
			})

		}
	},
	}
</script>

<style>
	#enjonyday {
		width: 215px;
	}

	.center {
		margin-left: 50px;
	}
</style>
