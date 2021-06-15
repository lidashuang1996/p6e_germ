<template>
  <div class="input-account-component input-component">
    <label class="input">
      <input
        type="text"
        v-model="value"
        :placeholder="placeholder"
        :maxlength="maxlength"
        @input="inputEvent"
        @blur="blurEvent"
        @focus="focusEvent"
      />
    </label>
    <label class="button">
      <a-button type="link"
                @click.stop="obtainCode"
                v-if="codeDateTime === 0">
        <span v-text="$t('sign.in.ap.np.code.button.obtain')"></span>
      </a-button>
      <a-button type="link"
                :disabled="true"
                v-if="codeDateTime !== 0">
        <span v-text="$t('sign.in.ap.np.code.button.countdown', { data: codeDateTime })"></span>
      </a-button>
    </label>
    <p class="input-error" v-if="error !== ''" v-text="'* ' + error"></p>
  </div>
</template>

<script lang="ts">
import { CodeInputInterface } from '../components';
import { Options, Vue } from 'vue-class-component';

@Options({
  props: {
    errors: Array,
    maxlength: Number,
    placeholder: String
  },
  emits: ['focus', 'code']
})
export default class InputCodeComponent extends Vue implements CodeInputInterface {
  /** 输入框的内容 */
  private value = '';
  /** 错误提示的内容 */
  private error = '';
  /** PROPS 参数 -- 异常提示列表 */
  private errors = [];
  /** PROPS 参数 -- 最大输入长度 */
  private maxlength = 6;
  /** PROPS 参数 -- 输入框默认的内容 */
  private placeholder = '请输入验证码';
  /** 从缓存中读取的登录验证编码 */
  public codeDateTime = 0;
  /** 倒计时缓存对象 */
  private countDownTimeout: any = null;

  /**
   * 摧毁倒计时
   */
  public unmounted () {
    this.closeCountDownTimeout();
  }

  /**
   * 失去焦点
   */
  private blurEvent () {
    this.test();
  }

  /**
   * 获取焦点
   */
  private focusEvent () {
    this.$emit('focus');
    this.closePrompt();
  }

  /**
   * 输入事件
   * 只允许数字输入
   */
  private inputEvent () {
    let v = '';
    const value = this.value;
    for (let i = 0; i < value.length; i++) {
      if (value.charAt(i) >= '0' && value.charAt(i) <= '9') {
        v += value.charAt(i);
      }
    }
    this.value = v;
  }

  /**
   * 关闭错误提示
   */
  private closePrompt () {
    this.error = '';
  }

  /**
   * 关闭倒计时
   */
  private closeCountDownTimeout () {
    if (this.countDownTimeout !== null) {
      clearTimeout(this.countDownTimeout);
      this.countDownTimeout = null;
    }
  }

  /**
   * 获取登录验证码
   */
  private obtainCode () {
    this.$emit('code');
  }

  /**
   * 验证方法
   */
  public test (): boolean {
    const value = this.value;
    if (value === '') {
      this.error = this.errors[0];
      return false;
    }
    if (value.length !== 6) {
      this.error = this.errors[1];
      return false;
    }
    this.error = '';
    return true;
  }

  /**
   * 倒计时
   */
  public countdown (num: number | null) {
    if (this.codeDateTime <= 0) {
      this.closeCountDownTimeout();
      this.codeDateTime = num === null ? 60 : num;
      const f = () => {
        if (this.codeDateTime <= 0) {
          this.codeDateTime = 0;
        } else {
          this.codeDateTime -= 1;
          this.countDownTimeout = setTimeout(() => f(), 1000);
        }
      };
      f();
    }
  }

  /**
   * 返回数据框的数据
   */
  public getData (): string {
    return this.value;
  }
}
</script>
