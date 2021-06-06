<template>
  <!-- p6e auth sign up -->
  <div class="sign-account-no-password">
    <form class="sign-account-no-password-form">
      <!-- 账号 -->
      <InputAccountComponent class="input-account"  @keyup.enter="confirm" @focus="closePrompt" ref="refInputAccount"/>
      <!-- 验证码 -->
      <InputCodeComponent class="input-code"  @keyup.enter="confirm" @focus="closePrompt" @code="obtainCode" ref="refInputCode"/>
      <!-- 按钮 -->
      <div class="input-button">
        <a-button type="primary"
                  block="block"
                  class="button"
                  :loading="isLoading"
                  @click.stop="confirm">
          <span v-if="!isLoading">登 录</span>
        </a-button>
        <!-- 错误提示 -->
        <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
/* eslint-disable */
// @ts-ignore
const G_MARK = window['P6E_OAUTH2_DATA'].mark;
import Utils from '@/utils/main';
import { Modal } from 'ant-design-vue';
import { Options, Vue } from 'vue-class-component';
import { CodeInputInterface, InputInterface } from '@/components/components';
import InputCodeComponent from '@/components/Input/InputCodeComponent.vue';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';
import { ApiSignInObtainCode, ApiSignInCode } from '@/http/main-sign-in';

@Options({
  components: {
    InputCodeComponent,
    InputAccountComponent
  }
})
export default class SignAccountNoPassword extends Vue {

  /** 错误提示文本 */
  public error = '';
  /** 登录是否加载中 */
  public isLoading = false;
  private readonly codeCacheName: string = 'P6E_NR_CODE_LOGIN';
  private code: { account: string; content: string; } | null = null;

  public mounted () {
    const code = Utils.Cache.getData(this.codeCacheName);
    if (code !== null && code !== undefined) {
      this.code = code;
    }
  }

  /**
   * 确认的方法
   */
  public async confirm () {
    if (!this.isLoading) {
      const code = this.$refs.refInputCode as CodeInputInterface;
      const account = this.$refs.refInputAccount as InputInterface;
      if (account.test() && code.test() && this.code !== null) {
        // 发送登录请求
        this.isLoading = true;
        const res = await ApiSignInCode({
          mark: G_MARK,
          codeKey: this.code.content,
          codeContent: code.getData(),
          account: account.getData()
        });
        this.isLoading = false;
        if (res.code === 0) {
          // 处理登录之后的操作
          console.log(account.getData(), code.getData());
        } else {
          this.error = '账号或者密码错误';
        }
      }
    }
  }

  /**
   * 获取 CODE 的事件
   */
  public async obtainCode () {
    const code = this.$refs.refInputCode as CodeInputInterface;
    const account = this.$refs.refInputAccount as InputInterface;
    if (account.test()) {
      code.countdown(60);
      const res = await ApiSignInObtainCode({ mark: G_MARK, account: account.getData() });
      if (res.code === 0) {
        /** 缓存数据 */
        this.code = res.data;
        Utils.Cache.setData(this.codeCacheName, res.data);
      } else {
        Modal.error({
          title: '提示',
          okText: '确认',
          content: '网络异常，请稍后重试！'
        });
      }
    }
  }

  /**
   * 关闭错误提示
   */
  public closePrompt () {
    this.error = '';
  }
}
</script>
