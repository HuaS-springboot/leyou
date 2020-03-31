//表格提交规则
//添加规则时，注释好规则的简单的说明

export const rules={
    //商城装修
    'mallSetting':{
      'shopping_name':[
        { required: true, message: '请输入商城名称', trigger: 'blur' }
      ],
    },
    //系统参数
    'sysSetting':{
      'order_close_time':[
        { required: true, message: '请输入未付款订单关闭时间', trigger: 'blur' }
      ],
      'order_confirm_day':[
        { required: true, message: '请输入自动收货天数', trigger: 'blur' }
      ],
      'order_allow_refund_day':[
        { required: true, message: '请输入允许退款天数', trigger: 'blur' }
      ]
    },
    //支付参数
    'paySetting':{
      'wx_pay_appid':[
        { required: true, message: '请输入微信支付appid', trigger: 'blur' }
      ],
      'wx_pay_appsecret':[
        { required: true, message: '请输入微信支付appsecret', trigger: 'blur' }
      ],
      'wx_pay_mchid':[
        { required: true, message: '请输入微信支付mchild', trigger: 'blur' }
      ],
      'wx_pay_apisecret':[
        { required: true, message: '请输入微信支付apisecret', trigger: 'blur' }
      ]
    },
    
  






    // 添加用户等级提交规则
    "addUserLever":{
        name: [
          { required: true, message: '请输入等级名称', trigger: 'blur' }
        ],
        weight: [
          { required: true, message: '请选择等级权重', trigger: 'blur' }
        ]
    },
    //demo 页面规则
    'demoRule':{
        name: [
          { required: true, message: '请输入活动名称111', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }
}
