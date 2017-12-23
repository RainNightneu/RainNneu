import { SearchPipe } from './search.pipe';

describe('SearchPipe', () => {
  
  const pipe = new SearchPipe();
  
  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });
  
  it('check search value', () => {
  let movies = [
    {
      name: 'Power Rangers'
    },
    {
      name: 'Spider-Man: Homecoming'
    }
  ];
    expect(pipe.transform(movies, 'Power')).toContain({name: 'Power Rangers'});
  });
});
