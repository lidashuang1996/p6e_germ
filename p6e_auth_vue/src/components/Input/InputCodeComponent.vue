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
      <a-button type="link" v-if="codeDateTime === 0" @click.stop="obtainCode">获取验证码</a-button>
      <a-button type="link" v-if="codeDateTime !== 0" :disabled="true">{{ codeDateTime }} 秒后可重发</a-button>
    </label>
    <p class="input-error" v-if="error !== ''" v-text="'* ' + error"></p>
  </div>
</template>

<script lang="ts">
import Utils from '@/utils/main';
import { CodeInputInterface } from '../components';
import { Options, Vue } from 'vue-class-component';

@Options({
  emits: ['focus', 'code']
})
export default class InputCodeComponent extends Vue implements CodeInputInterface {
  public placeholder = '请输入验证码';
  public maxlength = 6;
  public value = '';
  public error = '';

  /** 从缓存中读取的登录验证编码 */
  public codeDateTime = 0;

  /**
   * 钩子函数，缓存中读取倒计时的事件
   */
  public mounted () {
    this.countdown(Utils.Cache.getSignInCodeCache());
  }

  /**
   * 失去焦点
   */
  public blurEvent () {
    this.test();
  }

  /**
   * 获取焦点
   */
  public focusEvent () {
    this.$emit('focus');
    this.closePrompt();
  }

  /**
   * 输入事件
   */
  public inputEvent () {
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
   * 验证方法
   */
  public test (): boolean {
    const value = this.value;
    if (value === '') {
      this.error = '验证码不能为空';
      return false;
    }
    if (value.length < 6) {
      this.error = '验证码长度为6位';
      return false;
    }
    this.error = '';
    return true;
  }

  /**
   * 关闭错误提示
   */
  public closePrompt () {
    this.error = '';
  }

  /**
   * 获取登录验证码
   */
  public obtainCode () {
    this.$emit('code');
  }

  /**
   * 倒计时
   */
  public countdown (num: number | null) {
    if (this.codeDateTime <= 0) {
      if (num !== null) {
        this.codeDateTime = num;
        Utils.Cache.setSignInCodeCache(num);
      }
      if (this.codeDateTime <= 0) {
        this.codeDateTime = 0;
      } else {
        this.codeDateTime -= 1;
        const f = () => {
          if (this.codeDateTime <= 0) {
            this.codeDateTime = 0;
          } else {
            this.codeDateTime -= 1;
            setTimeout(() => f(), 1000);
          }
        };
        setTimeout(() => f(), 1000);
      }
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
