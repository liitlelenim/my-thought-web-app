import {Card, IconButton} from "@mui/material";
import {Link} from "react-router-dom";
import "./ThoughtOverview.css";
import {ThumbUp} from "@mui/icons-material";

const ThoughtOverview = ({content, date, likes, tags, author}) => {
    const formatJavaDate = (date) => {
        const formattedDate = new Date(date)
        return formattedDate.toLocaleTimeString() + " " + formattedDate.toLocaleDateString();
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
            <IconButton size="large"
                        color="inherit"
            >
                <ThumbUp
                    fontSize={"large"}/>
            </IconButton>
            <span>{likes}</span>
        </div>

    </Card>)
}
export {ThoughtOverview};