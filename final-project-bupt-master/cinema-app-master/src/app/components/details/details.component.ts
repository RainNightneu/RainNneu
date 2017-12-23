import { Component, OnInit, OnDestroy } from '@angular/core';
import { MainService } from '../../services/main.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/takeUntil';
import { Subject } from 'rxjs/Subject';
import { Store } from '@ngrx/store';
import { AppStore } from '../../app.store';

import { ShoppingCartService } from "../../services/shopping-cart.service";
import { Observable } from "rxjs/Observable";
import { Observer } from "rxjs/Observer";
import { Product } from "../../_models/product";
import { ShoppingCart } from "../../_models/shopping-cart";

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit, OnDestroy {

  public pageIsReady = false;
  public movie;
  public product: Product;
  public products: Observable<Product[]>;
  
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  
  constructor(
   private service: MainService,
   private route: ActivatedRoute,
   private store: Store<AppStore>,
   private shoppingCartService: ShoppingCartService
  ) { }

  public addProductToCart(product: Product): void {
    this.shoppingCartService.addItem(product, 1);
  }

  public removeProductFromCart(product: Product): void {
    this.shoppingCartService.addItem(product, -1);
  }

  public productInCart(product: Product): boolean {
    return Observable.create((obs: Observer<boolean>) => {
      const sub = this.shoppingCartService
                      .get()
                      .subscribe((cart) => {
                        obs.next(cart.items.some((i) => i.productId === product.key));
                        obs.complete();
                      });
      sub.unsubscribe();
    });
  }

 
  
  ngOnInit() {
    this.route.paramMap
        .switchMap((params: ParamMap) => 
          this.service.getMovie(+params.get('id'))
        )
        .takeUntil(this.ngUnsubscribe)
        .subscribe(movie => {
          this.movie = movie;
          this.pageIsReady = true;
          this.store.dispatch({type: 'SEE_A_MOVIE'});
        });
  }
  
  ngOnDestroy(){
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  addMovietoCart(){
    this.product = new Product();
    this.product.id = this.movie.id;
    this.product.description = this.movie.description;
    this.product.img = this.movie.img;
    this.product.key = this.movie.key;
    this.product.length = this.movie.length;
    this.product.name = this.movie.name;
    this.product.rate = this.movie.rate;
    this.product.price = this.movie.price;
    this.addProductToCart(this.product);
    alert("Add successfully!");
  }
}
