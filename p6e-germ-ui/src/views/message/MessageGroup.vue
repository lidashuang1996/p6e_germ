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
             title="消息中心-新增组"
             :maskClosable="false"
             :visible="modal.visible"
             @ok="confirmAddData"
             @change="closeAddModel">
      <p>Some contents...</p>
      <p>Some contents...</p>
      <p>Some contents...</p>
    </a-modal>
  </div>
</template>

<script lang="ts">
import Api from '@/api/main';
import { Options, Vue } from 'vue-class-component';
@Options({})
export default class MessageGroup extends Vue {
  /** 表格数据 */
  private table: TableView<never, never> = {
    page: 1,
    size: 16,
    total: 0,
    search: '',
    loading: false,
    columns: [
      { title: '编号', dataIndex: 'id', key: 'id', width: 80, fixed: 'left' },
      { title: '名称', dataIndex: 'name', key: 'name', width: 200, ellipsis: true },
      { title: '状态', dataIndex: 'status', key: 'status', width: 70, ellipsis: true },
      { title: '类型', dataIndex: 'type', key: 'type', width: 70, ellipsis: true },
      { title: '限流规则', dataIndex: 'limit', key: 'limit', width: 100, ellipsis: true },
      { title: '路由规则', dataIndex: 'route', key: 'route', width: 100, ellipsis: true },
      { title: '描述', dataIndex: 'describe', key: 'describe', ellipsis: true },
      { title: '创建时间', dataIndex: 'createDate', key: 'createDate', width: 200, ellipsis: true },
      { title: '更新时间', dataIndex: 'updateDate', key: 'updateDate', width: 200, ellipsis: true },
      { title: '最后操作人', dataIndex: 'operate', key: 'operate', width: 110, ellipsis: true },
      { title: '操作', key: 'action', width: 110, fixed: 'right', slots: { customRender: 'action' } }
    ],
    items: []
  };

  /** 弹出层 */
  private modal = {
    visible: false
  }

  /** 初始化--钩子函数 */
  public async mounted (): Promise<void> {
    await this.getTableData();
  }

  /** 表格的搜索 */
  private async tableSearch (): Promise<void> {
    await this.getTableData();
  }

  /** 获取表格数据 */
  private async getTableData (): Promise<void> {
    const param: HttpMessageGroupListParam = {
      page: this.table.page,
      size: this.table.size
    };
    this.table.loading = true;
    const res = await Api.message.getGroupList(param);
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
