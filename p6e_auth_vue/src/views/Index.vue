<template>
  <!-- p6e sign index -->
  <div class="index">
    <!-- p6e sign index container -->
    <div class="index-container" v-show="isIndex" :style="backgroundStyle">
      <div class="index-main">
        <!-- logo -->
        <div class="index-logo" :style="marginStyle">
          <img src="../assets/logo/logo.png" alt="LOGO"/>
        </div>
        <!-- main -->
        <IndexContent></IndexContent>
        <!-- footer -->
        <div class="index-footer" :style="marginStyle">
          <p class="index-footer-row"
             :key="'INDEX_FOOTER_ROW_' + i"
             v-for="(item, i) in $tm('index.footer')">
            <a class="index-footer-nav"
               v-text="it.name"
               v-for="(it, j) in item"
               :href="it.url"
               :target="it.target"
               :key="'INDEX_FOOTER_ROW_' + i + '_NAV_' + j">
            </a>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Window } from '@/utils/main';
import IndexContent from './IndexContent.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    IndexContent
  }
})
export default class Index extends Vue {
  /** 是否显示主体内容 */
  private isIndex = false;
  /** margin 样式 */
  private marginStyle = '';
  /** margin 样式 */
  private backgroundStyle = '';

  /**
   * 勾子函数
   */
  public async mounted (): Promise<void> {
    /** 注册窗体大小改变事件，且立即执行 */
    Window.onResizeEvent((e) => {
      if (e.height > 750) {
        this.marginStyle = 'margin-top: ' + ((e.height - 750) / 2) + 'px;';
      }
      if (e.width / e.height >= 1920 / 1080) {
        this.backgroundStyle = 'background-size: 100% auto';
      } else {
        this.backgroundStyle = 'background-size: auto 100%';
      }
    }, true);
    /** 显示主体的内容数据 */
    this.isIndex = true;
  }
}
</script>
