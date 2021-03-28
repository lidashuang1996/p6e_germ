/** 导入网络请求的包 */
import { HttpRequest, HttpGlobal, HttpGlobalDefault, HttpRequestConfig } from './main';
import $request, { AxiosInstance } from 'axios';

/**
 * 对 HTTP/HTTPS/WebSocket 请求的实现
 * 这里是通过 axios 的实现 【fetch/ajax（XMLHttpRequest）】
 */
export default class MainAxios implements HttpRequest {
  /** 定义请求对象 */
  private readonly request: AxiosInstance;
  /** 定义请求管理者 */
  private global: HttpGlobal = new HttpGlobalDefault();

  constructor () {
    /** 创建的请求对象 */
    this.request = $request.create();
    /** 设置超时的时间 */
    this.request.defaults.timeout = 30000;
    /** 设置请求头 */
    this.request.defaults.headers = {
      'Content-Type': 'application/json;'
    };

    /** 设置添加请求拦截器 */
    this.request.interceptors.request.use((config) => {
      return config;
    }, (error) => {
      return error;
    });

    /** 设置添加响应拦截器 */
    this.request.interceptors.response.use((config) => {
      console.log(config);
      return config;
    }, (error) => {
      return error;
    });
  }

  /**
   * 设置全局处理器的方法
   */
  public setHttpGlobal (global: HttpGlobal) {
    this.global = global;
  }

  public get<T> (url: string, config?: HttpRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      this.request.get<T>(url, config as any)
        .then((res) => { resolve(res.data); })
        .catch((e) => { console.error(e); reject(e); });
    });
  }

  public delete<T> (url: string, config?: HttpRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      this.request.delete<T>(url, config as any)
        .then((res) => { resolve(res.data); })
        .catch((e) => { console.error(e); reject(e); });
    });
  }

  public post<T> (url: string, data: never, config?: HttpRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      this.request.post<T>(url, data, config as any)
        .then((res) => { resolve(res.data); })
        .catch((e) => { console.error(e); reject(e); });
    });
  }

  public put<T> (url: string, data: never, config?: HttpRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      this.request.put<T>(url, data, config as any)
        .then((res) => { resolve(res.data); })
        .catch((e) => { console.error(e); reject(e); });
    });
  }

  public initToken (): void {
    console.log('aaa');
  }
}
