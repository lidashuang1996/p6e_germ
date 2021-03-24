<template>
  <!-- p6e auth sign up -->
  <div class="sign-account-no-password">
    <form class="sign-account-mo-password-form">
      <InputAccountComponent/>
      <a-button type="primary"  @mouseenter="loading = true" block style="margin-top: 32px; height: 42px; border-right: 2px; overflow: hidden;">mouseenter me!</a-button>
    </form>
  </div>
</template>

<script lang="ts">
import InputPasswordComponent from '@/components/Input/InputPasswordComponent.vue';
import InputAccountComponent from '@/components/Input/InputAccountComponent.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    InputAccountComponent,
    InputPasswordComponent
  }
})
/* eslint-disable */
export default class SignAccountNoPassword extends Vue {
}
</script>
