<script>
export default {
  name: "RecordLx",
  data() {


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
      form: {
        id: '',
        name: '',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },

    }
  },
  methods:{

    resetForm() {   //清空输入框
      this.$refs.form.resetFields();
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
      this.$axios.post('http://localhost:8099/record/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name: this.name,//搜索姓名
          storage: this.storage+'',
          goodstype: this.goodstype+'',
          roleId:this.user.roleId+'',
          userid:this.user.id+''

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
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background: '#f3f6fd',color: '#555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="40">
      </el-table-column>
      <el-table-column prop="goodsname" label="物品名">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库"  >
      </el-table-column>
      <el-table-column prop="goodstypename" label="分类"   >
      </el-table-column>
      <el-table-column prop="count" label="数量" width="80">
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人">
      </el-table-column>
      <el-table-column prop="username" label="申请人">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
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
  </div>
</template>

<style scoped>

</style>