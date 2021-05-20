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
// 从全局读取的数据
/* eslint-disable */
// @ts-ignore
const G_MARK = window['P6E_OAUTH2_DATA'].mark;
import JSEncrypt from 'jsencrypt';
import { Modal } from 'ant-design-vue';
import { ApiSignIn } from '@/http/main-sign-in';
import { ApiSignVoucher } from '@/http/main-sign';
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
  /** 登录的凭证内容 */
  private voucher = '';
  /** 公钥 */
  private publicKey = '';
  /**
   * 登录确认的方法
   */
  public async confirm () {
    // 转换为输入框的类型
    const account = this.$refs.refInputAccount as InputInterface;
    const password = this.$refs.refInputPassword as InputInterface;
    // 验证数据格式以及判断是否在登录中
    if (account.test() && password.test() && !this.isLoading) {
      // 发送登录请求
      try {
        this.isLoading = true;
        // 获取登录的凭证
        if (this.voucher === '') {
          const res1 = await ApiSignVoucher();
          if (res1.code === 0) {
            this.voucher = res1.data.voucher;
            this.publicKey = res1.data.publicKey;
          } else {
            this.isLoading = false;
            this.error = res1.message;
            return;
          }
        }
        // 使用公钥加密
        const encrypt = new JSEncrypt({});
        encrypt.setPublicKey('-----BEGIN PUBLIC KEY-----' + this.publicKey + '-----END PUBLIC KEY-----');
        const resultEncrypt = encrypt.encrypt(password.getData());
        if (!resultEncrypt) {
          this.error = '网页异常，请稍后重试！';
          return;
        }
        // 执行登录操作
        const res2 = await ApiSignIn({
          mark: G_MARK,
          voucher: this.voucher,
          account: account.getData(),
          password: resultEncrypt.toString()
        });
        if (res2.code === 0) {
          // 处理登录之后的操作
          console.log(account.getData(), password.getData());
        } else {
          this.isLoading = false;
          this.error = '账号或者密码错误';
          return;
        }
      } catch (e) {
        this.isLoading = false;
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
