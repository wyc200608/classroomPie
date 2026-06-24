<template>
  <div class="teacher-detail">
    <Header currentPage="index" />
    
    <div class="content">
      <a-row :gutter="[0, 20]">
        <a-col :span="18" :offset="3">
          <!-- 课程详情头部 -->
          <a-card class="course-header">
            <a-row>
              <a-col :span="4">
                <div class="avatar-container">
                  <a-avatar :size="80" :src="courseAvatar">
                    <template #icon><UserOutlined /></template>
                  </a-avatar>
                </div>
              </a-col>
              <a-col :span="12" class="course-info">
                <h2 class="course-name">{{ course.cname }}</h2>
                <div class="course-meta">
                  <span class="invite-code">邀请码: {{ course.invite }}</span>
                  <span class="student-count">学生人数: {{ course.studentCount || 0 }}</span>
                </div>
                <div class="course-desc">{{ course.cdescribe }}</div>
              </a-col>
              <a-col :span="8" class="course-actions">
                <a-row justify="end">
                  <a-col style="margin-bottom: 10px">
                    <a-button type="primary" @click="goToCourse">进入课程</a-button>
                  </a-col>
                  <a-col>
                    <a-space>
                      <a-button @click="editCourse">编辑课程</a-button>
                      <a-button danger @click="deleteCourse">删除课程</a-button>
                    </a-space>
                  </a-col>
                </a-row>
              </a-col>
            </a-row>
          </a-card>
          
          <!-- 课程学生列表 -->
          <a-card class="course-students" title="学生列表">
            <a-table
              :columns="studentColumns"
              :data-source="students"
              :pagination="false"
              row-key="phone"
              size="middle"
            />
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import Header from '@/components/common/Header.vue'
import { UserOutlined } from '@ant-design/icons-vue'
import axios from '@/utils/axios'
import { message } from 'ant-design-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const course = ref({
  cname: '',
  invite: '',
  cdescribe: '',
  studentCount: 0
})
const courseAvatar = ref('')
const students = ref([])
const studentColumns = [
  {
    title: '姓名',
    dataIndex: 'name',
    key: 'name'
  },
  {
    title: '学号',
    dataIndex: 'studentNumber',
    key: 'studentNumber'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email'
  },
  {
    title: '操作',
    key: 'action',
    width: 150,
    slots: { customRender: 'action' }
  }
]

onMounted(async () => {
  if (userStore.identify !== 'teacher' || !userStore.id) {
    router.push('/teacher/login')
    return
  }
  
  try {
    // 获取课程详情
    const response = await axios.post('/course/getCourse', {
      cid: route.query.id
    })
    course.value = response.data
    
    // 获取教师头像
    if (response.data.tphone) {
      const avatarResponse = await axios.post('/teacher/getHeaderImg', {
        phone: response.data.tphone
      })
      if (avatarResponse.image) {
        courseAvatar.value = 'data:image/png;base64,' + avatarResponse.image
      }
    }
    
    // 获取学生列表
    const studentsResponse = await axios.post('/course/getStudents', {
      cid: route.query.id
    })
    students.value = studentsResponse.data || []
  } catch (error) {
    console.error('获取课程详情失败:', error)
  }
})

const goToCourse = () => {
  router.push(`/teacher/detail?id=${route.query.id}`)
}

const editCourse = () => {
  // 编辑课程逻辑
  message.info('编辑课程功能待实现')
}

const deleteCourse = async () => {
  try {
    const response = await axios.post('/course/deleteCourse', {
      cid: route.query.id
    })
    if (response.data === 'success') {
      router.push('/teacher')
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
.teacher-detail {
  min-height: 100vh;
}

.content {
  padding: 20px 0;
}

.course-header {
  padding: 20px;
}

.avatar-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.course-info {
  padding-left: 20px;
}

.course-name {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.course-meta {
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin-bottom: 10px;
}

.invite-code, .student-count {
  font-size: 14px;
  color: #8c8c8c;
}

.course-desc {
  font-size: 14px;
  color: #5f6368;
}

.course-actions {
  display: flex;
  align-items: center;
}

.course-students {
  min-height: 200px;
}
</style>