import Base from './base';
import Config from '@/config/main';

/** 基础的 URL */
const URL = Config.get('url');

/**
 * 账号密码登录
 * @param data 账号密码登录参数
 * @constructor
 */
export const ApiSignIn = (data: HttpSignInParam): Promise<HttpBaseResponse<HttpSignInResult>> => {
  return Base.delayResult(Base.http().post(URL + '/login/account_password', data), 1500);
};

/**
 * 获取验证码请求
 * @param data
 * @constructor
 */
export const ApiCode = (data: HttpObtainCodeParam): Promise<HttpBaseResponse<HttpObtainCodeResult>> => {
  return Base.http().get(URL + '/login_send_code.json?q=' + JSON.stringify(data));
};

/**
 * 验证码登录请求
 * @param data
 * @constructor
 */
export const ApiCodeSignIn = (data: HttpCodeSignInParam): Promise<HttpBaseResponse<HttpCodeSignInResult>> => {
  return Base.delayResult(Base.http().get(URL + '/login_code.json?q=' + JSON.stringify(data)), 1500);
};

export const ApiQrCode = (data: HttpObtainCodeParam, time = 0): Promise<HttpBaseResponse<HttpObtainCodeResult>> => {
  return Base.delayResult(Base.http().get(URL + '/login_send_code.json?q=' + JSON.stringify(data)), time);
};

/**
 * 轮询获取二维码数据的请求
 * @param data
 * @constructor
 */
export const ApiQrData = (data: HttpObtainCodeParam): Promise<HttpBaseResponse<HttpObtainCodeResult>> => {
  return Base.http().get(URL + '/login_send_code.json?q=' + JSON.stringify(data));
};
