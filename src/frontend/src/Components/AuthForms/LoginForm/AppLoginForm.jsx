import {Alert, Button, Card, Stack, TextField, Typography} from "@mui/material";
import {Link, useHistory} from "react-router-dom";
import "./AppLoginForm.css";
import {useState} from "react";

const AppLoginForm = ({setAuthToken}) => {

    let alreadySendRequest = false;
    const baseApiEndpoint = process.env.REACT_APP_API_BASE;
    const loginEndpoint = "/auth/login";
    const history = useHistory();
    const tryLoggingIn = () => {
        if (alreadySendRequest) {
            return;
        }
        alreadySendRequest = true;
        if (usernameInput === "" || passwordInput === "") {
            setLoginMessage("You have to input required information to log in!");
            return;
        }
        const request = {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: new URLSearchParams({
                'username': usernameInput,
                'password': passwordInput
            })
        }

        fetch(baseApiEndpoint + loginEndpoint, request)
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

            setAuthToken(json.jwt);
            history.push("/")
        })
            .catch((err) => {
                alreadySendRequest = false;
            })


    }
    const [loginMessage, setLoginMessage] = useState("");
    const [usernameInput, setUsernameInput] = useState("");
    const [passwordInput, setPasswordInput] = useState("");
    return (
        <>
            <Alert className={"form-message"} color={"error"}
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
                    <div className={"login-button-group"}>
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