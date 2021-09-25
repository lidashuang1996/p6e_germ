declare interface HttpMessageGroupListParam extends HttpBaseParam {
  name?: string;
  //
  // private Integer status;
  // private String type;
  // private String limit;
  // private String route;
  // private String name;
  // private String describe;
}

declare type HttpMessageGroupListResult = HttpBaseResult<any>;

declare interface HttpMessageGroupPlatformListParam extends HttpBaseParam {
  groupId: string;
}
declare interface HttpMessageGroupPlatformListItemDataResult {
  actuator: string | null;
  config: string;
  createDate: string;
  describe: string;
  id: number;
  limit: string;
  name: string;
  operate: string;
  status: number;
  updateDate: string;
  weight: number;
}
declare interface HttpMessageGroupPlatformListDataResult {
  list: HttpMessageGroupPlatformListItemDataResult[];
  page: number;
  size: number;
  total: number;
}

declare type HttpMessageGroupPlatformListResult = HttpBaseResult<any>;

declare interface HttpMessageLogListParam extends HttpBaseParam {
  mark?: string;
}

declare interface HttpMessageLogListItemFlowDataResult {
  id: number;
  status: number;
  content: string;
  date: string;
}

declare interface HttpMessageLogListItemDataResult {
  items: HttpMessageLogListItemFlowDataResult [];
  mark: string;
  pid: number;
  tid: number;
  source: string;
  status?: number;
}

declare interface HttpMessageLogListDataResult {
  list: HttpMessageLogListItemDataResult[];
  page: number;
  size: number;
  total: number;
}

declare type HttpMessageLogListResult = HttpBaseResult<HttpMessageLogListDataResult>;
