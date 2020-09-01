import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-a-funny-chart',
  templateUrl: './a-funny-chart.component.html',
  styleUrls: ['./a-funny-chart.component.css']
})
export class AFunnyChartComponent implements OnInit {

  public chartType: string = 'doughnut';

  public chartDatasets: Array<any> = [
    { data: [73, 27], label: 'Why should we pass' }
  ];

  public chartLabels: Array<any> = ['Reasons to pass', 'Reasons to pass in other color'];

  public chartColors: Array<any> = [
    {
      backgroundColor: ['#F7464A', '#46BFBD'],
      hoverBackgroundColor: ['#FF5A5E', '#5AD3D1'],
      borderWidth: 2,
    }
  ];

  public chartOptions: any = {
    responsive: true
  };

  constructor() { }

  ngOnInit(): void {
  }

  public chartClicked(e: any): void { }
  public chartHovered(e: any): void { }

}
