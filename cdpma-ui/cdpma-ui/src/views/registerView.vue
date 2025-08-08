<template>
  <div class="login">
    <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="registerRules"
        class="login-form"
        >
        <h3 class="title">{{ title }}</h3>

        <el-form-item prop="operatorName">
            <el-input
            v-model="registerForm.operatorName"
            placeholder="姓名"
            autocomplete="off"
            />
        </el-form-item>

        <el-form-item prop="operatorNickname">
            <el-input
            v-model="registerForm.operatorNickname"
            placeholder="昵称"
            autocomplete="off"
            />
        </el-form-item>

        <el-form-item prop="operatorIdCard">
            <el-input
            v-model="registerForm.operatorIdCard"
            placeholder="身份证号"
            autocomplete="off"
            />
        </el-form-item>

        <el-form-item prop="operatorPhone">
            <el-input
            v-model="registerForm.operatorPhone"
            placeholder="手机号"
            autocomplete="off"
            />
        </el-form-item>

        <el-form-item prop="operatorEmail">
            <el-input
            v-model="registerForm.operatorEmail"
            placeholder="邮箱"
            autocomplete="off"
            />
        </el-form-item>

        <el-form-item prop="operatorGender">
            <el-select v-model="registerForm.operatorGender" placeholder="性别">
            <el-option label="男" value="M" />
            <el-option label="女" value="F" />
            <el-option label="其他" value="O" />
            </el-select>
        </el-form-item>

        <el-form-item prop="operatorAge">
            <el-input
            v-model="registerForm.operatorAge"
            placeholder="年龄"
            autocomplete="off"
            type="number"
            />
        </el-form-item>

        <el-form-item prop="operatorRole">
            <el-select v-model="registerForm.operatorRole" placeholder="角色">
            <el-option label="管理员" value="ADMIN" />
            <el-option label="用户" value="USER" />
            <el-option label="导购" value="ASSISTANT" />
            </el-select>
        </el-form-item>

        <el-form-item prop="operatorRegion">
            <el-select
                v-model="registerForm.operatorRegion"
                placeholder="请选择地区"
                filterable
                clearable
            >
                <el-option
                v-for="item in regionOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                />
            </el-select>
        </el-form-item>

        <el-form-item prop="passwordHash">
            <el-input
            v-model="registerForm.passwordHash"
            placeholder="密码"
            autocomplete="off"
            />
        </el-form-item>

        <el-form-item style="width: 100%;">
            <el-button
            :loading="loading"
            size="default"
            type="primary"
            style="width: 100%;"
            @click.prevent="handleRegister"
            >
            <span v-if="!loading">注 册</span>
            <span v-else>注 册 中...</span>
            </el-button>
            <div style="float: right;">
            <router-link class="link-type" to="/login">返回登录</router-link>
            </div>
        </el-form-item>
        </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { register } from '@/api/operator'
import { ElMessage } from 'element-plus'
 
// 1. 初始化 store 和 router
const router = useRouter()
const route = useRoute()

// 2. 注册表单数据
const registerForm = reactive({
  operatorName: '',
  operatorNickname: '',
  operatorIdCard: '',
  operatorPhone: '',
  operatorGender: '',
  operatorAge:'',
  operatorEmail: '',
  operatorRole: '',
  operatorRegion: '',
  passwordHash: ''
})

// 3. 其他状态
const title = ref('CDP-MA注册')
const loading = ref(false)
const redirect = ref(undefined)

// 4. 表单引用
const registerFormRef = ref(null)

