<script>
import AsideLx from "@/components/AsideLx.vue";
import HeardLx from "@/components/HeardLx.vue";

import asideLx from "@/components/AsideLx.vue";

export default {
  name: "IndexLx",
  computed: {
    asideLx() {
      return asideLx
    }
  },
  components: { HeardLx, AsideLx},
  data(){
    return {
      isCollapse:false,
      aside_width:'200px',
      icon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      // console.log(1111)
      this.isCollapse = !this.isCollapse
      if(!this.isCollapse){//展开
        this.aside_width='200px';
        this.icon='el-icon-s-fold'
      }else{//收起
        this.aside_width='64px';
        this.icon='el-icon-s-unfold'
      }
    }
  }
};
</script>

<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246);height: 100%;margin-left: -10px">
      <AsideLx :isCollapse="isCollapse"></AsideLx>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px;height: 100%;border-bottom: rgba(179,192,209,0.3) 1px solid;">
       <HeardLx @doCollapse="doCollapse" :icon="icon"></HeardLx>
      </el-header>

      <el-main
          default-active="/HomeLx"
          style="height: 100%">
<!--        <MainLx></MainLx>-->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>

.el-header {
  //background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
.el-main {
  padding: 5px;
}

.el-aside {
  color: #333;
}
</style>