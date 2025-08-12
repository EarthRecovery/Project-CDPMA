<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button type="primary" plain size="small" @click="handleQuery">
        <search />
        查询
      </el-button>
    </el-col>
  </el-row>

  <div ref="chartRef" style="width: 100%; height: 400px;"></div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { timeCostStat, timeCostStatRefresh } from '@/api/stat'
import * as echarts from 'echarts'

// 定义响应式数据，存放接口返回的对象数据
const rawData = ref([])

const chartRef = ref(null)
let chartInstance = null

// 初始化图表
const initChart = () => {
  if (!chartRef.value) return
  if (chartInstance) {
    chartInstance.dispose()
  }
  chartInstance = echarts.init(chartRef.value)

  // 将对象转换为数组形式用于echarts
  const titles = rawData.value.map(item => item.title)
  const counts = rawData.value.map(item => item.count)

  const option = {
    title: {
      text: '操作统计横向柱状图',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }
    },
    grid: {
      left: '15%',
      right: '5%',
      bottom: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      name: '平均时间',
      axisLine: { show: true }
    },
    yAxis: {
      type: 'category',
      data: titles,
      inverse: true,
      axisTick: { show: false },
      axisLine: { show: true },
      name: '',
      nameLocation: 'middle',
      nameGap: 40
    },
    series: [{
      name: '平均时间',
      type: 'bar',
      data: counts,
      itemStyle: {
        color: '#5470C6'
      },
      barCategoryGap: '40%'
    }]
  }
  chartInstance.setOption(option)
}

// 监听窗口大小变化，自适应图表大小
const resizeChart = () => {
  if (chartInstance) {
    chartInstance.resize()
  }
}

// 点击查询按钮触发，调用刷新接口并更新数据
const handleQuery = () => {
  timeCostStatRefresh().then(response => {
    if (response.code === 200 && response.data) {
      rawData.value.splice(0, rawData.value.length, ...Object.entries(response.data).map(([title, count]) => ({ title, count })))
      initChart()
    } else {
      console.error('刷新数据失败:', response.msg)
    }
  }).catch(error => {
    console.error('刷新数据请求失败:', error)
  })
}


onMounted(() => {
  timeCostStat().then(response => {
    if (response.code === 200 && response.data) {
      rawData.value.splice(0, rawData.value.length, ...Object.entries(response.data).map(([title, count]) => ({ title, count })))
      console.log('获取到的统计数据:', rawData.value)
      initChart()
    } else {
      console.error('获取数据失败:', response.msg)
    }
  }).catch(error => {
    console.error('请求失败:', error)
  })

  window.addEventListener('resize', resizeChart)
})


onBeforeUnmount(() => {
  window.removeEventListener('resize', resizeChart)
  if (chartInstance) {
    chartInstance.dispose()
  }
})
</script>
