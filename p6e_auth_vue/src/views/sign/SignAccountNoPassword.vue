<template>
  <!-- p6e auth sign up -->
  <div class="sign-account-no-password">
    <form class="sign-account-no-password-form">
      <!-- 账号 -->
      <InputAccountComponent class="input-account" ref="refInputAccount"/>
      <!-- 验证码 -->
      <InputCodeComponent class="input-code" ref="refInputCode"/>
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
import { InputInterface } from '@/components/components';
import InputCodeComponent from '@/components/Input/InputCodeComponent.vue';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';

@Options({
  components: {
    InputCodeComponent,
    InputAccountComponent
  }
})
export default class SignAccountNoPassword extends Vue {
  /** 错误提示文本 */
  public error = '';

  /**
   * 确认的方法
   */
  public confirm () {
    const code = this.$refs.refInputCode as InputInterface;
    const account = this.$refs.refInputAccount as InputInterface;
    if (account.test() && code.test()) {
      this.error = '31232131';
      console.log(account.getData(), code.getData());
    }
  }
}
</script>
