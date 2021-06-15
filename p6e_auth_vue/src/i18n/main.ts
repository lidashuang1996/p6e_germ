import zhCN from './ch';
import { Utils } from '@/utils/main';
import { createI18n } from 'vue-i18n';

/** 当前语言参数 */
// const language = Utils.getLanguageParam('zh-CN');

/** 创建 i18n 对象 */
export default createI18n({
  locale: 'zh',
  messages: {
    /* eslint-disable */
    // @ts-ignore
    zh: zhCN,
    /* eslint-disable */
    // @ts-ignore
    en: zhCN
  }
});
