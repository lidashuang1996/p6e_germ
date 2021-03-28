<template>
  <!-- p6e auth sign in -->
  <div class="sign-in sign-container">
    <!-- 登录模式切换 -->
    <div class="sign-in-mode-switch" @click.stop="signInModeSwitch()">
      <QrcodeOutlined v-if="mode === 'AP'"/>
      <AppstoreAddOutlined v-if="mode === 'QC'"/>
    </div>
    <!-- 账号密码/二维码模式 -->
    <sign-in-code v-if="mode === 'QC'"/>
    <sign-in-account v-if="mode === 'AP'"/>
    <!-- 其它的第三方授权登录 -->
    <div class="sign-in-mode-other">
      <div class="other-title">社交账号登录</div>
      <div class="other-content">
        <p class="other-nav-wx"><WechatOutlined/><span>微信</span></p>
        <p class="other-nav-qq"><QqOutlined/><span>QQ</span></p>
        <p class="other-nav-wb"><WeiboCircleOutlined/><span>微博</span></p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {
  QrcodeOutlined,
  AppstoreAddOutlined,
  WechatOutlined,
  QqOutlined,
  WeiboCircleOutlined
} from '@ant-design/icons-vue';
import SignInCode from './SignInCode.vue';
import SignInAccount from './SignInAccount.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    SignInCode,
    SignInAccount,
    QrcodeOutlined,
    AppstoreAddOutlined,
    WechatOutlined,
    QqOutlined,
    WeiboCircleOutlined
  }
})
export default class SignIn extends Vue {
  /**
   * QC QrCode 二维码
   * AP Account/Password 账号密码
   * VC Verification code 手机验证码
   */
  private mode = 'AP';

  /**
   * 登录模式的切换
   */
  public signInModeSwitch () {
    this.mode = this.mode === 'AP' ? 'QC' : 'AP';
  }
}
</script>
