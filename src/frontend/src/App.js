import {Route, Switch} from "react-router-dom";
import {MainPage} from "./Components/MainPage";
import {AppNavBar} from "./Components/AppNavBar/AppNavBar";

function App() {
    return (
        <div className="App">
            <AppNavBar/>
            <Switch>
                <Route path={"/"} component={MainPage} exact/>
            </Switch>
        </div>
    );
}

export default App;
