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
import {
  LoadingOutlined
} from '@ant-design/icons-vue';
import { ApiQrCode, ApiQrData } from '@/http/main-sign-in';
import CommonUtil from '@/utils/CommonUtil';
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

  /**
   * 进入页面就加载二维码的数据
   */
  public async mounted () {
    this.isLoading = true;
    const res = await ApiQrCode({ account: '' }, 2000);
    this.isLoading = false;
    if (res.code === 0) {
      this.getQrData();
      const qrCodeComponent = this.$refs.refQrCodeComponent as QrCodeComponentInterface;
      qrCodeComponent.qrCode('1321321311');
      // 每隔 30 S 刷新一次二维码
      setTimeout(() => this.refreshQrCode(), 5000);
    }
  }

  /**
   * 刷新二维码
   */
  public async refreshQrCode () {
    const res = await ApiQrCode({ account: '' });
    if (res.code === 0) {
      const qrCodeComponent = this.$refs.refQrCodeComponent as QrCodeComponentInterface;
      qrCodeComponent.qrCode(CommonUtil.uuid());
      console.log('312312321 ==> ', qrCodeComponent);
    }
  }

  /**
   * 轮询获取数据
   */
  private getQrData () {
    const f = async () => {
      const res = await ApiQrData({ account: '' });
      if (res.code !== 0) {
        console.log('正确  success ...');
      } else {
        setTimeout(() => f(), 1200);
      }
    };
    f();
  }
}
</script>
