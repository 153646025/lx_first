<script >
export default {
  name: "HomeLx",
  data(){
    return{
      user: JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  methods:{
    init(){
      this.user=JSON.parse(sessionStorage.getItem('CurUser'))
    }
  }
}
</script>

<template>
<div>
  <el-descriptions  :title="'欢迎你！'+user.name" :column="3" size="40" border>

    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        账号
      </template>
      {{user.no}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        手机号
      </template>
      <el-tag>{{user.phone}}</el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-picture-outline-round"></i>
        性别
      </template>
      <el-tag
              type="success"
              disable-transitions>
        <i :class="user.sex==1? 'el-icon-male':'el-icon-female'"></i>
        {{user.sex==1?"男":"女"}}
      </el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        年龄
      </template>
      {{user.age}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        角色
      </template>
      <el-tag
          :type="user.roleId === 0 ? 'danger' : (user.roleId === 1 ? 'primary' : 'success')"
          disable-transitions>{{user.roleId === 0 ? '超级管理员' :
          (user.roleId === 1 ? '管理员' : '用户')}}
      </el-tag>
    </el-descriptions-item>
  </el-descriptions>
</div>
</template>

<style scoped>

</style>