// 5. 地区列表
const regionOptions = [
  { label: '北京', value: 'BEIJING' },
  { label: '天津', value: 'TIANJIN' },
  { label: '河北', value: 'HEBEI' },
  { label: '山西', value: 'SHANXI' },
  { label: '内蒙古', value: 'INNER_MONGOLIA' },
  { label: '辽宁', value: 'LIAONING' },
  { label: '吉林', value: 'JILIN' },
  { label: '黑龙江', value: 'HEILONGJIANG' },
  { label: '上海', value: 'SHANGHAI' },
  { label: '江苏', value: 'JIANGSU' },
  { label: '浙江', value: 'ZHEJIANG' },
  { label: '安徽', value: 'ANHUI' },
  { label: '福建', value: 'FUJIAN' },
  { label: '江西', value: 'JIANGXI' },
  { label: '山东', value: 'SHANDONG' },
  { label: '河南', value: 'HENAN' },
  { label: '湖北', value: 'HUBEI' },
  { label: '湖南', value: 'HUNAN' },
  { label: '广东', value: 'GUANGDONG' },
  { label: '广西', value: 'GUANGXI' },
  { label: '海南', value: 'HAINAN' },
  { label: '重庆', value: 'CHONGQING' },
  { label: '四川', value: 'SICHUAN' },
  { label: '贵州', value: 'GUIZHOU' },
  { label: '云南', value: 'YUNNAN' },
  { label: '西藏', value: 'TIBET' },
  { label: '陕西', value: 'SHAANXI' },
  { label: '甘肃', value: 'GANSU' },
  { label: '青海', value: 'QINGHAI' },
  { label: '宁夏', value: 'NINGXIA' },
  { label: '新疆', value: 'XINJIANG' },
  { label: '台湾', value: 'TAIWAN' },
  { label: '香港', value: 'HONGKONG' },
  { label: '澳门', value: 'MACAU' }
]



// 6. 校验规则
const registerRules = {
  operatorName: [
    { required: true, message: '操作员姓名不能为空', trigger: 'blur' },
    { max: 60, message: '姓名长度不能超过60个字符', trigger: 'blur' }
  ],
  operatorNickname: [
    { max: 60, message: '昵称长度不能超过60个字符', trigger: 'blur' }
  ],
  operatorIdCard: [
    { required: true, message: '身份证号不能为空', trigger: 'blur' },
    {
      pattern: /^[1-9]\d{5}(18|19|20)?\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/,
      message: '身份证号格式不正确',
      trigger: 'blur'
    }
  ],
  operatorPhone: [
    { required: true, message: '电话不能为空', trigger: 'blur' },
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '电话格式不正确',
      trigger: 'blur'
    }
  ],
  operatorGender: [
    { required: true, message: '性别不能为空', trigger: 'change' },
    {
      pattern: /^[MFO]$/,
      message: '性别必须为 M（男）, F（女）或 O（其他）',
      trigger: 'change'
    }
  ],
  operatorAge: [
    { required: true, message: '年龄不能为空', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (!Number.isInteger(Number(value))) {
          callback(new Error('年龄必须是数字'))
        } else if (value > 200) {
          callback(new Error('年龄不能大于200岁'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  operatorEmail: [
    { required: true, message: '邮箱不能为空', trigger: 'blur' },
    {
      type: 'email',
      message: '邮箱格式不正确',
      trigger: 'blur'
    }
  ],
  operatorRole: [
    { required: true, message: '角色不能为空', trigger: 'blur' }
  ],
  operatorRegion: [
    { required: true, message: '所属大区不能为空', trigger: 'blur' }
  ],
  passwordHash: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    {
      pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/,
      message: '密码必须包含大小写字母、数字和特殊字符，且长度不少于8位',
      trigger: 'blur'
    }
  ]
}

// 8. 监听路由变化
watch(
  () => route.query.redirect,
  (val) => {
    redirect.value = val
  },
  { immediate: true }
)

// 10. 注册处理
const handleRegister = () => {
  registerFormRef.value.validate((valid) => {
    if (valid) {
      register(registerForm).then(response => {
        if (response.code === 200) {
          ElMessage.success('注册成功')
          loading.value = false
          router.push({ path: '/login', query: { redirect: redirect.value || '/' } })
        } else {
          ElMessage.error('注册失败: ' + response.message)
        }
      }).catch(error => {
        ElMessage.error('注册失败: ' + error.message)
      })
    }
  })
}
</script>


<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  /* 建议用相对路径或webpack别名 */
  background-image: url("@/assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}
.login-form {
  border-radius: 6px;
  background: #fff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  z-index: 1;

  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
</style>
