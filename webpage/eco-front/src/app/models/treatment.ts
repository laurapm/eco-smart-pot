export interface Treatment {
  id: string,
  plant: string,
  device: string,
  type: string,
  title: string,
  action: [{
    perform: string,
    params: [{
      key: string,
      value: string
    }]
  }],
  requestTime: Date,
  actionTime: Date,
  comment: string
}
