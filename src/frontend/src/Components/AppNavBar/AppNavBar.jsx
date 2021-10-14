import "./AppNavBar.css";
import {AppBar, IconButton, Toolbar, Typography} from "@mui/material";
import AccountCircle from '@mui/icons-material/AccountCircle';
import {useState} from "react";
import {Search} from "@mui/icons-material";

const AppNavBar = () => {

    const [searchInput, setSearchInput] = useState("");
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
            <Typography variant="h5" component="div" class={"logo"}>
                MyThought
            </Typography>

            <IconButton
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
            <IconButton
                size="large"
                color="inherit"
            >
                <AccountCircle
                    fontSize={"large"}/>
            </IconButton>
        </Toolbar>
    </AppBar>
}
export {AppNavBar}