<template>
  <!-- p6e auth sign up -->
  <div class="sign-up" style="min-height: 493px;">
    <div class="set-initial-password" v-show="false">
      <div class="title">设置密码</div>
      <div class="content">初次登录的账号「手机号/邮箱」需要设置初始密码，设置初始密码以后可以通过账号密码登录的方法，进行登录操作。</div>
      <!-- 密码 -->
      <InputPasswordComponent class="input-password" @keyup.enter="confirm"/>
      <!-- 密码 -->
      <InputPasswordComponent class="input-password" @keyup.enter="confirm"/>
      <!-- 按钮 -->
      <div class="input-button">
        <a-button type="primary"
                  block="block"
                  class="button"
                  :loading="isLoading"
                  @click.stop="confirm">
          <span v-if="!isLoading">确 认</span>
        </a-button>
        <!-- 错误提示 -->
        <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
      </div>
    </div>
    <div class="bind-account">
      <div class="title">绑定账号</div>
      <div class="content">使用第三方平台初次登录时候，需要绑定一下账号「手机号/邮箱」，绑定账号后可以通过第三方平台直接登录绑定账号。</div>
      <!-- 账号 -->
      <InputAccountComponent class="input-account"  @keyup.enter="confirm"/>
      <!-- 验证码 -->
      <InputCodeComponent class="input-code"  @keyup.enter="confirm"/>
      <!-- 按钮 -->
      <div class="input-button">
        <a-button type="primary"
                  block="block"
                  class="button"
                  :loading="isLoading"
                  @click.stop="confirm">
          <span v-if="!isLoading">确 认</span>
        </a-button>
        <!-- 错误提示 -->
        <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import InputCodeComponent from '@/components/Input/InputCodeComponent.vue';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';
import InputPasswordComponent from '@/components/Input/InputPasswordComponent.vue';
@Options({
  components: {
    InputCodeComponent,
    InputAccountComponent,
    InputPasswordComponent
  }
})
/* eslint-disable */
export default class SignUp extends Vue {
  /** 错误提示文本 */
  public error = '';
  /** 登录是否加载中 */
  public isLoading = false;
}
</script>
