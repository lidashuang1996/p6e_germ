<template>
  <a-layout style="min-height: 100vh">
    <a-layout-header class="p6e-layout-header">
      <div class="p6e-layout-header-left">
        <LayoutHeaderLogo/>
      </div>
      <div class="p6e-layout-header-right">
        <LayoutHeaderSearch/>
        <LayoutHeaderUser/>
      </div>
    </a-layout-header>
    <a-layout>
      <a-layout-sider v-model="collapsed" collapsible style="padding: 16px 0;">
        <a-menu theme="dark" v-model="selectedKeys" mode="inline">
          <a-menu-item key="1">
            <pie-chart-outlined />
            <span>首页</span>
          </a-menu-item>
          <a-sub-menu key="sub1">
            <template #title>
            <span>
              <user-outlined />
              <span>认证中心</span>
            </span>
            </template>
            <a-menu-item key="3">Tom</a-menu-item>
            <a-menu-item key="4">Bill</a-menu-item>
            <a-menu-item key="5">Alex</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <template #title>
            <span>
              <team-outlined />
              <span>权限中心</span>
            </span>
            </template>
            <a-menu-item key="6">Team 1</a-menu-item>
            <a-menu-item key="8">Team 2</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="s2ub2">
            <template #title>
            <span>
              <team-outlined />
              <span>消息中心</span>
            </span>
            </template>
            <a-menu-item key="1621">首页</a-menu-item>
            <a-menu-item key="1822">组管理</a-menu-item>
            <a-menu-item key="1823">平台管理</a-menu-item>
            <a-menu-item key="1824">模版管理</a-menu-item>
            <a-menu-item key="1825">日志管理</a-menu-item>
          </a-sub-menu>
          <a-menu-item key="9">
            <file-outlined />
            <span>账号中心</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout-content class="p6e-layout-content">
        <a-row class="p6e-layout-content-title">
          <a-col><h2>{{ menu.name }}</h2></a-col>
        </a-row>
        <router-view @menu-name="setMenuName"/>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script lang="ts">
import {
  PieChartOutlined,
  DesktopOutlined,
  UserOutlined,
  TeamOutlined,
  FileOutlined
} from '@ant-design/icons-vue';
import { Options, Vue } from 'vue-class-component';
import LayoutHeaderLogo from '@/layout/LayoutHeaderLogo.vue';
import LayoutHeaderSearch from '@/layout/LayoutHeaderSearch.vue';
import LayoutHeaderUser from '@/layout/LayoutHeaderUser.vue';

@Options({
  components: {
    LayoutHeaderUser,
    LayoutHeaderSearch,
    LayoutHeaderLogo,
    PieChartOutlined,
    DesktopOutlined,
    UserOutlined,
    TeamOutlined,
    FileOutlined
  }
})
export default class LayoutApp extends Vue {
  private collapsed = false;
  private selectedKeys = '1';
  private menu = {
    name: ''
  };

  private setMenuName (name: string): void {
    this.menu.name = name;
  }
}
</script>
<style lang="scss">
.p6e-layout-header {
  background: #262f3e !important;
  height: 50px !important;
  line-height: 50px !important;
  padding: 0 20px !important;
  display: flex !important;
  align-items: center !important;
  justify-content: space-between !important;
  .p6e-layout-header-left, .p6e-layout-header-right {
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }
}
.p6e-layout-content {
  .p6e-layout-content-title {
    background: #ffffff;
    padding: 12px 24px;
    margin-bottom: 24px;
    border-bottom: 1px solid #ddd;
    h2 {
      padding: 0;
      margin: 0;
      font-size: 18px;
      font-weight: 800;
    }
  }
}
</style>
