export default class Global {
  private static DATA = {};

  /* eslint-disable */
  public static init () {
    // @ts-ignore
    this.DATA = window['P6E_OAUTH2_DATA'];
  }

  public static getOauth2 (): { type: string, mark: string } {
    return this.DATA as { type: string, mark: string };
  }

}
