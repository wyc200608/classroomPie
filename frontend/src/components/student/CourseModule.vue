<template>
  <div class="course-module">
    <div class="course-module-header">
      <div class="course-module-header-left">
        <span
          class="course-name"
          @click="goToDetail"
        >{{ courseInfo.cname }}</span>
        <span class="course-invite">{{ courseInfo.invite }}</span>
      </div>
      <span class="course-date">{{ courseInfo.cdate }}</span>
    </div>
    <ul class="course-module-body">
      <li><span class="label">最近作业</span></li>
      <li>{{ courseInfo.work }}</li>
    </ul>
    <div class="course-module-footer">
      <a-row justify="space-between">
        <a-row :gutter="[10,0]">
          <a-col>
            <a-avatar :src="image" :size="24" />
          </a-col>
          <a-col>{{ courseInfo.tname }}</a-col>
        </a-row>
        <a-col>
          <a-dropdown :trigger="['click']" @click="handleMenuClick">
            <a-space @click.stop>
              <span>更多</span>
              <DownOutlined />
            </a-space>
            <template #overlay>
              <a-menu>
                <a-menu-item key="archive">
                  <span @click.stop="archive">归档</span>
                </a-menu-item>
                <a-menu-item key="delete">
                  <span @click.stop="deleteCourse">退课</span>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { DownOutlined } from '@ant-design/icons-vue'
import { Cookies } from '@/utils/cookie'
import axios from '@/utils/axios'

const props = defineProps({
  courseInfo: {
    type: Object,
    required: true
  }
})

const router = useRouter()
const image = ref('')

onMounted(() => {
  getHeaderImg()
})

const getHeaderImg = async () => {
  try {
    const response = await axios.post('/teacher/getHeaderImg', {
      phone: props.courseInfo.tphone
    })
    image.value = 'data:image/png;base64,' + response.image
  } catch (error) {
    console.log(error)
  }
}

const goToDetail = () => {
  router.push(`/student/detail?id=${props.courseInfo.cid}`)
}

const archive = async () => {
  try {
    const response = await axios.post('/sc/archiveCourse', {
      scid: props.courseInfo.scid,
      archive: 1
    })
    
    if (response.data === 'success') {
      message.success('归档成功')
      // TODO: 刷新课程列表
    } else if (response.data === 'failed') {
      message.error('归档失败')
    }
  } catch (error) {
    console.log(error)
  }
}

const deleteCourse = async () => {
  try {
    const response = await axios.post('/sc/deleteCourse', {
      scid: props.courseInfo.scid
    })
    
    if (response.data === 'success') {
      message.success('退课成功')
      // TODO: 刷新课程列表
    } else if (response.data === 'failed') {
      message.error('退课失败')
    }
  } catch (error) {
    console.log(error)
  }
}

const handleMenuClick = (e) => {
  e.domEvent.stopPropagation()
}
</script>

<style scoped>
.course-module {
  width: 300px;
  height: 200px;
  margin-bottom: 20px;
}

.course-module-header {
  height: 60px;
  background-color: #1890ff;
  border-radius: 8px 8px 0 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.course-module-header-left {
  display: flex;
  flex-direction: column;
}

.course-name {
  font-size: 20px;
  color: #fff;
  cursor: pointer;
}

.course-invite {
  font-size: 12px;
  color: rgba(255, 255, 255, 1);
}

.course-date {
  color: rgba(255, 255, 255, 1);
  align-self: flex-end;
}

.course-module-body {
  list-style: none;
  padding: 15px 20px;
  background-color: #fff;
  margin: 0;
}

.course-module-body li {
  margin-bottom: 10px;
}

.course-module-body li:last-child {
  margin-bottom: 0;
}

.label {
  color: rgba(95, 99, 104, 1);
  font-size: 12px;
}

.course-module-footer {
  padding: 10px 20px;
  background-color: #fff;
  border-radius: 0 0 8px 8px;
}
</style>