import {IconButton, ListSubheader, Typography} from "@mui/material";

import {faBackward} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import {useAppDispatch} from "../RootStore";
import {LEFT_STATE, setLeftState} from "../states/left_state";

export function ContextListHeader() {
    const dispatch = useAppDispatch();

    return (
        <ListSubheader
            component="div"
            id="chat_prompt_list"
            style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "flex-end",
                marginBottom: "1rem"
            }}
        >
            <Typography variant="subtitle2">
                Prompt List
            </Typography>
            <IconButton
                style={{
                    top: "4px",
                    left: "8px"
                }}
                onClick={() => setLeftState(dispatch, LEFT_STATE.DEFAULT)}
            >
                <FontAwesomeIcon
                    icon={faBackward}
                    size="xs"
                />
            </IconButton>
        </ListSubheader>
    );
}