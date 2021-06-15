<template>
  <!-- p6e auth sign up -->
  <div class="sign-bind-account sign-container">
    <div class="title">
      <span v-text="$t('sign.ba.title')"></span>
    </div>
    <template :key="'SIGN_BA_CONTENT_' + index"
              v-for="(item, index) in $tm('sign.ba.content')">
      <div class="content" v-text="item[0]" v-if="item.length === 1"></div>
      <div class="content" v-html="replaceContent(item[0], item[1])" v-else></div>
    </template>
    <div class="operation">
      <!-- 账号 -->
      <InputAccountComponent ref="refInputAccount"
                             class="input-account"
                             @focus="closePrompt"
                             @keyup.enter="confirm"
                             :errors="$tm('sign.ba.account.errors')"
                             :placeholder="$t('sign.ba.account.placeholder')"/>
      <!-- 验证码 -->
      <InputCodeComponent ref="refInputCode"
                          class="input-code"
                          @code="obtainCode"
                          @focus="closePrompt"
                          @keyup.enter="confirm"
                          :errors="$tm('sign.ba.code.errors')"
                          :placeholder="$t('sign.ba.code.placeholder')"/>
      <!-- 按钮 -->
      <div class="input-button">
        <a-button type="primary"
                  block="block"
                  class="button"
                  :loading="isLoading"
                  @click.stop="confirm">
          <span v-if="!isLoading" v-text="$tm('sign.ba.button')"></span>
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
import { Utils } from '@/utils/main';
@Options({
  components: {
    InputCodeComponent,
    InputAccountComponent,
    InputPasswordComponent
  }
})
export default class SignBindAccount extends Vue {
  /** 错误提示文本 */
  public error = '';
  /** 登录是否加载中 */
  public isLoading = false;

  /**
   * 获取 CODE 的事件
   */
  private async obtainCode () {
    console.log('123');
  }

  /**
   * 关闭错误提示
   */
  private closePrompt () {
    this.error = '';
  }

  /**
   * 替换内容
   */
  public replaceContent (content: string, obj: { [key: string]: { [key: string]: string } }): string {
    for (const key in obj) {
      if (Object.prototype.hasOwnProperty.call(obj, key)) {
        const p = {} as { [key: string]: string };
        const value = obj[key];
        p[key] = '<a href="' + value.url + '">' + value.name + '</a>';
        content = Utils.translate(content, p);
      }
    }
    return content;
  }
}
</script>
