<template>
  <div class="error error-404">
    <h1 class="title">404</h1>
    <p class="subtitle">很抱歉，您访问的页面不存在！</p>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({})
export default class Error404 extends Vue {
}
</script>
