<template>
  <div class="student-index">
    <Header currentPage="index" />
    
    <div class="content">
      <a-row :gutter="[0, 20]">
        <a-col :span="18" :offset="3">
          <a-row :gutter="[21, 30]" justify="start">
            <a-col v-for="course in courses" :span="8">
              <CourseModule :courseInfo="course" />
            </a-col>
          </a-row>
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

    <!-- 加入课程弹窗 -->
    <a-modal
      v-model:open="showJoinModal"
      title="加入课程"
      @ok="handleJoinCourse"
    >
      <a-form layout="vertical">
        <a-form-item label="邀请码">
          <a-input v-model:value="inviteCode" placeholder="请输入邀请码" />
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
import Header from '@/components/common/Header.vue'
import CourseModule from '@/components/student/CourseModule.vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'

const router = useRouter()
const userStore = useUserStore()
const courseStore = useCourseStore()
const courses = ref([])
const dropdownOpen = ref(false)
const showCreateModal = ref(false)
const showJoinModal = ref(false)
const createLoading = ref(false)
const courseName = ref('')
const courseDate = ref(null)
const inviteCode = ref('')

onMounted(async () => {
  if (userStore.identify !== 'student' || !userStore.id) {
    router.push('/student/login')
    return
  }
  
  try {
    await courseStore.getCourseByStudent()
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
      await courseStore.getCourseByStudent()
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

const handleJoinCourse = async () => {
  if (!inviteCode.value) {
    message.warning('请输入邀请码')
    return
  }
  
  try {
    const result = await courseStore.addStudyCourse(inviteCode.value)
    if (result && result.msg === 'success') {
      message.success('加入成功')
      showJoinModal.value = false
      inviteCode.value = ''
      await courseStore.getCourseByStudent()
      courses.value = courseStore.coursesInfo
    } else {
      message.error('加入失败')
    }
  } catch (error) {
    console.error('加入课程失败:', error)
    message.error('加入失败')
  }
}
</script>

<style scoped>
.student-index {
  min-height: 100vh;
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