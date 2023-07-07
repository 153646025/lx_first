<script>
  export default {
    name: "AsideLx",
    data(){
      return {
        // isCollapse:false
        // menu:[
        //   {
        //     menuclick:'AdminLx',
        //     menuname:'管理员管理',
        //     menuicon:'el-icon-s-custom'
        //   },{
        //     menuclick:'UserLx',
        //     menuname:'用户管理',
        //     menuicon:'el-icon-user-solid'
        //   }
        // ]
      }
    },
    computed:{
      "menu":{
        get(){
          return this.$store.state.menu
        }
      }
    },
    props:{
      isCollapse:Boolean
    },
  }


</script>

<template>
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      default-active="/HomeLx"
      :collapse="isCollapse"
      :collapse-transition="false"
      style="height: 100vh"
      router
  >
    <el-menu-item index="/HomeLx">
      <el-menu-item-group></el-menu-item-group>
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <el-menu-item-group></el-menu-item-group>
      <i :class="item.menuicon"></i>
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>


  </el-menu>

</template>

<style scoped>

</style>