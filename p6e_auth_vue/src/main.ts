import { createApp } from 'vue';
import App from './App.vue';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import './registerServiceWorker';
import router from './router';

import './style/main.scss';
// app.config.productionTip = false;
createApp(App).use(router).use(Antd).mount('#app');

/* eslint-disable */
// @ts-ignore
window['P6E_AUTH_CERTIFICATE_VOUCHER'] = '3131XXXdadasda321312dasdas';
// @ts-ignore
window['P6E_AUTH_CERTIFICATE_PUBLIC_KEY'] = '3131XXXdadasda321312dasdas';
