export default class Config {
  /** 当前前环境 */
  private static CURRENT_ENVIRONMENT = (process.env.NODE_ENV === 'production' ? 'pro' : process.env.NODE_ENV === 'development' ? 'dev' : 'unknown');

  /** 配置文件信息 */
  private static CONFIG_DATA: any = {
    pro: {
      url: 'http://baidu.com/'
    },
    dev: {
      url: 'http://localhost'
    }
  };

  /**
   * 获取制定的配置信息
   * @param name 配置信息的名称
   */
  public static get (name: string) {
    let data = this.CONFIG_DATA[this.obtainCurrentEnvironment().toLowerCase()];
    name.toLowerCase().split('.').forEach(item => { data = data[item]; });
    return data;
  }

  /**
   * 获取当前环境的数据
   * @return 当前的环境序号
   */
  public static obtainCurrentEnvironment (): string {
    return this.CURRENT_ENVIRONMENT;
  }
}
