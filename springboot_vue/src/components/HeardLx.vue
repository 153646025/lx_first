<script>
  export default {
    name: "HeardLx",
    props:{
      icon:String
    },
    data(){
      return{
        user: JSON.parse(sessionStorage.getItem('CurUser'))
      }
    },
    methods:{
      toUser(){
        console.log('to_User')
        this.$router.push('/HomeLx')
      },
      logout(){
        this.$confirm('确定要退出登良？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.$message({
            type: 'success',
            message: '退出登录成功!'
          });
          this.$router.push('/')
          sessionStorage.clear()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消退出登录'
          });
        });
      },
      collapse(){
        this.$emit('doCollapse')
      },
    },
    created() {
      this.$router.push('/HomeLx')
    }
  }
</script>

<template>
  <div style="display: flex;line-height: 60px">
    <div style="margin-top: 8px" >
      <i :class="icon" style="font-size: 20px;cursor: pointer;" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 34px;">
      <span>欢迎来到仓库管理系统</span>
    </div>
    <el-dropdown>
      <span>{{user.name}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu>
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>

</template>

<style scoped>

</style>