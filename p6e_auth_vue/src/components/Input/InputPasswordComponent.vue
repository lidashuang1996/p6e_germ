<template>
  <div class="input-password-component input-component">
     <label class="input">
       <input
         :type="inputType"
         :placeholder="placeholder"
         :maxlength="maxlength"
         v-model="value"
         @blur="blurEvent"
         @focus="focusEvent"
       />
     </label>
    <label class="ico" @click.stop="inputTypeSwitch">
      <EyeInvisibleOutlined v-if="inputType === 'password'"/>
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
  emits: ['focus'],
  components: {
    EyeOutlined,
    EyeInvisibleOutlined
  }
})
export default class InputPasswordComponent extends Vue implements InputInterface {
  public value = '';
  public error = '';
  public maxlength = 24;
  public inputType = 'password';
  public placeholder = '请输入密码';

  /**
   * 输入框的类型切换
   */
  public inputTypeSwitch () {
    this.inputType = this.inputType === 'password' ? 'text' : 'password';
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
      this.error = '密码不能为空';
      return false;
    }
    if (value.length < 5) {
      this.error = '密码长度不能低于5位';
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
