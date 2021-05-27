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
export const ApiSignInObtainCode = (data: HttpObtainCodeParam): Promise<HttpBaseResponse<HttpObtainCodeResult>> => {
  return Base.http().get(URL + '/login/nr_code' + Base.objectToKeyValueString(data));
};

/**
 * 验证码登录请求
 * @param data
 * @constructor
 */
export const ApiSignInCode = (data: HttpSignInCodeParam): Promise<HttpBaseResponse<HttpSignInCodeResult>> => {
  return Base.delayResult(Base.http().post(URL + '/login/nr_code/data', data), 1500);
};

export const ApiSignInQrCode = (data: HttpSignInQrCodeParam): Promise<HttpBaseResponse<HttpSignInQrCodeResult>> => {
  return Base.http().get(URL + '/login/qr_code' + Base.objectToKeyValueString(data));
};

/**
 * 轮询获取二维码数据的请求
 * @param data
 * @constructor
 */
export const ApiSignInQrData = (data: HttpSignInQrCodeParam): Promise<HttpBaseResponse<HttpSignInQrCodeResult>> => {
  return Base.http().get(URL + '/login/qr_code/data' + Base.objectToKeyValueString(data));
};

export const ApiSignInQq = (data: HttpSignInOtherParam): Promise<HttpBaseResponse<HttpSignInOtherResult>> => {
  return Base.http().get(URL + '/login/qq' + Base.objectToKeyValueString(data));
};

export const ApiSignInWeChat = (data: HttpSignInOtherParam): Promise<HttpBaseResponse<HttpSignInOtherResult>> => {
  return Base.http().get(URL + '/login/we_chat' + Base.objectToKeyValueString(data));
};

export const ApiSignInSina = (data: HttpSignInOtherParam): Promise<HttpBaseResponse<HttpSignInOtherResult>> => {
  return Base.http().get(URL + '/login/sina' + Base.objectToKeyValueString(data));
};
