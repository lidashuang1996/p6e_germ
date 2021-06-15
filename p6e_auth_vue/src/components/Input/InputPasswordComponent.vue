<template>
  <div class="input-password-component input-component">
     <label class="input">
       <input
         :type="type"
         :placeholder="placeholder"
         :maxlength="maxlength"
         v-model="value"
         @blur="blurEvent"
         @focus="focusEvent"
       />
     </label>
    <label class="ico" @click.stop="inputTypeSwitch">
      <EyeInvisibleOutlined v-if="type === 'password'"/>
      <EyeOutlined v-else/>
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
export default class InputPasswordComponent extends Vue implements InputInterface {
  public value = '';
  /** 错误提示文本 */
  private error = '';
  /** PROPS 参数 -- 文本输入框类型 */
  public type = 'password';
  /** PROPS 参数 -- 异常提示列表 */
  private errors = [];
  /** PROPS 参数 -- 最大输入长度 */
  public maxlength = 24;
  /** PROPS 参数 -- 输入框默认的内容 */
  public placeholder = '请输入密码';

  /**
   * 输入框的类型切换
   */
  public inputTypeSwitch () {
    this.type = this.type === 'password' ? 'text' : 'password';
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
   * 验证方法
   */
  public test (): boolean {
    const value = this.value;
    if (value === '') {
      this.error = this.errors[0];
      return false;
    }
    if (value.length < 5) {
      this.error = this.errors[1];
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
