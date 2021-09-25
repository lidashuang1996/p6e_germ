export default class Config {
  /** 当前前环境 */
  private static CURRENT_ENVIRONMENT: string | null = null;

  /** 配置文件信息 */
  // eslint-disable-next-line
  private static CONFIG_DATA: any = {
    pro: {
      url: 'http://192.168.190.82:8080'
    },
    dev: {
      message: {
        url: 'http://127.0.0.1:16000'
      }
    }
  };

  /**
   * 获取制定的配置信息
   * @param path 获取的数据的路径
   */
  public static get (path: string): string {
    let data = this.CONFIG_DATA[this.obtainCurrentEnvironment()];
    path = path.toLowerCase();
    path.split('.').forEach(item => { data = data[item]; });
    return data;
  }

  /**
   * 获取当前环境的数据
   * @return 当前的环境序号
   */
  public static obtainCurrentEnvironment (): string {
    if (this.CURRENT_ENVIRONMENT == null) {
      if (process.env.NODE_ENV === 'production') {
        this.CURRENT_ENVIRONMENT = 'pro';
      } else if (process.env.NODE_ENV === 'development') {
        this.CURRENT_ENVIRONMENT = 'dev';
      } else {
        this.CURRENT_ENVIRONMENT = 'unknown';
      }
    }
    return this.CURRENT_ENVIRONMENT;
  }
}
