import {Alert, Button, Card, Stack, TextField, Typography} from "@mui/material";
import {Link, useHistory} from "react-router-dom";
import "../AuthFormForm.css";
import {useState} from "react";

const AppLoginForm = () => {
    let alreadySentRequest = false;
    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const loginUrl = "/auth/login";
    const history = useHistory();
    if(sessionStorage.getItem("jwt")){
        history.replace(`/pages/0?username=${sessionStorage.getItem("username")}`);
    }
    const tryLoggingIn = () => {
        if (alreadySentRequest) {
            return;
        }
        alreadySentRequest = true;
        if (usernameInput === "" || passwordInput === "") {
            setLoginMessage("You have to input required information to log in!");
            return;
        }
        const username = usernameInput;
        const request = {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: new URLSearchParams({
                'username': username,
                'password': passwordInput
            })
        }
        fetch(baseApiUrl + loginUrl, request)
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 403) {

                    setLoginMessage("The credentials you've submitted are invalid.");
                    throw new Error("Invalid credentials");
                } else {
                    throw new Error();
                }
            }).then((json) => {

            sessionStorage.setItem("jwt", json.substring("jwt : ".length));
            sessionStorage.setItem("username", username);
            history.push("/")
            window.location.reload();
        })
            .catch((_) => {
                alreadySentRequest = false;
            })


    }
    const [loginMessage, setLoginMessage] = useState("");
    const [usernameInput, setUsernameInput] = useState("");
    const [passwordInput, setPasswordInput] = useState("");
    return (
        <>
            <Alert className={"form-message"} severity={"error"} color={"error"}
                   style={(loginMessage !== "") ? {
                       opacity: "1",
                       transition: "opacity 0.25s"
                   } : {
                       opacity: "0.0",
                       transition: "opacity 0.25s"
                   }}>{loginMessage}</Alert>
            <Card variant={"outlined"} className={"form-card"}>
                <Stack spacing={2} className={"login-form-stack"}>
                    <Typography variant={"h6"}>Log into your account</Typography>
                    <TextField
                        label="Username"
                        onChange={(event) => {
                            setUsernameInput(event.target.value)
                        }}
                        value={usernameInput}
                    />
                    <TextField
                        label="Password"
                        type="password"
                        autoComplete="current-password"
                        onChange={(event) => {
                            setPasswordInput(event.target.value)
                        }}
                        value={passwordInput}
                    />
                    <div className={"form-button-group"}>
                        <Button variant={"contained"} onClick={tryLoggingIn}>Log in</Button>
                        <Typography variant={"p"}>Don't have an account?
                            <br/>
                            <Link to={"/auth/signup"}>Sign up</Link>
                        </Typography>
                    </div>
                </Stack>
            </Card>
        </>);
}
export {AppLoginForm};