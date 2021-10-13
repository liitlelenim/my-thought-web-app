import {Route, Switch} from "react-router-dom";
import {MainPage} from "./Components/MainPage";

function App() {
    return (
        <div className="App">
            <Switch>
                <Route path={"/"} component={MainPage} exact/>
            </Switch>
        </div>
    );
}

export default App;
