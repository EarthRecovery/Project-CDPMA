<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="isExpired" prop="isExpired">
        <el-select v-model="queryParams.isExpired" placeholder="请选择isExpired" clearable style="width: 100px;">
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="handleQuery">
        <search />
        搜索
        </el-button>
        <el-button size="small" @click="resetQuery">
        <refresh />
        重置
        </el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="couponsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="优惠券名称" align="center" prop="couponName" :show-overflow-tooltip="true" />
      <el-table-column label="折扣效果" align="center" prop="discountEffect" />
      <el-table-column label="有效期" align="center" prop="expirationDuration" />
      <el-table-column label="发放时间" align="center" prop="issuanceTime" :formatter="formatDateTime"/>
    </el-table>

    <el-pagination
      v-show="total > 0"
      :total="total"
      v-model:current-page="queryParams.pageNum"
      v-model:page-size="queryParams.pageSize"
      @update:current-page="handlePageSelectionChange"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, reactive} from 'vue'
import { Search, Refresh } from '@element-plus/icons-vue'
import { getCouponsList } from '@/api/coupons'
import { ElMessage, ElPagination} from 'element-plus'
import { useStore } from 'vuex'
import { formatDateTime } from '@/utils/time'

const store = useStore()

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
  isExpired: false,
  operatorId: null,
})

const selectedRows = ref([])
const couponsList = ref([])

const queryForm = ref(null)
const showSearch = ref(true)
const total = ref(0)

const loading = ref(false)


const handlePageSelectionChange = (page) => {
  queryParams.pageNum = page
  handleQuery()
}


const handleSelectionChange = (row) => {
    selectedRows.value = row
}

const resetQuery = () => {
  queryParams.pageNum = 1
  queryParams.pageSize = 6
  queryParams.isExpired = null
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  couponsList.value = []
  queryParams.operatorId = store.state.operator.id // 获取当前用户ID
  getCouponsList(queryParams).then(response => {
    couponsList.value = response.rows
    total.value = response.total
    loading.value = false
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

onMounted(() => {
  handleQuery(queryParams)
})
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
