<template>
  <LayoutApp/>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import LayoutApp from '@/layout/LayoutApp.vue';

@Options({
  components: {
    LayoutApp
  }
})
export default class App extends Vue {}
</script>
