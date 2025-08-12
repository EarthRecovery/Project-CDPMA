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
      <el-form-item label="用户名" prop="operatorName">
        <el-input
          v-model="queryParams.operatorName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户电话" prop="operatorPhone">
        <el-input
          v-model="queryParams.operatorPhone"
          placeholder="请输入用户电话"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户邮箱" prop="operatorEmail">
        <el-input
          v-model="queryParams.operatorEmail"
          placeholder="请输入用户邮箱"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户角色" prop="operatorRole">
        <el-select v-model="queryParams.operatorRole" placeholder="用户角色" style="width: 240px">
          <el-option
            v-for="dict in dictStore.type.sys_operator_role"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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

    <el-table v-loading="loading" :data="operatorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="operatorId" width="100" />
      <el-table-column label="用户名" align="center" prop="operatorName" :show-overflow-tooltip="true" />
      <el-table-column label="用户电话" align="center" prop="operatorPhone" width="100" />
      <el-table-column label="用户邮箱" align="center" prop="operatorEmail" width="100" />
      <el-table-column label="用户角色" align="center" prop="operatorRole" width="100" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="100">
        <template #default="{ row }">
          <span>{{ parseTime(row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="{ row }">
          <el-button size="small" type="text" @click="handleUpdate(row)">
          <edit />
          修改
          </el-button>
          <el-button size="small" type="text" @click="handleDeleteById(row)">
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

    <el-dialog :title="title" v-model="EditOpen" width="780px" append-to-body>
      <el-form :model="form" label-width="80px" ref="EditformRef" :rules="rules">
        <!-- 第一行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="ID">
            <span>{{ form.operatorId }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="用户名">
            <el-input v-model="form.operatorName" placeholder="请输入用户名" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="昵称">
            <el-input v-model="form.operatorNickName" placeholder="请输入昵称" />
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="身份证号">
            <el-input v-model="form.operatorIdCard" placeholder="请输入身份证号" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="电话">
            <el-input v-model="form.operatorPhone" placeholder="请输入电话" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="性别">
            <el-select v-model="form.operatorGender" placeholder="请选择性别">
                <el-option label="男" value="M" />
                <el-option label="女" value="F" />
                <el-option label="其他" value="O" />
            </el-select>
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第三行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="年龄">
            <el-input-number v-model="form.operatorAge" :min="18" :max="100" placeholder="请输入年龄" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="邮箱">
            <el-input v-model="form.operatorEmail" placeholder="请输入邮箱" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="角色">
            <el-select v-model="form.operatorRole" placeholder="请选择角色" clearable>
              <el-option
                v-for="item in dictStore.type.sys_operator_role"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        </el-row>

        <!-- 第四行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="所属大区">
            <el-input v-model="form.operatorRegion" placeholder="请输入所属大区" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="创建时间">
            <el-date-picker v-model="form.operatorCreatedAt" type="datetime" placeholder="选择时间" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="是否禁用">
            <el-switch v-model="form.operatorIsDisabled" />
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



    <!-- 新增用户对话框 -->
    <el-dialog :title="title" v-model="AddOpen" width="780px" append-to-body>
      <el-form :model="form" label-width="80px" ref="AddformRef" :rules="rules">
        <!-- 第一行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="ID" prop="operatorId">
            <span>{{ form.operatorId }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="用户名" prop="operatorName">
            <el-input v-model="form.operatorName" placeholder="请输入用户名" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="昵称" prop="operatorNickName">
            <el-input v-model="form.operatorNickName" placeholder="请输入昵称" />
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="身份证号" prop="operatorIdCard">
            <el-input v-model="form.operatorIdCard" placeholder="请输入身份证号" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="电话" prop="operatorPhone">
            <el-input v-model="form.operatorPhone" placeholder="请输入电话" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="性别">
            <el-select v-model="form.operatorGender" placeholder="请选择性别">
                <el-option label="男" value="M" />
                <el-option label="女" value="F" />
                <el-option label="其他" value="O" />
            </el-select>
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 第三行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="年龄" prop="operatorAge">
            <el-input-number v-model="form.operatorAge" :min="18" :max="100" placeholder="请输入年龄" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="邮箱" prop="operatorEmail">
            <el-input v-model="form.operatorEmail" placeholder="请输入邮箱" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="角色">
            <el-select v-model="form.operatorRole" placeholder="请选择角色" clearable>
              <el-option
                v-for="item in dictStore.type.sys_operator_role"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        </el-row>

        <!-- 第四行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="所属大区" prop="operatorRegion">
            <el-input v-model="form.operatorRegion" placeholder="请输入所属大区" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="是否禁用">
            <el-switch v-model="form.operatorIsDisabled" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="密码">
            <el-input v-model="form.passwordHash" type="passwordHash" placeholder="请输入密码" />
            </el-form-item>
        </el-col>
        </el-row>

        <!-- 提交按钮 -->
        <el-row>
        <el-col :span="24">
            <el-form-item>
            <el-button type="primary" @click="addForm" size="small">提交</el-button>
            </el-form-item>
        </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive,onMounted } from 'vue'
import { Edit, Delete, Plus, Search, Refresh } from '@element-plus/icons-vue'
// import operator from '@/store/modules/operator'
import { getOperatorList, deleteOperatorByIds, addOperator, editOperator } from '@/api/operator'
import { ElMessage, ElMessageBox, ElPagination } from 'element-plus'
import { nextTick } from 'vue'



const dictStore = reactive({
  type: {
    sys_operator_role: [
      { value: 'ADMIN', label: '管理员' },
      { value: 'ASSISTANT', label: '导购' },
      { value: 'USER', label: '用户' }
    ],
  }
})

const title = ref('')
const EditOpen = ref(false)
const AddOpen = ref(false)
const loading = ref(false)
const total = ref(0)
const EditformRef = ref(null)
const AddformRef = ref(null)

const queryParams = reactive({
  operatorName: '',
  operatorEmail: '',
  operatorPhone: '',
  operatorRole: '',
  pageNum: 1,
  pageSize: 6
})

const form = reactive({
  operatorId: '',
  operatorName: '',
  operatorNickName: '',
  operatorIdCard: '',
  operatorPhone: '',
  operatorGender: '',
  operatorAge: '',
  operatorEmail: '',
  operatorRole: '',
  operatorRegion: '',
  operatorCreatedAt: '',
  operatorIsDisabled: '',
  passwordHash: ''
})

const showSearch = ref(true)
const operatorList = ref([])
const selectedRows = ref([])

const rules = {
  operatorName: [
    { required: true, message: '请输入操作员名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度应在 2 到 20 个字符', trigger: 'blur' }
  ],
  operatorNickName: [
    { max: 20, message: '昵称不能超过 20 个字符', trigger: 'blur' }
  ],
  operatorIdCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /^[0-9]{15,18}$/, message: '身份证号格式不正确', trigger: 'blur' }
  ],
  operatorPhone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  operatorGender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  operatorAge: [
    { type: 'number', message: '年龄必须为数字', trigger: 'blur' }
  ],
  operatorEmail: [
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ],
  operatorRole: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ],
  operatorRegion: [
    { required: true, message: '请输入地区', trigger: 'blur' }
  ]
}

const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

const resetQuery = () => {
  queryParams.operatorName = ''
  queryParams.operatorPhone = ''
  queryParams.operatorRole = ''
  queryParams.operatorEmail = ''
  queryParams.pageNum = 1
  queryParams.pageSize = 3
}

const handleAdd = () => {
  title.value = '新增用户'
  clearForm()
  openAddDialog()
}

const handleUpdate = (row) => {
  if (row) {
    Object.assign(form, row)
  }
  title.value = '修改用户'
  openEditDialog()
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
        const ids = selectedRows.value.map(row => row.operatorId)
        console.log('删除的ID:', ids)
        deleteOperatorByIds(ids).then((res) => {
          getList()
          ElMessage.success('删除成功， 共删除 ' + res.operatorNum
 + ' 条记录')
        })
    }).catch(() => {
        ElMessageBox.alert('已取消删除操作')
    })
}

