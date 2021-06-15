<template>
  <!-- p6e auth sign up -->
  <div class="sign-forget-password sign-container">
    <div class="step-1" v-if="step === 1">
      <div class="title">
        <span v-text="$t('sign.fp.title')"></span>
      </div>
      <template :key="'SIGN_FP_STEP_0_CONTENT_' + index"
                v-for="(item, index) in $tm('sign.fp.step')[0].content">
        <div class="content" v-text="item[0]" v-if="item.length === 1"></div>
        <div class="content" v-html="replaceContent(item[0], item[1])" v-else></div>
      </template>
      <div class="operation">
        <!-- 账号 -->
        <InputAccountComponent ref="refInputAccount"
                               class="input-account"
                               @focus="closePrompt"
                               @keyup.enter="confirm1"
                               :errors="$tm('sign.fp.step')[0].account.errors"
                               :placeholder="$tm('sign.fp.step')[0].account.placeholder"/>
        <!-- 验证码 -->
        <InputCodeComponent ref="refInputCode"
                            class="input-code"
                            @code="obtainCode"
                            @focus="closePrompt"
                            @keyup.enter="confirm1"
                            :errors="$tm('sign.fp.step')[0].code.errors"
                            :placeholder="$tm('sign.fp.step')[0].code.placeholder"/>
        <!-- 按钮 -->
        <div class="input-button">
          <a-button type="primary"
                    block="block"
                    class="button"
                    :loading="isLoading"
                    @click.stop="confirm1">
            <span v-if="!isLoading" v-text="$tm('sign.fp.step')[0].button"></span>
          </a-button>
          <!-- 错误提示 -->
          <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
        </div>
      </div>
    </div>
    <div class="step-2" v-if="step === 2">
      <div class="title">
        <span v-text="$tm('sign.fp.title')"></span>
      </div>
      <template :key="'SIGN_FP_STEP_1_CONTENT_' + index"
                v-for="(item, index) in $tm('sign.fp.step')[1].content">
        <div class="content" v-text="item[0]" v-if="item.length === 1"></div>
        <div class="content" v-html="replaceContent(item[0], item[1])" v-else></div>
      </template>
      <div class="operation">
        <!-- 密码 -->
        <InputPasswordComponent ref="refInputPassword1"
                                class="input-password"
                                @keyup.enter="confirm2"
                                @focus="closePrompt"
                                :errors="$tm('sign.fp.step')[1].password1.errors"
                                :placeholder="$tm('sign.fp.step')[1].password1.placeholder"/>
        <!-- 密码 -->
        <InputPasswordComponent ref="refInputPassword2"
                                class="input-password"
                                @keyup.enter="confirm2"
                                @focus="closePrompt"
                                :errors="$tm('sign.fp.step')[1].password2.errors"
                                :placeholder="$tm('sign.fp.step')[1].password2.placeholder"/>
        <!-- 按钮 -->
        <div class="input-button">
          <a-button type="primary"
                    block="block"
                    class="button"
                    :loading="isLoading"
                    @click.stop="confirm2">
            <span v-if="!isLoading" v-text="$tm('sign.fp.step')[1].button"></span>
          </a-button>
          <!-- 错误提示 -->
          <p class="error" v-if="error !== ''" v-text="'* ' + error"></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import InputCodeComponent from '@/components/Input/InputCodeComponent.vue';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';
import InputPasswordComponent from '@/components/Input/InputPasswordComponent.vue';
import { Modal } from 'ant-design-vue';
import { Utils } from '@/utils/main';
@Options({
  components: {
    InputCodeComponent,
    InputAccountComponent,
    InputPasswordComponent
  }
})
export default class SignForgetPassword extends Vue {
  /** 当前的步骤 */
  private step = 2;
  /** 错误提示文本 */
  private error = '';
  /** 登录是否加载中 */
  private isLoading = false;

  private ti () {
    Modal.error({
      centered: true,
      title: '提示',
      okText: '确定',
      content: '服务暂未开通'
    });
  }

  private confirm1 () {
    this.ti();
  }

  private confirm2 () {
    this.ti();
  }

  /**
   * 获取 CODE 的事件
   */
  private async obtainCode () {
    this.ti();
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
