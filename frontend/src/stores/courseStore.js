import { defineStore } from 'pinia'
import { Cookies } from '@/utils/cookie'
import axios from '@/utils/axios'

export const useCourseStore = defineStore('course', {
  state: () => ({
    coursesInfo: [],
    courseInfo: null
  }),

  actions: {
    async getCourseByTeacher() {
      try {
        const response = await axios.post('/course/getCourseByTeacher', {
          phone: Cookies.get('id')
        })
        this.coursesInfo = response.data
        return response.data
      } catch (error) {
        console.log(error)
        return []
      }
    },

    async getCourseByStudent() {
      try {
        const response = await axios.post('/course/getCourseByStudent', {
          phone: Cookies.get('id')
        })
        this.coursesInfo = response.data
        return response.data
      } catch (error) {
        console.log(error)
        return []
      }
    },

    async createCourse(courseName, courseDate) {
      try {
        const response = await axios.post('/teacher/getInfo', {
          phone: Cookies.get('id')
        })
        const teacherId = response.data.tid
        
        const result = await axios.post('/course/createCourse', {
          tid: teacherId,
          cname: courseName,
          cdate: courseDate
        })
        
        return result.data
      } catch (error) {
        console.log(error)
        return { msg: 'failed' }
      }
    },

    async addTeachCourse(courseCode) {
      try {
        const response = await axios.post('/teacher/getInfo', {
          phone: Cookies.get('id')
        })
        const teacherId = response.data.tid
        
        const result = await axios.post('/tc/addTeachCourse', {
          tid: teacherId,
          invite: courseCode
        })
        
        return result.data
      } catch (error) {
        console.log(error)
        return null
      }
    },

    async archiveCourse(tcid, cid, archive = 1) {
      try {
        const result = await axios.post('/course/archiveCourse', {
          tcid,
          cid,
          archive
        })
        return result.data
      } catch (error) {
        console.log(error)
        return null
      }
    },

    async deleteCourse(cid) {
      try {
        const result = await axios.post('/course/deleteCourse', {
          cid
        })
        return result.data
      } catch (error) {
        console.log(error)
        return null
      }
    },

    async archiveStudentCourse(scid, archive = 1) {
      try {
        const result = await axios.post('/sc/archiveCourse', {
          scid,
          archive
        })
        return result.data
      } catch (error) {
        console.log(error)
        return null
      }
    },

    async deleteStudentCourse(scid) {
      try {
        const result = await axios.post('/sc/deleteCourse', {
          scid
        })
        return result.data
      } catch (error) {
        console.log(error)
        return null
      }
    },

    async addStudyCourse(courseCode) {
      try {
        const response = await axios.post('/student/getInfo', {
          phone: Cookies.get('id')
        })
        const studentId = response.data.sid
        
        const result = await axios.post('/sc/addStudyCourse', {
          sid: studentId,
          invite: courseCode
        })
        
        return result.data
      } catch (error) {
        console.log(error)
        return null
      }
    },

    async findArchiveCourse(archiveType = 'teacher') {
      try {
        let url
        if (archiveType === 'teacher') {
          url = '/tc/findArchiveCourse'
        } else {
          url = '/sc/findArchiveCourse'
        }
        
        const response = await axios.post(url, {
          phone: Cookies.get('id')
        })
        return response.data
      } catch (error) {
        console.log(error)
        return []
      }
    },

    async updateCourse(cid, cname, cdate) {
      try {
        const result = await axios.post('/course/updateCourse', {
          cid,
          cname,
          cdate
        })
        return result.data
      } catch (error) {
        console.log(error)
        return null
      }
    }
  }
})