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
      <el-form-item label="sender" prop="sender" >
        <el-input
          v-model="queryParams.sender"
          placeholder="请输入sender"
          clearable
          style="width: 140px; padding-right: 40px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="notificationType" prop="notificationType">
        <el-select
            v-model="queryParams.notificationType"
            placeholder="请选择通知类型"
            clearable
            style="width: 160px;"
        >
            <el-option label="系统通知" :value="1" />
            <el-option label="用户通知" :value="2" />
            <el-option label="管理员通知" :value="3" />
            <el-option label="推广通知" :value="4" />
            <el-option label="警报通知" :value="5" />
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

    <el-table v-loading="loading" :data="notificationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="notificationId" width="100" />
      <el-table-column label="发送时间" align="center" prop="timestamp" />
      <el-table-column label="发送者" align="center" prop="sender" />
      <el-table-column label="通知类型" align="center" prop="notificationType" />
      <el-table-column label="内容" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" width="200">
        <template #default="{ row }">
          <el-button size="small" type="text" @click="showDetail(row)">
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
      <el-form-item label="内容" prop="content">
        <el-input
            v-model="form"
            type="textarea"
            :rows="6"
            maxlength="1000"
            show-word-limit
            style="width: 100%;"
            readonly
        />
        </el-form-item>
      </el-form>
    </el-dialog>


  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import {  Search, Refresh } from '@element-plus/icons-vue'
import { getNotificationList } from '@/api/notification'
import { useStore } from 'vuex'
import { ElMessage, ElPagination } from 'element-plus'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 7,
  sender: '',
  notificationType: null,
  receiver: null
})

const form = ref('')

const selectedRows = ref([])
const notificationList = ref([])
const store = useStore()

const queryForm = ref(null)
const showSearch = ref(true)
const total = ref(0)

const loading = ref(false)

const editOn = ref(false)
const editTitle = ref('通知详情')


const handlePageSelectionChange = (page) => {
  queryParams.pageNum = page
  handleQuery()
}

const handleSelectionChange = (row) => {
    selectedRows.value = row
}
const resetQuery = () => {
  queryParams.pageNum = 1
  queryParams.pageSize = 7
  queryParams.sender = ''
  queryParams.notificationType = null
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  notificationList.value = []
  console.log('Query Params:', queryParams)
  getNotificationList(queryParams).then(response => {
    notificationList.value = response.rows
    total.value = response.total
    loading.value = false
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

const showDetail = (row) => {
  if (row) {
    console.log('Selected Notification:', row.content)
    form.value = row.content
    editOn.value = true
  }
}

onMounted(() => {
    queryParams.receiver = store.state.operator.id
    handleQuery()
})

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
