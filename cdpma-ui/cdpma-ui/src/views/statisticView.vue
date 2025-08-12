<template>
  <div class="app-container">
    <ChinaMap />
    <TimeCost />
  </div>
</template>

<script setup>
import ChinaMap from '@/components/Stat/ChinaMap.vue'
import TimeCost from '@/components/Stat/TimeCost.vue'
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
