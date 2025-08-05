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
    
    <el-sub-menu index="1">
      <template #title>
        <span>Processing Center</span>
      </template>
      <el-menu-item index="1-1" @click="handleLogout">logout</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="2" @click="showInfo"> Basic Information</el-menu-item>
  </el-menu>
  </section>
  <el-dialog :title="title" v-model="infoCardVisible" width="780px" append-to-body>
    <div>
      <p><strong>Operator Name:</strong> {{ operatorName }}</p>
      <p><strong>Operator ID:</strong> {{ operatorId }}</p>
      <p><strong>Nick Name:</strong> {{ operatorNickName }}</p>
      <p><strong>Phone Number:</strong> {{ operatorPhone }}</p>
      <p><strong>Email:</strong> {{ operatorEmail }}</p>
      <p><strong>Region:</strong> {{ operatorRegion }}</p>
      <p><strong>ID Card:</strong> {{ operatorIdCard }}</p>
      <p><strong>Gender:</strong> {{ operatorGender }}</p>
      <p><strong>Age:</strong> {{ operatorAge }}</p>
      <p><strong>Role:</strong> {{ operatorRole }}</p>
      <div class="user-only" v-if="operatorRole === 'USER'">
        <p><strong>Account Balance:</strong> {{ userAccount }}</p>
        <p><strong>Points:</strong> {{ points }}</p>
        <p><strong>Coupons:</strong> {{ coupons }}</p>
        <p><strong>Orders:</strong> {{ orders }}</p>
        <p><strong>User Level:</strong> {{ userLevel }}</p>
        <p><strong>RFM:</strong> {{ rfm }}</p>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import store from '@/store'
import { computed } from 'vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { logout } from '@/api/login'
import { removeToken } from '@/utils/auth'

const operatorId = computed(() => store.state.operator.id)
const operatorName = computed(() => store.state.operator.operatorName)
const operatorNickName = computed(() => store.state.operator.operatorNickname)
const operatorPhone = computed(() => store.state.operator.phoneNumber)
const operatorEmail = computed(() => store.state.operator.email)
const operatorRegion = computed(() => store.state.operator.region)
const operatorIdCard = computed(() => store.state.operator.idCard)
const operatorGender = computed(() => store.state.operator.gender)
const operatorAge = computed(() => store.state.operator.age)
const operatorRole = computed(() => store.state.operator.role)

console.log(computed(() => store.state.user))

const userAccount = computed(() => {
  return operatorRole.value === 'USER' ? store.state.user.accountBalance : null
})
const points = computed(() => {
  return operatorRole.value === 'USER' ? store.state.user.points : null
})
const coupons = computed(() => {
  return operatorRole.value === 'USER' ? store.state.user.coupons : null
})
const orders = computed(() => {
  return operatorRole.value === 'USER' ? store.state.user.orders : null
})
const userLevel = computed(() => {
  return operatorRole.value === 'USER' ? store.state.user.userLevel : null
})
const rfm = computed(() => {
  return operatorRole.value === 'USER' ? store.state.user.rfm : null
})

const title = ref('Operator Information')


const infoCardVisible = ref(false)

const showInfo = () => {
  // 显示操作员信息
  infoCardVisible.value = true
}

const handleLogout = () => {
  // 处理登出逻辑
  logout().then(() => {
    removeToken()
    // 登出成功后的操作
    ElMessage({
      message: 'Logout successful',
      type: 'success'
    })
    window.location.href = '/login'
  })
}
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