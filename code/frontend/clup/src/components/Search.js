import React from 'react';
import Searchbar from './sub-components/Searchbar';
import Searchhistory from './sub-components/Searchhistory'

const Search = () => {
    return(
        <div>
            <Searchbar />
            <Searchhistory />
        </div>
    );
}

export default Search;