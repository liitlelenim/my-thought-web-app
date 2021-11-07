import {ThoughtOverview} from "../AppMain/ThoughtOverview/ThoughtOverview";
import {Link, useHistory, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import "./FullThought.css";
import {ThoughtComment} from "./ThoughtComment/ThoughtComment";
import {Button, TextField} from "@mui/material";

const FullThought = () => {
    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const thoughtsApiUrl = "/thoughts";
    const commentsApiUrl = "/comments";
    let {thoughtId} = useParams();
    const [thoughtLoaded, setThoughtLoaded] = useState(false);
    const jwt = sessionStorage.getItem("jwt");
    const getThoughtData = () => {
        fetch(baseApiUrl + thoughtsApiUrl + "/" + thoughtId)
            .then((res) => {
                if (res.ok) {
                    return res.json();
                } else if (res.status === 404) {
                    setThoughtLoaded(true)
                }
            }).then((json) => {
            setThought(json);
            setThoughtLoaded(true);
        });
    }
    useEffect(() => {
        getThoughtData();
    }, [])
    const history = useHistory();
    const [commentContent, setCommentContent] = useState("");

    const handleCommentChange = (event) => {
        const content = event.target.value.trimLeft().replace(/\s+/g, ' ');
        if (content.length <= 255) {
            setCommentContent(content);
        }
    }

    const [thought, setThought] = useState(undefined);

    let requestProcessing = false;
    const handleCommentCreation = () => {
        if (commentContent.length === 0 || commentContent.length > 255 || requestProcessing) {
            return;
        }
        requestProcessing = true;
        const requestOptions = {
            method: "POST",
            body: ("\"" + commentContent + "\""),
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("jwt")}`
            }
        }

        fetch(`${baseApiUrl}${thoughtsApiUrl}/${thought.id}${commentsApiUrl}`, requestOptions)
            .then((response) => {
                requestProcessing = false;
                if (response.status === 201) {
                    setCommentContent("");
                    getThoughtData();
                } else if (response.status === 403) {
                    history.replace("auth/login");
                }
            })
    }

    if (thoughtLoaded) {
        if (thought !== undefined) {
            return (<div>
                    <ThoughtOverview id={thought.id}
                                     content={thought.content}
                                     date={thought.postDate}
                                     likersProp={thought.likersUsernames}
                                     tags={thought.tags}
                                     author={thought.authorUsername}
                                     isLink={false}/>
                    <div className={"comments-container"}>
                        <h2>Comments ({thought.comments.length}):</h2>
                        {jwt ?
                            <div className={"comment-creation-container"}>
                                <TextField
                                    className={"comment-creation-field"}
                                    multiline={true}
                                    rows={5}
                                    label="What's on your mind?"
                                    value={commentContent}
                                    onChange={(event) => handleCommentChange(event)}
                                />
                                <div className={"comment-creation-utility-container"}>
                                    <div className={"characters-counter"}>{commentContent.length}/255</div>

                                    <Button variant="contained" component="button" onClick={() => {
                                        handleCommentCreation()
                                    }}>
                                        Post
                                    </Button>
                                </div>
                            </div>
                            :
                            <span className={"centered-info"}>
                        <Link to={"/auth/login"}>Log in</Link> to comment thoughts!
                        </span>}
                        {thought.comments.map((comment) => {
                            return <ThoughtComment author={comment.authorUsername} content={comment.content}
                                                   date={comment.postDate}/>;
                        })}
                    </div>
                </div>
            )
        } else {
            return <h1>Oops it's empty here :C</h1>;
        }
    } else {
        return <></>;
    }

}
export
{
    FullThought
}