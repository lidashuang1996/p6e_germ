export default class Cache {
  /** 登录验证码缓存的名称 */
  private static SIGN_IN_CODE_NAME = 'P6E_AUTH@SIGN_IN_CODE';

  public static setSignInCodeCache (num: number) {
    window.localStorage.setItem(this.SIGN_IN_CODE_NAME, String(num));
  }

  public static getSignInCodeCache (): number {
    const data = window.localStorage.getItem(this.SIGN_IN_CODE_NAME);
    return data === null ? 0 : Number(data);
  }

  public static delSignInCodeCache () {
    window.localStorage.removeItem(this.SIGN_IN_CODE_NAME);
  }
}
