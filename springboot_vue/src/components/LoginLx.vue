<template>
  <div id="divbox">
    <!-- 【此图像是圆圈头像logo】 -->
<!--    <img src="../imgs/1.jpg"-->
<!--         alt="Your Image">-->
    <div class="login-form">
      <div id="logo">
				<span style="font-family: 'Microsoft YaHei';letter-spacing: 0.5px; font-weight: bold; font-size: 40px;">
					&nbsp;<span style="color:#4F5155"> 欢迎登录</span><span style="color:rgb(137, 204, 255) ;">仓库管理系统</span>
				</span>
      </div>
      <el-form ref="loginForm" :model="loginForm" label-width="80px" :rules="rules">
        <el-form-item label="账号" prop="no" >
          <el-input v-model="loginForm.no" class="input" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginForm.password" class="input" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>

          <el-button type="primary" @click="onSubmit('loginForm')" style="width: 300px;">登录</el-button>
          <br />
          <span style="color: #89CCFFFF;" @click="reg()">没有账号？点击注册</span>
        </el-form-item>
      </el-form>
    </div>


  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        no: '',
        password: '',
      },
      rules: {
        no: [{
          required: true,
          message: '请输入正确账号！',
          trigger: 'blur'
        },
          {
            min: 1,
            max: 10,
            message: '长度在 2 到 10 个字符',
            trigger: 'blur'
          }
        ],
        password: [{required: true,message: '请输入密码!',trigger: 'blur'}]
      }
    }
  },
  methods: {
    reg() {
      alert("注册");
    },
    onSubmit() {//【登录】
      if(this.loginForm.no.length<1||this.loginForm.no.length>10){
        this.$message({showClose: true,message: '长度在 2到 10 个字符~', type: 'error'});
      }else{
        //数据向后端发送进行验证
        //$refs是一个引用
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            //如果发送成功，跳转到其他组件
            //去后台验证用户名和密码
            this.$axios.post('http://localhost:8099/user/login',this.loginForm).then(res=>res.data).then(res=>{
              console.log(res)
              if (res.code==200){
                sessionStorage.setItem("CurUser",JSON.stringify(res.data.user))
                console.log(res.data.menu)
                this.$store.commit("setMenu",res.data.menu)
                //跳转到主页
                this.$router.replace('/IndexLx');
                //【跳转语句】
                this.$message({showClose: true,message: '恭喜你，账户正确✔',type: 'success'});
                /* this.$message({showClose: true,message: '输入的账户或密码错误~', type: 'error'});
                this.$message({showClose: true,message: '系统忙，维修人员正在抢修！',type: 'warning'}); */
              }else {
                this.$message({showClose: true,message: '输入的账户或密码错误~', type: 'error'});
              }
            });

          }
        });
      }
    }
  }
}
</script>

<style scoped>
#divbox {
  width: 100%;
  height: 100vh;
  background-image: url("../imgs/2.jpg");
  /*将🎀页面背景  图片路径替换为你自己的图片路径*/
  background-size: cover;
  /*保持图片比例并完全覆盖元素*/
  background-position: center center;
  /*将图片居中对齐*/
}

.login-form {
  width: 442px;
  height: 400px;
  background-color: rgba(248, 242, 235, 0);
;
  position: relative;
  left: 455px;
  top: 170px;


}

#logo {
  width: 100%;
  height: 60px;
  /* background-color: aquamarine; */
  margin-bottom: 60px;
}

.input {
  max-width: 300px;
}

img {
  border-radius: 50%;
  width: 100vh;
  position: absolute;
  left: 638px;
  top: 50px;
}

.el-form-item_label {
  text-align: right;
  vertical-align: middle;
  float: left;
  font-size: 14px;
   color: #070707;
  line-height: 40px;
  padding: 0 12px 0 0;
  box-sizing: border-box;
}
</style>
