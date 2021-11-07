import {Redirect, Route, Switch} from "react-router-dom";
import {MainPage} from "./Components/MainPage";
import {AppNavBar} from "./Components/AppNavBar/AppNavBar";
import {AppLoginForm} from "./Components/AuthForms/LoginForm/AppLoginForm";
import {AppSignUpForm} from "./Components/AuthForms/SignUpForm/AppSignUpForm";
import {FullThought} from "./Components/FullThought/FullThought";
import {QueryParamProvider} from "use-query-params";

function App() {

    return (
        <div className="App">
            <AppNavBar/>
            <Switch>
                <QueryParamProvider ReactRouterRoute={Route}>
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
                </QueryParamProvider>
            </Switch>
        </div>
    )
        ;
}

export default App;
