import {IconButton, ListSubheader, Typography} from "@mui/material";

import {faBackward} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import {useRecoilState} from "recoil";
import {LEFT_STATE, LeftMenuState} from "../../../states/left_state";
import {NowContextIdState} from "../../../states/context";

export function ContextListHeader() {
    const [, setLeftMenuState] = useRecoilState(LeftMenuState);
    const [, setNowContextId] = useRecoilState(NowContextIdState);

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
                onClick={() => {
                    setLeftMenuState(LEFT_STATE.DEFAULT)
                    setNowContextId("")
                }}
            >
                <FontAwesomeIcon
                    icon={faBackward}
                    size="xs"
                />
            </IconButton>
        </ListSubheader>
    );
}