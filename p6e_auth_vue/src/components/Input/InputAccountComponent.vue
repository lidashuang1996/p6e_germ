<template>
  <div class="input-account-component input-component">
    <label class="input">
      <input
        type="text"
        v-model="value"
        :placeholder="placeholder"
        :maxlength="maxlength"
        @blur="blurEvent"
        @focus="focusEvent"/>
    </label>
    <p class="input-error" v-if="error !== ''" v-text="'* ' + error"></p>
  </div>
</template>

<script lang="ts">
import {
  EyeOutlined,
  EyeInvisibleOutlined
} from '@ant-design/icons-vue';
import { InputInterface } from '../components';
import { Options, Vue } from 'vue-class-component';

@Options({
  props: {
    errors: Array,
    maxlength: Number,
    placeholder: String
  },
  emits: ['focus'],
  components: {
    EyeOutlined,
    EyeInvisibleOutlined
  }
})
export default class InputAccountComponent extends Vue implements InputInterface {
  /** 输入框的内容 */
  private value = '';
  /** 错误提示的内容 */
  private error = '';
  /** PROPS 参数 -- 异常提示列表 */
  private errors = [];
  /** PROPS 参数 -- 最大输入长度 */
  private maxlength = 32;
  /** PROPS 参数 -- 输入框默认的内容 */
  private placeholder = '请输入邮箱/手机号码';

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
   * 验证方法
   */
  public test (): boolean {
    const value = this.value;
    if (value === '') {
      this.error = this.errors[0];
      return false;
    }
    if (!this.testEmail(value) && !this.testPhone(value)) {
      this.error = this.errors[1];
      return false;
    }
    this.error = '';
    return true;
  }

  /**
   * 验证是否为电话号码
   */
  public testPhone (content: string): boolean {
    const reg = /^[1][34578][0-9]{9}$/;
    return reg.test(content);
  }

  /**
   * 验证是否为邮箱
   */
  public testEmail (content: string): boolean {
    const reg = /^([a-zA-Z]|[0-9])(\w|-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    return reg.test(content);
  }

  /**
   * 关闭错误提示
   */
  public closePrompt () {
    this.error = '';
  }

  /**
   * 返回数据框的数据
   */
  public getData (): string {
    return this.value;
  }
}
</script>
