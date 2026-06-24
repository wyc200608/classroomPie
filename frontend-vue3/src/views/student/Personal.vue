<template>
  <div class="student-personal">
    <Header currentPage="index" />
    
    <div class="content">
      <a-row :gutter="[0, 20]">
        <a-col :span="18" :offset="3">
          <!-- 个人资料卡片 -->
          <a-card class="profile-card">
            <template #title>个人资料</template>
            <a-row :gutter="[40, 20]">
              <a-col :span="8" class="avatar-section">
                <a-avatar :size="100" :src="avatar">
                  <template #icon><UserOutlined /></template>
                </a-avatar>
                <div class="avatar-actions">
                  <a-button @click="showAvatarUpload = true">更换头像</a-button>
                </div>
              </a-col>
              <a-col :span="16" class="info-section">
                <a-form :model="form" layout="vertical">
                  <a-row :gutter="[20, 0]">
                    <a-col :span="12">
                      <a-form-item label="姓名" name="name">
                        <a-input v-model:value="form.name" placeholder="请输入姓名" />
                      </a-form-item>
                    </a-col>
                    <a-col :span="12">
                      <a-form-item label="手机号" name="phone">
                        <a-input v-model:value="form.phone" placeholder="手机号" disabled />
                      </a-form-item>
                    </a-col>
                  </a-row>
                  <a-row :gutter="[20, 0]">
                    <a-col :span="12">
                      <a-form-item label="邮箱" name="email">
                        <a-input v-model:value="form.email" placeholder="请输入邮箱" />
                      </a-form-item>
                    </a-col>
                    <a-col :span="12">
                      <a-form-item label="专业" name="major">
                        <a-input v-model:value="form.major" placeholder="请输入专业" />
                      </a-form-item>
                    </a-col>
                  </a-row>
                  <a-row :gutter="[20, 0]">
                    <a-col :span="12">
                      <a-form-item label="年级" name="grade">
                        <a-select v-model:value="form.grade" placeholder="请选择年级">
                          <a-select-option value="大一">大一</a-select-option>
                          <a-select-option value="大二">大二</a-select-option>
                          <a-select-option value="大三">大三</a-select-option>
                          <a-select-option value="大四">大四</a-select-option>
                        </a-select>
                      </a-form-item>
                    </a-col>
                    <a-col :span="12">
                      <a-form-item label="班级" name="class">
                        <a-input v-model:value="form.class" placeholder="请输入班级" />
                      </a-form-item>
                    </a-col>
                  </a-row>
                  <a-row>
                    <a-col>
                      <a-button type="primary" @click="handleUpdate">保存修改</a-button>
                      <a-button style="margin-left: 10px" @click="handleReset">重置</a-button>
                    </a-col>
                  </a-row>
                </a-form>
              </a-col>
            </a-row>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import Header from '@/components/common/Header.vue'
import { UserOutlined } from '@ant-design/icons-vue'
import axios from '@/utils/axios'
import { message } from 'ant-design-vue'

const router = useRouter()
const userStore = useUserStore()
const avatar = ref('')
const form = ref({
  name: '',
  phone: '',
  email: '',
  major: '',
  grade: '',
  class: ''
})

onMounted(async () => {
  if (userStore.identify !== 'student' || !userStore.id) {
    router.push('/student/login')
    return
  }
  
  try {
    // 获取学生信息
    const response = await axios.post('/student/getStudent', {
      phone: userStore.id
    })
    if (response.data) {
      form.value = {
        name: response.data.name,
        phone: response.data.phone,
        email: response.data.email || '',
        major: response.data.major || '',
        grade: response.data.grade || '',
        class: response.data.class || ''
      }
    }
    
    // 获取头像
    const avatarResponse = await axios.post('/student/getHeaderImg', {
      phone: userStore.id
    })
    if (avatarResponse.image) {
      avatar.value = 'data:image/png;base64,' + avatarResponse.image
    }
  } catch (error) {
    console.error('获取个人信息失败:', error)
  }
})

const handleUpdate = async () => {
  try {
    const response = await axios.post('/student/updateStudent', {
      phone: userStore.id,
      ...form.value
    })
    if (response.data === 'success') {
      message.success('更新成功')
    } else {
      message.error('更新失败')
    }
  } catch (error) {
    console.error('更新失败:', error)
    message.error('更新失败')
  }
}

const handleReset = () => {
  // 重置表单逻辑
}
</script>

<style scoped>
.student-personal {
  min-height: 100vh;
}

.content {
  padding: 20px 0;
}

.profile-card {
  padding: 20px;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.avatar-actions {
  margin-top: 10px;
}

.info-section {
  padding-left: 20px;
}
</style>