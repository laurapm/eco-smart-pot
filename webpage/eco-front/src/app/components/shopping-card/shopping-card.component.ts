import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/services/products.service';
import { Product } from 'src/app/models/product';

@Component({
  selector: 'app-shopping-card',
  templateUrl: './shopping-card.component.html',
  styleUrls: ['./shopping-card.component.css']
})
export class ShoppingCardComponent implements OnInit {

  products: Product[] = [];

  errorMessage = '';

  constructor(private api: ProductsService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.api.getProducts().subscribe(
      data => {
        data.forEach( (element: Product) => {
          this.products.push(element);
        });
      },
      err => {
        this.errorMessage  = err.message;
        console.error(this.errorMessage);
      }
    );
  }

  addProducts(products: Product[]): void {
    this.products = products;
  }

}
