<template>
  <!-- p6e sign index -->
  <div class="index">
    <div class="index-container" v-show="isShow">
      <div class="index-main">
        <!-- logo -->
        <div class="index-logo" :style="windowStyle">
          <img src="../assets/logo/logo.png" alt="logo"/>
        </div>
        <!-- main -->
        <div class="index-content">
          <router-view/>
        </div>
        <!-- footer -->
        <div class="index-footer" :style="windowStyle">
          <p class="index-footer-row">
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">知乎专栏</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">圆桌</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">发现</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">移动应用</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">联系我们</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">移动应用</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">来知乎工作</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">注册机构号</a>
          </p>
          <p class="index-footer-row">
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">© 2021 知乎</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">京 ICP 证 110745 号</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">京 ICP 备 13052560 号 - 1</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">京公网安备 11010802020088 号</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">出版物经营许可证</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">互联网药品信息服务资格证书（京）- 非经营性 - 2017 - 0067</a>
          </p>
          <p class="index-footer-row">
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">侵权举报</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">网上有害信息举报专区</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">儿童色情信息举报专区</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">违法和不良信息举报：010-82716601</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({})
export default class Index extends Vue {
  public isShow = false;
  public windowStyle = '';

  public mounted (): void {
    this.windowStyleCalculation();
    window.onresize = () => {
      this.windowStyleCalculation();
    };
    this.isShow = true;
  }

  /**
   * 窗体的样式的计算方法
   */
  public windowStyleCalculation () {
    const height = Number(document.body.clientHeight);
    if (height > 750) {
      this.windowStyle = 'margin-top: ' + ((height - 750) / 2) + 'px;';
    }
  }
}
</script>
