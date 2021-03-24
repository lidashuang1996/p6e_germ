<template>
  <div class="input-account-component input-component">
     <label class="input">
       <input
         type="text"
         v-model="value"
         :placeholder="placeholder"
         :maxlength="maxlength"
         @blur="blurEvent"
         @focus="focusEvent"
       />
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
  emits: ['focus'],
  components: {
    EyeOutlined,
    EyeInvisibleOutlined
  }
})
export default class InputPasswordComponent extends Vue implements InputInterface {
  public placeholder = '请输入手机号码/邮箱';
  public maxlength = 32;
  public value = '';
  public error = '';

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
      this.error = '账号不能为空';
      return false;
    }
    if (value === '123456') {
      this.error = '账号格式错误';
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
   * 返回数据框的数据
   */
  public getData (): string {
    return this.value;
  }
}
</script>
