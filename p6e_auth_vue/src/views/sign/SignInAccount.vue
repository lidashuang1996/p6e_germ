<template>
  <!-- p6e auth sign in account -->
  <div class="sign-in-account sign-container">
    <!-- 账号登录 / 账号密码登录，免密码登录 -->
    <div class="sign-in-account-tab">
      <ul>
        <li :class="tab === 'NP' && 'active'"
            @click.stop="tabSwitch('NP')"
            v-text="$t('sign.in.ap.np.title')"></li>
        <li :class="tab === 'YP' && 'active'"
            @click.stop="tabSwitch('YP')"
            v-text="$t('sign.in.ap.yp.title')"></li>
      </ul>
    </div>
    <!-- 内容 -->
    <div class="sign-in-account-content">
      <sign-account-password v-if="tab === 'YP'"/>
      <sign-account-no-password v-if="tab === 'NP'"/>
    </div>
    <!-- 协议内容 -->
    <div class="sign-in-account-agreement">
      <p>
        <template :key="index" v-for="(item, index) in $tm('sign.in.ap.agreement')">
          <span v-text="item.content" v-if="item.type === 'text'"></span>
          <a target="_blank" :href="item.url" v-text="item.content" v-if="item.type === 'link'"></a>
        </template>
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
   * NP 免密登录
   * YP 密码登录
   */
  public tab = 'NP';

  /**
   * 菜单切换
   */
  public tabSwitch (index: string) {
    this.tab = index;
  }
}
</script>
