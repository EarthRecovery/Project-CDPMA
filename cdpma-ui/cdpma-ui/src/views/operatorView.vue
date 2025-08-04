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
        <el-button type="primary" size="mini" @click="handleQuery">
        <search />
        搜索
        </el-button>
        <el-button size="mini" @click="resetQuery">
        <refresh />
        重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain size="mini" @click="handleAdd">
        <plus />
        新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >
        <edit />
        修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >
        <delete />
        删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="operatorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="operatorId" width="100" />
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
          <el-button size="mini" type="text" @click="handleUpdate(row)">
          <edit />
          修改
          </el-button>
          <el-button size="mini" type="text"  @click="handleDelete(row)">
          <delete />  
          删除
          </el-button>
          <el-button size="mini" type="text" @click="handleMainBody(row)">
          <edit />
          查看正文
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改小说对话框 -->
    <el-dialog :title="title" v-model="open" width="780px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="小说标题" prop="novelTitle">
              <el-input v-model="form.novelTitle" placeholder="请输入小说标题" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dictStore.type.sys_novel_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="内容">
              <editor v-model="form.novelMainBody" :min-height="192" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm" size="mini">确 定</el-button>
          <el-button size="mini" @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog :title="title" v-model="mainBodyOpen" width="780px" append-to-body>
      <el-form :model="form" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="小说正文">
              <editor v-model="form.novelMainBody" :min-height="192" disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Edit, Delete, Plus, Search, Refresh } from '@element-plus/icons-vue'
// import operator from '@/store/modules/operator'
import { getOperatorList } from '@/api/operator'

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
const open = ref(false)
const mainBodyOpen = ref(false)
const loading = ref(false)
const single = ref(false)
const multiple = ref(false)
const total = ref(0)

const queryParams = reactive({
  operatorName: '',
  operatorEmail: '',
  operatorPhone: '',
  operatorRole: '',
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  novelTitle: '',
  status: '',
  novelMainBody: ''
})

const rules = {
  novelTitle: [{ required: true, message: '请输入小说标题', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  novelMainBody: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const formRef = ref(null)
const showSearch = ref(true)
const operatorList = ref([])

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
  queryParams.pageSize = 10
}

const handleAdd = () => {
  title.value = '新增小说'
  open.value = true
}

const handleUpdate = (row) => {
  if (row) {
    Object.assign(form, row)
  }
  title.value = '修改小说'
  open.value = true
}

const handleDelete = (row) => {
  if (row) {
    console.log('删除单条', row)
  } else {
    console.log('批量删除')
  }
}

const handleMainBody = (row) => {
  Object.assign(form, row)
  mainBodyOpen.value = true
}

const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      console.log('提交表单', form)
    }
  })
}

const cancel = () => {
  open.value = false
}

const handleSelectionChange = () => {}
const getList = () => {
    loading.value = true
    getOperatorList(queryParams).then(response => {
        operatorList.value = response.data
        total.value = response.length
        loading.value = false
      })
}

const parseTime = (time) => {
  const date = new Date(time)
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
