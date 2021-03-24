<template>
  <!-- p6e auth sign in -->
  <div class="sign-in-code sign-container">
    <div class="title">扫码登录</div>
    <div class="sign-in-code-content">
      <!-- 加载中的动画 -->
      <LoadingOutlined class="code-loading" v-if="isLoading"/>
      <!-- 二维码组件 -->
      <qr-code-component ref="refQrCodeComponent" v-show="!isLoading"/>
    </div>
    <div class="subtitle">打开 APP 点开扫一扫，即可完成登录</div>
  </div>
</template>

<script lang="ts">
import {
  LoadingOutlined
} from '@ant-design/icons-vue';
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
  public mounted () {
    const qrCodeComponent = this.$refs.refQrCodeComponent as QrCodeComponentInterface;
    qrCodeComponent.qrCode('1321321311');
    setInterval(() => {
      this.refreshQrCode();
    }, 10000);
  }

  /**
   * 刷新二维码
   */
  public refreshQrCode () {
    const qrCodeComponent = this.$refs.refQrCodeComponent as QrCodeComponentInterface;
    qrCodeComponent.qrCode(CommonUtil.uuid());
  }
}
</script>
