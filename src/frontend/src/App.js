import {Redirect, Route, Switch} from "react-router-dom";
import {MainPage} from "./Components/MainPage";
import {AppNavBar} from "./Components/AppNavBar/AppNavBar";
import {AppLoginForm} from "./Components/AuthForms/LoginForm/AppLoginForm";
import {AppSignUpForm} from "./Components/AuthForms/SignUpForm/AppSignUpForm";
import {FullThought} from "./Components/FullThought/FullThought";

function App() {

    return (
        <div className="App">
            <AppNavBar/>
            <Switch>
                <Route path={"/"} exact>
                    <Redirect
                        to={{
                            pathname: "/pages/0"
                        }}/>
                </Route>
                <Route path={"/pages/:pageNumber"}>
                    <MainPage/>
                </Route>
                <Route path={"/auth/login"} exact>
                    <AppLoginForm/>
                </Route>
                <Route path={"/auth/signup"} exact>
                    <AppSignUpForm/>
                </Route>
                <Route path={"/thought/:thoughtId"} exact>
                    <FullThought/>
                </Route>
            </Switch>
        </div>
    );
}

export default App;
