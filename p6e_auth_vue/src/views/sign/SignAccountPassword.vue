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
                  @click.stop="confirm">
          登 录
        </a-button>
        <!-- 错误提示 -->
        <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
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
  /**
   * 登录确认的方法
   */
  public confirm () {
    const account = this.$refs.refInputAccount as InputInterface;
    const password = this.$refs.refInputPassword as InputInterface;
    if (account.test() && password.test()) {
      this.error = '31232131';
      console.log(account.getData(), password.getData());
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
