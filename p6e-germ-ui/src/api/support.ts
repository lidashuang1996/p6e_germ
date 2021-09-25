import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

class Support {
  /** 定义请求对象 */
  private readonly launcher: AxiosInstance;
  /** 令牌 */
  private token: string | null = null;
  /** 预处理器 */
  /* eslint-disable-next-line */
  private preprocessor = (o: any) => {};

  /**
   * GET 请求参数格式化
   */
  /* eslint-disable */
  public static formattingGetKeyValue (o: any): string {
    if (o === null || o === undefined) {
      return '';
    }
    let r = '';
    for (const k in o) {
      if (o.hasOwnProperty(k)) {
        r += k + '=' + o[k];
      }
    }
    return r;
  }

  /**
   * 构造方法初始化
   */
  constructor () {
    /** 创建的请求对象 */
    this.launcher = axios.create();
    /** 设置超时的时间 */
    this.launcher.defaults.timeout = 30000;
    /** 设置请求头 */
    this.launcher.defaults.headers = {
      'Content-Type': 'application/json;'
    };

    /** 设置添加请求拦截器 */
    this.launcher.interceptors.request.use((config) => {
      if (this.token !== null) {
        config.headers.Authorization = 'Bearer ' + this.token;
      }
      return config;
    }, (error) => {
      return error;
    });

    /** 设置添加响应拦截器 */
    this.launcher.interceptors.response.use((config) => {
      // 发送的 url
      const f = config.config.url;
      // 接收的 url
      const s = config.request.responseURL;
      // 发送的 url 和接收的 url 不同则表示为重定向
      if (f !== s) {
        window.location.href = s;
      }
      return config;
    }, (error) => {
      return { data: { code: -1, message: 'ERROR_NETWORK', data: error } };
    });
  }

  /**
   * 设置发射器对象
   * @param fun 控制反转执行回调
   */
  public execute (fun: (launcher: AxiosInstance) => void) {
    fun(this.launcher);
  }

  /**
   * 初始化令牌
   * @param token 令牌
   */
  public initToken (token: string) {
    this.token = token;
  }

  /**
   * 初始化令牌
   * @param fun 初始化预处理器
   */
  /* eslint-disable-next-line */
  public initPreprocessor (fun: (o: any) => void) {
    this.preprocessor = fun;
  }

  public get<T> (is: boolean, url: string, config?: AxiosRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      try {
        // 删除掉 axios 对结果的封装
        this.launcher.get<T>(url, config)
          .then((res) => {
            resolve(res.data);
            if (is) {
              this.preprocessor(res);
            }
          })
          .catch((e) => {
            reject(e);
            if (is) {
              this.preprocessor(e);
            }
          });
      } catch (e) {
        reject(e);
      }
    });
  }

  public delete<T> (is: boolean, url: string, config?: AxiosRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      try {
        // 删除掉 axios 对结果的封装
        this.launcher.delete<T>(url, config)
          .then((res) => {
            resolve(res.data);
            if (is) {
              this.preprocessor(res);
            }
          })
          .catch((e) => {
            reject(e);
            if (is) {
              this.preprocessor(e);
            }
          });
      } catch (e) {
        reject(e);
      }
    });
  }

  /* eslint-disable-next-line */
  public post<T> (is: boolean, url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      try {
        // 删除掉 axios 对结果的封装
        this.launcher.post<T>(url, data, config)
          .then((res) => {
            resolve(res.data);
            if (is) {
              this.preprocessor(res);
            }
          })
          .catch((e) => {
            reject(e);
            if (is) {
              this.preprocessor(e);
            }
          });
      } catch (e) {
        reject(e);
      }
    });
  }

  public put<T> (is: boolean, url: string, data: never, config?: AxiosRequestConfig): Promise<T> {
    return new Promise<T>((resolve, reject) => {
      try {
        // 删除掉 axios 对结果的封装
        this.launcher.put<T>(url, config)
          .then((res) => {
            resolve(res.data);
            if (is) {
              this.preprocessor(res);
            }
          })
          .catch((e) => {
            reject(e);
            if (is) {
              this.preprocessor(e);
            }
          });
      } catch (e) {
        reject(e);
      }
    });
  }

}

export const formattingGetKeyValue = (o: any): string => {
  if (o === null || o === undefined) {
    return '';
  }
  let r = '';
  for (const k in o) {
    if (o.hasOwnProperty(k)) {
      r += '&' + k + '=' + o[k];
    }
  }
  return r.length > 0 ? '?' + r.substring(1) : r;
};

/** 导出支持对象 */
export default new Support();
