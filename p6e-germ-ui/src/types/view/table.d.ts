/** 表格视图 */
declare interface TableColumnView {
  title: string;
  key: string;
  fixed?: string;
  width?: number;
  dataIndex?: string;
  ellipsis?: boolean;
  slots?: { customRender: string };
}

declare interface TableView<T, W> {
  page: number;
  size: number;
  total: number;
  loading: boolean;
  search?: string;
  param?: T;
  columns: TableColumnView[];
  items: W[];
}
/** ------------------------ */

declare interface ModalView<T> {
  visible: boolean;
  data?: T;
}
