import {IconButton} from "@mui/material";
import {Favorite, FavoriteBorderOutlined} from "@mui/icons-material";

const LikeComponent = ({likers, username, togglePostLike}) => {
    return (<>
        <IconButton size="large"
                    color="inherit"
                    onClick={togglePostLike}
        >
            {likers.includes(username) ?
                <Favorite fontSize={"large"} color={"primary"}/>
                :
                <FavoriteBorderOutlined fontSize={"large"}/>}
        </IconButton>
        <span>{likers.length}</span>
    </>)
}
export {LikeComponent}