<template>
  <div class="teacher-index">
    <a-affix :offset-top="0">
      <div class="header">
        <a-col :span="18" :offset="3">
          <a-row align="middle" class="header-row">
            <a-col :span="4">
              <div class="logo">CloudClass</div>
            </a-col>
            <a-col :span="10">
              <a-menu mode="horizontal" selected-keys="index">
                <a-menu-item key="index">
                  <router-link to="/teacher">首页</router-link>
                </a-menu-item>
                <a-menu-item key="discussion">
                  <router-link to="/teacher">讨论区</router-link>
                </a-menu-item>
              </a-menu>
            </a-col>
            <a-col :span="2" :offset="8">
              <TeacherSculpture />
            </a-col>
          </a-row>
        </a-col>
      </div>
    </a-affix>
    
    <div class="content">
      <a-row :gutter="[0, 20]">
        <a-col :span="18" :offset="3">
          <div class="ktcon">
            <a-row class="control-bar" justify="space-between">
              <a-col class="control-title">我的课程</a-col>
              <a-row justify="start" :gutter="[10, 0]" align="middle">
                <a-col><a>课程排序</a></a-col>
                <a-col>
                  <a-dropdown v-model:open="dropdownOpen">
                    <a-button type="primary">创建课程</a-button>
                    <template #overlay>
                      <a-menu>
                        <a-menu-item key="create">创建新课程</a-menu-item>
                      </a-menu>
                    </template>
                  </a-dropdown>
                </a-col>
                <a-col><a-button>快速发布活动</a-button></a-col>
              </a-row>
            </a-row>
            
            <a-row :gutter="[21, 30]" justify="start">
              <a-col v-for="course in courses" :key="course.cid" :span="8">
                <CourseModule :item="course" />
              </a-col>
              <a-col :span="8">
                <div class="course-add-btn">
                  <a-button type="dashed" size="large" @click="showCreateModal = true">
                    <template #icon><PlusOutlined /></template>
                    创建课程
                  </a-button>
                </div>
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-row>
    </div>

    <!-- 创建课程弹窗 -->
    <a-modal
      v-model:open="showCreateModal"
      title="创建课程"
      @ok="handleCreateCourse"
      :confirm-loading="createLoading"
    >
      <a-form layout="vertical">
        <a-form-item label="课程名称">
          <a-input v-model:value="courseName" placeholder="请输入课程名称" />
        </a-form-item>
        <a-form-item label="课程日期">
          <a-date-picker v-model:value="courseDate" format="YYYY-MM-DD" placeholder="请选择课程日期" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { useCourseStore } from '@/stores/courseStore'
import TeacherSculpture from '@/components/teacher/TeacherSculpture.vue'
import CourseModule from '@/components/teacher/TeacherCourseModule.vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'

const router = useRouter()
const userStore = useUserStore()
const courseStore = useCourseStore()
const courses = ref([])
const dropdownOpen = ref(false)
const showCreateModal = ref(false)
const createLoading = ref(false)
const courseName = ref('')
const courseDate = ref(null)

onMounted(async () => {
  if (userStore.identify !== 'teacher' || !userStore.id) {
    router.push('/teacher/login')
    return
  }
  
  try {
    await courseStore.getCourseByTeacher()
    courses.value = courseStore.coursesInfo
  } catch (error) {
    console.error('获取课程失败:', error)
  }
})

const handleCreateCourse = async () => {
  if (!courseName.value || !courseDate.value) {
    message.warning('请输入课程名称和日期')
    return
  }
  
  createLoading.value = true
  try {
    const result = await courseStore.createCourse(courseName.value, courseDate.value.format('YYYY-MM-DD'))
    if (result.msg === 'success') {
      message.success('创建成功')
      showCreateModal.value = false
      courseName.value = ''
      courseDate.value = null
      await courseStore.getCourseByTeacher()
      courses.value = courseStore.coursesInfo
    } else {
      message.error('创建失败')
    }
  } catch (error) {
    console.error('创建课程失败:', error)
    message.error('创建失败')
  } finally {
    createLoading.value = false
  }
}
</script>

<style scoped>
.teacher-index {
  min-height: 100vh;
}

.header {
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.header-row {
  height: 50px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #1677ff;
}

.content {
  padding: 20px 0;
}

.ktcon {
  background: #f0f2f5;
  min-height: 400px;
}

.control-bar {
  padding: 20px;
  background: white;
  border-radius: 8px;
  margin-bottom: 20px;
}

.control-title {
  font-size: 18px;
  font-weight: bold;
}

.course-add-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 200px;
}
</style>