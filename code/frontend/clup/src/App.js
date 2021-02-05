import './css/App.css';
import Login from './components/Login';
import Signup from './components/Signup';
import Home from './components/Home';
import Search from './components/Search';
import Shops from './components/Shops';
import Shop from './components/Shop';
import Lineup from './components/Lineup';
import Ticket from './components/Ticket';
import SignupManager from './components/SignupManager';
import HomeManager from './components/HomeManager';
import NewShop from './components/NewShop';
import ShopManager from './components/ShopManager';
import Scanner from './components/Scanner';

import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'

function App() {
    return (
        <Router>
            <div className="App">
                <Switch>
                    <Route path="/Login" exact component={Login} /> {/* Login page */}
                    <Route path="/Signup" exact component={Signup} /> {/* Sign up page for users */}
                    <Route path="/Home" exact component={Home} /> {/* Home page for users */}
                    <Route path="/Search" exact component={Search} /> {/* Search a shop page */}
                    <Route path="/Shops" exact component={Shops} /> {/* List of shops page */}
                    <Route path="/Shop" exact component={Shop} /> {/* Shop page for users */}
                    <Route path="/Lineup" exact component={Lineup} /> {/* Line up page */}
                    <Route path="/Ticket" exact component={Ticket} /> {/* Ticket page */}
                    <Route path="/SignupManager" exact component={SignupManager} /> {/* Sign up page for managers */}
                    <Route path="/HomeManager" exact component={HomeManager} /> {/* Home page for managers */}
                    <Route path="/NewShop" exact component={NewShop} /> {/* New shop page */}
                    <Route path="/ShopManager" exact component={ShopManager} /> {/* Shop page for managers */}
                    <Route path="/Scanner" exact component={Scanner} /> {/* Scanner page */}
                </Switch>
            </div>
        </Router>

    );
}
export default App;

//to change view from a component to another we must add the following import:
//import { Link } from 'react-router-dom'
//The syntax to use it is very simple:
//<Link to='/AnyWhereHere'>
//  <AnyElementHere/>
//</Link>

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

//CONTEXT API for state management

//