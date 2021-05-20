import MainAxios from './main-axios';

export interface HttpGlobal {
  onRequest?: () => void;
  onResponse?: () => void;
  onError?: ()=> void;
}

export class HttpGlobalDefault implements HttpGlobal {
}

export interface HttpRequestConfig {
  a?: string;
}

export interface HttpResponseConfig {
  a?: string;
}

export interface WebSocketRequest {
  a?: string;
}

export declare interface HttpRequest {
  /**
   * 初始化 token
   */
  initToken (token: string | null): void;

  /**
   * get 请求
   * @param url 请求的 url 地址
   * @param config 请求的配置文件
   */
  get<T> (url: string, config?: HttpRequestConfig): Promise<T>;

  /**
   * put 请求
   * @param url 请求的 url 地址
   * @param data 请求的数据
   * @param config 请求的配置文件
   */
  put<T, W> (url: string, data?: W, config?: HttpRequestConfig): Promise<T>;

  /**
   * post 请求
   * @param url 请求的 url 地址
   * @param data 请求的数据
   * @param config 请求的配置文件
   */
  post<T, W> (url: string, data?: W, config?: HttpRequestConfig): Promise<T>;

  /**
   * delete 请求
   * @param url 请求的 url 地址
   * @param data 请求的数据
   * @param config 请求的配置文件
   */
  delete<T, W> (url: string, data?: W, config?: HttpRequestConfig): Promise<T>;
}

export const createMainAxios = (): HttpRequest => new MainAxios();
