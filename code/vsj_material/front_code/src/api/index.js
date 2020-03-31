import request from '../utils/request';
export const axiosRequest=(url,query,method)=>{
    return request({
        url:url,
        method:method,
        data:query
    })
}
//文件上传
export const upload=(query)=>{
    return request({
        url:'upload/uploadFile',
        method:'post',
        data:query
    })
}
// //登录接口
// export const userLogin=(query) =>{
//     return request({
//         url: 'user/checkLogin',
//         method: 'POST',
//         data: query        
//     })
// }
// //获取商城设置
// export const mallSetting = (query) => {
//     return request({
//         url: 'v1/system/query',
//         method: 'POST',
//         data: query
//     })
// }
// //保存商城设置
// export const saveMallSetting = (query) => {
//     return request({
//         url: 'v1/system/update',
//         method: 'POST',
//         data: query
//     })
// }

//查询会员等级
export const getUserLever=(query)=>{
    return request({
        url:'memeber/findAllLevel',
        method:"POST",
        data: query
    })
}

//添加会员等级
export const addUserLever=(query)=>{
    return request({
        url:'memeber/saveLevel',
        method:"POST",
        data: query
    })
}



// //查询商品分类列表
// export const getTypeList=(query)=>{
//     return request({
//         url:'product/getProductCategoryList',
//         method:"POST",
//         data: query
//     })
// }


//获取订单列表
export const getOrderList=(query)=>{
    return request({
        url:'order/getOrderList',
        method:"POST",
        data: query
    })
}

//获取会员列表
export const getMemberList=(query)=>{
    return request({
        url:'memeber/getMemberList',
        method:"POST",
        data: query
    })
}
//获取会员列表































