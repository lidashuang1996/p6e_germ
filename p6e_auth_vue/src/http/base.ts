import { HttpRequest, createMainAxios } from './support/main';

export default class Base {
  /** Http 请求基础对象 */
  private static readonly $http: HttpRequest = createMainAxios();

  /**
   * 延迟返回
   * @param promise Promise 请求方法
   * @param time 延迟的时间 毫秒
   */
  public static delayResult<T> (promise: Promise<T>, time: number): Promise<T> {
    // 时间的回调函数
    const timeFunction = (t: number, callback: () => void) => {
      if (t > 0 && t < time) {
        setTimeout(() => { callback(); }, (time - t));
      } else {
        callback();
      }
    };
    return new Promise<T>((resolve: (res: T) => void, reject: (e: Error) => void) => {
      const dateTime = new Date().getTime();
      promise
        .then((res: T) => {
          timeFunction((new Date().getTime() - dateTime), () => { resolve(res); });
        })
        .catch((e: Error) => {
          timeFunction((new Date().getTime() - dateTime), () => { reject(e); });
        });
    });
  }

  public static http (): HttpRequest {
    return this.$http;
  }
}
