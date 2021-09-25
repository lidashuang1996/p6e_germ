<template>
  <div class="p6e-container">
    <!-- 搜索 -->
    <div class="p6e-container-row">
      <a-input-search
        v-model="table.search"
        placeholder="请输入搜索内容"
        enter-button
        @search="tableSearch"/>
      <a-button type="primary" style="width: 120px; margin-left: 16px;" @click.stop="openAddModel">新增</a-button>
    </div>
    <!-- 表格 -->
    <div class="p6e-container-row">
      <a-table class="p6e-table" bordered
               :columns="table.columns"
               :data-source="table.items"
               :rowKey="record => record.id">
        <template #action="{ text }">
          <a-button class="p6e-table-button" type="link" @click.stop="">编辑</a-button>
          <a-popconfirm ok-text="确认"
                        cancel-text="取消"
                        placement="topRight"
                        @confirm="confirmDeleteData">
            <template #title>
              <p>您确认删除编号为{{ text.id }}的模版数据?</p>
            </template>
            <a-button class="p6e-table-button" type="link" danger>删除</a-button>
          </a-popconfirm>
        </template>
      </a-table>
    </div>
    <!-- 新增 -->
    <a-modal ok-text="确认"
             cancel-text="取消"
             title="消息中心-新增平台"
             :maskClosable="false"
             :visible="modal.visible"
             @ok="confirmAddData"
             @change="closeAddModel">
      <div>
        <span>名称</span>
        <a-input v-model="table.search"/>
      </div>
      <div>
        <span>权重</span>
        <a-input-number id="inputNumber" v-model:value="value" :min="1" :max="10" />
      </div>
      <div>
        <span>状态</span>
        <a-select
          ref="select"
          v-model:value="value1"
          style="width: 120px"
          @focus="focus"
          @change="handleChange"
        >
          <a-select-option value="jack">Jack</a-select-option>
          <a-select-option value="lucy">Lucy</a-select-option>
          <a-select-option value="disabled" disabled>Disabled</a-select-option>
          <a-select-option value="Yiminghe">yiminghe</a-select-option>
        </a-select>
      </div>
      <div>
        <span>处理器</span>
        <a-select
          ref="select"
          v-model:value="value1"
          style="width: 120px"
          @focus="focus"
          @change="handleChange"
        >
          <a-select-option value="jack">Jack</a-select-option>
          <a-select-option value="lucy">Lucy</a-select-option>
          <a-select-option value="disabled" disabled>Disabled</a-select-option>
          <a-select-option value="Yiminghe">yiminghe</a-select-option>
        </a-select>
      </div>
      <div>
        <span>限流规则</span>
        <a-input v-model="table.search"/>
      </div>
      <div>
        <span>配置文件</span>
        <a-input v-model="table.search"/>
      </div>
      <div>
        <span>描述</span>
        <a-input v-model="table.search"/>
      </div>
<!--      <a-input v-model:value="formState.name" />-->
<!--      <a-input v-model:value="formState.name" />-->
<!--      <a-input v-model:value="formState.name" />-->
    </a-modal>
  </div>
</template>

<script lang="ts">
import Api from '@/api/main';
import Extend from '@/extend/main';
import { Options, mixins } from 'vue-class-component';
@Options({})
export default class MessageGroupPlatform extends mixins(Extend.Base) {
  private gid = '';
  /** 表格数据 */
  private table: TableView<HttpMessageGroupPlatformListParam, HttpMessageGroupPlatformListItemDataResult> = {
    page: 1,
    size: 16,
    total: 0,
    search: '',
    loading: false,
    columns: [
      { title: '编号', dataIndex: 'id', key: 'id', width: 80, fixed: 'left' },
      { title: '名称', dataIndex: 'name', key: 'name', width: 200, ellipsis: true },
      { title: '权重', dataIndex: 'weight', key: 'weight', width: 100, ellipsis: true },
      { title: '状态', dataIndex: 'status', key: 'status', width: 100, ellipsis: true },
      { title: '处理器', dataIndex: 'actuator', key: 'actuator', width: 100, ellipsis: true },
      { title: '限流规则', dataIndex: 'limit', key: 'limit', width: 100, ellipsis: true },
      { title: '描述', dataIndex: 'describe', key: 'describe', ellipsis: true },
      { title: '配置文件', dataIndex: 'config', key: 'config', ellipsis: true },
      { title: '创建时间', dataIndex: 'createDate', key: 'createDate', width: 200, ellipsis: true },
      { title: '更新时间', dataIndex: 'updateDate', key: 'updateDate', width: 200, ellipsis: true },
      { title: '最后操作人', dataIndex: 'operate', key: 'operate', width: 110, ellipsis: true },
      { title: '操作', key: 'action', width: 110, fixed: 'right', slots: { customRender: 'action' } }
    ],
    items: []
  };

  /** 弹出层 */
  private modal: ModalView<any> = {
    visible: false,
    data: {}
  }

  /** 初始化--钩子函数 */
  public async mounted (): Promise<void> {
    this.setMenuName('消息中心-组-平台管理');
    const gid = this.getPathParam('gid');
    if (gid !== null) {
      this.gid = gid;
      await this.getTableData();
    }
  }

  /** 表格的搜索 */
  private async tableSearch (): Promise<void> {
    await this.getTableData();
  }

  /** 获取表格数据 */
  private async getTableData (): Promise<void> {
    const param: HttpMessageGroupPlatformListParam = {
      groupId: this.gid,
      page: this.table.page,
      size: this.table.size
    };
    this.table.loading = true;
    const res = await Api.message.getGroupPlatformList(param);
    this.table.loading = false;
    if (res.code === 0) {
      this.table.items = res.data.list;
      this.table.total = res.data.total;
    }
  }

  /** 打开弹出层 */
  private openAddModel (): void {
    this.modal.visible = true;
  }

  /** 关闭弹出层 */
  private closeAddModel (): void {
    this.modal.visible = false;
  }

  /** 确认新增 */
  private confirmAddData (): void {
    console.log(1);
  }

  /** 确认删除 */
  private confirmDeleteData (): void {
    console.log(2);
  }
}
</script>
