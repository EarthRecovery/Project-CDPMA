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
          :disabled="multiple"
          @click="handleDelete"
        >
        <delete />
        删除
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="jobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="Id" width="100" />
      <el-table-column label="名称" align="center" prop="Name" :show-overflow-tooltip="true" />
      <el-table-column label="param1" align="center" prop="form1" />、
      <el-table-column label="param2" align="center" prop="form2" />
      <el-table-column label="param3" align="center" prop="form3" />
      <el-table-column label="param4" align="center" prop="form4" />、
      <el-table-column label="param5" align="center" prop="form5" />
      <el-table-column label="param6" align="center" prop="form6" />
      <el-table-column label="操作" align="center" width="200">
        <template #default="{ row }">
          <el-button size="small" type="text" @click="handleUpdate(row)">
          <edit />
          修改
          </el-button>
          <el-button size="small" type="text"  @click="handleDeleteById(row)">
          <delete />  
          删除
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
            <el-form-item label="ID">
            <span>{{ form.Id }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="name">
            <el-input v-model="form.Name" placeholder="请输入name" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="param1">
            <el-input v-model="form.form1" placeholder="请输入param1" />
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="param2">
            <el-input v-model="form.form2" placeholder="请输入param2" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="param3">
            <el-input v-model="form.form3" placeholder="请输入param3" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="param4">
            <el-input v-model="form.form4" placeholder="请输入param4" />
            </el-form-item>
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
      <el-form :model="form" label-width="80px" ref="createFormRef">
        <!-- 第一行 -->
        <el-row :gutter="20">
        <el-col>
            <el-form-item label="jobDesc">
            <el-input v-model="form.jobDesc" placeholder="请输入任务描述" />
            </el-form-item>
        </el-col>
        <el-col>
            <el-form-item label="scheduleConf">
            <el-input v-model="form.scheduleConf" placeholder="请输入调度配置" />
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-select
            v-model="form.executorHandler"
            placeholder="请选择触发器"
            style="width: 200px;"
            >
            <el-option
                v-for="item in executorHandlerList"
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
import {editXX, deleteXXByIds } from '@/api/template'
import { addJob, getJobList } from '@/api/job'

import { ElMessage, ElPagination, ElMessageBox } from 'element-plus'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
})

const form = reactive({
  jobDesc: '',
  scheduleConf: '',
  executorHandler: '',
})

const EditformRef = ref(null)
const createFormRef = ref(null)
const editTitle = ref('XX信息d编辑')
const createTitle = ref('新增XX')
const editOn = ref(false)
const createOn = ref(false)

const selectedRows = ref([])
const jobList = ref([])

const total = ref(0)

const loading = ref(false)

const createForm = () => {
  addJob(form).then(response => {
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
  editXX(form).then(response => {
    if (response.code === 200) {
      ElMessage.success('XX信息更新成功')
      editOn.value = false
      handleQuery() 
    } else {
      ElMessage.error('XX信息更新失败: ' + response.message)
    }
  }).catch(error => {
    ElMessage.error('更新XX信息失败: ' + error.message)
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
    deleteXXByIds([row.Id]).then(() => {
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
        console.log('删除的记录:', selectedRows.value)
        const ids = selectedRows.value.map(row => row.goodId)
        console.log('删除的ID:', ids)
        deleteXXByIds(ids).then(() => {
          handleQuery() // 刷新商品列表
          ElMessage.success('删除成功')
        })
    }).catch(() => {
        ElMessageBox.alert('已取消删除操作')
    })
}

const handleAdd = () => {
  clearForm()
  createOn.value = true
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  jobList.value = []
  getJobList(queryParams).then(response => {
    jobList.value = response.rows
    total.value = response.total
    loading.value = false
    console.log('Job List:', response)
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

onMounted(() => {
    handleQuery()
})

const clearForm = () => {
    form.jobDesc = ''
    form.scheduleConf = ''
    form.executorHandler = ''
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
