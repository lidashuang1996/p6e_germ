<template>
  <!-- p6e auth sign up -->
  <div class="sign-account-no-password">
    <form class="sign-account-no-password-form">
      <!-- 账号 -->
      <InputAccountComponent ref="refInputAccount"
                             class="input-account"
                             @focus="closePrompt"
                             @keyup.enter="confirm"
                             :errors="$tm('sign.in.ap.np.account.errors')"
                             :placeholder="$t('sign.in.ap.np.account.placeholder')"/>
      <!-- 验证码 -->
      <InputCodeComponent ref="refInputCode"
                          class="input-code"
                          @code="obtainCode"
                          @focus="closePrompt"
                          @keyup.enter="confirm"
                          :errors="$tm('sign.in.ap.np.code.errors')"
                          :placeholder="$t('sign.in.ap.np.code.placeholder')"/>
      <!-- 按钮 -->
      <div class="input-button">
        <a-button type="primary"
                  block="block"
                  class="button"
                  :loading="isLoading"
                  @click.stop="confirm">
          <span v-if="!isLoading" v-text="$t('sign.in.ap.np.button')"></span>
        </a-button>
        <!-- 错误提示 -->
        <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { Global } from '@/utils/main';
import { Modal } from 'ant-design-vue';
import { Options, Vue } from 'vue-class-component';
import { ApiSignInObtainCode, ApiSignInCode } from '@/http/main-sign-in';
import InputCodeComponent from '@/components/Input/InputCodeComponent.vue';
import { CodeInputInterface, InputInterface } from '@/components/components';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';

/** 全局读取的 MARK */
const G_MARK = Global.getOauth2().mark;

@Options({
  components: {
    InputCodeComponent,
    InputAccountComponent
  }
})
export default class SignAccountNoPassword extends Vue {
  /** 错误提示文本 */
  private error = '';
  /** 登录是否加载中 */
  private isLoading = false;
  /** 凭证缓存 */
  private codeVoucher: { account: string; content: string; } | null = null;
  /**
   * 钩子函数
   */
  public mounted () {
    /** 读取全局缓存倒计时 */
    const code = Global.getSignCountdown();
    if (code > 0) {
      this.setCodeInput(code);
    }
  }

  /**
   * 确认的方法
   */
  private async confirm () {
    if (!this.isLoading) {
      const code = this.$refs.refInputCode as CodeInputInterface;
      const account = this.$refs.refInputAccount as InputInterface;
      if (account.test() && code.test() && this.codeVoucher !== null) {
        // 发送登录请求
        this.isLoading = true;
        const res = await ApiSignInCode({
          mark: G_MARK,
          codeKey: this.codeVoucher.content,
          codeContent: code.getData(),
          account: account.getData()
        });
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

  /**
   * 获取 CODE 的事件
   */
  private async obtainCode () {
    const account = this.$refs.refInputAccount as InputInterface;
    if (account.test()) {
      // 设置为 60
      this.setCodeInput(60);
      Global.setSignCountdown(60);
      const res = await ApiSignInObtainCode({ mark: G_MARK, account: account.getData() });
      if (res.code === 0) {
        this.codeVoucher = res.data;
      } else {
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

  /**
   * 设置倒计时时间
   */
  private setCodeInput (n: number) {
    const code = this.$refs.refInputCode as CodeInputInterface;
    code.countdown(n);
  }

  /**
   * 关闭错误提示
   */
  private closePrompt () {
    this.error = '';
  }
}
</script>
