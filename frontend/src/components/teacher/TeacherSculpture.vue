<template>
  <div class="sculpture-container">
    <a-dropdown>
      <a-avatar :size="32" :src="avatar">
        <template #icon>
          <UserOutlined />
        </template>
      </a-avatar>
      <template #overlay>
        <a-menu>
          <a-menu-item>
            <router-link to="/teacher/personal">个人中心</router-link>
          </a-menu-item>
          <a-menu-divider />
          <a-menu-item @click="handleLogout">
            退出登录
          </a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { UserOutlined } from '@ant-design/icons-vue'
import axios from '@/utils/axios'

const router = useRouter()
const userStore = useUserStore()
const avatar = ref('')

onMounted(() => {
  getAvatar()
})

watch(() => userStore.id, () => {
  getAvatar()
})

const getAvatar = async () => {
  if (!userStore.id) return
  
  try {
    const url = userStore.identify === 'student' 
      ? '/student/getHeaderImg' 
      : '/teacher/getHeaderImg'
    
    const response = await axios.post(url, { phone: userStore.id })
    if (response.image) {
      avatar.value = 'data:image/png;base64,' + response.image
    }
  } catch (error) {
    console.error('获取头像失败:', error)
  }
}

const handleLogout = () => {
  userStore.clearUser()
  router.push(userStore.identify === 'student' ? '/student/login' : '/teacher/login')
}
</script>

<style scoped>
.sculpture-container {
  cursor: pointer;
}
</style>