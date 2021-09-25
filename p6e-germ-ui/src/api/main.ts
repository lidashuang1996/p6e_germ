import support from './support';
import __message__ from './message/main';

/** 初始化预处理器 */
support.initPreprocessor((o: {
  data?: { code: number, message?: string };
  status: number;
  statusText: string;
}) => {
  console.log(o);
});

const message = { ...__message__ };

export default {
  message
};
