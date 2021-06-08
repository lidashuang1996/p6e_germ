<template>
  <div class="index-content">
    <!-- 登录/注册 内容 -->
    <SignIn v-if="type === 'sign'"/>
    <Notice v-if="type === 'auth'"/>
    <!-- 数据出现异常 内容 -->
    <div class="index-content-null" v-if="type === ''">
      <a-result status="warning" title="解析数据出现异常">
        <template #extra>
          <a-button type="primary" size="large" block>返回主页</a-button>
        </template>
      </a-result>
    </div>
  </div>
</template>

<script lang="ts">
import SignIn from '@/views/sign/SignIn.vue';
import Notice from '@/views/notice/Notice.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    SignIn,
    Notice
  }
})
export default class IndexContent extends Vue {
  /** 页面类型 */
  protected type = '';

  /**
   * 勾子函数
   */
  public mounted () {
    /* eslint-disable */
    // @ts-ignore
    const oauth2 = window['P6E_OAUTH2_DATA'];
    if (oauth2 !== null && oauth2 !== undefined
      && oauth2.type !== null && oauth2.type !== undefined) {
      switch (oauth2.type) {
        case 'auth':
        case 'sign':
          this.type = oauth2.type;
          break;
      }
    }
  }
}
</script>
