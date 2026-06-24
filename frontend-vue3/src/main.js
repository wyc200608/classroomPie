import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import * as IconsVue from '@ant-design/icons-vue'

import App from './App.vue'
import router from './router'
import './assets/style.css'

// 创建应用实例
const app = createApp(App)

// 使用Pinia状态管理
app.use(createPinia())

// 使用Ant Design Vue
app.use(Antd)

// 使用路由
app.use(router)

// 注册所有图标
const icons = IconsVue
for (const iconName in icons) {
  app.component(iconName, icons[iconName])
}

// 挂载应用
app.mount('#app')