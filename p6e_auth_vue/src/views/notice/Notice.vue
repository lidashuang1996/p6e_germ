<template>
  <!-- p6e auth notice -->
  <div class="notice">
    <div class="notice-title">提示</div>
    <div class="notice-content">
      <div class="notice-content-img">
        <div class="notice-content-left">
          <img src="../../assets/logo/logo_a.png" alt="LEFT_LOGO"/>
          <span>1111</span>
        </div>
        <div class="notice-content-interaction">
          <SwapRightOutlined class="interaction-top"/>
          <SwapRightOutlined class="interaction-bottom"/>
        </div>
        <div class="notice-content-right">
          <img src="../../assets/logo/logo_b.png" alt="RIGHT_LOGO"/>
          <span>2222</span>
        </div>
      </div>
      <div class="notice-content-text">
        <p>你正在进行 P6E 将授权给 VUE，确认代表同意相关协议。</p>
      </div>
    </div>
    <div class="notice-button">
      <a-button type="primary"
                :loading="isLoading"
                @click.stop="confirm"
                block="block"
                class="button">
        <span v-if="!isLoading">确 认</span>
      </a-button>
    </div>
  </div>
</template>

<script lang="ts">
import {
  SwapRightOutlined
} from '@ant-design/icons-vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    SwapRightOutlined
  }
})
/* eslint-disable */
export default class Notice extends Vue {
  /** 是否为加载中 */
  public isLoading = false;
  /**
   * 登录确认的方法
   */
  public async confirm () {
    this.isLoading = true;
  }
}
</script>
