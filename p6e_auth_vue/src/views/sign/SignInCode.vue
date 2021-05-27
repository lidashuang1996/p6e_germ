<template>
  <!-- p6e auth sign in -->
  <div class="sign-in-code sign-container">
    <div class="title">扫码登录</div>
    <div class="sign-in-code-content">
      <!-- 加载中的动画 -->
      <LoadingOutlined class="code-loading" v-if="isLoading"/>
      <!-- 二维码组件 -->
      <qr-code-component ref="refQrCodeComponent"/>
    </div>
    <div class="subtitle">打开 APP 点开扫一扫，即可完成登录</div>
  </div>
</template>

<script lang="ts">
/* eslint-disable */
// @ts-ignore
const G_MARK = window['P6E_OAUTH2_DATA'].mark;
import {
  LoadingOutlined
} from '@ant-design/icons-vue';
import { ApiSignInQrCode, ApiSignInQrData } from '@/http/main-sign-in';
import { Options, Vue } from 'vue-class-component';
import { QrCodeComponentInterface } from '@/components/components';
import QrCodeComponent from '@/components/QrCode/QrCodeComponent.vue';

@Options({
  components: {
    QrCodeComponent,
    LoadingOutlined
  }
})
export default class SignInCode extends Vue {
  /** 是否加载中 */
  private isLoading = true;

  private qrCode = '';
  private loopTimeout: any = null;
  private refreshTimeout: any = null;


  /**
   * 进入页面就加载二维码的数据
   */
  public async mounted () {
    this.isLoading = true;
    await this.refreshQrCode();
    this.isLoading = false;
    this.getQrData();
  }

  public unmounted () {
    if (this.loopTimeout != null) {
      clearTimeout(this.loopTimeout);
    }
    if (this.refreshTimeout != null) {
      clearTimeout(this.refreshTimeout);
    }
  }

  /**
   * 刷新二维码
   */
  public async refreshQrCode () {
    const res = await ApiSignInQrCode({ mark: G_MARK });
    if (res.code === 0) {
      const qrCodeComponent = this.$refs.refQrCodeComponent as QrCodeComponentInterface;
      this.qrCode = res.data.content.split('?code=')[1];
      qrCodeComponent.qrCode(res.data.content);
    }
    this.refreshTimeout = setTimeout(() => this.refreshQrCode(), 60000);
  }

  /**
   * 轮询获取数据
   */
  private getQrData () {
    const f = async () => {
      if (this.qrCode !== '') {
        const res = await ApiSignInQrData({ mark: G_MARK, code: this.qrCode });
        if (res.code === 0) {
          console.log('正确  success ...');
        }
      }
      this.loopTimeout = setTimeout(() => f(), 2200);
    };
    f();
  }
}
</script>