const handleDeleteById = (row) => {
  ElMessageBox.confirm('是否确认删除该记录？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteOperatorByIds([row.operatorId]).then((res) => {
      getList()
      ElMessage.success('删除成功， 共删除 ' + res.operatorNum
 + ' 条记录')
    })
  }).catch(() => {
    ElMessageBox.alert('已取消删除操作')
  })
}


const editForm = () => {
  EditformRef.value.validate((valid) => {
    if (valid) {
      console.log('提交表单', form)
      editOperator(form).then(response => {
        ElMessage.success('修改成功' + response)
      }).catch(error => {
        ElMessageBox.alert('修改失败: ' + error.message)
      })
      // AddOpen.value = false
      getList()
    } else {
      console.log('表单验证失败')
    }
  })
}

const addForm = () => {
  AddformRef.value.validate((valid) => {
    if (valid) {
      console.log('提交表单', form)
      addOperator(form).then(response => {
        ElMessage.success('新增成功' + response)
      }).catch(error => {
        ElMessageBox.alert('新增失败: ' + error.message)
      })
      // AddOpen.value = false
      getList()
    } else {
      console.log('表单验证失败')
    }
  })
}
const handlePageSelectionChange = (row) => {
    queryParams.pageNum = row
    getList()
}

const handleSelectionChange = (row) => {
    selectedRows.value = row
}
const getList = () => {
    loading.value = true
    selectedRows.value = []
    getOperatorList(queryParams).then(response => {
        console.log('获取操作员列表:', response)
        operatorList.value = response.rows
        total.value = response.total
        loading.value = false
      })
}

const parseTime = (time) => {
  const date = new Date(time)
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
}

const openEditDialog = () => {
  EditOpen.value = true
  nextTick(() => {
    EditformRef.value?.clearValidate()
  })
}

const openAddDialog = () => {
  AddOpen.value = true
  nextTick(() => {
    AddformRef.value?.clearValidate()
  })
}

onMounted(() => {
  getList()
})

const clearForm = () => {
  form.operatorId = ''
  form.operatorName = ''
  form.operatorNickName = ''
  form.operatorIdCard = ''
  form.operatorPhone = ''
  form.operatorGender = ''
  form.operatorAge = ''
  form.operatorEmail = ''
  form.operatorRole = ''
  form.operatorRegion = ''
  form.operatorCreatedAt = ''
  form.operatorIsDisabled = ''
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
