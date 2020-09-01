import { Component, OnInit, ViewChild, EventEmitter, Output } from '@angular/core';
import { ProductsService } from 'src/app/services/products.service';
import { Product } from 'src/app/models/product';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit {

  @ViewChild('from') from: any;
  @ViewChild('to')   to:   any;
  @Output() emitBetweenPrices = new EventEmitter();

  constructor(private api: ProductsService) { }

  ngOnInit(): void {
  }

  searchBetweenPrices(): void {
    let minPrice: number = this.from.nativeElement.valueAsNumber;
    let maxPrice: number = this.to.nativeElement.valueAsNumber;

    var products = [];
    if (minPrice <= maxPrice) {
      this.api.getProductsBetweenPrices(minPrice, maxPrice).subscribe(
        data => {
          data.forEach( (element: Product) => {
            products.push(element);
          });

          this.emitProducts(products);
        },
        err => {
          console.error(err.message);
        }
      );
    } else {
      alert("Cannot filter if the minimum price if bigger than the maximum price!");
    }
  }

  emitProducts(products: Product[]): void {
    this.emitBetweenPrices.emit(products);
  }

}
