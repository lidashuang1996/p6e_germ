export default class Utils {
  public static isNotEmpty (o: any): boolean {
    return o !== null && o !== undefined;
  }

  public static isFunction (o: any): boolean {
    return typeof o === 'function';
  }

  public static getUrlParam (n: string): string | null {
    const p = window.location.search;
    if (p !== null && p !== undefined && p.length > 0) {
      const pc = p.substring(1);
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
    }
    return null;
  }
}
