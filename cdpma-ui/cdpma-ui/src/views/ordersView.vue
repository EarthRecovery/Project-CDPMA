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
      <el-form-item label="goodName" prop="goodName">
        <el-input
          v-model="queryParams.goodName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="isPaid" prop="isPaid">
        <el-radio-group v-model="queryParams.isPaid">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="isCancelled" prop="isCancelled">
        <el-radio-group v-model="queryParams.isCancelled">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
        </el-radio-group>
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

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品名" align="center" prop="goodName" :show-overflow-tooltip="true" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="总金额" align="center" width="120">
        <template #default="{ row }">
            {{ (row.unitPrice * row.quantity).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="订单时间" align="center" prop="orderTime" :formatter="formatDateTime"/>
      <el-table-column label="支付状态" align="center" prop="paid" />
      <el-table-column label="取消状态" align="center" prop="cancelled" />
      <el-table-column label="操作" align="center" width="200">
        <template #default="{ row }">
          <el-button size="small" type="text" @click="handlePay(row)" v-if="row.paid == false">
          <edit />
          支付
          </el-button>
          <el-button size="small" type="text"  @click="showDetail(row)">
          <delete />  
          详情
          </el-button>
          <el-button size="small" type="text"  @click="handleCancel(row) " v-if="row.paid == false && row.cancelled == false">
          <delete />  
          取消
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

    <el-dialog :title="detailTitle" v-model="showOn" width="780px">
        <el-form :model="form" label-width="80px" ref="ShowformRef">
            <!-- 第一行 -->
            <el-row :gutter="20">
            <el-col :span="8">
                <el-form-item label="订单ID">
                <span>{{ form.goodId }}</span>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="商品名">
                <span>{{ form.goodName }}</span>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="数量">
                <span>{{ form.quantity }}</span>
                </el-form-item>
            </el-col>
            </el-row>

            <!-- 第二行 -->
            <el-row :gutter="20">
            <el-col :span="8">
                <el-form-item label="单价">
                <span>{{ form.unitPrice }}</span>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="总金额">
                <span>{{ form.totalPrice }}</span>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="下单时间">
                <span>{{ form.orderTime }}</span>
                </el-form-item>
            </el-col>
            </el-row>

            <!-- 第三行 -->
            <el-row :gutter="20">
            <el-col :span="8">
                <el-form-item label="支付状态">
                <span>{{ form.paid ? '已支付' : '未支付' }}</span>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="取消状态">
                <span>{{ form.cancelled ? '已取消' : '正常' }}</span>
                </el-form-item>
            </el-col>
            </el-row>

            <!-- 第四行 -->
            <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="反馈内容">
                <el-input v-model="form.feedback" placeholder="请输入反馈" />
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="反馈评分">
                    <el-input-number
                    v-model="form.feedbackScore"
                    :min="1"
                    :max="5"
                    :step="1"
                    controls-position="right"
                    placeholder="请输入评分"
                    />
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

  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { Edit, Delete, Search, Refresh } from '@element-plus/icons-vue'
import { getOrderList, payOrder, cancelOrder, editScore, editFeedBack } from '@/api/orders'
import { ElMessage, ElPagination , ElMessageBox } from 'element-plus'
import { useStore } from 'vuex'
import { formatDateTime } from '@/utils/time'

const queryParams = reactive({
  pageNum: 1,
  pageSize: 6,
  isPaid: false,
  isCancelled: false,
  goodName: null,
  operatorId: null,
})

const store = useStore()

const form = reactive({
  goodId: null,          // 订单ID
  goodName: '',          // 商品名
  quantity: 0,       // 数量
  unitPrice: 0,      // 单价
  totalPrice: 0,     // 总金额（数量 × 单价）
  orderTime: '',     // 下单时间
  paid: false,       // 支付状态
  cancelled: false,  // 取消状态
  feedback: '',      // 反馈内容
  feedbackScore: ''  // 反馈分数
})

const ShowformRef = ref(null)
const showOn = ref(false)

const selectedRows = ref([])
const orderList = ref([])

const queryForm = ref(null)
const showSearch = ref(true)
const total = ref(0)

const loading = ref(false)

const handlePageSelectionChange = (page) => {
  queryParams.pageNum = page
  handleQuery()
}


const handleSelectionChange = (row) => {
    selectedRows.value = row
}

const handlePay = (row) => {
  ElMessageBox.confirm(`确认支付订单: ${row.goodName} 吗?`, '支付确认', {
    confirmButtonText: '支付',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    payOrder(row.orderId).then(() => {
      ElMessage.success(`支付成功: ${row.goodName}`)
      handleQuery()
    }).catch(error => {
      ElMessage.error('支付失败: ' + error.message)
    })
  }).catch(() => {
    ElMessage.info('已取消支付')
  })
}

const handleCancel = (row) => {
  ElMessageBox.confirm(`确认取消订单: ${row.goodName} 吗?`, '取消确认', {
    confirmButtonText: '取消',
    cancelButtonText: '不取消',
    type: 'warning'
  }).then(() => {
    cancelOrder(row.orderId).then(() => {
      ElMessage.success(`订单已取消: ${row.goodName}`)
      handleQuery()
    }).catch(error => {
      ElMessage.error('取消失败: ' + error.message)
    })
  }).catch(() => {
    ElMessage.info('已取消取消操作')
  })
}

const showDetail = (row) =>{
    Object.assign(form, row)
    form.totalPrice = row.unitPrice * row.quantity // 计算总金额
    showOn.value = true
}

const editForm = () => {
    // 提交编辑表单
    ElMessageBox.confirm('确认提交编辑吗?', '编辑确认', {
        confirmButtonText: '提交',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        // 处理编辑逻辑
        if(!form.feedback || !form.feedbackScore) {
            ElMessage.error('反馈内容和评分不能为空')
            return
        }
        if(isNaN(form.feedbackScore) || form.feedbackScore < 0 || form.feedbackScore > 5) {
            ElMessage.error('反馈评分必须在0到5之间')
            return
        }
        if(form.feedback.length > 200) {
            ElMessage.error('反馈内容不能超过200字')
            return
        }
        editFeedBack(form.goodId, form.feedback).then(() => {
            showOn.value = false
            ElMessage.success('编辑成功')
            handleQuery() // 刷新订单列表
        }).catch(error => {
            ElMessage.error('编辑失败: ' + error.message)
        })
        editScore(form.goodId, form.feedbackScore).then(() => {
            showOn.value = false
            ElMessage.success('评分已更新')
            handleQuery() // 刷新订单列表
        }).catch(error => {
            ElMessage.error('评分更新失败: ' + error.message)
        })
        ElMessage.success('编辑成功')
    }).catch(() => {
        ElMessage.info('已取消编辑')
    })
}

const resetQuery = () => {
  queryParams.pageNum = 1
  queryParams.pageSize = 6
  queryParams.isPaid = false
  queryParams.isCancelled = false
  queryParams.goodName = null
}


const handleQuery = () => {
  // 处理查询逻辑
  loading.value = true
  orderList.value = []
  console.log('查询参数:', queryParams)
  queryParams.operatorId = store.state.operator.id
  getOrderList(queryParams).then(response => {
    orderList.value = response.rows
    total.value = response.total
    loading.value = false
  }).catch(error => {
    ElMessage.error('查询失败: ' + error.message)
    loading.value = false
  })
}

onMounted(() => {
    handleQuery()
})

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
