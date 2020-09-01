import { Component, OnInit, SimpleChanges } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductsService } from 'src/app/services/products.service';
import { TicketRequest } from 'src/app/models/ticketRequest';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-cart-list',
  templateUrl: './cart-list.component.html',
  styleUrls: ['./cart-list.component.css']
})
export class CartListComponent implements OnInit {

  products: Map<string, any> = new Map;
  totalPrice: number = 0;
  purchaseSuccessfully: boolean = false;
  purchaseWentWrong: boolean    = false;

  errorMessage = '';

  constructor(
    private token: TokenStorageService,
    private api: ProductsService) { }

  ngOnInit(): void {
  }

  addProduct(product: Product): void {
    let id         = product.id;
    let hasProduct = this.products.has(id);

    // Add Product to Cart
    if (hasProduct) {
      this.products.set(id, {
        product: product,
        quantity: this.products.get(id).quantity + 1
      });
    } else {
      this.products.set(id, {
        product: product,
        quantity: 1
      });
    }

    // Total Price
    this.totalPrice += product.price;

  }

  /*
  addItem(id: string): void {
    let prod = this.products.get(id);

    this.products.set(id, {
      product:  prod.product,
      quantity: prod.quantity + 1
    } );

    this.totalPrice += prod.product.price;

  }

  removeItem(id: string): void {
    let prod     = this.products.get(id);
    let quantity = prod.quantity - 1;

    if (quantity <= 0) {
      this.products.delete(id);
    } else {
      this.products.set(id, {
        product:  prod.product,
        quantity: quantity
      });
    }

    this.totalPrice -= prod.product.price;
  }
  */

  trackByFn(index: number, el: any): number {
    return el.product.id;
  }

  payCart(): void {

    let item:any[] = [];
    this.products.forEach( element => {
      item.push({
        product: element.product.id,
        quantity: element.quantity,
        priceUnit: element.product.price
      });
    });

    let ticket: TicketRequest = {
      owner: "5f43d6dd48e46bd6a9972627", // this.token.getUser().id;
      item: item,
      date: new Date()
    };

    this.api.buyProducts(ticket).subscribe(
      data => {
        this.purchaseSuccessfully = true;
        this.purchaseWentWrong    = false;
        this.products   = new Map();
        this.totalPrice = 0;
      },
      err => {
        this.purchaseSuccessfully = false;
        this.purchaseWentWrong    = true;
        this.errorMessage  = err.message;
        console.error(this.errorMessage);
      }
    );
  }

}
