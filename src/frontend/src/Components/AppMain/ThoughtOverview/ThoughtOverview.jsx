import {Card} from "@mui/material";
import {Link, useHistory} from "react-router-dom";
import "./ThoughtOverview.css";
import {LikeComponent} from "./LikeComponent/LikeComponent";
import {useState} from "react";

const ThoughtOverview = ({id, content, date, likersProp, tags, author}) => {
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
            <Link to={`/users/${author}`}>{author}</Link>
            <span>{
                formatJavaDate(date)
            }</span>
        </div>
        <div className={"content-container"}>
            {content}
        </div>

        <div className={"tags-container"}>
            {tags.map((tag, id) =>
                <span className="tag" key={id}>#{tag}</span>
            )}
        </div>
        <div className={"like-container"}>
            <LikeComponent likers={likers} username={auth.username} togglePostLike={toggleThoughtLikeRequest}/>
        </div>
    </Card>)
}
export {ThoughtOverview};