import { Component, OnInit, OnDestroy } from '@angular/core';
import { MainService } from '../../services/main.service';
import 'rxjs/add/operator/takeUntil';
import { Subject } from 'rxjs/Subject';
import { Store } from '@ngrx/store';
import { AppStore } from '../../app.store';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit, OnDestroy {
  public movieList;
  public genres;
  public banner;
  public searchPhrase = '';
  public showGenre;
  public showDropdown = false;
  
  private ngUnsubscribe: Subject<void> = new Subject<void>();

  constructor(
    private database: MainService,
    private store: Store<AppStore>
  ){}

  ngOnInit() {
    this.getMovieList();
    this.getGenres();
    this.setBanner();
  }
  
  ngOnDestroy(){
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  getMovieList(){
    this.database.getMovies()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(movies => {
        this.movieList = movies;
        this.store.dispatch({type: 'LOAD_SUCCEEDED'});
      });
  }
  
  RateMovieList(){

        this.database.getMovies()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(movies => {

        this.movieList = movies;
      let k;
        for(var i=0;i<this.movieList.length;i++)
        {
          for(var j=0;j<this.movieList.length;j++)
          {
            if(this.movieList[i].rate>this.movieList[j].rate)
            {
              k=this.movieList[i];
              this.movieList[i]=this.movieList[j];
              this.movieList[j]=k;
            }
          }
        }
 this.store.dispatch({type: 'LOAD_SUCCEEDED'});
      });

      let s=" ";  
this.onSearch(s);
  }

  LengthMovieList(){

        this.database.getMovies()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(movies => {

        this.movieList = movies;
      let k;
        for(var i=0;i<this.movieList.length;i++)
        {
          for(var j=0;j<this.movieList.length;j++)
          {
            if(this.movieList[i].length>this.movieList[j].length)
            {
              k=this.movieList[i];
              this.movieList[i]=this.movieList[j];
              this.movieList[j]=k;
            }
          }
        }
 this.store.dispatch({type: 'LOAD_SUCCEEDED'});
      });

      let s="  ";  
this.onSearch(s);
  }

  PriceMovieList(){

        this.database.getMovies()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(movies => {

        this.movieList = movies;
      let k;
        for(var i=0;i<this.movieList.length;i++)
        {
          for(var j=0;j<this.movieList.length;j++)
          {
            if(this.movieList[i].price>this.movieList[j].price)
            {
              k=this.movieList[i];
              this.movieList[i]=this.movieList[j];
              this.movieList[j]=k;
            }
          }
        }
 this.store.dispatch({type: 'LOAD_SUCCEEDED'});
      });

      let s="   ";  
this.onSearch(s);
  }

  getGenres(){
    this.database.getGenres()
        .takeUntil(this.ngUnsubscribe)
        .subscribe(genres => {
          this.genres = Object.keys(genres);
        });
  }
  
  setGenre(genre){
    this.showGenre != genre ? this.showGenre = genre : this.showGenre = null;
    this.store.dispatch({type: 'FILTER_MOVIES'});
  }
  
  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  onSearch(value){
    this.searchPhrase = value;
    this.store.dispatch({type: 'SEARCH_MOVIES'});
  }
  
  setBanner(){
    this.database.getMovie(24)
        .takeUntil(this.ngUnsubscribe)
        .subscribe(movie => this.banner = movie);
  }

}
