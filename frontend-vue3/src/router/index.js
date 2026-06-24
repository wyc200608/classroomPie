import { createRouter, createWebHistory } from 'vue-router'
import { Cookies } from '@/utils/cookie'

import StudentIndex from '../views/student/Index.vue'
import StudentDetail from '../views/student/Detail.vue'
import StudentPersonal from '../views/student/Personal.vue'
import TeacherIndex from '../views/teacher/Index.vue'
import TeacherDetail from '../views/teacher/Detail.vue'
import TeacherPersonal from '../views/teacher/Personal.vue'
import StudentLogin from '../views/student/Login.vue'
import TeacherLogin from '../views/teacher/Login.vue'

// 路由配置
const routes = [
  // 学生端路由
  {
    path: '/student',
    name: 'student-index',
    component: StudentIndex,
    meta: { role: 'student' }
  },
  {
    path: '/student/detail',
    name: 'student-detail',
    component: StudentDetail,
    meta: { role: 'student' }
  },
  {
    path: '/student/personal',
    name: 'student-personal',
    component: StudentPersonal,
    meta: { role: 'student' }
  },
  {
    path: '/student/login',
    name: 'student-login',
    component: StudentLogin
  },
  
  // 教师端路由
  {
    path: '/teacher',
    name: 'teacher-index',
    component: TeacherIndex,
    meta: { role: 'teacher' }
  },
  {
    path: '/teacher/detail',
    name: 'teacher-detail',
    component: TeacherDetail,
    meta: { role: 'teacher' }
  },
  {
    path: '/teacher/personal',
    name: 'teacher-personal',
    component: TeacherPersonal,
    meta: { role: 'teacher' }
  },
  {
    path: '/teacher/login',
    name: 'teacher-login',
    component: TeacherLogin
  },
  
  // 默认重定向
  {
    path: '/',
    redirect: '/student'
  }
]

// 创建路由器
const Router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
Router.beforeEach((to, from, next) => {
  const identify = Cookies.get('identify')
  const id = Cookies.get('id')
  
  if (to.meta.role) {
    if (!identify || !id) {
      // 未登录，跳转到登录页
      if (to.meta.role === 'student') {
        next({ name: 'student-login' })
      } else if (to.meta.role === 'teacher') {
        next({ name: 'teacher-login' })
      }
    } else if (identify !== to.meta.role) {
      // 角色不匹配
      if (identify === 'student') {
        next({ name: 'student-index' })
      } else if (identify === 'teacher') {
        next({ name: 'teacher-index' })
      }
    } else {
      next()
    }
  } else {
    next()
  }
})

export default Router