import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Product } from 'src/app/models/product';
@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  @Output() addProduct = new EventEmitter();
  @Input('products') products: Product[] = [];
  selectedProduct: Product;

  constructor() { }

  ngOnInit(): void {
  }

  getImagePath(name: string): string {
    let path: string;

    if (name === 'eco smart pot') {
      // Thanks to https://trippyneeds.com/products/piano-pot-touch-sensitive-smart-musical-flower-pot-blueooth
      // for the image for the smart pot
      path = '../../../../assets/smart-pot.jpg'
    } else if (name === 'red pot') {
      path = '../../../../assets/red-pot.jpg'
    } else if (name === 'blue pot') {
      path = '../../../../assets/blue-pot.jpg'
    } else if (name === 'green pot') {
      path = '../../../../assets/green-pot.jpg'
    } else if (name === 'pink pot') {
      path = '../../../../assets/pink-pot.jpg'
    } else if (name === 'aprobado') {
      path = '../../../../assets/passed-exam.jpg'
    } else {
      path = '../../../../assets/no-image.jpg'
    }

    return path;
  }

  onSelect(product: Product): void {
    this.addProduct.emit(product);
  }

}
