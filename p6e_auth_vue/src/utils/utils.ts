export default class Utils {
  public static isNotEmpty (o: any): boolean {
    return o !== null && o !== undefined;
  }

  public static isFunction (o: any): boolean {
    return typeof o === 'function';
  }
}
