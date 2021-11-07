import {useEffect, useState} from "react";
import {ThoughtOverview} from "./AppMain/ThoughtOverview/ThoughtOverview";
import {useHistory, useParams} from "react-router-dom";
import {ThoughtCreationArea} from "./AppMain/ThoughtCreationArea/ThoughtCreationArea";
import "./MainPage.css";
import {PageNav} from "./AppMain/PageNav/PageNav";
import {MostPopularTagsTable} from "./MostPopularTagsTable/MostPopularTagsTable";
import {StringParam, useQueryParam} from "use-query-params";

const MainPage = () => {


    const [tagSearch] = useQueryParam("tag", StringParam);
    const [username] = useQueryParam("username", StringParam);
    const history = useHistory();
    let {pageNumber} = useParams();

    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const thoughtsPageUrl = "/thoughts/pages/";
    if (isNaN(pageNumber)) {
        pageNumber = 0;
    }
    const [page, setPage] = useState(pageNumber);
    const updateThoughts = () => {
        let finalEndpoint = baseApiUrl + thoughtsPageUrl + page;
        if (username) {
            finalEndpoint += `?username=${username}`;
        } else if (tagSearch) {
            finalEndpoint += `?tag=${tagSearch}`;
        }
        fetch(finalEndpoint)
            .then((res) => {
                if (res.ok) {
                    return res.json();
                } else {
                    setThoughts(undefined);
                    throw new Error("Page not found")
                }
            }).then((json) => {
            setThoughts(json);
        })
    }
    const [thoughts, setThoughts] = useState([])
    useEffect(() => {
        updateThoughts();
    }, [tagSearch, username]);

    useEffect(() => {
        let newUrl = `/pages/${page}`;
        if (username) {
            newUrl += `?username=${username}`;
        } else if (tagSearch) {
            newUrl += `?tag=${tagSearch}`;
        }
        history.replace(newUrl);
        window.scrollTo({
            top: 0,
            behavior: "auto"
        });
        updateThoughts();
    }, [page,baseApiUrl, history, tagSearch,username]);


    return <div className={"main-page-content"}>
        {!username ? <ThoughtCreationArea updateThoughts={updateThoughts}/> :
            <h2 className={"centered-info"}>{username}'s thoughts:</h2>}
        {thoughts ?
            thoughts.map((thought, id) =>
                <ThoughtOverview
                    id={thought.id}
                    content={thought.content}
                    date={thought.postDate}
                    likersProp={thought.likersUsernames}
                    tags={thought.tags}
                    author={thought.authorUsername}
                    isLink={true}
                    key={id}
                />) : <></>}
        <MostPopularTagsTable/>
        <PageNav page={parseInt(page)} setPage={setPage} thoughtsAmount={thoughts.length}/>
    </div>;
}
export
{
    MainPage
}
