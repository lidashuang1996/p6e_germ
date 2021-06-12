<template>
  <!-- p6e auth sign in -->
  <div class="sign-in-code sign-container">
    <div class="title" v-text="$t('sign.in.qc.title')"></div>
    <div class="sign-in-code-content">
      <!-- 加载中的动画 -->
      <LoadingOutlined class="code-loading" v-if="isLoading"/>
      <div class="code-component-box">
        <!-- 二维码层 -->
        <div class="code-component-expiration" v-if="qrRefreshStatus" onclick="resetQrCode">
          <template v-for="(item, index) in $tm('sign.in.qc.expiration')" :key="index">
            <p v-text="item"></p>
          </template>
        </div>
        <!-- 二维码组件 -->
        <qr-code-component ref="refQrCodeComponent"/>
      </div>
    </div>
    <div class="subtitle">
      <span v-text="$t('sign.in.qc.subtitle')"></span>
    </div>
  </div>
</template>

<script lang="ts">
import {
  LoadingOutlined
} from '@ant-design/icons-vue';
import { Modal } from 'ant-design-vue';
import { Global, Utils } from '@/utils/main';
import { Options, Vue } from 'vue-class-component';
import { QrCodeComponentInterface } from '@/components/components';
import QrCodeComponent from '@/components/QrCode/QrCodeComponent.vue';
import { ApiSignInQrCode, ApiSignInQrData } from '@/http/main-sign-in';

/** 全局读取的 MARK */
const G_MARK = Global.getOauth2().mark;

@Options({
  components: {
    QrCodeComponent,
    LoadingOutlined
  }
})
export default class SignInCode extends Vue {
  /** 是否加载中 */
  private isLoading = true;
  private isClose = false;

  private qrCode = '';
  private qrMark = '';
  private qrRefreshCount = 0;
  private qrRefreshStatus = true;
  private loopTimeout: any = null;
  private refreshTimeout: any = null;

  /**
   * 进入页面就加载二维码的数据
   */
  public async mounted () {
    // 状态修改为关闭状态
    this.isClose = false;
    // 开启加载动画
    this.isLoading = true;
    // 加载请求数据
    await this.refreshQrCode();
    // 关闭加载动画
    this.isLoading = false;
    // 轮训获取二维码数据
    await this.getQrData();
    // 重置请求的次数
    this.qrRefreshCount = 0;
  }

  /**
   * 关闭轮训获取数据
   */
  public unmounted () {
    // 状态修改为关闭状态
    this.isClose = true;
    if (this.loopTimeout != null) {
      clearTimeout(this.loopTimeout);
      this.loopTimeout = null;
    }
    if (this.refreshTimeout != null) {
      clearTimeout(this.refreshTimeout);
      this.refreshTimeout = null;
    }
  }

  /**
   * 重新获取二维码
   */
  public async resetQrCode () {
    // 重置请求的次数
    this.qrRefreshCount = 0;
    // 重置请求的状态
    this.qrRefreshStatus = false;
    // 开启加载动画
    this.isLoading = true;
    // 加载请求数据
    await this.refreshQrCode();
    // 关闭加载动画
    this.isLoading = false;
    // 轮训获取二维码数据
    await this.getQrData();
  }

  /**
   * 刷新二维码
   */
  private async refreshQrCode () {
    // 刷新次数累加
    this.qrRefreshCount = this.qrRefreshCount + 1;
    if (this.qrRefreshCount > 3) {
      // 超过三次就关闭刷新
      this.qrRefreshStatus = true;
      this.unmounted();
    } else {
      // 刷新数据
      const res = await ApiSignInQrCode({ mark: G_MARK });
      if (res.code === 0) {
        const qrCodeComponent = this.$refs.refQrCodeComponent as QrCodeComponentInterface;
        const url = res.data.content;
        this.qrCode = Utils.getUrlParam('code', url);
        this.qrMark = Utils.getUrlParam('mark', url);
        qrCodeComponent.qrCode(url);
      }
      // 判断是否关闭，如果没有关闭就继续执行
      if (!this.isClose) {
        this.refreshTimeout = setTimeout(() => this.refreshQrCode(), 60000);
      }
    }
  }

  /**
   * 轮询获取数据
   */
  private async getQrData () {
    const f = async () => {
      // 获取二维码里面的数据
      if (this.qrMark !== '' && this.qrCode !== '') {
        const res = await ApiSignInQrData({ mark: this.qrMark, code: this.qrCode });
        if (res.code !== 0) {
          const ree = this.$tm('error.' + res.message) as { [ key: string ]: string };
          Modal.error({
            centered: true,
            title: ree.title,
            okText: ree.okText,
            content: ree.content
          });
        }
      }
      // 判断是否关闭，如果没有关闭就继续执行
      if (!this.isClose) {
        this.loopTimeout = setTimeout(() => f(), 2200);
      }
    };
    // 开启执行获取二维码的数据
    await f();
  }
}
</script>
