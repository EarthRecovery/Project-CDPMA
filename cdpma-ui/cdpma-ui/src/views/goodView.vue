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
          v-model="selectedCategories"
          :options="categoryTree"
          :props="cascaderProps"
          placeholder="请选择商品分类"
          clearable
        />
      </el-form-item>
      <el-form-item label="价格范围" prop="priceRange">
      <el-row gutter={10}>
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
        <el-button type="primary" plain size="small" @click="handleBuyAll">
        <plus />
        购买
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="goodList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="goodId" width="100" />
      <el-table-column label="商品名" align="center" prop="goodName" :show-overflow-tooltip="true" />
      <el-table-column label="价格" align="center" prop="price" width="100" />
      <el-table-column label="点赞数" align="center" prop="likeNum" width="100" />
      <el-table-column label="数量" align="center" prop="quantity" width="200">
        <template v-slot="scope">
            <el-input v-model="scope.row.quantity" placeholder="请输入数量" size="small" style="width: 80%;" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="{ row }">
          <el-button size="small" type="text" @click="handleCheck(row)">
          <edit />
          查看
          </el-button>
          <el-button size="small" type="text" @click="handleBuy(row)">
          <edit />
          购买
          </el-button>
          <el-button size="small" type="text"  @click="handleFavorite(row)">
          <delete />  
          收藏
          </el-button>
          <el-button size="small" type="text"  @click="addLike(row)">
          点赞
          </el-button>
          <el-button size="small" type="text"  @click="deleteLike(row)">
          取消点赞
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
            <el-form-item label="商品数量">
            <span>{{ form.quantity }}</span>
            </el-form-item>
        </el-col>
        
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="商品名">
            <span>{{ form.goodName }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="价格">
            <span>{{ form.price }}</span>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="点赞数">
            <span>{{ form.likeNum }}</span>
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
import { getCategory, getGoodList, buyGood, favoriteGood } from '@/api/good'
import { addLikeAPI, deleteLikeAPI } from '@/api/like'
import { useStore } from 'vuex'
import { buildCategoryTree} from '@/utils/category'

import { ElMessage, ElPagination, ElMessageBox } from 'element-plus'

const queryForm = ref(null)
const showSearch = ref(true)
const total = ref(0)
const goodList = ref([])
const loading = ref(false)
const editOn = ref(false)
const editTitle = ref('商品信息')
const EditformRef = ref(null)
const selectedRows = ref([]) // 用于存储选中的行

const store = useStore()

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
  likeNum: 0,
  quantity: 0,
  price: null,
})

const handleCheck = (row) => {
  if(row){
    Object.assign(form, row)
    editOn.value = true
  }
}

const handleFavorite = (row) => {
  if(row){
    favoriteGood(row.goodId, store.state.operator.id).then(()=> {
      ElMessage.success('收藏成功')
    }).catch(error => {
      ElMessage.error('收藏失败: ' + error.message)
    })
  }
}

const addLike = (row) => {
  if(row){
    addLikeAPI(store.state.operator.id, row.goodId).then(() => {
      ElMessage.success('点赞成功')
      handleQuery() // 刷新商品列表
    }).catch(error => {
      ElMessage.error('点赞失败: ' + error.message)
    })
  }
}

const deleteLike = (row) => {
  if(row){
    deleteLikeAPI(store.state.operator.id, row.goodId).then(() => {
      ElMessage.success('取消点赞成功')
      handleQuery() // 刷新商品列表
    }).catch(error => {
      ElMessage.error('取消点赞失败: ' + error.message)
    })
  }
}

const handleBuy = (row) => {
  if(row){
    console.log(row)
    if(row.quantity <= 0) {
      ElMessage.error('商品数量不足')
      return
    }
    ElMessageBox.confirm('是否确认购买该商品？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        buyGood(row, store.state.operator.id).then(() => {
            handleQuery() // 刷新商品列表
            ElMessage.success('购买成功 ')
        })
    }).catch(() => {
        ElMessageBox.alert('已取消购买操作')
    })
  }
}

const handleBuyAll = () => {
  if(selectedRows.value.length === 0) {
    ElMessage.warning('请至少选择一件商品')
    return
  }
  ElMessageBox.confirm('是否确认购买选中的商品？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
  }).then(() => {
      selectedRows.value.forEach(row => {
          if(row.quantity <= 0) {
              ElMessage.error(`商品 ${row.goodName} 数量不足`)
              return
          }
      })
      selectedRows.value.forEach(row => {
          buyGood(row, store.state.operator.id).then(() => {
              handleQuery() // 刷新商品列表
              ElMessage.success(`购买商品 ${row.goodName} 成功`)
          })
      })
  }).catch(() => {
      ElMessageBox.alert('已取消购买操作')
  })
}

const handleQuery = () => {
  // 处理查询逻辑
  console.log('查询参数:', queryParams)
  loading.value = true
  goodList.value = []
  getGoodList(queryParams).then(response => {
    // 假设 response.data 是商品列表
    goodList.value = response.rows
    for(let i = 0; i < goodList.value.length; i++) {
      goodList.value[i].quantity = 1 // 初始化数量为1
    }
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
// 选中的分类
const selectedCategories = ref([])

// `el-cascader` 配置项，定义label、value和children的属性
const cascaderProps = {
  label: 'categoryName',
  value: 'categoryId',
  children: 'children',
}

const handleSelectionChange = (row) => {
    selectedRows.value = row
}

const handlePageSelectionChange = (page) => {
  queryParams.pageNum = page
  handleQuery() // 刷新商品列表
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
})

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
