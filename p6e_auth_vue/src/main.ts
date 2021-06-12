/** 导入 vue 基础组件 */
import { createApp } from 'vue';

/** 导入 APP 组建文件 */
import App from './App.vue';

/** 注册 PWA 服务 */
import './registerServiceWorker';

/**
 * 导入 ANTD UI
 */
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

/** i18n */
import i18n from '@/i18n/main';

/** 导入自定义 scss */
import './style/main.scss';

/** 初始化全局数据 */
import { Global } from '@/utils/main';
Global.init();

/** 创建节点挂载插件 */
// app.config.productionTip = false;
createApp(App).use(Antd).use(i18n).mount('#app');
