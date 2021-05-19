/** 窗体类型 */
export interface WindowType<T> {
  type: string;
  content: T;
}

/** 节流配置类型 */
export interface ThrottleConfigType {
  status: boolean;
  datetime: number;
}

/** 窗体大小改变参数类型 */
export interface WindowResizeParamType {
  width: number;
  height: number;
}

/** 窗体大小改变事件类型 */
export type WindowResizeEvent = (e: WindowResizeParamType) => void;

/**
 * window
 * 事件监听
 * 全局保证只有一个这样的监听
 */
export default class Window {
  /** 常量名称 */
  private static readonly RESIZE_TYPE = 'RESIZE';
  /** 是否初始化 */
  private static IS_INIT = false;
  /** 缓存的事件队列 */
  private static QUEUE: WindowType<any>[] = [];
  /** 窗体改变节流配置 */
  private static RESIZE_THROTTLE_CONFIG: ThrottleConfigType = {
    status: false,
    datetime: 0
  };

  /** 初始化操作 */
  public static init (): void {
    this.IS_INIT = true;
    window.onresize = () => this.throttle(this.RESIZE_TYPE);
  }

  /** 添加窗体大小改变的事件 */
  public static onResizeEvent (f: WindowResizeEvent, b = false): void {
    if (!this.IS_INIT) { this.init(); }
    this.QUEUE.push({ type: this.RESIZE_TYPE, content: f });
    if (b) {
      const width = Number(document.body.clientWidth);
      const height = Number(document.body.clientHeight);
      f({ width, height });
    }
  }

  /** 节流 */
  private static throttle (type: string): void {
    switch (type) {
      case this.RESIZE_TYPE:
        if (this.RESIZE_THROTTLE_CONFIG.datetime <= 0) {
          this.nativeResizeEvent();
        } else {
          if (!this.RESIZE_THROTTLE_CONFIG.status) {
            this.RESIZE_THROTTLE_CONFIG.status = true;
            setTimeout(() => {
              this.nativeResizeEvent();
              this.RESIZE_THROTTLE_CONFIG.status = false;
            }, this.RESIZE_THROTTLE_CONFIG.datetime);
          }
        }
        break;
      default:
        break;
    }
  }

  /** 本地窗体改变事件 */
  private static nativeResizeEvent (): void {
    const width = Number(document.body.clientWidth);
    const height = Number(document.body.clientHeight);
    this.QUEUE.forEach((item) => {
      if (this.RESIZE_TYPE === item.type) {
        item.content({ width, height });
      }
    });
  }
}
