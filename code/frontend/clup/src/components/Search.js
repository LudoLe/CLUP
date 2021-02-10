import React from 'react';
import { useHistory } from "react-router-dom";
import GoBackButton from './sub-components/GoBackButton';

const Search = () => {

    const history = useHistory();

    const handleSubmit = (e) => {
        e.preventDefault();
        alert("function not availble");
    }

    const handleAllShops = () =>{
        history.push("/Shops");
    }

    return (
        <div className="flexColumnCenter">
            <GoBackButton/>
            <div className="flexRowCenter">
                <form onSubmit={handleSubmit}>
                    <input id="searchInput" name="searchInput" type="text" placeholder="search a shop"></input>
                    <button type="submit"> search </button>
                </form>
            </div>
            <div>
                <button onClick={handleAllShops}> ALL SHOPS </button>
            </div>
        </div>
    );
}

export default Search;