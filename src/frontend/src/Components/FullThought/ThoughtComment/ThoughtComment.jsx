import {Link} from "react-router-dom";
import {Card} from "@mui/material";
import "./ThoughtComment.css";

const ThoughtComment = ({author, content, date}) => {
    const formatJavaDate = (date) => {
        const formattedDate = new Date(date)
        return formattedDate.toLocaleTimeString() + " " + formattedDate.toLocaleDateString();
    }

    return <Card variant={"outlined"} className={"thought-comment-container"}>
        <div className={"author-and-date-container"}>
            <Link to={`/users/${author}`} className={"user-link"}>{author}</Link>
            <span className={"thought-date"}>{
                formatJavaDate(date)
            }</span>
        </div>
        <div className={"content-container"}>
            {content}
        </div>
    </Card>;
}
export {ThoughtComment}