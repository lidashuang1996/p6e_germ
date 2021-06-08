<template>
  <!-- p6e sign index -->
  <div class="index">
    <div class="index-container" v-show="isShow" :style="backgroundStyle">
      <div class="index-main">
        <!-- logo -->
        <div class="index-logo" :style="marginStyle">
          <img src="../assets/logo/logo.png" alt="LOGO"/>
        </div>
        <!-- main -->
        <IndexContent></IndexContent>
        <!-- footer -->
        <div class="index-footer" :style="marginStyle">
          <p class="index-footer-row">
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">AAA</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">BBB</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">CCC</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">DDDDDDD</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">FFFFFFF</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">GGGGGGG</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">HHHHHHH</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">JJJJJJJ</a>
          </p>
          <p class="index-footer-row">
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">© 2021 XXXX</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">京 ICP 证 XXXX 号</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">京 ICP 备 XXXX 号 - 1</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">京公网安备 XXXXX 号</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">XXXXXXXXXXXXXXXX</a>
            <a class="index-footer-nav" target="_blank" href="javascript:void(0);">互联XXXXXXXXXXXXXXXXXXXX格证书（京）- XXXXXXXX - 2017 - 0067</a>
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
import Window from '@/utils/window';
import IndexContent from './IndexContent.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    IndexContent
  }
})
export default class Index extends Vue {
  /** 是否显示主体内容 */
  public isShow = false;
  /** margin 样式 */
  public marginStyle = '';
  /** margin 样式 */
  public backgroundStyle = '';

  /**
   * 勾子函数
   */
  public async mounted () {
    /** 窗体的样式的计算方法 */
    const f = (e: { width: number, height: number }) => {
      if (e.height > 750) {
        this.marginStyle = 'margin-top: ' + ((e.height - 750) / 2) + 'px;';
      }
      if (e.width / e.height >= 1920 / 1080) {
        this.backgroundStyle = 'background-size: 100% auto';
      } else {
        this.backgroundStyle = 'background-size: auto 100%';
      }
    };
    Window.onResizeEvent((e) => f(e), true);
    this.isShow = true;
  }
}
</script>
