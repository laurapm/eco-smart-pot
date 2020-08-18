import { ProductsService   } from '../../../services/products.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {


  products: any;
  currentProduct = null;
  currentIndex = -1;
  name = '';

  constructor(private pruductsService: ProductsService) { }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  retrieveProducts(): void{
    this.pruductsService.getAll()
      .subscribe(
        data => {
          this.products = data;
          console.log(data);
        },
        error => {
          console.log(error);
        }
      );
  }

  refreshList(): void {
    this.retrieveProducts();
    this.currentProduct = null;
    this.currentIndex = -1;
  }

  setActiveProduct(product, index): void {
    this.currentProduct = product;
    this.currentIndex = index;
  }

  searchName():void {
    this.pruductsService.findByName(this.name)
      .subscribe(
        data => {
          this.products = data;
          console.log(data);
        },
        error => {
          console.log(error);
        }
      );
  }
}
