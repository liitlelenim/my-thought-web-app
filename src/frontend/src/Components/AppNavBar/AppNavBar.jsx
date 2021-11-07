import "./AppNavBar.css";
import {AppBar, IconButton, Toolbar, Typography} from "@mui/material";
import {Link, useHistory} from "react-router-dom";
import {useEffect, useState} from "react";
import {AccountCircle, Logout, Search} from "@mui/icons-material";


const AppNavBar = () => {

    const history = useHistory();
    const [searchInput, setSearchInput] = useState("");

    const handleSearch = () => {
        if (searchInput !== "") {
            history.replace(`/pages/0?tag=${searchInput}`);
        }
    }
    const [jwt, setJwt] = useState(undefined);
    useEffect(() => {
        setJwt(sessionStorage.getItem("jwt"));
    });
    const handleSearchBarChange = (event) => {
        let searchText = event.target.value;
        searchText = searchText.trim();
        setSearchInput(searchText);
    }
    return <AppBar position={"static"}>
        <Toolbar style={{
            display: "flex",
            justifyItems: "right"
        }
        }>

            <Typography variant="h5" className={"logo-box"} component="div" style={{
                fontWeight: "600",
                fontSize: "2rem",
                letterSpacing: "0.1rem"
            }}>
                <Link to={"/"} className={"undecorated-link"}>
                    MyThought
                </Link>

            </Typography>

            <IconButton
                onClick={handleSearch}
                size="large"
                color="inherit"
            >
                <Search fontSize={"large"}/>
            </IconButton>
            <textarea
                placeholder={"Enter tag"}
                maxLength={20}
                wrap={"off"}
                className={"search-field"}
                onChange={handleSearchBarChange}
                value={searchInput}/>
            <Link to={"/auth/login"} className={"undecorated-link"}>
                <IconButton
                    size="large"
                    color="inherit"
                >
                    <AccountCircle
                        fontSize={"large"}/>
                </IconButton>
            </Link>
            {
                jwt ?
                    <IconButton color={"inherit"} onClick={() => {
                        sessionStorage.clear();
                        history.replace("/");
                        window.location.reload();
                    }}>
                        <Logout fontSize={"large"}/>
                    </IconButton>
                    :
                    <></>
            }

        </Toolbar>
    </AppBar>
}
export {AppNavBar}