export default class Utils {
  public static isNotEmpty (o: any): boolean {
    return o !== null && o !== undefined;
  }

  public static isFunction (o: any): boolean {
    return typeof o === 'function';
  }

  public static getUrlParam (n: string, p: string = window.location.href): string {
    if (p !== null && p !== undefined && p.length > 0) {
      const i = p.indexOf('?');
      if (i >= 0 && i < p.length) {
        const pc = p.substring(i + 1);
        const ps = pc.split('&');
        if (ps !== null && ps !== undefined && ps.length > 0) {
          for (let i = 0; i < ps.length; i++) {
            const kv = ps[i].split('=');
            if (kv !== null && kv !== undefined && kv.length === 2) {
              if (n === decodeURIComponent(kv[0])) {
                return decodeURIComponent(kv[1]);
              }
            }
          }
        }
      } else {
        return '';
      }
    }
    return '';
  }

  public static getMetaContent (name: string, def: string): string {
    const metas = document.getElementsByTagName('meta');
    for (let i = 0; i < metas.length; i++) {
      if (metas[i].getAttribute('name') === name) {
        const content = metas[i].getAttribute('content');
        /* eslint-disable */
        // @ts-ignore
        return this.isNotEmpty(content) ? content : def;
      }
    }
    return def;
  }

  /**
   * 获取语言参数
   */
  public static getLanguageParam (def: string = 'zh-CN'): string {
    return this.getMetaContent('Content-Language', def);
  }

  public static translate (c: string, p: { [ key: string ]: string }): string {
    for (const k in p) {
      if (p.hasOwnProperty(k)) {
        const v = p[k];
        c = c.replace(String('{' + k + '}'), v);
      }
    }
    return c;
  }
}
