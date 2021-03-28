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
const voucher = window['P6E_AUTH_CERTIFICATE_VOUCHER'];

import { Options, Vue } from 'vue-class-component';
import { CodeInputInterface, InputInterface } from '@/components/components';
import InputCodeComponent from '@/components/Input/InputCodeComponent.vue';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';
import { ApiCode, ApiCodeSignIn } from '@/http/main-sign-in';

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

  /**
   * 确认的方法
   */
  public async confirm () {
    const code = this.$refs.refInputCode as CodeInputInterface;
    const account = this.$refs.refInputAccount as InputInterface;
    if (account.test() && code.test()) {
      // 发送登录请求
      this.isLoading = true;
      const res = await ApiCodeSignIn({ voucher: voucher, code: code.getData(), account: account.getData() });
      this.isLoading = false;
      if (res.code === 0) {
        // 处理登录之后的操作
        console.log(account.getData(), code.getData());
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
      const res = await ApiCode({ account: account.getData() });
      console.log('发送验证码..' + res);
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
