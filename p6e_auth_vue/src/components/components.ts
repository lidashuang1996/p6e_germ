/**
 * 生成二维码组件的接口
 */
export interface QrCodeComponentInterface {
  /**
   * 生成二维码
   */
  qrCode (text: string): void;
}

/**
 * 输入框组件的接口
 */
export interface InputInterface {
  /**
   * 验证数据
   */
  test (): boolean;

  /**
   * 获取数据框数据
   */
  getData (): string;
}
