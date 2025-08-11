<template>
  <div class="app-container">
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

    <ChinaMap />
  </div>
</template>

<script setup>
import ChinaMap from '@/components/Stat/ChinaMap.vue'
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
