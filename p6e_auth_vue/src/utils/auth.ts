import Base from '@/http/base';
import Cache from '@/utils/cache';
import { ApiSignVerification } from '@/http/main-sign';

export default class Auth {
  private static IS_INIT = false;
  private static IS_AUTH = false;
  /* eslint-disable */
  // @ts-ignore
  private static readonly G_MARK = window['P6E_OAUTH2_DATA'].mark;

  public static async init () {
    // 读取缓存
    const authData = Cache.getAuthData();
    if (authData !== null) {
      Base.setToken(authData.accessToken);
      const res = await ApiSignVerification({ mark: this.G_MARK });
      if (res.code === 0 && res.data !== null) {
        this.IS_AUTH = true;
        Cache.setAuthData(res.data);
      } else {
        Base.delToken();
      }
    }
    this.IS_INIT = true;
  }

  public static async isAuthenticated (): Promise<boolean> {
    if (!this.IS_INIT) {
      await this.init();
    }
    return this.IS_AUTH;
  }
}
