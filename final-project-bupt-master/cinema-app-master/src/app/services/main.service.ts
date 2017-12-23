import { Injectable } from '@angular/core';
import { genreType } from './movie.model';
import { MOVIES } from './movie.mock-data';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

@Injectable()
export class MainService {

  movieList:any[] = MOVIES;
  
  genres = genreType;

  getMovies(): Observable<any> {
    return Observable.of(this.movieList);
  }

  getMovie(id): Observable<any> {
    let selectedMovie = this.movieList.filter(movie => movie.id === id);
    return Observable.of(selectedMovie[0]);
  }
  
  getGenres(): Observable<any> {
    return Observable.of(this.genres);
  }

}
