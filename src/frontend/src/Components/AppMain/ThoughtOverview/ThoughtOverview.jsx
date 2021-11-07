import {Card, IconButton} from "@mui/material";
import {Link, useHistory} from "react-router-dom";
import "./ThoughtOverview.css";
import {LikeComponent} from "./LikeComponent/LikeComponent";
import {useState} from "react";
import {Comment} from "@mui/icons-material";

const ThoughtOverview = ({id, content, date, likersProp, tags, author, isLink}) => {
    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const toggleLikeUrl = `/thoughts/${id}/likes`;
    const [likers, setLikers] = useState(likersProp);
    const history = useHistory();
    const auth = {
        jwt: sessionStorage.getItem("jwt"),
        username: sessionStorage.getItem("username")
    }
    const formatJavaDate = (date) => {
        const formattedDate = new Date(date)
        return formattedDate.toLocaleTimeString() + " " + formattedDate.toLocaleDateString();
    }
    const toggleThoughtLikeFrontend = () => {
        const likerIndex = likers.indexOf(auth.username);
        if (likerIndex > -1) {
            setLikers(likers.filter((liker, index) => index !== likerIndex))
        } else {
            setLikers([...likers, auth.username])
        }
    }
    const toggleThoughtLikeRequest = () => {
        if (auth.username === "") {
            history.replace("/auth/login");
        }
        const requestOptions = {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${auth.jwt}`
            }
        };
        fetch(baseApiUrl + toggleLikeUrl, requestOptions)
            .then((response) => {
                if (response.status === 201) {
                    toggleThoughtLikeFrontend();
                } else if (response.status === 403) {
                    history.replace("/auth/login");
                }
            });
    }
    return (<Card variant={"outlined"} className={"thought-card"}>
        <div className={"author-and-date-container"}>
            <Link to={`/users/${author}`} className={"user-link"}>{author}</Link>
            <span className={"thought-date"}>{
                formatJavaDate(date)
            }</span>
        </div>
        <div className={"content-container"}>
            {content}
        </div>

        <div className={"tags-container"}>
            {tags.map((tag, id) =>
                <Link to={`/pages/0?tag=${tag}`} className="tag" key={id}>#{tag}</Link>
            )}
        </div>
        <div className={"actions-container"}>
            <LikeComponent likers={likers} username={auth.username} togglePostLike={toggleThoughtLikeRequest}/>
            {isLink ? <IconButton
                onClick={() => {
                    history.replace(`/thought/${id}`)
                }}
                to={`/thought/${id}`}
                className={"undecorated-link comment-link"}>
                <Comment
                    fontSize={"large"}/></IconButton> : <></>}
        </div>
    </Card>)
}
export {ThoughtOverview};