import { defineStore } from 'pinia'
import Cookies from 'js-cookie'

export const useUserStore = defineStore('user', {
  state: () => ({
    identify: Cookies.get('identify') || '',
    id: Cookies.get('id') || '',
    info: {}
  }),
  
  getters: {
    isStudent: (state) => state.identify === 'student',
    isTeacher: (state) => state.identify === 'teacher',
    isLoggedIn: (state) => !!state.identify && !!state.id
  },
  
  actions: {
    setUser(identify, id) {
      this.identify = identify
      this.id = id
      Cookies.set('identify', identify)
      Cookies.set('id', id)
    },
    
    clearUser() {
      this.identify = ''
      this.id = ''
      Cookies.remove('identify')
      Cookies.remove('id')
      Cookies.remove('token')
    },
    
    setInfo(info) {
      this.info = info
    },
    
    updateInfo(updates) {
      this.info = { ...this.info, ...updates }
    }
  }
})