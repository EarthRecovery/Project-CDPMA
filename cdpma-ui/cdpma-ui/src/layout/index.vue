<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="mainBody">
    <div class="head">
      <head-bar/>
    </div>
    <div class="body">
      <side-bar/>
      <app-main/>
    </div>
  </div>
  
  
</template>

<script setup>
import { AppMain, HeadBar, SideBar } from './components'
</script>

<style lang="scss" scoped>
.mainBody {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.head {
  width: 100%;
}

.body {
  display: flex;
  flex-direction: row;
}
</style>