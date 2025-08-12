<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="small" @click="handleAdd">
        <plus />
        新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          size="small"
          @click="handleDelete"
        >
        <delete />
        删除
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="jobList.data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="jobDesc" align="center" prop="jobDesc" width="100" />
      <el-table-column label="scheduleConf" align="center" prop="scheduleConf" :show-overflow-tooltip="true" />
      <el-table-column label="executorHandler" align="center" prop="executorHandler" />
      <el-table-column label="executorParam" align="center" prop="executorParam" />
      <el-table-column label="triggerStatus" align="center" prop="triggerStatus">
        <template #default="{ row }">
          <span>{{ row.triggerStatus === 0 ? '中止' : row.triggerStatus === 1 ? '运行' : '未知' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template #default="{ row }">
          <el-button size="small" type="text" @click="handleUpdate(row)">
          <edit />
          修改
          </el-button>
          <el-button size="small" type="text" @click="handleDeleteById(row)">
          <delete />  
          删除
          </el-button>
          <!-- 开启 / 关闭 动态按钮 -->
          <el-button
            size="small"
            type="text"
            @click="handleToggleStatus(row)"
          >
            <delete />
            {{ row.triggerStatus === 0 ? '开启' : '关闭' }}
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
            <el-form-item label="jobDesc">
            <el-input v-model="form.jobDesc" placeholder="请输入jobDesc" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="scheduleConf">
            <el-input v-model="form.scheduleConf" placeholder="请输入scheduleConf" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-select
            v-model="form.executorHandler"
            placeholder="请选择执行器"
            style="width: 80%;"
            >
            <el-option
                v-for="item in operationResponseList"
                :key="item.responseName"
                :label="item.responseName"
                :value="item.responseName"
            />
            </el-select>
        </el-col>
        </el-row>

        <!-- 提交按钮 -->
        <el-row>
        <el-col :span="24">
            <el-form-item>
            <el-button type="primary" @click="editForm" size="small">提交</el-button>
            </el-form-item>
        </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <el-dialog :title="createTitle" v-model="createOn" width="780px">
      <el-form :model="createJobForm" label-width="80px" ref="createFormRef">
        <!-- 第一行 -->
        <el-row :gutter="20">
        <el-col>
            <el-form-item label="任务描述">
            <el-input v-model="createJobForm.jobDesc" placeholder="请输入任务描述" style="width: 80%;" />
            </el-form-item>
        </el-col>
        <el-col>
            <el-form-item label="调度配置">
            <el-input v-model="createJobForm.scheduleConf" placeholder="请输入调度配置" style="width: 80%;" />
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-select
            v-model="createJobForm.executorHandler"
            placeholder="请选择执行器"
            style="width: 80%;"
            >
            <el-option
                v-for="item in operationResponseList"
                :key="item.responseName"
                :label="item.responseName"
                :value="item.responseName"
            />
            </el-select>
        </el-col>
        </el-row>

        <!-- 提交按钮 -->
        <el-row style="margin-top: 20px;">
        <el-col :span="24">
            <el-form-item>
            <el-button type="primary" @click="createForm" size="small">提交</el-button>
            </el-form-item>
        </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { Edit, Delete, Plus } from '@element-plus/icons-vue'
import { addJob, getJobList, deleteJobById, updateJob, startJob, pauseJob } from '@/api/job'
import {  getOperationResponseList } from '@/api/operation'

import { ElMessage, ElPagination, ElMessageBox } from 'element-plus'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
})

const form = reactive({
  id: 0,
  jobGroup: 0,
  jobDesc: '',
  addTime: null,
  updateTime: null,
  author: '',
  alarmEmail: '',
  scheduleType: '',
  scheduleConf: '',
  misfireStrategy: '',
  executorRouteStrategy: '',
  executorHandler: '',
  executorParam: '',
  executorBlockStrategy: '',
  executorTimeout: 0,
  executorFailRetryCount: 0,
  glueType: '',
  glueSource: '',
  glueRemark: '',
  glueUpdatetime: null,
  childJobId: '',
  triggerStatus: 0,
  triggerLastTime: 0,
  triggerNextTime: 0,
});

