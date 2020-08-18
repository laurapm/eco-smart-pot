import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../../../../services/products.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {

  currentProduct = null;
  msg = '';

  constructor(
    private productsService: ProductsService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.msg = '';
    this.getProducts(this.route.snapshot.paramMap.get('id'));
  }

  getProducts(id): void {
    this.productsService.get(id)
      .subscribe(
        data => {
          this.currentProduct = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  updateStatus(status): void {
    const data = {
      name: this.currentProduct.name,
      description: this.currentProduct.description,
      available: status
    };

    this.productsService.update(this.currentProduct.id, data)
      .subscribe(
        resp => {
          this.currentProduct.published = status;
          console.log(resp);
        },
        error => {
          console.log(error);
        }
      );
    }


  updateProduct(): void {
    this.productsService.update(this.currentProduct.id, this.currentProduct)
      .subscribe(
        resp => {
          console.log(resp);
          this.msg = "Update Successfully";
        },
        error => {
          console.log(error);
        }
      );
  }

}
