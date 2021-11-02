import {ArrowBackRounded, ArrowForwardRounded} from "@mui/icons-material";
import {IconButton} from "@mui/material";
import "./PageNav.css";

const PageNav = ({page, setPage, thoughtsAmount}) => {
    return <div className={"page-nav"}>
        {
            page > 0
                ?
                <IconButton>
                    <ArrowBackRounded fontSize={"large"} className={"page-nav-button"}
                                      onClick={() => setPage(page - 1)}/>
                </IconButton>
                : <IconButton disabled={true}>
                    <ArrowBackRounded fontSize={"large"} className={"page-nav-button"}/>
                </IconButton>}

        <span className={"current-page"}>{page}</span>

        {thoughtsAmount === 5 ? <IconButton>
                <ArrowForwardRounded fontSize={"large"} className={"page-nav-button"} onClick={() => setPage(page + 1)}/>
            </IconButton>
            :
            <IconButton disabled={true}>
                <ArrowForwardRounded fontSize={"large"} className={"page-nav-button"}/>
            </IconButton>
        }
    </div>;
}
export {PageNav};