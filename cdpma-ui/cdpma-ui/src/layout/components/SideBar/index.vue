<template>
  <section class="side-bar" v-if="isRoleLoaded">
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
          <!-- Admin -->
          <el-sub-menu index="1" v-if="isAdminVisible">
            <template #title>
              <el-icon><Location /></el-icon>
              <span>Admin</span>
            </template>
            <el-menu-item index="1-1">operator-admin</el-menu-item>
            <el-menu-item index="1-2">good-admin</el-menu-item>
            <el-menu-item index="1-3">runtime-log</el-menu-item>
            <el-menu-item index="1-4">login/logout-log</el-menu-item>
          </el-sub-menu>

          <!-- Assistant -->
          <el-sub-menu index="2" v-if="isAssistantVisible">
            <template #title>
              <el-icon><IconMenu /></el-icon>
              <span>Assistant</span>
            </template>
            <el-menu-item index="2-1">item one</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
          </el-sub-menu>

          <!-- User -->
          <el-sub-menu index="3" v-if="isUserVisible">
            <template #title>
              <el-icon><IconMenu /></el-icon>
              <span>User</span>
            </template>
            <el-menu-item index="3-1">Good</el-menu-item>
            <el-menu-item index="3-2">Favorite</el-menu-item>
            <el-menu-item index="3-3">item three</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </el-row>
  </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { Menu as IconMenu, Location} from '@element-plus/icons-vue'

const router = useRouter()

// 控制菜单可见性
const isAdminVisible = ref(true)
const isAssistantVisible = ref(true)
const isUserVisible = ref(true)
const isRoleLoaded = ref(false)

const store = useStore()

onMounted(() => {
  // 获取用户角色信息
  const userRole = computed(() => store.state.operator.role)
  console.log('User Role:', userRole.value)
  console.log('User Role:', store.state.operator)

  // 根据角色设置菜单可见性
  if (userRole.value == 'ADMIN') {
    isAdminVisible.value = true
    isAssistantVisible.value = true
    isUserVisible.value = true
  } else if (userRole.value == 'ASSISTANT') {
    isAdminVisible.value = false
    isAssistantVisible.value = true
    isUserVisible.value = false
  } else if (userRole.value == 'USER') {
    isAdminVisible.value = false
    isAssistantVisible.value = false
    isUserVisible.value = true
  }else{
    isAdminVisible.value = false
    isAssistantVisible.value = false
    isUserVisible.value = false
  }
  isRoleLoaded.value = true
})

const handleSelect = (key) => {
  if (key === '1-1') {
    router.push({ path: '/admin/operator' })
  }
  else if (key === '1-2'){
    router.push({ path: '/admin/good' })
  }
  else if (key === '1-3'){
    router.push({ path: '/admin/runtimeLog' })
  }
  else if (key === '1-4'){
    router.push({ path: '/admin/loginLog' })
  }
  else if (key === '3-1'){
    router.push({ path: '/user/good' })
  }
  else if (key === '3-2'){
    router.push({ path: '/user/favorite' })
  }
}
</script>

<style scoped lang="scss">
.side-bar {
  height: 100%;
  background-color: #545c64;
}
</style>
