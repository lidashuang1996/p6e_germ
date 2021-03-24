import { createApp } from 'vue';
import App from './App.vue';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import './registerServiceWorker';
import router from './router';

import './style/main.scss';
// app.config.productionTip = false;
createApp(App).use(router).use(Antd).mount('#app');
