<template>
  <div class="p6e-layout-header-user">
    <a-avatar size="small" style="background-color: #1890ff">
      <template #icon><UserOutlined /></template>
    </a-avatar>
    <CaretDownOutlined class="p6e-layout-header-mark"/>
  </div>
</template>
<script lang="ts">
import { UserOutlined, CaretDownOutlined } from '@ant-design/icons-vue';
import { Options, Vue } from 'vue-class-component';

@Options({
  components: {
    UserOutlined,
    CaretDownOutlined
  }
})
export default class LayoutHeaderUser extends Vue {
  private collapsed = false;
  private selectedKeys = '1';
}
</script>
