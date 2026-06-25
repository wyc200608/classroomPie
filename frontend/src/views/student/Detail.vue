<template>
  <div class="student-detail">
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
                  <span class="teacher">{{ course.tname }}</span>
                  <span class="invite-code">邀请码: {{ course.invite }}</span>
                </div>
                <div class="course-desc">{{ course.cdescribe }}</div>
              </a-col>
              <a-col :span="8" class="course-actions">
                <a-row justify="end">
                  <a-col style="margin-bottom: 10px">
                    <a-button type="primary" @click="goToCourse">进入课程</a-button>
                  </a-col>
                  <a-col>
                    <a-button danger @click="leaveCourse">退课</a-button>
                  </a-col>
                </a-row>
              </a-col>
            </a-row>
          </a-card>
          
          <!-- 课程内容 -->
          <a-row :gutter="[20, 20]" style="margin-top: 20px">
            <a-col :span="12">
              <a-card title="课程公告" class="course-notice">
                <a-empty v-if="!course.notices || course.notices.length === 0" />
                <a-list v-else item-layout="horizontal" :data-source="course.notices">
                  <template #renderItem="{ item }">
                    <a-list-item>
                      <a-list-item-meta :title="item.title" :description="item.content" />
                      <template #extra>
                        <span class="notice-date">{{ item.date }}</span>
                      </template>
                    </a-list-item>
                  </template>
                </a-list>
              </a-card>
            </a-col>
            <a-col :span="12">
              <a-card title="最近作业" class="course-work">
                <a-empty v-if="!course.works || course.works.length === 0" />
                <a-list v-else item-layout="horizontal" :data-source="course.works">
                  <template #renderItem="{ item }">
                    <a-list-item>
                      <a-list-item-meta :title="item.title" :description="item.content" />
                      <template #extra>
                        <a-space>
                          <span class="work-date">{{ item.date }}</span>
                          <a-tag :color="item.status === '已完成' ? 'green' : 'orange'">
                            {{ item.status }}
                          </a-tag>
                        </a-space>
                      </template>
                    </a-list-item>
                  </template>
                </a-list>
              </a-card>
            </a-col>
          </a-row>
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
  tname: '',
  invite: '',
  cdescribe: '',
  notices: [],
  works: []
})
const courseAvatar = ref('')

onMounted(async () => {
  if (userStore.identify !== 'student' || !userStore.id) {
    router.push('/student/login')
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
  } catch (error) {
    console.error('获取课程详情失败:', error)
  }
})

const goToCourse = () => {
  // 进入课程内容页面
  router.push(`/student/detail?id=${route.query.id}`)
}

const leaveCourse = async () => {
  try {
    const response = await axios.post('/sc/deleteCourse', {
      scid: route.query.id
    })
    if (response.data === 'success') {
      router.push('/student')
    } else {
      message.error('退课失败')
    }
  } catch (error) {
    console.error('退课失败:', error)
    message.error('退课失败')
  }
}
</script>

<style scoped>
.student-detail {
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

.teacher {
  font-size: 16px;
  color: #5f6368;
}

.invite-code {
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

.course-notice, .course-work {
  min-height: 200px;
}

.notice-date {
  font-size: 12px;
  color: #8c8c8c;
}

.work-date {
  font-size: 12px;
  color: #8c8c8c;
}
</style>