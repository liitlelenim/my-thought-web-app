import {Route, Switch} from "react-router-dom";
import {MainPage} from "./Components/MainPage";
import {AppNavBar} from "./Components/AppNavBar/AppNavBar";
import {useState} from "react";
import {AppLoginForm} from "./Components/AuthForms/LoginForm/AppLoginForm";
import {AppSignUpForm} from "./Components/AuthForms/SignUpForm/AppSignUpForm";

function App() {
    const [JWT, setJWT] = useState("");
    return (
        <div className="App">
            <AppNavBar/>
            <Switch>
                <Route path={"/"} component={MainPage} exact/>
                <Route path={"/auth/login"} exact>
                    <AppLoginForm setAuthToken={(jwt) => {
                        setJWT(jwt)
                    }}/>
                </Route>
                <Route path={"/auth/signup"} exact>
                    <AppSignUpForm/>
                </Route>
            </Switch>
        </div>
    );
}

export default App;
