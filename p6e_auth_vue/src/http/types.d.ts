declare interface HttpBaseResponse<T> {
  code: number;
  message: string;
  data?: T;
}

/**
 * 登录的请求参数类型
 */
declare interface HttpSignInParam {
  /** 凭证 */
  voucher: string;
  /** 账号 */
  account: string;
  /** 密码 */
  password: string;
}

/**
 * 登录的结果参数类型
 */
declare interface HttpSignInResult {
  code: string;
}

declare interface HttpObtainCodeParam {
  account: string;
}

declare interface HttpObtainCodeResult {
  code: string;
}

/**
 * 登录的请求参数类型
 */
declare interface HttpCodeSignInParam {
  /** 凭证 */
  voucher: string;
  /** 密码 */
  code: string;
  /** 账号 */
  account: string;
}

/**
 * 登录的结果参数类型
 */
declare interface HttpCodeSignInResult {
  code: string;
}


