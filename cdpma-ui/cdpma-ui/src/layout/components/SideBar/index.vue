<template>
  <section class="side-bar">
    <el-row class="tac">
      <el-col :span="24">
        <el-menu
          active-text-color="#ffd04b"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          @select="handleSelect"
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>Admin</span>
            </template>
              <el-menu-item index="1-1">operator-admin</el-menu-item>
              <el-menu-item index="1-2">item two</el-menu-item>
              <el-menu-item index="1-3">item three</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="2">
            <el-icon><icon-menu /></el-icon>
            <span>Navigator Two</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <el-icon><document /></el-icon>
            <span>Navigator Three</span>
          </el-menu-item>
          <el-menu-item index="4">
            <el-icon><setting /></el-icon>
            <span>Navigator Four</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import { Document, Menu as IconMenu, Location, Setting } from '@element-plus/icons-vue'

export default {
  components: {
    Document,
    IconMenu,
    Location,
    Setting
  },
  methods: {
    handleSelect(key) {
      if(key === '1-1'){
        this.$router.push({ path: '/system-user/operator' })
      }
    }
  }
}
</script>

<style scoped lang="scss">

.side-bar {
  height: 100%;
}
</style>
