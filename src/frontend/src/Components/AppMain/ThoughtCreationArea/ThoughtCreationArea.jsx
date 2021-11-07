import {Button, TextField} from "@mui/material";
import {Link, useHistory} from "react-router-dom";
import "./ThoughtCreationArea.css";
import {useState} from "react";

const ThoughtCreationArea = ({updateThoughts}) => {

    const history = useHistory();
    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const postThoughtUrl = "/thoughts";
    const [thoughtContent, setThoughtContent] = useState("");
    const [tags, setTags] = useState([]);

    const [tagsText, setTagsText] = useState("");
    const handleTagTextFieldChange = (event) => {
        let value = event.target.value.trimLeft().replace(/\s+/g, ' ');
        let tags = value.split(" ");
        tags.map(tag => {
            return tag.trim();
        })
        tags = tags.filter(tag => tag !== "")
        let tagTooLong = false;
        tags.forEach((tag) => {
            if (tag.length > 20) {
                tagTooLong = true;
            }
        })
        if (tagTooLong || tags.length > 5) {
            return;
        }
        setTags(tags);
        event.target.value = value;
        setTagsText(value);
    }

    const handleThoughtCreationChange = (event) => {
        const content = event.target.value.trimLeft().replace(/\s+/g, ' ');
        if (content.length <= 255) {
            setThoughtContent(content);
        }
    }
    let requestProcessing = false;
    const handleThoughtPosting = () => {

        if (thoughtContent.length === 0 || thoughtContent.length > 255 || tags.length === 0 || requestProcessing) {
            return;
        }
        requestProcessing = true;
        const requestOptions = {
            method: "POST",
            body: JSON.stringify({
                content: thoughtContent,
                tags: [...new Set(tags)]
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("jwt")}`
            }
        }
        fetch(baseApiUrl + postThoughtUrl, requestOptions)
            .then((response) => {
                requestProcessing = false;
                if (response.status === 201) {
                    setThoughtContent("");
                    setTagsText("");
                    setTags([]);
                    updateThoughts();
                } else if (response.status === 403) {
                    history.replace("auth/login");
                }
            })
    }

    return (sessionStorage.getItem("username") ?
        <div className={"thought-creation-area-container"}>
            <TextField
                className={"thought-creation-text-field"}
                multiline={true}
                rows={5}
                label="What's on your mind?"
                value={thoughtContent}
                onChange={(event) => handleThoughtCreationChange(event)}
            />
            <div className={"characters-counter"}>{thoughtContent.length}/255</div>

            <TextField
                className={"tag-creation-text-field"}
                multiline={true}
                rows={3}
                label="Tag your thought! (Split tags with single space)!"
                onChange={(event) => handleTagTextFieldChange(event)}
                value={tagsText}
            />
            <div className={"characters-counter"}>{tags.length}/5</div>
            <div className={"button-to-right"}>
                <Button variant="contained" component="button" onClick={handleThoughtPosting}>
                    Post
                </Button>
            </div>
        </div>
        :
        <span className={"centered-info"}>
            <Link
                to={"/auth/login"}>Log in</Link> to share your thoughts!</span>)
}
export {ThoughtCreationArea}