<template>
  <!-- p6e auth sign in 账号密码登录 -->
  <div class="sign-account-password">
    <!-- 账号/密码的表单 -->
    <form class="sign-account-password-form">
      <!-- 账号 -->
      <InputAccountComponent ref="refInputAccount"
                             class="input-account"
                             @focus="closePrompt"
                             @keyup.enter="confirm"
                             :errors="$tm('sign.in.ap.yp.account.errors')"
                             :placeholder="$t('sign.in.ap.yp.account.placeholder')"/>
      <!-- 密码 -->
      <InputPasswordComponent ref="refInputPassword"
                              class="input-password"
                              @keyup.enter="confirm"
                              @focus="closePrompt"
                              :errors="$tm('sign.in.ap.yp.password.errors')"
                              :placeholder="$t('sign.in.ap.yp.password.placeholder')"/>

      <!-- 按钮 -->
      <div class="input-button">
        <a-button type="primary"
                  block="block"
                  class="button"
                  :loading="isLoading"
                  @click.stop="confirm">
          <span v-if="!isLoading" v-text="$tm('sign.in.ap.yp.button')"></span>
        </a-button>
        <!-- 忘记密码 -->
        <a-button type="link" class="forget-password" @click="forgetPassword">
          <span v-text="$tm('sign.in.ap.yp.buttonForgetPassword')"></span>
        </a-button>
        <!-- 错误提示 -->
        <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import JSEncrypt from 'jsencrypt';
import { Modal } from 'ant-design-vue';
import { Global } from '@/utils/main';
import { ApiSignIn } from '@/http/main-sign-in';
import { ApiSignVoucher } from '@/http/main-sign';
import { Options, Vue } from 'vue-class-component';
import { InputInterface } from '@/components/components.ts';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';
import InputPasswordComponent from '@/components/Input/InputPasswordComponent.vue';

/** 全局读取的 MARK */
const G_MARK = Global.getOauth2().mark;

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
  private async confirm () {
    // 转换为输入框的类型
    const account = this.$refs.refInputAccount as InputInterface;
    const password = this.$refs.refInputPassword as InputInterface;
    // 验证数据格式以及判断是否在登录中
    if (account.test() && password.test() && !this.isLoading) {
      this.isLoading = true;
      // 获取登录的凭证
      if (this.voucher === '') {
        const res1 = await ApiSignVoucher({ mark: G_MARK });
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
      if (resultEncrypt) {
        // 执行登录操作
        const res2 = await ApiSignIn({
          mark: G_MARK,
          voucher: this.voucher,
          account: account.getData(),
          password: resultEncrypt.toString()
        });
        if (res2.code !== 0) {
          const ree = this.$tm('error.' + res2.message) as { [ key: string ]: string };
          Modal.error({
            centered: true,
            title: ree.title,
            okText: ree.okText,
            content: ree.content
          });
        }
      } else {
        const ree = this.$tm('error.SERVICE_EXCEPTION') as { [ key: string ]: string };
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
   * 忘记密码
   */
  private forgetPassword () {
    console.log('forgetPassword --- 123');
  }

  /**
   * 关闭错误提示
   */
  private closePrompt () {
    this.error = '';
  }
}
</script>
