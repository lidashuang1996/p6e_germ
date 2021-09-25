<template>
  <div class="p6e-layout-header-search">
    <input type="text"
           v-model="input"
           placeholder="搜索产品、文档..." />
  </div>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
@Options({})
export default class LayoutHeaderSearch extends Vue {
  private input = '';
}
</script>
