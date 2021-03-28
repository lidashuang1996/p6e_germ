// export interface TaskModel {
//   date: number;
//   isRun: boolean;
//   model: TaskCallbackModel;
// }
//
// export interface TaskCallbackModel {
//   fun: () => void;
//   interval: number;
//   isRun?: boolean;
//   isPolling?: boolean;
// }
//
// export default class Task {
//
//   /** 时间缓存的列表 */
//   private static CACHE: { [key: string]: TaskModel } = {};
//
//   public static init () {
//     setInterval(() => {
//       const newDate = new Date().getTime();
//       for (let key in this.CACHE) {
//         const date = this.CACHE[key].date;
//         const isRun = this.CACHE[key].isRun;
//         const model = this.CACHE[key].model;
//         if (isRun) {
//           this.CACHE[key].isRun = false;
//           model.fun();
//         }
//
//
//         if (!model.isPolling) {
//           delete this.CACHE[key];
//         }
//       }
//     }, 1000);
//   }
//
//   public static set (key: string, value: TaskCallbackModel) {
//     this.CACHE[key] = { date: new Date().getTime(), model: value, isRun: value.isRun || false };
//   }
//
//   public static del (key: string) {
//     delete this.CACHE[key];
//   }
//
// }
