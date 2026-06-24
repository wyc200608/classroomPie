import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import * as IconsVue from '@ant-design/icons-vue'

import App from './App.vue'
import router from './router'
import './assets/style.css'

// 创建应用实例
const app = createApp(App)

app.use(createPinia())
app.use(Antd)
app.use(router)

const icons = IconsVue
for (const iconName in icons) {
  app.component(iconName, icons[iconName])
}

// 挂载应用
app.mount('#app')