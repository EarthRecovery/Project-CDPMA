<template>
  <section class="head-bar">
    <el-menu
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    :ellipsis="false"
    @select="handleSelect"
  >
    <el-menu-item index="0">
        <h1>CDPMA</h1>
    </el-menu-item>
    
    <el-menu-item index="1">Processing Center</el-menu-item>
    <el-sub-menu index="2">
      <template #title>Basic Information</template>
      <el-menu-item index="2-1">name: {{ operatorName }}</el-menu-item>
      <el-menu-item index="2-2">id: {{ operatorId }}</el-menu-item>
      <el-menu-item index="2-3">role: {{ role }}</el-menu-item>
    </el-sub-menu>
  </el-menu>
  </section>
</template>

<script setup>
import store from '@/store'
import { computed } from 'vue'

const operatorName = computed(() => store.state.operator.operatorName)
const operatorId = computed(() => store.state.operator.id)
const role = computed(() => store.state.operator.role)
</script>

<style lang="scss" scoped>
.head-bar {
  background-color: #f5f5f5;

  .grid-content {
    height: 30px;
  }
}

.el-menu--horizontal > .el-menu-item:nth-child(1) {
  margin-right: auto;
}
</style>