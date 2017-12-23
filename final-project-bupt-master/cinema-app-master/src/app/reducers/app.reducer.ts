export function movieList(state = [], {type, payload}) {
  switch (type) {
        case 'LOAD_SUCCEEDED':
            return [...state, type];
        case 'SEARCH_MOVIES': 
            return [...state, type];
        case 'FILTER_MOVIES':
            return [...state, type];
        case 'SEE_A_MOVIE':
            return [...state, type];
        default: 
            return state;
    }
}