const createJobForm = reactive({
  jobDesc: '',
  scheduleConf: '',
  executorHandler: ''
})

const EditformRef = ref(null)
const createFormRef = ref(null)
const editTitle = ref('定时任务编辑')
const createTitle = ref('新增定时任务')
const editOn = ref(false)
const createOn = ref(false)
const operationResponseList = ref([])

const selectedRows = ref([])
const jobList = reactive({
  code: 0,
  msg: '',
  data: []
})

const total = ref(0)

const loading = ref(false)

const createForm = () => {
  addJob(createJobForm).then(response => {
    if (response.code === 200) {
      ElMessage.success('任务创建成功')
      createOn.value = false
      handleQuery() // 刷新任务列表
    } else {
      ElMessage.error('任务创建失败: ' + response.message)
    }
  }).catch(error => {
    ElMessage.error('创建任务失败: ' + error.message)
  })
}

const editForm = () => {
  console.log('Editing Job:', form)
  updateJob(form).then(response => {
    if (response.code === 200) {
      ElMessage.success('任务更新成功')
      editOn.value = false
      handleQuery() 
    } else {
      ElMessage.error('任务更新失败: ' + response.message)
    }
  }).catch(error => {
    ElMessage.error('更新任务失败: ' + error.message)
  })
}

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
    deleteJobById(row.id).then(() => {
      handleQuery() // 刷新商品列表
      ElMessage.success('删除成功 ')
    })
  }).catch(() => {
    ElMessageBox.alert('已取消删除操作')
  })
}

const handleUpdate = (row) => {
  if (row) {
    Object.assign(form, row)
    editOn.value = true
  }
}

const handleToggleStatus = (row) => {
  if(row.triggerStatus === 0) {
    // 开启任务
    startJob(row.id)
      .then(() => {
        ElMessage.success('任务已开启')
        handleQuery() // 刷新任务列表
      })
      .catch(error => {
        ElMessage.error('开启任务失败: ' + error.message)
      })
  } else {
    // 关闭任务
    pauseJob(row.id)
      .then(() => {
        ElMessage.success('任务已暂停')
        handleQuery() // 刷新任务列表
      })
      .catch(error => {
        ElMessage.error('暂停任务失败: ' + error.message)
      })
  }
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
        const ids = selectedRows.value.map(row => row.id)
        for(const id of ids) {
            deleteJobById(id).then(() => {
              handleQuery() // 刷新任务列表
            }).catch(error => {
              ElMessage.error('删除任务失败: ' + error.message)
            })
        }
        
        ElMessage.success('删除成功')
    }).catch(() => {
        ElMessageBox.alert('已取消删除操作')
    })
    
}

const handleAdd = () => {
  clearCreateJobForm()
  createOn.value = true
}


const handleQuery = async () => {
  // 处理查询逻辑
  loading.value = true
  jobList.value = []
  try {
    const response = await getJobList(queryParams)
    Object.assign(jobList, response)
    console.log('Job List:', response)
    loading.value = false
    total.value = jobList.data.length
  } catch (error) {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  }
}

onMounted(async () => {
    await Promise.all([
      fillOperationResponseList()
    ])
    handleQuery()
})

const fillOperationResponseList = async () => {
  try {
    const response = await getOperationResponseList()
    operationResponseList.value = response.data
    console.log('OperationResponseList:', operationResponseList.value)
    operationResponseList.value = operationResponseList.value.filter(item => item.isUrgent == false)
  } catch (error) {
    console.error('获取 OperationResponseList 失败:', error)
  }
}

const clearCreateJobForm = () => {
    createJobForm.jobDesc = ''
    createJobForm.scheduleConf = ''
    createJobForm.executorHandler = ''
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
