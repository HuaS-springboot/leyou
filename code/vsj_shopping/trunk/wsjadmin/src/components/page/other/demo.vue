<template>
    <div>
        
        <vChoosegood :chooseIdArr='chooseIdArr' v-if='chooseVisible' @saveChoose='saveChoose' @closeChoose='closeChoose'></vChoosegood>

    </div>
</template>
<script>
import vChoosegood from "@/components/common/chooseGood"
export default {
    components:{
        vChoosegood
    },
    data:function(){
        return{
            chooseIdArr:[],
            chooseVisible:false,
        }
    },
    created(){
    },
    methods:{
        submit(){
        },
        closeChoose(){
            this.chooseVisible=false
        },
        submit2(){
            this.chooseIdArr[0]='73'
            this.chooseVisible=true
        },
        submit21(){
            this.chooseIdArr[0]='77'
            this.chooseVisible=true
        },
        saveChoose(value){
            this.chooseVisible=false
            this.chooseIdArr=value
        }
        
    }
}
</script>
<style scoped>
    .btn{
        border: 1px solid black;
        color: black;
        width: 80px;
        height: 30px;
    }

</style>