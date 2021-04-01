<template>
  <router-view/>
<!--  <error-404/>-->
</template>
<script lang="ts">
import Error404 from '@/views/error/Error404.vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    Error404
  }
})
export default class App extends Vue {
}
</script>
