export default class Cache {
  /** 登录验证码缓存的名称 */
  private static SIGN_IN_CODE_NAME = 'P6E_AUTH@SIGN_IN_CODE';

  /**
   * 设置登录里面获取验证码的缓存
   * @param num 缓存时间
   */
  public static setSignInCodeCache (num: number) {
    window.localStorage.setItem(this.SIGN_IN_CODE_NAME, JSON.stringify({ date: new Date().getTime(), num }));
  }

  /**
   * 获取登录里面获取验证码的缓存
   */
  public static getSignInCodeCache (): number {
    const data = window.localStorage.getItem(this.SIGN_IN_CODE_NAME);
    if (data === null) {
      return 0;
    } else {
      const o: { date: number; num: number } = JSON.parse(data);
      const d = o.num - Math.floor((new Date().getTime() - o.date) / 1000);
      return d <= 0 ? 0 : d;
    }
  }

  /**
   * 删除登录里面获取验证码的缓存
   */
  public static delSignInCodeCache () {
    window.localStorage.removeItem(this.SIGN_IN_CODE_NAME);
  }
}
