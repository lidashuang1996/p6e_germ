<template>
  <!-- 登录/注册/确认授权/忘记密码/绑定账号 内容 -->
  <div class="index-content">
    <!-- 登录/注册 -->
    <SignIn v-if="type === 'sign'"/>
    <!-- 确认授权 -->
    <Notice v-if="type === 'auth'"/>
    <!-- 绑定账号 -->
    <SignBindAccount v-if="type === 'bind_account'"/>
    <!-- 忘记密码 -->
    <SignForgetPassword v-if="type === 'forget_password'"/>
    <!-- 数据出现异常 内容 -->
    <div class="index-content-null" v-if="type === ''">
      <a-result
        :title="$t('index.content.empty.title')"
        :status="$t('index.content.empty.status')">
        <template #extra>
          <a-button
            block
            size="large"
            type="primary"
            @click="backToHomePage">
            <span v-text="$t('index.content.empty.button')"></span>
          </a-button>
        </template>
      </a-result>
    </div>
  </div>
</template>

<script lang="ts">
import { Global, Utils } from '@/utils/main';
import SignIn from '@/views/sign/SignIn.vue';
import Notice from '@/views/notice/Notice.vue';
import { Options, Vue } from 'vue-class-component';
import SignBindAccount from '@/views/sign/SignBindAccount.vue';
import SignForgetPassword from '@/views/sign/SignForgetPassword.vue';

@Options({
  components: {
    SignIn,
    Notice,
    SignBindAccount,
    SignForgetPassword
  }
})
export default class IndexContent extends Vue {
  /** 页面类型 */
  private type = '';

  /**
   * 勾子函数
   */
  public mounted () {
    const oauth2 = Global.getOauth2();
    if (Utils.isNotEmpty(oauth2) && Utils.isNotEmpty(oauth2.type)) {
      switch (oauth2.type) {
        case 'auth': // 授权页面
        case 'sign': // 登录/注册页面
        case 'bind_account': // 绑定账号
        case 'forget_password': // 找回密码
          this.type = oauth2.type;
          break;
      }
    }
  }

  /**
   * 返回首页
   */
  private backToHomePage () {
    window.location.href = this.$t('index.url');
  }
}
</script>
