<template>
  <div class="course-module">
    <div class="course-module-header">
      <div class="course-module-header-left">
        <span class="course-name" @click="goToDetail">{{ item.cname }}</span>
        <span class="invite-code">邀请码: {{ item.invite }}</span>
      </div>
      <span class="course-date">{{ item.cdate }}</span>
    </div>
    <ul class="course-module-body">
      <li><span class="label">学生人数</span></li>
      <li>{{ item.studentCount || 0 }}</li>
      <li><span class="label">最近作业</span></li>
      <li>{{ item.work || '暂无作业' }}</li>
    </ul>
    <div class="course-module-footer">
      <a-row justify="space-between" style="width: 100%">
        <a-col>
          <a-avatar :size="24" :src="teacherAvatar">
            <template #icon><UserOutlined /></template>
          </a-avatar>
          <span style="margin-left: 8px">{{ item.tname }}</span>
        </a-col>
        <a-col>
          <a-space>
            <a-button type="link" @click="goToDetail">管理</a-button>
            <a-button type="link" @click="archiveCourse">归档</a-button>
            <a-button type="link" @click="deleteCourse">删除</a-button>
          </a-space>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import axios from '@/utils/axios'
import { UserOutlined } from '@ant-design/icons-vue'

const props = defineProps({
  item: {
    type: Object,
    required: true
  }
})

const router = useRouter()
const teacherAvatar = ref('')

onMounted(async () => {
  if (props.item.tphone) {
    try {
      const response = await axios.post('/teacher/getHeaderImg', {
        phone: props.item.tphone
      })
      if (response.image) {
        teacherAvatar.value = 'data:image/png;base64,' + response.image
      }
    } catch (error) {
      console.error('获取教师头像失败:', error)
    }
  }
})

const goToDetail = () => {
  router.push(`/teacher/detail?id=${props.item.cid}`)
}

const archiveCourse = async () => {
  try {
    const response = await axios.post('/course/archiveCourse', {
      cid: props.item.cid,
      archive: 1
    })
    if (response.data === 'success') {
      message.success('归档成功')
      router.go(0)
    } else {
      message.error('归档失败')
    }
  } catch (error) {
    console.error('归档失败:', error)
    message.error('归档失败')
  }
}

const deleteCourse = async () => {
  try {
    const response = await axios.post('/course/deleteCourse', {
      cid: props.item.cid
    })
    if (response.data === 'success') {
      message.success('删除成功')
      router.go(0)
    } else {
      message.error('删除失败')
    }
  } catch (error) {
    console.error('删除失败:', error)
    message.error('删除失败')
  }
}
</script>

<style scoped>
.course-module {
  width: 100%;
  height: 200px;
  background: linear-gradient(45deg, #f0f0f0, #e0e0e0);
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
}

.course-module:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.course-module-header {
  padding: 16px;
  background-color: #1677ff;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.course-module-header-left {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.course-name {
  font-size: 20px;
  color: white;
  cursor: pointer;
}

.course-name:hover {
  text-decoration: underline;
}

.invite-code {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.9);
}

.course-date {
  color: rgba(255, 255, 255, 0.9);
  font-size: 12px;
}

.course-module-body {
  flex: 1;
  padding: 12px 16px;
  list-style: none;
  overflow-y: auto;
  margin: 0;
}

.course-module-body li {
  margin-bottom: 8px;
  font-size: 12px;
}

.course-module-body li .label {
  color: #5f6368;
}

.course-module-body li:last-child {
  font-weight: bold;
  color: #333;
  margin-bottom: 0;
}

.course-module-footer {
  padding: 12px 16px;
  background-color: white;
  border-top: 1px solid #f0f0f0;
}

a-link {
  cursor: pointer;
}
</style>