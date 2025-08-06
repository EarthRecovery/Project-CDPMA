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
      <el-form-item label="operatorId" prop="operatorId">
        <el-input
          v-model="queryParams.operatorId"
          placeholder="请输入operatorId"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="action" prop="action">
        <el-select
            v-model="queryParams.status"
            placeholder="请选择操作类型"
            clearable
            style="width: 100px;"
            >
            <el-option
                label="登录"
                :value="0"
            />
            <el-option
                label="登出"
                :value="1"
            />
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


    <el-table v-loading="loading" :data="LoginLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="operatorId" align="center" prop="operatorId" width="100" />
      <el-table-column label="action" align="center" prop="action" :show-overflow-tooltip="true" />
      <el-table-column label="ipAddress" align="center" prop="ipAddress" />
      <el-table-column label="operatorName" align="center" prop="operatorName" />
      <el-table-column label="操作" align="center" width="200">
        <template #default="{ row }">
          <el-button size="small" type="text" @click="handleUpdate(row)">
          <edit />
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
        <el-col :span="12">
          <el-form-item label="日志ID">
            <span>{{ form.logId }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="操作员ID">
            <span>{{ form.operatorId }}</span>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="动作">
            <span>{{ form.action }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="时间戳">
            <span>{{ form.timestamp }}</span>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="IP 地址">
            <span>{{ form.ipAddress }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="操作员姓名">
            <span>{{ form.operatorName }}</span>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="日志信息">
            <span>{{ form.message }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      </el-form>
    </el-dialog>

    
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { Edit,  Search, Refresh } from '@element-plus/icons-vue'
import { getLoginLogList } from '@/api/loginLog'

import { ElMessage, ElPagination} from 'element-plus'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
  operatorId: null,
  action: null
})

const form = reactive({
  logId: null,
  operatorId: '',
  action: '',
  timestamp: '',
  ipAddress: '',
  operatorName: '',
  message: '',
})

const EditformRef = ref(null)
const editTitle = ref('详情')
const editOn = ref(false)

const selectedRows = ref([])
const LoginLogList = ref([])

const queryForm = ref(null)
const showSearch = ref(true)
const total = ref(0)

const loading = ref(false)

const handlePageSelectionChange = (page) => {
  queryParams.pageNum = page
  handleQuery()
}

const handleUpdate = (row) => {
  if (row) {
    Object.assign(form, row)
    editOn.value = true
  }
}

const handleSelectionChange = (row) => {
    selectedRows.value = row
}

const resetQuery = () => {
  queryParams.pageNum = 1
  queryParams.pageSize = 6
  queryParams.param1 = null
  queryParams.param2 = null
  queryParams.param3 = null
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  LoginLogList.value = []
  getLoginLogList(queryParams).then(response => {
    LoginLogList.value = response.rows
    total.value = response.total
    loading.value = false
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

onMounted(() => {

})

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
