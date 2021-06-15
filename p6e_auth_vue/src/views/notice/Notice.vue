<template>
  <!-- p6e auth notice -->
  <div class="notice">
    <!-- 通知的内容 -->
    <div class="notice-content">
      <div class="notice-content-img">
        <img :src="icon" alt="NOTICE_LOGO"/>
      </div>
      <div class="notice-content-text">
        <p>
          <template :key="index" v-for="(item, index) in $tm('sign.notice.content')">
            <span class="name"
                  v-if="index === 1"
                  v-text="'「' + name + '」'"></span>
            <span v-text="item"></span>
          </template>
        </p>
      </div>
    </div>
    <!-- 通知的按钮 -->
    <div class="notice-button">
      <a-button type="primary"
                :loading="isLoading"
                @click.stop="confirm"
                block="block"
                class="button">
        <span v-if="!isLoading" v-text="$t('sign.notice.button')"></span>
      </a-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Global, Utils } from '@/utils/main';
import { Modal } from 'ant-design-vue';
import { Options, Vue } from 'vue-class-component';
import { ApiSignInConfirm } from '@/http/main-sign-in';

@Options({})
export default class Notice extends Vue {
  /** 客户端信息 */
  private icon = '';
  private name = '';
  /** 是否为加载中 */
  private isLoading = false;

  /**
   * 钩子函数
   */
  public mounted () {
    const oauth2 = Global.getOauth2();
    if (Utils.isNotEmpty(oauth2)) {
      this.icon = oauth2.icon;
      this.name = oauth2.name;
    }
  }

  /**
   * 登录确认的方法
   */
  public async confirm () {
    const code = Utils.getUrlParam('code', Global.getGlobalURL());
    if (Utils.isNotEmpty(code)) {
      this.isLoading = true;
      const res = await ApiSignInConfirm({ code: code as string });
      this.isLoading = false;
      if (res.code !== 0) {
        const ree = this.$tm('error.' + res.message) as { [ key: string ]: string };
        Modal.error({
          centered: true,
          title: ree.title,
          okText: ree.okText,
          content: ree.content
        });
      }
    }
  }
}
</script>
