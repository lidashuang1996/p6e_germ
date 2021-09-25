<template>
  <div class="p6e-container">
    <!-- 搜索 -->
    <div class="p6e-container-row">
      <a-input-search
        :value="table.search"
        @change="e => table.search = e.target.value"
        placeholder="请输入标记序号搜索"
        enter-button
        @search="tableSearch"/>
    </div>
    <!-- 表格 -->
    <div class="p6e-container-row">
      <a-table class="p6e-table" bordered
               :loading="table.loading"
               :columns="table.columns"
               :data-source="table.items"
               :rowKey="record => record.mark">
        <template #action="{ text }">
          <a-button class="p6e-table-button" type="link" @click.stop="seeFlow(text)">流程查看</a-button>
          <a-button class="p6e-table-button" type="link" @click.stop="seeTemplate(text)">模版查看</a-button>
        </template>
      </a-table>
    </div>
    <!-- 流程查看 -->
    <a-modal ok-text="确认"
             cancel-text="取消"
             title="消息中心-流程查看"
             :footer="null"
             @change="closeFlowModel"
             :visible="flow.visible">
      <a-timeline>
        <a-timeline-item v-for="(item, index) in flow.data" :key="index">
          <p>{{ item.date }}</p>
          <p>{{ item.content }}</p>
        </a-timeline-item>
      </a-timeline>
    </a-modal>
    <!-- 模版查看 -->
    <a-modal ok-text="确认"
             cancel-text="取消"
             title="消息中心-模版查看"
             :footer="null"
             @change="closeTemplateModel"
             :visible="template.visible">
      <p>Some contents...</p>
      <p>Some contents...</p>
      <p>Some contents...</p>
    </a-modal>
  </div>
</template>

<script lang="ts">
import Api from '@/api/main';
import Extend from '@/extend/main';
import { Options, mixins } from 'vue-class-component';
@Options({})
export default class MessageLog extends mixins(Extend.Base) {
  /** 表格数据 */
  private table: TableView<HttpMessageLogListParam, HttpMessageLogListItemDataResult> = {
    page: 1,
    size: 16,
    total: 0,
    search: '',
    loading: false,
    param: {},
    columns: [
      { title: '标记', dataIndex: 'mark', key: 'mark', width: 180 },
      { title: '来源', dataIndex: 'source', key: 'source', width: 100, ellipsis: true },
      { title: '平台', dataIndex: 'pid', key: 'pid', width: 100, ellipsis: true },
      { title: '模版', dataIndex: 'tid', key: 'tid', width: 100, ellipsis: true },
      { title: '状态', dataIndex: 'status', key: 'status', width: 100, ellipsis: true },
      { title: '操作', key: 'action', width: 110, fixed: 'right', slots: { customRender: 'action' } }
    ],
    items: []
  };

  /** 弹出层 */
  private flow: ModalView<HttpMessageLogListItemFlowDataResult[]> = { visible: false, data: [] };

  /** 弹出层 */
  private template: ModalView<never> = { visible: false };

  /** 初始化--钩子函数 */
  public async mounted (): Promise<void> {
    this.setMenuName('消息中心-日志管理');
    await this.getTableData();
  }

  /** 表格的搜索 */
  private async tableSearch (): Promise<void> {
    console.log(this.table.search);
    if (this.table.param !== undefined && this.table.search !== '') {
      this.table.param.mark = this.table.search;
    }
    await this.getTableData();
  }

  /** 获取表格数据 */
  private async getTableData (): Promise<void> {
    const param: HttpMessageLogListParam = {
      ...this.table.param,
      page: this.table.page,
      size: this.table.size
    };
    this.table.loading = true;
    const res = await Api.message.getLogList(param);
    this.table.loading = false;
    if (res.code === 0) {
      res.data.list.forEach(item => {
        item.status = item.items[item.items.length - 1].status;
      });
      this.table.items = res.data.list;
      this.table.total = res.data.total;
    }
  }

  /** 打开流层弹出层 */
  private openFlowModel (items: HttpMessageLogListItemFlowDataResult[]): void {
    this.flow.visible = true;
    this.flow.data = items;
  }

  /** 关闭流层弹出层 */
  private closeFlowModel (): void {
    this.flow.visible = false;
  }

  /** 打开模版弹出层 */
  private openTemplateModel (): void {
    this.template.visible = true;
  }

  /** 关闭模版弹出层 */
  private closeTemplateModel (): void {
    this.template.visible = false;
  }

  /** 流层查看 */
  private seeFlow ({ items }: { items: HttpMessageLogListItemFlowDataResult[] }): void {
    this.openFlowModel(items);
  }

  /** 模版查看 */
  private seeTemplate ({ mark }: { mark: string }): void {
    console.log(mark);
    this.openTemplateModel();
  }
}
</script>
