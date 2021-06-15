export default class Global {
  private static DATA = {};
  private static GLOBAL_URL = '';
  private static SIGN_COUNT_DOWN = 0;
  private static readonly SIGN_COUNT_DOWN_NAME: string = 'P6E_SIGN_COUNT_DOWN';

  /* eslint-disable */
  public static init () {
    // @ts-ignore
    this.DATA = window['P6E_OAUTH2_DATA'];
    this.GLOBAL_URL = window.location.href;
  }

  public static getOauth2 (): { type: string, mark: string, icon: string, name: string } {
    return this.DATA as { type: string, mark: string, icon: string, name: string };
  }

  public static setSignCountdown (num: number) {
    this.SIGN_COUNT_DOWN = num;
  }

  public static getSignCountdown (): number {
    return this.SIGN_COUNT_DOWN;
  }

  public static getGlobalURL (): string {
    return this.GLOBAL_URL;
  }

}
