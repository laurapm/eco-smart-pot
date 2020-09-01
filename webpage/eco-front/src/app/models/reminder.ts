export interface Reminder {
  id: string,
  device: string,
  title: string,
  message: string,
  requestTime: Date,
  remindingTime: Date
}
