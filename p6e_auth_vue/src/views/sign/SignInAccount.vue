<template>
  <!-- p6e auth sign in account -->
  <div class="sign-in-account">
    <!-- 账号登录 / 账号密码登录，免密码登录 -->
    <div class="sign-in-account-tab">
      <ul>
        <li :class="tab === '1' && 'active'" @click.stop="tabSwitch('1')">免密码登录</li>
        <li :class="tab === '2' && 'active'" @click.stop="tabSwitch('2')">密码登录</li>
      </ul>
    </div>
    <!-- 内容 -->
    <div class="sign-in-account-content">
      <sign-account-no-password v-if="tab === '1'"/>
      <sign-account-password v-if="tab === '2'"/>
    </div>
    <!-- 协议内容 -->
    <div class="sign-in-account-agreement">
      <p>
        未注册账号验证后自动登录，注册即代表同意
        <a href="https://www.baidu.com" target="_blank">《XXXX协议》</a>
        <a href="https://www.baidu.com" target="_blank">《隐私保护指引》</a>
      </p>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import SignAccountPassword from './SignAccountPassword.vue';
import SignAccountNoPassword from './SignAccountNoPassword.vue';

@Options({
  components: {
    SignAccountPassword,
    SignAccountNoPassword
  }
})
export default class SignInAccount extends Vue {
  /**
   * 当前的 TAB 的索引
   * 1 免密登录
   * 2 密码登录
   */
  public tab = '2';

  /**
   * 菜单切换
   */
  public tabSwitch (index: string) {
    this.tab = index;
  }
}
</script>
