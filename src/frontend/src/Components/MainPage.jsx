import {useEffect, useState} from "react";
import {ThoughtOverview} from "./AppMain/ThoughtOverview/ThoughtOverview";
import {useLocation, useParams} from "react-router-dom";

const MainPage = () => {

    const useQuery = () => {
        return new URLSearchParams(useLocation().search);
    }
    const query = useQuery();
    const tagSearch = query.get("tag");

    let {pageNumber} = useParams();

    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const thoughtsPageUrl = "/thoughts/pages/";
    if (isNaN(pageNumber)) {
        pageNumber = 0;
    }
    const [page, setPage] = useState(pageNumber);
    const [thoughts, setThoughts] = useState([])
    useEffect(() => {
        let finalEndpoint = baseApiUrl + thoughtsPageUrl + page;
        if (tagSearch) {
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
    }, [page, tagSearch]);

    return <div>
        {thoughts !== undefined ?
            thoughts.map((thought, id) =>
                <ThoughtOverview
                    id={thought.id}
                    content={thought.content}
                    date={thought.postDate}
                    likersProp={thought.likersUsernames}
                    tags={thought.tags}
                    author={thought.authorUsername}
                    key={id}
                />) : (<h1>404</h1>)}
    </div>;
}
export {MainPage}
