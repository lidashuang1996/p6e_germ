/**
 * 网络基础请求参数
 */
declare interface HttpBaseParam {
  page?: number;
  size?: number;
}

/**
 * 网络基础结果参数
 */
declare interface HttpBaseResult<T> {
  data: T;
  code: number;
  message?: string;
}
