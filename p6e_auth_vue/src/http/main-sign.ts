import Base from './base';
import Config from '@/config/main';

/** 基础的 URL */
const URL = Config.get('url');

/**
 * 凭证<证书公钥>获取
 */
export const ApiSignVoucher = (data: HttpSignVoucherParam): Promise<HttpBaseResponse<HttpSignVoucherResult>> => {
  return Base.http().get(URL + '/login/secret_voucher?mark=' + data.mark);
};

/**
 * 登录验证
 * @param data 登录验证参数
 */
export const ApiSignVerification = (data: HttpSignVerificationParam): Promise<HttpBaseResponse<HttpSignVerificationResult>> => {
  return Base.http().get(URL + '/login/verification?mark=' + data.mark);
};
