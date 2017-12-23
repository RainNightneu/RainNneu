import { GenrePipe } from './genre.pipe';

describe('GenrePipe', () => {
  
  const pipe = new GenrePipe();
  
  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });
  
  it('check genre value', () => {
    let movies = [
      {
        genres: [
          'action',
          'comedy'
        ]
      },
      {
        genres: [
          'adventure',
          'comedy'
        ]
      }
    ];
    expect(pipe.transform(movies, 'action')).toContain({genres: ['action', 'comedy']});
  });
});
