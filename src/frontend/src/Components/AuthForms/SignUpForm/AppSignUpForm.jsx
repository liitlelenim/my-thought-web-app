import {Link, useHistory} from "react-router-dom";
import {Alert, Button, Card, Stack, TextField, Typography} from "@mui/material";
import {useState} from "react";

const AppSignUpForm = () => {

    const history = useHistory();
    let alreadySentRequest = false;
    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const signUpUrl = "/auth/signup";
    if (sessionStorage.getItem("jwt")) {
        history.replace(`/pages/0?username=${sessionStorage.getItem("username")}`);
    }
    const validPassword = (password) => {
        if (password.length < 6) {
            return false;
        }
        let containsUpperCase = false;
        let containsLowerCase = false;
        let containsDigit = false;
        for (const char of password) {
            if (!isNaN(char)) {
                containsDigit = true;
            }
            if (char === char.toUpperCase()) {
                containsUpperCase = true;
            }
            if (char === char.toLowerCase()) {
                containsLowerCase = true;
            }
        }
        return containsLowerCase && containsUpperCase && containsDigit;
    }

    const trySigningUp = () => {
        if (alreadySentRequest)
            return;
        alreadySentRequest = true;
        if (usernameInput === "" || passwordInput === "") {
            setSignUpMessage("You have to input required information to create an account!");
            setAlertSeverity("error");
            alreadySentRequest = false;
            return;
        }
        if (!validPassword(passwordInput)) {
            setSignUpMessage("Password must contain at least one lower case letter," +
                " one upper case letter and one digit and be at least six characters long!");
            setAlertSeverity("error");
            alreadySentRequest = false;
            return;
        }
        if (passwordInput !== confirmPasswordInput) {
            setSignUpMessage("Password and confirmation password must be the same!")
            setAlertSeverity("error");
            alreadySentRequest = false;
            return;
        }
        const request = {
            method: "POST",
            body: JSON.stringify({
                username: usernameInput,
                password: passwordInput
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }
        fetch(baseApiUrl + signUpUrl, request)
            .then((response) => {
                if (response.status === 201) {
                    setSignUpMessage("Account successfully created!");
                    setAlertSeverity("success");
                    alreadySentRequest = false;
                    setUsernameInput("");
                    setPasswordInput("");
                    setConfirmPasswordInput("");
                } else if (response.status === 400) {
                    setSignUpMessage("User with given username already exists!");
                    setAlertSeverity("error");
                    alreadySentRequest = false;
                }
            })

    }

    const [signUpMessage, setSignUpMessage] = useState("");
    const [alertSeverity, setAlertSeverity] = useState("error")
    const [usernameInput, setUsernameInput] = useState("");
    const [passwordInput, setPasswordInput] = useState("");
    const [confirmPasswordInput, setConfirmPasswordInput] = useState("");

    return <>
        <Alert className={"form-message"} severity={alertSeverity} color={alertSeverity}
               style={(signUpMessage !== "") ? {
                   opacity: "1",
                   transition: "opacity 0.25s"
               } : {
                   opacity: "0.0",
                   transition: "opacity 0.25s"
               }}>{signUpMessage}</Alert>
        <Card variant={"outlined"} className={"form-card"}>
            <Stack spacing={2} className={"login-form-stack"}>
                <Typography variant={"h6"}>Create new account</Typography>
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
                <TextField
                    label="Confirm password"
                    type="password"
                    autoComplete="current-password"
                    onChange={(event) => {
                        setConfirmPasswordInput(event.target.value)
                    }}
                    value={confirmPasswordInput}
                />
                <div className={"form-button-group"}>
                    <Button variant={"contained"} onClick={trySigningUp}>Sign up</Button>
                    <Typography variant={"p"}>Already have an account?
                        <br/>
                        <Link to={"/auth/login"}>Log in</Link>
                    </Typography>
                </div>
            </Stack>
        </Card>
    </>
}
export {AppSignUpForm}