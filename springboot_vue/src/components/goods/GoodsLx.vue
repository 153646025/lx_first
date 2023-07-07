<script>
import SelectUserLx from "@/components/user/SelectUserLx.vue";
export default {
  name: "GoodsLx",
  components: {SelectUserLx},
  data() {
    let checkCount = (rule,value,callback) =>{
      if (value>9999){
        callback(new Error("数量过大"));
      }else {
        callback();
      }
    }


    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      storageData:[],
      goodstypeData:[],
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name:'',
      storage: '',
      goodstype: '',
      centerDialogVisible: false,
      inDialogVisible:false,
      outDialogVisible:false,
      innerVisible:false,
      currentRow:{},
      tempUser:{},
      form: {
        id: '',
        name: '',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },
      form1:{
        goods:'',
        goodaname:'',
        count:'',
        userid:'',
        adminid:'',
        remark:'',
        action:'1'
      },
      rules1:{},
      rules: {
        name: [
          { required: true, message: '请输入物品名', trigger: 'blur' },
        ],
        storage:[
          { required: true, message: '请输入仓库名', trigger: 'blur' },
        ],
        goodstype:[
          { required: true, message: '请输入分类', trigger: 'blur' },
        ],
        count: [
          {required:true,message:'请输入数量',trigger:'blur'},
          {pattern:/^([1-9][0-9]*){1,4}$/,message: '数量必须为正',trigger: 'blur'},
          {validator:checkCount,trigger: 'blur'}
        ]


      }
    }
  },
  methods:{
    confirmUser(){
      this.form1.username=this.tempUser.name
      this.form1.userid=this.tempUser.id

      this.innerVisible=false
    },
    doSelectUser(val){
      console.log(val)
      this.tempUser=val
    },
    selectCurrentChange(val){
      this.currentRow = val;
    },
    formatStorage(row){
      let temp = this.storageData.find(item=>{
        return item.id == row.storage
      })
      return temp && temp.name
    },
    formatGoodsytpe(row){
      let temp = this.goodstypeData.find(item=>{
        return item.id == row.goodstype
      })
      return temp && temp.name
    },
    resetForm() {   //清空输入框
      this.$refs.form.resetFields();
    },
    resetInForm(){
      this.$refs.form1.resetFields();
    },
    mod(row){

      this.centerDialogVisible = true
      this.$nextTick(()=>{
        //获取数据
        this.form.id = row.id
        this.form.name = row.name
        this.form.storage = row.storage
        this.form.goodstype = row.goodstype
        this.form.count = row.count
        this.form.remark = row.remark

      })
    },
    del(id){
      console.log(id)
      this.$axios.get('http://localhost:8099/goods/delete?id='+id
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
    inGoods(){
      if (!this.currentRow.id){
        alert("请选择记录")
        return;
      }
      this.inDialogVisible = true
      this.$nextTick(()=>{
        this.resetInForm()
      })
      this.form1.goodaname = this.currentRow.name;
      this.form1.goods = this.currentRow.id;
      this.form1.adminid = this.user.id;
      this.action='1'

    },
    outGoods(){
      if (!this.currentRow.id){
        alert("请选择记录")
        return;
      }
      this.inDialogVisible = true
      this.$nextTick(()=>{
        this.resetInForm()
      })
      this.form1.goodaname = this.currentRow.name;
      this.form1.goods = this.currentRow.id;
      this.form1.adminid = this.user.id;
      this.action='2'
    },

    doMod(){
      this.$axios.post('http://localhost:8099/goods/update',this.form
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
      this.$axios.post('http://localhost:8099/goods/save',this.form
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
    doInGoods(){
      this.$axios.post('http://localhost:8099/record/save',this.form1
      ).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.inDialogVisible = false
          this.loadPost()
          this.resetInForm()
        }else {
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
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
      this.storage='';
      this.goodstype='';
    },
    loadPost(){    //分页查询
      this.$axios.post('http://localhost:8099/goods/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name: this.name,//搜索姓名
          storage: this.storage+'',
          goodstype: this.goodstype+'',

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
    loadStorage(){
      this.$axios.get('http://localhost:8099/storage/list',).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.storageData=res.data
          this.total=res.total
        }else {
          alert('获取数据失败')
        }
      })
    },
    loadGoodstype(){
      this.$axios.get('http://localhost:8099/goodstype/list',).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.goodstypeData=res.data
          this.total=res.total
        }else {
          alert('获取数据失败')
        }
      })
    },
    selectUser(){
      this.innerVisible=true
    },


  },
  beforeMount() {
    this.loadPost();
    this.loadStorage();
    this.loadGoodstype();
  }
}

</script>

<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="物品名" suffix-icon="el-icon-search" style="width: 200px"
                @keyup.enter.native="loadPost"
      >
      </el-input>
      <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="请选择分类" style="margin-left: 5px">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="loadParam">重置</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="add" v-if="user.roleId!=2">新增</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="inGoods" v-if="user.roleId!=2">入库</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="outGoods" v-if="user.roleId!=2">出库</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background: '#f3f6fd',color: '#555'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" width="40">
      </el-table-column>
      <el-table-column prop="name" label="物品名">
      </el-table-column>
      <el-table-column prop="storage" label="仓库" :formatter="formatStorage" >
      </el-table-column>
      <el-table-column prop="goodstype" label="分类" :formatter="formatGoodsytpe"  >
      </el-table-column>
      <el-table-column prop="count" label="数量">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          v-if="user.roleId!=2"

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
        <el-form-item label="物品名" prop="name">
          <el-col :span="15">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库" prop="storage">
          <el-col :span="15">
            <el-select v-model="form.storage" placeholder="请选择仓库" style="margin-left: 5px">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="分类" prop="goodstype">
          <el-col :span="15">
            <el-select v-model="form.goodstype" placeholder="请选择分类" style="margin-left: 5px">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="15">
            <el-input v-model="form.count"></el-input>
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

    <el-dialog
        title="出入库"
        :visible.sync="inDialogVisible"
        width="30%"
        center>
      <el-dialog
          width="90%"
          title="用户选择"
          :visible.sync="innerVisible"
          append-to-body>
          <SelectUserLx @doSelectUser="doSelectUser"></SelectUserLx>
        <span slot="footer" class="dialog-footer">
        <el-button @click="innerVisible=false">取 消</el-button>
        <el-button type="primary" @click="confirmUser">确 定</el-button>
      </span>

      </el-dialog>
      <el-form ref="form1" :model="form1" :rules="rules1" label-width="80px">
        <el-form-item label="物品名" prop="name">
          <el-col :span="15">
            <el-input v-model="form1.goodaname" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="15">
            <el-input v-model="form1.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人" prop="count">
          <el-col :span="15">
            <el-input v-model="form1.username" @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="15">
            <el-input type="textarea" v-model="form1.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="inDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doInGoods">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<style scoped>

</style>