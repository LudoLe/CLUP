import './css/App.css';
import Start from './components/Start';
import Home from './components/Home';
import Search from './components/Search';
import Shops from './components/Shops';
import Shop from './components/Shop';
import Lineup from './components/Lineup';
import Ticket from './components/Ticket';
import HomeManager from './components/HomeManager';
import NewShop from './components/NewShop';
import ShopManager from './components/ShopManager';
import Scanner from './components/Scanner';

import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';


function App() {
    return (
        <Router>
            <div id="App" className="App"> 
                <Switch>
                    <Route path="/" exact component={Start} /> {/* Login page */}
                    <Route path="/Home" exact component={Home} /> {/* Home page for users */}
                    <Route path="/Search" exact component={Search} /> {/* Search a shop page */}
                    <Route path="/Shops" exact component={Shops} /> {/* List of shops page */}
                    <Route path="/Shop/:id" exact component={Shop} /> {/* Shop page for users */}
                    <Route path="/Lineup" exact component={Lineup} /> {/* Line up page */}
                    <Route path="/Ticket/:id" exact component={Ticket} /> {/* Ticket page */}
                    <Route path="/HomeManager" exact component={HomeManager} /> {/* Home page for managers */}
                    <Route path="/NewShop" exact component={NewShop} /> {/* New shop page */}
                    <Route path="/ShopManager/:id" exact component={ShopManager} /> {/* Shop page for managers */}
                    <Route path="/Scanner" exact component={Scanner} /> {/* Scanner page */}
                </Switch>
            </div>
        </Router>

    );
}
export default App;

//to fetch items from an API use the following code:
//import {useState, useEffect} from 'react'
//the use effect will make the fetch happen when the component mounts:
//useEffect(() => {
//  fetchItems();
//},[])
//with the useState we can save the fetched items in the state:
//const [items, setItems] = useState({});
//const fetchItems = async () => {
//  const data = await fetch('https://urlgoeshere');
//  const items = await data.json();
//  setItems(items);
//}

//fetching a POST request:
/* evt.preventDefault();
//fetching a POST request:
const Url = "http://localhost:8080/";
const Params = {
    param1: 'value',
    param2: 2
}
const Body = {
    thingone: "something",
    etc: "something else"
};
const requestSettings = {
    headers: {
        "header-name": "header-content"
    },
    body: Body,
    method: "POST"
};
fetch(Url + new URLSearchParams(Params), requestSettings)
    .then(response => { return response.json() })
    .then(data => { console.log(data); setData(data) })
    .catch(err => { console.log(err); setError(err) }); */

//CONTEXT API for state management