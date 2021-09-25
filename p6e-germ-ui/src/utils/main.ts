import cache from './cache';

/**
 * 导出 LOG 函数
 * @param o
 */
const log = (o: never): void => {
  console.log(o);
};

export default {
  log,
  cache
};
