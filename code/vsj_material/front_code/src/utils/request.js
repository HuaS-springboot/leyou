import axios from 'axios';
const SERVER="http://172.16.0.233:8888/api/v1/";  //马瑞
//const SERVER='http://39.98.39.33:8751/api/v1/';     //服务器 
//const SERVER="http://172.16.0.230:8751/api/v1/";  //赵勇
//const SERVER="http://172.16.0.227:8751/api/v1/";  //孙华
//const SERVER="http://172.16.0.226:8751/api/v1/";    //王志晓
// const SERVER="http://172.16.0.230:8751/api/v1/";    //赵勇

export const baseURL = SERVER + "";
const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    baseURL: baseURL,
    timeout: 5000,
    headers:{
        'Content-Type':'application/json;charset=UTF-8'
    }
    
})
service.SERVER = SERVER;
service.BASE_URL = baseURL;

// http request拦截器 添加一个请求拦截器
service.interceptors.request.use( config => {
    //平台码必传
    config.headers.platformCode=window.vm.$store.state.platformCode
    
    if(localStorage.getItem('token')){
        config.headers.token=localStorage.getItem('token')
    }
    if(localStorage.getItem('userId')){
        config.headers.userId=localStorage.getItem('userId')
    }
    return config;
}, error => {
    console.log(error);
    return Promise.reject();
})
//相应拦截器
service.interceptors.response.use(response => {
    
    if(response.status === 200){
        if(response.data.code==1000){
            window.vm.$alert('账号长时间未操作，请重新登录', {
                confirmButtonText: '确定',
                callback: action => {
                    window.vm.$router.push('/login')
                }
            });
        }else if(response.data.code==500){
            window.vm.$alert(response.data.message, {
                confirmButtonText: '确定',
                callback: action => {
                    
                }
            });
        }else{
            return response.data.data;//正常返回数据
        }   
        
    }if(response.status=== 403){
        window.vm.$router.push('/403')
    }else{
        Promise.reject();//异常返回报错
    }
}, error => {
    console.log(error);
    return Promise.reject();
})

export default service;
