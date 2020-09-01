import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-measurements-chart',
  templateUrl: './measurements-chart.component.html',
  styleUrls: ['./measurements-chart.component.css']
})
export class MeasurementsChartComponent implements OnInit {

  public chartType: string = 'line';

  @Input('dataset')
  set data(data: any) {
    this.chartLabels   = data.labels;
    this.chartDatasets = [
       { data: data.data, label: data.label }
    ];
  }

  @Input('colors')
  set colors(colors: any) {
    this.chartColors = colors;
  }

  public chartDatasets: Array<any> = [
    { data: [0, 0, 0, 0, 0, 0, 0], label: 'Measurement Values' }
  ];
  public chartLabels:   Array<any> = ['-', '-', '-', '-', '-', '-', '-'];

  public chartColors: Array<any>   = [
    {
      backgroundColor: 'rgba(105, 0, 132, .2)',
      borderColor: 'rgba(200, 99, 132, .7)',
      borderWidth: 2,
    }];

  public chartOptions: any = {
    responsive: true
  };

  constructor() { }

  ngOnInit(): void {
  }

  public chartClicked(e: any): void { }
  public chartHovered(e: any): void { }

}
