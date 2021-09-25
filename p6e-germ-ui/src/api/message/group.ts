import Config from '@/config/main';
import Support, { formattingGetKeyValue } from '@/api/support';

/**
 * 请求的基础 URL 地址
 */
const MESSAGE_BASE_URL = Config.get('message.url');

/**
 * 消息中心--组列表获取
 */
export const getGroupList = (param: HttpMessageGroupListParam, is = true): Promise<HttpMessageGroupListResult> => {
  return Support.get(is, MESSAGE_BASE_URL + '/console/group/list' + formattingGetKeyValue(param));
};

/**
 * 消息中心--组平台列表获取
 */
export const getGroupPlatformList = (param: HttpMessageGroupPlatformListParam, is = true): Promise<HttpMessageGroupPlatformListResult> => {
  return Support.get(is, MESSAGE_BASE_URL + '/console/platform/list' + formattingGetKeyValue(param));
};

/**
 * 消息中心--日志
 */
export const getLogList = (param: HttpMessageLogListParam, is = true): Promise<HttpMessageLogListResult> => {
  return Support.get(is, MESSAGE_BASE_URL + '/console/log/list' + formattingGetKeyValue(param));
};
