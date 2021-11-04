import "./MostPopularTagsTable.css";
import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import {IconButton} from "@mui/material";
import {ArrowRight} from "@mui/icons-material";

const MostPopularTagsTable = () => {
    const [tableHidden, setTableHidden] = useState(true);
    const [tagsToShow, setTagsToShow] = useState([]);
    const baseApiUrl = process.env.REACT_APP_API_BASE;
    const mostPopularTagsUrl = "/tags/most-popular";
    useEffect(() => {
        fetch(baseApiUrl + mostPopularTagsUrl)
            .then((res) => {
                if (res.ok) {
                    return res.json()
                }
            }).then(json => setTagsToShow(json))
    }, []);


    return (<div data-render={tagsToShow.length > 0} data-hidden={tableHidden} className={"most-popular-tags-table"}>
        <h3 className={"most-popular-tags-header"}>Most popular tags</h3>
        <IconButton onClick={() => {
            setTableHidden(!tableHidden)
        }}
                    data-hidden={tableHidden}
                    className={"tags-table-button"}>
            <ArrowRight color={"primary"} className={"tags-table-button-icon"}/>
        </IconButton>
        {tagsToShow.map((tag, index) => <div className={"popular-tag"} key={index}>
            <Link to={`?tag=${tag}`} onClick={() => setTableHidden(true)} className={"tag"}>#{tag}</Link>
        </div>)}
    </div>);

}
export {MostPopularTagsTable}