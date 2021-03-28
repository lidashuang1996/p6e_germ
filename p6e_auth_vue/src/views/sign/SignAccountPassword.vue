<template>
  <!-- p6e auth sign in 账号密码登录 -->
  <div class="sign-account-password">
    <!-- 账号/密码的表单 -->
    <form class="sign-account-password-form">
      <!-- 账号 -->
      <InputAccountComponent class="input-account" @keyup.enter="confirm" @focus="closePrompt" ref="refInputAccount"/>
      <!-- 密码 -->
      <InputPasswordComponent class="input-password" @keyup.enter="confirm" @focus="closePrompt" ref="refInputPassword"/>
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

import { ApiSignIn } from '@/http/main-sign-in';
import { Options, Vue } from 'vue-class-component';
import { InputInterface } from '@/components/components.ts';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';
import InputPasswordComponent from '@/components/Input/InputPasswordComponent.vue';

@Options({
  components: {
    InputAccountComponent,
    InputPasswordComponent
  }
})
export default class SignAccountPassword extends Vue {
  /** 错误提示文本 */
  public error = '';
  /** 登录是否加载中 */
  public isLoading = false;

  /**
   * 登录确认的方法
   */
  public async confirm () {
    const account = this.$refs.refInputAccount as InputInterface;
    const password = this.$refs.refInputPassword as InputInterface;
    if (account.test() && password.test()) {
      // 发送登录请求
      this.isLoading = true;
      const res = await ApiSignIn({
        voucher: voucher,
        account: account.getData(),
        password: password.getData()
      });
      this.isLoading = false;
      if (res.code === 0) {
        // 处理登录之后的操作
        console.log(account.getData(), password.getData());
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
