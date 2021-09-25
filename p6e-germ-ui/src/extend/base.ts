import { Vue } from 'vue-class-component';

export default class ExtendBase extends Vue {
  /**
   * 获取 URL 路径上面参数
   */
  public getPathParam (name: string): string | null {
    if (this.$route.query[name] === null) {
      return null;
    } else {
      return String(this.$route.query[name]);
    }
  }

  /**
   * 设置菜单名称
   * @param name 菜单名称
   */
  public setMenuName (name: string): void {
    this.$emit('menu-name', name);
  }
}
