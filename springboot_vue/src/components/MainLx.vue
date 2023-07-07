<script>
export default {
  name: "MainLx",
  data() {
    let checkAge = (rule,value,callback) => {
      if (value>150){
        callback(new Error('年龄输入过大'));
      }else {
        callback();

      }
    };
    let checkDuplicate = (rule,value,callback)=>{
      if (this.form.id){
        return callback();
      }
      this.$axios.get("http://localhost:8099/user/findByNo?no="+this.form.no
      ).then(res=>res.data).then(res=>{
        if (res.code!=200){
          callback();
        }else {
          callback(new Error('账号已经存在'));
        }
      })
    }
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name:'',
      sex:'',
      sexs: [{
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }],
      centerDialogVisible: false,
      form: {
        id: '',
        no: '',
        name: '',
        password: '',
        age:'',
        sex:'',
        phone:'',
        roleId:'',

      },
      rules: {
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' },
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 个字符', trigger: 'blur' },
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数',trigger: 'blur'},
          {validator:checkAge,trigger: 'blur'}
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,message: '请输入正确的手机号',trigger: 'blur'}
        ],

      }
    }
  },
  methods:{
    resetForm() {   //清空输入框
      this.$refs.form.resetFields();
    },
    mod(row){

      this.centerDialogVisible = true
      this.$nextTick(()=>{
        //获取数据
        this.form.id = row.id
        this.form.no = row.no
        this.form.name = row.name
        this.form.password = ''
        this.form.age = row.age+ ''
        this.form.sex = row.sex+ ''
        this.form.phone = row.phone
        this.form.roleId = row.roleId+ ''
      })
    },
    del(id){
      console.log(id)
      this.$axios.get('http://localhost:8099/user/del?id='+id
      ).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.loadPost()
        }else {
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },
    add(){    //添加
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        this.resetForm()
      })

    },
    doMod(){
      this.$axios.post('http://localhost:8099/user/update',this.form
      ).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetForm()
        }else {
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })

    },
    doSave(){
      this.$axios.post('http://localhost:8099/user/save',this.form
      ).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this.resetForm()
        }else {
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },
    save(){    //把from表单的内容存入数据库
        this.$refs.form.validate((valid) => {
          if (valid) {
            if (this.form.id){
              this.doMod()
            }else {
              this.doSave()
            }

          } else {
            console.log('error submit!!');
            return false;
          }
        });

    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()

    },
    loadParam(){    //重置搜索框
      this.name='';
      this.sex='';
    },
    loadPost(){    //分页查询
      this.$axios.post('http://localhost:8099/user/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name: this.name,//搜索姓名
          sex: this.sex,  //搜索性别
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else {
          alert('获取数据失败')
        }
      })
    },
    filterTag(value, row) {
      return row.roleId === value;
    }
  },
  beforeMount() {
    this.loadPost();
  }
}

</script>

<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入姓名" suffix-icon="el-icon-search" style="width: 200px"
                @keyup.enter.native="loadPost"
      >
      </el-input>
      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px;width: 120px">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="loadParam">重置</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="add">新增</el-button>
    </div>
  <el-table :data="tableData"
            :header-cell-style="{background: '#f3f6fd',color: '#555'}"
            border
  >
    <el-table-column prop="id" label="ID" width="40">
    </el-table-column>
    <el-table-column prop="no" label="账号" width="120">
    </el-table-column>
    <el-table-column prop="name" label="姓名">
    </el-table-column>
    <el-table-column prop="password" label="密码">
    </el-table-column>
    <el-table-column prop="age" label="年龄">
    </el-table-column>
    <el-table-column prop="sex" label="性别">
      <template slot-scope="scope">
        <el-tag
            :type="scope.row.sex === 1 ? 'primary' : 'success'"
            disable-transitions>{{scope.row.sex === 1 ? '男' : '女'}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="phone" label="电话">
    </el-table-column>
    <el-table-column
        prop="roleId"
        label="角色"
        :filters="[{ text: '超级管理员', value: 0 }, { text: '管理员', value: 1 },{ text: '用户', value: 2 }]"
        :filter-method="filterTag"
        filter-placement="bottom-end"
    >
      <template slot-scope="scope">
        <el-tag
            :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
            disable-transitions>{{scope.row.roleId === 0 ? '超级管理员' :
                                  (scope.row.roleId === 1 ? '管理员' : '用户')}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column
        fixed="right"
        label="操作"

    >
      <template slot-scope="scope">
      <el-button size="small" type="warning" @click="mod(scope.row)">编辑</el-button>
        <el-popconfirm
            confirm-button-text='确定'
            cancel-button-text='取消'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm="del(scope.row.id)"
            style="margin-left: 5px"
        >
          <el-button slot="reference" size="small" type="danger" >删除</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 3, 5, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="no">
          <el-col :span="15">
          <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="15">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="15">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="15">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex" >
            <el-radio border label="1">男</el-radio>
            <el-radio border label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="15">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roleId" placeholder="请选择角色">
            <el-option label="超级管理员" value=0></el-option>
            <el-option label="管理员" value="1"></el-option>
            <el-option label="用户" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>