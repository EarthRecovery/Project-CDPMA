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
      <el-form-item label="status" prop="status">
        <el-select
            v-model="queryParams.status"
            placeholder="请选择状态"
            clearable
            style="width: 100px;"
            >
            <el-option
                label="成功"
                :value="0"
            />
            <el-option
                label="失败"
                :value="1"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="operatorId" prop="operatorId">
        <el-input
          v-model="queryParams.operatorId"
          placeholder="请输入operatorId"
          clearable
          @keyup.enter="handleQuery"
        />
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

    <el-table v-loading="loading" :data="runtimeLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="operatorId" align="center" prop="operatorId" width="100" />
      <el-table-column label="title" align="center" prop="title" :show-overflow-tooltip="true" />
      <el-table-column label="method" align="center" prop="method" />
      <el-table-column label="requestMethod" align="center" prop="requestMethod" />
      <el-table-column label="status" align="center" prop="status" />
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
  <el-form :model="form" label-width="100px" ref="EditformRef">
    <!-- 第一行 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="操作ID">
          <span>{{ form.operationId }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="标题">
          <span>{{ form.title }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="业务类型">
          <span>{{ form.businessType }}</span>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第二行 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="方法名">
          <span>{{ form.method }}</span>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
        <el-col :span="8">
        <el-form-item label="请求方式">
          <span>{{ form.requestMethod }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="操作类型">
          <span>{{ form.operatorType }}</span>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第三行 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="操作员名称">
          <span>{{ form.operatorName }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="操作员ID">
          <span>{{ form.operatorId }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="操作URL">
          <span>{{ form.operationUrl }}</span>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第四行 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="操作IP">
          <span>{{ form.operatorIp }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="操作参数">
          <span>{{ form.operationParams }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="返回结果">
          <span>{{ form.jsonResult }}</span>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第五行 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="状态">
          <span>{{ form.status }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="错误信息">
          <span>{{ form.errorMsg }}</span>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第六行 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="创建时间">
          <span>{{ form.createTime }}</span>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="耗时(ms)">
          <span>{{ form.costTime }}</span>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { Edit, Search, Refresh } from '@element-plus/icons-vue'
import { fetchRuntimeLogs } from '@/api/runtimeLog'

import { ElMessage, ElPagination } from 'element-plus'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
  status: null,
  operatorId: null,
})

const form = reactive({
  operationId: null,
  title: '',
  businessType: '',
  method: '',
  requestMethod: '',
  operatorType: '',
  operatorName: '',
  operatorId: '',
  operationUrl: '',
  operatorIp: '',
  operationParams: '',
  jsonResult: '',
  status: '',
  errorMsg: '',
  costTime: '',
  createTime: '',
})

const EditformRef = ref(null)
const editTitle = ref('信息详情')
const editOn = ref(false)

const selectedRows = ref([])
const runtimeLogList = ref([])

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
  queryParams.operatorId = null
  queryParams.status = null
  
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  runtimeLogList.value = []
  fetchRuntimeLogs(queryParams).then(response => {
    runtimeLogList.value = response.rows
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
