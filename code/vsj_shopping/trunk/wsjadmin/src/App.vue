<template>
    <div id="app">
        <router-view v-if="isRouterAlive"></router-view>
    </div>
</template>
<script>
export default {
    name:'app',
    provide(){
        return{
            reload:this.reload
        }
    },
    data(){
        return{
            isRouterAlive:true
        }
    },
    methods:{
        reload(){
            this.isRouterAlive=false
            this.$nextTick(function(){
                this.isRouterAlive=true
            })
        }
    }
}
</script>
<style>
    @import "./assets/css/main.css";
    @import "./assets/css/color-dark.css";     /*深色主题*/
    /*@import "./assets/css/theme-green/color-green.css";   浅绿色主题*/
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 148px;
        height: 148px;
        line-height: 148px;
        text-align: center;
    }
    .avatar {
        width: 148px;
        height: 148px;
        display: block;
    }
</style>