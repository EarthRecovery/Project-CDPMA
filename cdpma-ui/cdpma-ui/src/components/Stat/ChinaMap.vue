<template>
  <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          size="small"
          @click="handleQuery"
        >
        <search />
        查询
        </el-button>
      </el-col>
    </el-row>

  <div ref="chartRef" style="width: 100%; height: 400px;"></div>
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref, getCurrentInstance } from 'vue'
import { operatorMapStat, refresh } from '@/api/stat'

// 省份示例数据
var data = [
  { name: '北京市', value: 100 },
  { name: '天津市', value: 80 },
  { name: '上海市', value: 120 },
  { name: '广东省', value: 300 },
  { name: '浙江省', value: 150 },
  { name: '江苏省', value: 180 },
  // 其他省份随意补充，演示用
]

const chartRef = ref(null)
let chart = null

// 获取全局echarts实例
const { appContext } = getCurrentInstance()
const echarts = appContext.config.globalProperties.$echarts

onMounted(async () => {
  await operatorMapStat().then(response => {
    console.log('获取到的统计数据:', response.data)
    data = transfer(response.data)
    chart = echarts.init(chartRef.value)
  })
  

  // 引入地图JSON（echarts 5+ 需要你自己注册地图）
  import('C:\\Users\\Yuxia\\Desktop\\CS-Project\\projectCDP-MA\\Code\\CDPMA\\cdpma-ui\\cdpma-ui\\src\\assets\\json\\china.json').then((mapData) => {
    echarts.registerMap('china', mapData.default)
    console.log('地图省份名称列表：', mapData.default.features.map(f => f.properties.name))

    const option = {
      title: {
        text: '用户省份分布',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b} : {c}'
      },
      visualMap: {
        min: 0,
        max: 300,
        left: 'left',
        top: 'bottom',
        text: ['高','低'],
        calculable: true,
        inRange: {
          color: ['#e0f3f8', '#abd9e9', '#74add1', '#4575b4', '#313695']
        }
      },
      series: [
        {
          name: '省份数据',
          type: 'map',
          map: 'china',
          roam: false,
          emphasis: {
            label: {
              show: true
            }
          },
          data
        }
      ]
    }

    chart.setOption(option)
  })
})

const englishToChineseMap = {
  BEIJING: "北京市",
  TIANJIN: "天津市",
  HEBEI: "河北省",
  SHANXI: "山西省",
  INNER_MONGOLIA: "内蒙古自治区",
  LIAONING: "辽宁省",
  JILIN: "吉林省",
  HEILONGJIANG: "黑龙江省",
  SHANGHAI: "上海市",
  JIANGSU: "江苏省",
  ZHEJIANG: "浙江省",
  ANHUI: "安徽省",
  FUJIAN: "福建省",
  JIANGXI: "江西省",
  SHANDONG: "山东省",
  HENAN: "河南省",
  HUBEI: "湖北省",
  HUNAN: "湖南省",
  GUANGDONG: "广东省",
  GUANGXI: "广西壮族自治区",
  HAINAN: "海南省",
  CHONGQING: "重庆市",
  SICHUAN: "四川省",
  GUIZHOU: "贵州省",
  YUNNAN: "云南省",
  TIBET: "西藏自治区",
  SHAANXI: "陕西省",
  GANSU: "甘肃省",
  QINGHAI: "青海省",
  NINGXIA: "宁夏回族自治区",
  XINJIANG: "新疆维吾尔自治区",
  TAIWAN: "台湾省",
  HONGKONG: "香港特别行政区",
  MACAU: "澳门特别行政区"
};

onBeforeUnmount(() => {
  if (chart) {
    chart.dispose()
  }
})

const handleQuery = () => {
  refresh().then(response => {
    console.log('刷新后的统计数据:', response.data)
    data = transfer(response.data)
  })
}

const transfer = (data) => {
    console.log('原始数据:', data);
    // 把对象转成数组： [{name: 'ANHUI', value: 11}, {name: 'BEIJING', value: 8}, ...]
    const arr = Object.entries(data).map(([key, value]) => ({
        name: key,
        value: value
    }));
    
    // 再用映射把英文名转成中文名
    const result = arr.map(item => {
        const englishName = item.name.toUpperCase();
        return {
        name: englishToChineseMap[englishName] || item.name,
        value: item.value
        };
    });
    
    return result;
}
</script>

<style scoped>
/* 你可以根据需要调整大小 */
</style>
