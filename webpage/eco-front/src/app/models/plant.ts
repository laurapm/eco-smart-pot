export interface Plant {
  id: string,
  commonName: string,
  scientificName: string,
  family: string,
  lifesafeRange: [{
    type: string,
    min: number,
    max: number
  }]
}
