// Cookie 工具函数
export const Cookies = {
  set (key, value, options = {}) {
    let cookie = `${encodeURIComponent(key)}=${encodeURIComponent(value)}`
    
    if (options.expires) {
      const date = new Date()
      date.setTime(date.getTime() + options.expires)
      cookie += `; expires=${date.toUTCString()}`
    }
    
    if (options.path) {
      cookie += `; path=${options.path}`
    } else {
      cookie += `; path=/`
    }
    
    if (options.domain) {
      cookie += `; domain=${options.domain}`
    }
    
    if (options.secure) {
      cookie += `; secure`
    }
    
    document.cookie = cookie
  },
  
  get (key) {
    const name = `${encodeURIComponent(key)}=`
    const cookieArr = document.cookie.split(';')
    
    for (let i = 0; i < cookieArr.length; i++) {
      const cookie = cookieArr[i].trim()
      if (cookie.indexOf(name) === 0) {
        return decodeURIComponent(cookie.substring(name.length, cookie.length))
      }
    }
    
    return null
  },
  
  remove (key) {
    this.set(key, '', { expires: -1 })
  }
}

export default Cookies