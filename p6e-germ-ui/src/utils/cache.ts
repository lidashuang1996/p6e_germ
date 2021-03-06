/**
 * 缓存的接口
 */
export interface ICache {
  /** 写入 */
  setUserToken(v: string): void;
  /** 删除 */
  delUserToken(): void;
  /** 读取 */
  getUserToken(): string | null;
  /** 写入主题 */
  setTheme(v: string): void;
  /** 删除主题 */
  delTheme(): void;
  /** 获取主题 */
  getTheme(): string | null;
}

class LocalStorageCache implements ICache {
  private readonly USER_TOKEN_NAME = 'R_USER_TOKEN';
  private readonly THEME_NAME = 'R_THEME';

  public delUserToken (): void {
    window.localStorage.removeItem(this.USER_TOKEN_NAME);
  }

  public getUserToken (): string | null {
    return window.localStorage.getItem(this.USER_TOKEN_NAME);
  }

  public setUserToken (v: string): void {
    window.localStorage.setItem(this.USER_TOKEN_NAME, v);
  }

  public delTheme (): void {
    window.localStorage.removeItem(this.THEME_NAME);
  }

  public getTheme (): string | null {
    return window.localStorage.getItem(this.THEME_NAME);
  }

  public setTheme (v: string): void {
    window.localStorage.setItem(this.THEME_NAME, v);
  }
}

// LocalStorage 缓存的实现
export default new LocalStorageCache();
