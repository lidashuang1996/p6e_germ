<template>
  <!-- p6e auth sign in -->
  <div class="sign-in sign-container">
    <!-- 登录模式切换 -->
    <div class="sign-in-mode-switch"
         @click.stop="signInModeSwitch()"
         :title="mode === 'AP' ? $t('sign.in.qc.title') : $t('sign.in.ap.title')">
      <QrcodeOutlined v-if="mode === 'AP'"/>
      <AppstoreAddOutlined v-if="mode === 'QC'"/>
    </div>
    <!-- 账号密码/二维码模式 -->
    <sign-in-code v-if="mode === 'QC'"/>
    <sign-in-account v-if="mode === 'AP'"/>
    <!-- 其它的第三方授权登录 -->
    <div class="sign-in-mode-other">
      <div class="other-title">
        <span v-text="$t('sign.other.title')"></span>
      </div>
      <div class="other-content">
        <template :key="index"
                  v-for="(item, index) in $tm('sign.other.list')">
          <p class="other-nav-wx"
             @click.stop="wechat(item.name)"
             v-if="item.type === 'WC'">
            <WechatOutlined/>
            <span v-text="item.name"></span>
          </p>
          <p class="other-nav-qq"
             @click.stop="qq(item.name)"
             v-if="item.type === 'QQ'">
            <QqOutlined/>
            <span v-text="item.name"></span>
          </p>
          <p class="other-nav-wb"
             @click.stop="sina(item.name)"
             v-if="item.type === 'SA'">
            <WeiboCircleOutlined/>
            <span v-text="item.name"></span>
          </p>
        </template>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {
  QqOutlined,
  QrcodeOutlined,
  WechatOutlined,
  AppstoreAddOutlined,
  WeiboCircleOutlined
} from '@ant-design/icons-vue';
import { Modal } from 'ant-design-vue';
import SignInCode from './SignInCode.vue';
import { Global, Utils } from '@/utils/main';
import SignInAccount from './SignInAccount.vue';
import { Options, Vue } from 'vue-class-component';
import { ApiSignInQq, ApiSignInWeChat, ApiSignInSina } from '@/http/main-sign-in';

/** 全局读取的 MARK */
const G_MARK = Global.getOauth2().mark;

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
   * VC Verification Code 手机//邮箱 验证码
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
  private async qq (name: string) {
    this.otherLogin(await ApiSignInQq({ mark: G_MARK }), name);
  }

  /**
   * 微信第三方登录
   */
  private async wechat (name: string) {
    this.otherLogin(await ApiSignInWeChat({ mark: G_MARK }), name);
  }

  /**
   * 微博第三方登录
   */
  private async sina (name: string) {
    this.otherLogin(await ApiSignInSina({ mark: G_MARK }), name);
  }

  /**
   * 第三方账号登录
   */
  private otherLogin (res: { code: number; message: string; data: { content: string } }, name: string) {
    if (res.code === 0) {
      window.location.href = res.data.content;
    } else {
      const message = res.message;
      if (message === 'SERVICE_NOT_ENABLE') {
        const ree = this.$tm('error.SERVICE_NOT_ENABLE') as { [ key: string ]: string };
        Modal.warning({
          centered: true,
          title: ree.title,
          okText: ree.okText,
          content: Utils.translate(ree.content, { platform: name })
        });
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
}
</script>
