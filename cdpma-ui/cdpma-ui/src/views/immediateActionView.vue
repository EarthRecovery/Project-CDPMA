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
      <el-form-item label="触发器" prop="triggerConditionId">
        <el-select
          v-model="queryParams.triggerConditionId"
          placeholder="请选择触发器"
          style="width: 200px;"
        >
          <el-option
            v-for="item in operationTriggerList"
            :key="item.conditionId"
            :label="`${item.description} - ${item.conditionName}`"
            :value="item.conditionId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="响应" prop="operationResponse">
        <el-select
          v-model="queryParams.operationResponse"
          placeholder="请选择响应"
          style="width: 200px;"
        >
          <el-option
            v-for="item in operationResponseList"
            :key="item.responseId"
            :label="`${item.description} - ${item.responseName}`"
            :value="item.responseId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否启用" prop="isEnabled">
        <el-select
            v-model="queryParams.isEnabled"
            placeholder="请选择是否启用"
            clearable
            style="width: 140px;"
            >
            <el-option
                label="是"
                :value="true"
            />
            <el-option
                label="否"
                :value="false"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="small" @click="handleTriggerAdd">
        <plus />
        新增触发器
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

    <el-table v-loading="loading" :data="operationList" @selection-change="handleSelectionChange" :fit="true">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="operationId"/>
      <el-table-column label="触发器" align="center" prop="triggerConditionName" />
      <el-table-column label="响应" align="center" prop="operationResponseName"/>
      <el-table-column label="描述" align="center" prop="operationDescription"/>
      <el-table-column label="是否启用" align="center" prop="isEnabled">
        <template #default="{ row }">
          <el-tag :type="row.isEnabled ? 'true' : 'false'">
            {{ row.isEnabled ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" >
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

    <el-dialog :title="triggerAddTitle" v-model="triggerAddOn" width="780px">
      <el-form :model="triggerForm" label-width="80px" ref="createFormRef">
        <!-- 第一行 -->
        <el-row :gutter="20">
        <el-col :span="8" style="padding-left:40px;">
            <el-form-item label="conditionName">
            <el-input v-model="triggerForm.conditionName" placeholder="请输入name" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="description">
            <el-input v-model="triggerForm.description" placeholder="请输入description" />
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 提交按钮 -->
        <el-row>
        <el-col :span="24">
            <el-form-item>
            <el-button type="primary" @click="createTrigger" size="small">提交</el-button>
            </el-form-item>
        </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { Edit, Delete, Plus, Search, Refresh } from '@element-plus/icons-vue'
import { getOperationTriggerList, getOperationResponseList, getOperationList, addOperationTrigger } from '@/api/operation'
import {  editXX, deleteXXByIds } from '@/api/template'

import { ElMessage, ElPagination, ElMessageBox } from 'element-plus'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
  triggerConditionId: null,
  operationResponse: null,
  isEnabled: null,
})

const form = reactive({
  operationId: null,
  triggerCondition: '',
  operationResponse: '',
  operationDescription: '',
  isEnabled: ''
})

const triggerForm = reactive({
  conditionName: '',
  description: '',
  isUrgent: true,
})

const triggerAddOn = ref(false)
const triggerAddTitle = ref("新增触发器")
const EditformRef = ref(null)
const createFormRef = ref(null)
const editTitle = ref('XX信息编辑')
const editOn = ref(false)

const selectedRows = ref([])
const operationList = ref([])

const queryForm = ref(null)
const showSearch = ref(true)
const total = ref(0)

const loading = ref(false)

const operationTriggerList = ref([])
const operationResponseList = ref([])


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
const resetQuery = () => {
  queryParams.pageNum = 1
  queryParams.pageSize = 6
  queryParams.isEnabled = null
  queryParams.triggerConditionId = null
  queryParams.operationResponse = null
}

const handleTriggerAdd = () => {
  clearTriggerForm()
  triggerAddOn.value = true
}

const createTrigger = () => {
  addOperationTrigger(triggerForm).then(response => {
    if (response.code === 200) {
      ElMessage.success('触发器创建成功')
      triggerAddOn.value = false
      fillOperationTriggerList() // 刷新触发器列表
      handleQuery() // 刷新操作列表
    } else {
      ElMessage.error('触发器创建失败: ' + response.message)
    }
  }).catch(error => {
    ElMessage.error('创建触发器失败: ' + error.message)
  })
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  operationList.value = []
  getOperationList(queryParams).then(response => {
    operationList.value = response.rows
    editOperationList(operationList.value)
    total.value = response.total
    loading.value = false
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

onMounted(async () => {
  await Promise.all([
    fillOperationTriggerList(),
    fillOperationResponseList()
  ])

  handleQuery()
})

const clearTriggerForm = () => {
  triggerForm.conditionName = ''
  triggerForm.description = ''
  triggerForm.isUrgent = true
}

const fillOperationTriggerList = async () => {
  try {
    const response = await getOperationTriggerList()
    operationTriggerList.value = response.data.filter(item => item.isUrgent !== false)
  } catch (error) {
    console.error('获取 OperationTriggerList 失败:', error)
  }
}

const fillOperationResponseList = async () => {
  try {
    const response = await getOperationResponseList()
    operationResponseList.value = response.data
  } catch (error) {
    console.error('获取 OperationResponseList 失败:', error)
  }
}

const editOperationList = (list) => {
  list.forEach(item => {
    item.triggerConditionName = operationTriggerList.value.find(trigger => trigger.conditionId === item.triggerConditionId)?.conditionName || ''
    item.operationResponseName = operationResponseList.value.find(response => response.responseId === item.operationResponse)?.responseName || ''
  })
}

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
