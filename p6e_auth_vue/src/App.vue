<template>
  <Index/>
</template>
<script lang="ts">
import Index from '@/views/Index.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    Index
  }
})
export default class App extends Vue {}
</script>
