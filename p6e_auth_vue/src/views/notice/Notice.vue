<template>
  <!-- p6e auth notice -->
  <div class="notice">
    <div class="notice-content">
      <div class="notice-content-img">
        <img :src="icon" alt="LOGO"/>
      </div>
      <div class="notice-content-text">
        <p>你正在进行授权给 <span>「 <span v-text="name"></span> 」</span>，确认代表同意相关协议。</p>
      </div>
    </div>
    <div class="notice-button">
      <a-button type="primary"
                :loading="isLoading"
                @click.stop="confirm"
                block="block"
                class="button">
        <span v-if="!isLoading">确 认</span>
      </a-button>
    </div>
  </div>
</template>

<script lang="ts">
import Utils from '@/utils/utils';
import { Modal } from 'ant-design-vue';
import { Options, Vue } from 'vue-class-component';
import { ApiSignInConfirm } from '@/http/main-sign-in';

@Options({})
export default class Notice extends Vue {
  /** 是否为加载中 */
  private isLoading = false;

  /** 客户端信息 */
  private icon = '';
  private name = '';

  /**
   * 钩子函数
   */
  public mounted () {
    /* eslint-disable */
    // @ts-ignore
    const oauth2 = window['P6E_OAUTH2_DATA'];
    if (Utils.isNotEmpty(oauth2)) {
      this.icon = oauth2.icon;
      this.name = oauth2.name;
    }
  }

  /**
   * 登录确认的方法
   */
  public async confirm () {
    const code = Utils.getUrlParam('code');
    if (Utils.isNotEmpty(code)) {
      this.isLoading = true;
      const res = await ApiSignInConfirm({ code: code as string });
      this.isLoading = false;
      if (res.code === 0) {
        window.location.href = res.data;
      } else {
        Modal.error({
          title: '提示',
          okText: '确认',
          content: res.message
        });
      }
    }
  }
}
</script>
