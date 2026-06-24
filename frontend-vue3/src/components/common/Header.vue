<template>
  <a-affix :offset-top="top">
    <div id="header-wrap">
      <a-col :span="18" :offset="3" style="height: 50px">
        <a-row type="flex" align="bottom" style="height: 50px">
          <a-col :span="4">
            <div id="logo-text">CloudClass</div>
          </a-col>
          <a-col :span="10">
            <a-menu
              mode="horizontal"
              :selected-keys="selectedKeys"
            >
              <a-menu-item key="index">
                <router-link to="/student">首页</router-link>
              </a-menu-item>
              <a-menu-item key="discussion">
                <router-link to="/student">讨论区</router-link>
              </a-menu-item>
            </a-menu>
          </a-col>
          <a-col :span="2" :offset="8" style="height: 100%; padding-top: 5px">
            <Sculpture />
          </a-col>
        </a-row>
      </a-col>
    </div>
  </a-affix>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import Sculpture from '../student/Sculpture.vue'

const props = defineProps({
  currentPage: {
    type: String,
    default: 'index'
  }
})

const top = ref(0)
const selectedKeys = computed(() => [props.currentPage])

onMounted(() => {
  // 可以在这里添加滚动监听等逻辑
})
</script>

<style scoped>
#header-wrap {
  background-color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

#logo-text {
  font-size: 20px;
  font-weight: bold;
  color: #1677ff;
  cursor: pointer;
}

a-menu {
  background: transparent;
}

a-menu-item a {
  color: rgba(0, 0, 0, 0.85);
}

a-menu-item a:hover {
  color: #1677ff;
}
</style>