import { createApp } from 'vue';
import App from './App.vue';
import router from '@/router';
import store from '@/store';
import antd from '@/plugins/antd';
import '@/styles/layout.scss';
createApp(App).use(antd).use(store).use(router).mount('#app');
