<template>
  <div class="login-container">
    <a-card class="login-card">
      <template #title>
        <div class="login-title">教师登录</div>
      </template>
      <a-form @finish="handleSubmit" :model="form" name="loginForm">
        <a-form-item
          name="phone"
          :rules="[{ required: true, message: '请输入手机号', pattern: /^1[3-9]\d{9}$/, trigger: 'blur' }]"
        >
          <a-input
            v-model:value="form.phone"
            placeholder="手机号"
            size="large"
          >
            <template #prefix>
              <UserOutlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          name="password"
          :rules="[{ required: true, message: '请输入密码', min: 6, trigger: 'blur' }]"
        >
          <a-input-password
            v-model:value="form.password"
            placeholder="密码"
            size="large"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            block
            size="large"
            :loading="loading"
          >
            登录
          </a-button>
        </a-form-item>
        
        <div class="login-tips">
          <p>账号: 13800138000 | 密码: 123456</p>
        </div>
      </a-form>
    </a-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import axios from '@/utils/axios'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const form = reactive({
  phone: '',
  password: ''
})

const handleSubmit = async (values) => {
  loading.value = true
  try {
    const response = await axios.post('/teacher/login', {
      phone: values.phone,
      password: values.password
    })
    
    if (response.data) {
      userStore.setUser('teacher', values.phone)
      router.push({ name: 'teacher-index' })
    }
  } catch (error) {
    console.error('登录失败:', error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  padding: 24px;
}

.login-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #1677ff;
}
</style>