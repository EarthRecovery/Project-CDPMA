<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          size="small"
          :disabled="multiple"
          @click="handleDelete"
        >
        <delete />
        删除
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="favoriteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="goodId" width="100" />
      <el-table-column label="商品名" align="center" prop="goodName" width="100" />
      <el-table-column label="价格" align="center" prop="price" width="100" />
      <el-table-column label="商品种类" align="center" prop="categoryId" width="100" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="200" />

      <el-table-column label="操作" align="center" width="200">
        <template #default="{ row }">
          <el-button size="small" type="text"  @click="handleDeleteById(row)">
          <delete />  
          删除
          </el-button>
          <el-button size="small" type="text"  @click="showDetail(row)">
          <delete />  
          详情
          </el-button>
        </template>
        
      </el-table-column>
    </el-table>

    <el-pagination
      v-show="total > 0"
      :total="total"
      v-model:current-page="queryParams.pageNum"
      v-model:page-size="queryParams.pageSize"
      @update:current-page="handlePageSelectionChange"
    />

    <el-dialog :title="editTitle" v-model="editOn" width="780px">
      <el-form :model="form" label-width="80px" ref="EditformRef">
        <!-- 第一行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="goodId">
            <span>{{ form.goodId }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="goodName">
            {{ form.goodName }}
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="categoryId">
            <span>{{ form.categoryId }}</span>
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="price">
            <span>{{ form.price }}</span>
            </el-form-item>
        </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { Delete } from '@element-plus/icons-vue'
import { getFavoriteList, deleteFavoriteByIds } from '@/api/favorite'
import { useStore } from 'vuex'
import { ElMessage, ElPagination, ElMessageBox } from 'element-plus'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
})

const form = reactive({
  goodId: null,
  goodName: '',
  categoryId: null,
  createdBy: null,
  createdAt: null,
  updatedBy: null,
  updatedAt: null,
  isDisabled: false,
  quantity: 0,
  price: null,
})

const EditformRef = ref(null)
const editTitle = ref('商品信息')
const editOn = ref(false)

const selectedRows = ref([])
const favoriteList = ref([])

const total = ref(0)

const loading = ref(false)
const store = useStore()


const handlePageSelectionChange = (page) => {
  queryParams.pageNum = page
  handleQuery()
}

const handleDeleteById = (row) => {
  ElMessageBox.confirm('是否确认删除该记录？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteFavoriteByIds(store.state.operator.id, [row]).then(() => {
      handleQuery() // 刷新商品列表
      ElMessage.success('删除成功 ')
    })
  }).catch(() => {
    ElMessageBox.alert('已取消删除操作')
  })
}

const handleSelectionChange = (row) => {
    selectedRows.value = row
}

const handleDelete = () => {
    if (selectedRows.value.length === 0) {
        ElMessageBox.warning('请至少选择一条记录')
        return
    }
    ElMessageBox.confirm('是否确认删除选中记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        // 调用删除接口
        deleteFavoriteByIds(store.state.operator.id, selectedRows.value).then(() => {
          handleQuery() // 刷新商品列表
          ElMessage.success('删除成功')
        })
    }).catch(() => {
        ElMessageBox.alert('已取消删除操作')
    })
}

const showDetail = (row) => {
  if(row){
    Object.assign(form, row)
    editOn.value = true
  }
}

const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  favoriteList.value = []
  const operatorId = store.state.operator.id
  getFavoriteList(operatorId, queryParams).then(response => {
    favoriteList.value = response.rows
    total.value = response.total
    loading.value = false
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

onMounted(() => {
  handleQuery()
})
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
