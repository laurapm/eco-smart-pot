export interface Measurements {
  id: string,
  plant: string,
  device: string,
  date: Date,
  hour: number,
  humidityInt: [{
    minute: number,
    measure: {
      watered: boolean,
      value: number[]
    }
  }],
  humidityExt: [{
    minute: number,
    measure: number
  }],
  luminosityExt: [{
    minute: number,
    measure: number
  }],
  temperatureExt: [{
    minute: number,
    measure: number
  }]
}
