<template>
  <!-- p6e auth sign in -->
  <div class="sign-in sign-container">
    <!-- 登录模式切换 -->
    <div class="sign-in-mode-switch"
         @click.stop="signInModeSwitch()"
         :title="mode === 'AP' ? '扫码登录' : '账号登录'">
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
        <p class="other-nav-wx" @click.stop="wechat"><WechatOutlined/><span>微信</span></p>
        <p class="other-nav-qq" @click.stop="qq"><QqOutlined/><span>QQ</span></p>
        <p class="other-nav-wb" @click.stop="sina"><WeiboCircleOutlined/><span>微博</span></p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
/* eslint-disable */
// @ts-ignore
const G_MARK = window['P6E_OAUTH2_DATA'].mark;
import {
  QqOutlined,
  QrcodeOutlined,
  WechatOutlined,
  AppstoreAddOutlined,
  WeiboCircleOutlined
} from '@ant-design/icons-vue';
import { Modal } from 'ant-design-vue';
import SignInCode from './SignInCode.vue';
import SignInAccount from './SignInAccount.vue';
import { Options, Vue } from 'vue-class-component';
import { ApiSignInQq, ApiSignInWeChat, ApiSignInSina } from '@/http/main-sign-in';

@Options({
  components: {
    SignInCode,
    SignInAccount,
    QqOutlined,
    QrcodeOutlined,
    WechatOutlined,
    AppstoreAddOutlined,
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
  private signInModeSwitch (): void {
    this.mode = this.mode === 'AP' ? 'QC' : 'AP';
  }

  /**
   * QQ 第三方登录
   */
  private async qq () {
    const res = await ApiSignInQq({ mark: G_MARK });
    if (res.code === 0) {
      window.location.href = res.data.content;
    } else {
      Modal.warning({
        centered: true,
        title: '抱歉',
        okText: '确定',
        content: '第三方QQ账号登录暂未开通'
      });
    }
  }

  /**
   * 微信第三方登录
   */
  private async wechat () {
    const res = await ApiSignInWeChat({ mark: G_MARK })
    if (res.code === 0) {
      window.location.href = res.data.content;
    } else {
      Modal.warning({
        centered: true,
        title: '抱歉',
        okText: '确定',
        content: '第三方微信账号登录暂未开通'
      });
    }
  }

  /**
   * 微博第三方登录
   */
  private async sina () {
    const res = await ApiSignInSina({ mark: G_MARK })
    if (res.code === 0) {
      // window.location.href = res.data.content;
    } else {
      Modal.warning({
        centered: true,
        title: '抱歉',
        okText: '确定',
        content: '第三方微博账号登录暂未开通'
      });
    }
  }
}
</script>
