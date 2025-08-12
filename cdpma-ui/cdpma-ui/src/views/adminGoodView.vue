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
      <el-form-item label="商品名" prop="goodName">
        <el-input
          v-model="queryParams.goodName"
          placeholder="请输入商品名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品种类" prop="categoryId">
        <el-cascader
          v-model="queryParams.categoryId"
          :options="categoryTree"
          :props="cascaderProps"
          placeholder="请选择商品分类"
          clearable
        />
      </el-form-item>
      <el-form-item label="价格范围" prop="priceRange">
      <el-row :gutter="10">
        <el-col :span="11">
          <el-input-number
            v-model="queryParams.priceLow"
            :min="0"
            label="最低价格"
            placeholder="最低价格"
            style="width: 100%;"
          />
        </el-col>
        <el-col :span="2" class="text-center" style="line-height: 32px;">至</el-col>
        <el-col :span="11">
          <el-input-number
            v-model="queryParams.priceHigh"
            :min="0"
            label="最高价格"
            placeholder="最高价格"
            style="width: 100%;"
          />
        </el-col>
      </el-row>
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

    <el-table v-loading="loading" :data="goodList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="goodId" width="100" />
      <el-table-column label="商品名" align="center" prop="goodName" :show-overflow-tooltip="true" />
      <el-table-column label="商品种类" align="center" prop="categoryName" width="100" />
      <el-table-column label="价格" align="center" prop="price" width="100" />
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

    <el-dialog :title="editTitle" v-model="editOn" width="780px">
      <el-form :model="form" label-width="80px" ref="EditformRef">
        <!-- 第一行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="ID">
            <span>{{ form.goodId }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="商品分类">
            <span>{{ categoryName }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-cascader
            v-model="form.categoryId"
            :options="categoryTree"
            :props="cascaderProps"
            placeholder="修改商品分类"
            clearable
          />
        </el-col>
        
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="商品名">
            <el-input v-model="form.goodName" placeholder="请输入商品名" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="价格">
            <el-input-number v-model="form.price" :min="0" placeholder="请输入价格" />
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
        <el-col :span="8">
            <el-form-item label="ID">
            <span>{{ form.goodId }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-cascader
            v-model="form.categoryId"
            :options="categoryTree"
            :props="cascaderProps"
            placeholder="修改商品分类"
            clearable
          />
        </el-col>
        
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="商品名">
            <el-input v-model="form.goodName" placeholder="请输入商品名" />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="价格">
            <el-input-number v-model="form.price" :min="0" placeholder="请输入价格" />
            </el-form-item>
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
import { Edit, Delete, Plus, Search, Refresh } from '@element-plus/icons-vue'
import { getCategory, getGoodList, getCategoryName, editGood, deleteGoodByIds, createGood } from '@/api/good'
import { buildCategoryTree} from '@/utils/category'

import { ElMessage, ElPagination, ElMessageBox } from 'element-plus'

const queryForm = ref(null)
const showSearch = ref(true)
const total = ref(0)
const goodList = ref([])
const loading = ref(false)
const editOn = ref(false)
const editTitle = ref('商品信息编辑')
const EditformRef = ref(null)
const categoryName = ref('')
const selectedRows = ref([]) // 用于存储选中的行
const createOn = ref(false)
const createFormRef = ref(null)
const createTitle = ref('新增商品')

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
  categoryId: null,
  goodName: '',
  priceHigh: null,
  priceLow: null,
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
  price: null,
})

const handleAdd = () => {
  clearForm()
  createOn.value = true
}

const createForm = () => {
  form.categoryId = form.categoryId[form.categoryId.length - 1] // 获取最后一个选中的分类ID
  createGood(form).then(response => {
    if (response.code === 200) {
      ElMessage.success('商品信息创建成功')
      createOn.value = false
      handleQuery() // 刷新商品列表
    } else {
      ElMessage.error('商品信息创建失败: ' + response.message)
    }
  }).catch(error => {
    ElMessage.error('创建商品信息失败: ' + error.message)
  })
  console.log('提交表单:', form)
  categoryName.value = ''
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
        deleteGoodByIds(ids).then(() => {
          handleQuery() // 刷新商品列表
          ElMessage.success('删除成功')
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
    deleteGoodByIds([row.goodId]).then(() => {
      handleQuery() // 刷新商品列表
      ElMessage.success('删除成功 ')
    })
  }).catch(() => {
    ElMessageBox.alert('已取消删除操作')
  })
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  goodList.value = []
  if (Array.isArray(queryParams.categoryId)) {
    queryParams.categoryId = queryParams.categoryId.length > 0 ? queryParams.categoryId[queryParams.categoryId.length - 1] : null;
  } else {
    queryParams.categoryId = queryParams.categoryId || null;
  }
  getGoodList(queryParams).then(response => {
    // 假设 response.data 是商品列表
    goodList.value = response.rows
    total.value = response.total
    loading.value = false
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

const resetQuery = () => {
  queryParams.pageNum = 1
  queryParams.pageSize = 6
  queryParams.categoryId = null
  queryParams.goodName = ''
  queryParams.priceHigh = null
  queryParams.priceLow = null
}


// 存储分类数据的树形结构
const categoryTree = ref([])

// `el-cascader` 配置项，定义label、value和children的属性
const cascaderProps = {
  label: 'categoryName',
  value: 'categoryId',
  children: 'children',
}

const editForm = () => {
  form.categoryId = form.categoryId[form.categoryId.length - 1] // 获取最后一个选中的分类ID
  editGood(form).then(response => {
    if (response.code === 200) {
      ElMessage.success('商品信息更新成功')
      editOn.value = false
      handleQuery() // 刷新商品列表
    } else {
      ElMessage.error('商品信息更新失败: ' + response.message)
    }
  }).catch(error => {
    ElMessage.error('更新商品信息失败: ' + error.message)
  })
  console.log('提交表单:', form)
  categoryName.value = ''
}

const handleUpdate = (row) => {
  if (row) {
    Object.assign(form, row)
    getCategoryName(row.categoryId).then(response => {
      categoryName.value = response.data.categoryName
    }).catch(error => {
      ElMessage.error('获取商品分类名称失败: ' + error.message)
    })
    editOn.value = true
  }
}

const handleSelectionChange = (row) => {
    selectedRows.value = row
}

const handlePageSelectionChange = (page) => {
  queryParams.pageNum = page
  handleQuery() // 刷新商品列表
}

const parseTime = (time) => {
  const date = new Date(time)
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
}

onMounted(() => {
  // 模拟请求商品分类数据
  getCategory().then(response => {
    // 假设 API 返回的数据是一个数组，按返回的数据结构
    const categories = response.data // response 应该是分类数据数组

    // 转换成树形结构
    categoryTree.value = buildCategoryTree(categories)
  }).catch(error => {
    ElMessage.error('获取商品分类失败: ' + error.message)
  })

  handleQuery() 
})

const clearForm = () => {
  form.goodId = null
  form.goodName = ''
  form.categoryId = null
  form.price = null
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
