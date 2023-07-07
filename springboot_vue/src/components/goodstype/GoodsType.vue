<script>
export default {
  name: "GoodsType",
  data() {


    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name:'',
      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        remark:''
      },
      rules: {
        name: [
          { required: true, message: '请输入分类名', trigger: 'blur' },
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
        this.form.name = row.name
        this.form.remark = row.remark
      })
    },
    del(id){
      console.log(id)
      this.$axios.get('http://localhost:8099/goodstype/delete?id='+id
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
      this.$axios.post('http://localhost:8099/goodstype/update',this.form
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
      this.$axios.post('http://localhost:8099/goodstype/save',this.form
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
      this.$axios.post('http://localhost:8099/goodstype/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name: this.name,//搜索姓名
          sex: this.sex,  //搜索性别
          roleId: '2'
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
  },
  beforeMount() {
    this.loadPost();
  }
}

</script>

<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="分类名" suffix-icon="el-icon-search" style="width: 200px"
                @keyup.enter.native="loadPost"
      >
      </el-input>
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
      <el-table-column prop="name" label="分类名">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
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
        <el-form-item label="分类名" prop="name">
          <el-col :span="15">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="15">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
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