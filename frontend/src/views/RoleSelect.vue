<template>
  <div class="role-container">
    <a-card class="role-card">
      <template #title>
        <div class="role-title">课堂派登录</div>
      </template>
      <div class="role-content">
        <a-button
          type="primary"
          size="large"
          block
          class="role-button student"
          @click="goTo('student')"
        >
          学生登录
        </a-button>
        <a-button
          type="primary"
          size="large"
          block
          class="role-button teacher"
          @click="goTo('teacher')"
        >
          教师登录
        </a-button>
      </div>
    </a-card>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goTo = (role) => {
  router.push(`/${role}/login`)
}
</script>

<style scoped>
.role-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.role-card {
  width: 360px;
  padding: 24px;
}

.role-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #1677ff;
}

.role-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.role-button.student {
  background: #1677ff;
}

.role-button.teacher {
  background: #52c41a;
}
</style>
