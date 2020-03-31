import { axiosRequest } from '@/api/index'
//key  value 形式数组转obj
export const arrToObj = (arr) => {
    let trueObj = {}
    for (let i = 0; i < arr.length; i++) {
        let objSe = {}
        objSe[arr[i].key] = arr[i].value
        trueObj = Object.assign({}, trueObj, objSe)
    }
    return trueObj;
}

//  obj  转 key value 形式的数组
export const objToArr = (obj) => {
    let trueArr = []
    for (let i in obj) {
        trueArr.push({
            "key": i,
            "value": obj[i]
        })
        // objSe[obj[i].key]=arr[i].value
        // trueObj=Object.assign([],trueObj,objSe)
    }
    return trueArr;
}
// 文件上传格式体积限制
export const beforeAvatarUpload = (file) => {
    const isJPG = file.type === 'image/jpeg';
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
    }
    if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
    }
    return isJPG && isLt2M;
}
//json 数据  null  转变为  ''
export const  null2str=(data)=> {
    for (let x in data) {
      if (data[x] === null) { 
        data[x] = '';
      } else {
        if (Array.isArray(data[x])) { 
          data[x] = data[x].map(z => {
            return null2str(z);
          });
        }
        if(typeof(data[x]) === 'object'){ //
          data[x] = null2str(data[x])
        }
      }
    }
    return data;
}
//获取商品分类
export const getGoodsType = () => {
    let typeList = []
    axiosRequest('product/getProductCategoryList', '', 'post').then((res) => {
        for (let i = 0; i < res.length; i++) {
            typeList.push({
                label: res[i].categoryName,
                value: res[i].id,
                children: []
            })
            for (let j = 0; j < res[i].children.length; j++) {
                if (res[i].children[j].children.length == 0) {
                    typeList[i].children.push({
                        label: res[i].children[j].categoryName,
                        value: res[i].children[j].id,
                    })
                } else {
                    typeList[i].children.push({
                        label: res[i].children[j].categoryName,
                        value: res[i].children[j].id,
                        children: []
                    })
                    for (let k = 0; k < res[i].children[j].children.length; k++) {
                        typeList[i].children[j].children.push({
                            label: res[i].children[j].children[k].categoryName,
                            value: res[i].children[j].children[k].id
                        })
                    }
                }
            }
        }
    })
    return typeList
}//时间格式转换
export const formatDate = (date) =>{  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute; 
    var second= date.getSeconds();  
    second = minute < 10 ? ('0' + second) : second;  
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+ second;  
};  
//查询系统的分销模式默认配置
export const getFxDefault = (productId) => {
    let fxDefault = {};
    axiosRequest('product/getStageDistr', { 'id': productId }, 'post').then((res) => {
        fxDefault = res
    })
    return fxDefault

}
//查询系统的极差模式默认配置
export const getJcDefault = (productId) => {
    let jcDefault = {};
    axiosRequest('')
}
// /******  修改的弹框
//  * that: 传过来的this实例
//  * title: 提示的消息
//  * content: 修改的内容
//  * 
//  */
// export const editMessage = (that, title, content) => {
//     const h = that.$createElement;
//     that.$msgbox({
//         title: title,
//         message: h('p', null, [
//             h('span', null, content),
//             h('input', { style: 'color: teal;margin-left:10px;',on:{input:e=>{
//                 console.log(e.target.value)
//             }} }, '')
//         ]),
//         showCancelButton: true,
//         confirmButtonText: '确定',
//         cancelButtonText: '取消',
//         beforeClose:(action, instance, done)=>{
//             done()
//         }
//     }).then(() => {
//         that.$message({
//             type: 'succese',
//             message: '修改成功 ',
//         });
//     }).catch(() => {
//         that.$message({
//             type: 'info',
//             message: '取消修改'
//         })
//     })
    
// }