import {JSX} from "react";
import {IconButton, List, ListItemButton, ListSubheader, Typography} from "@mui/material";

import {faBackward, faPlus} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import {Layer} from "../base/Layer";
import {useAppDispatch} from "../RootStore";

import {LEFT_STATE, setLeftState} from "../states/left_state";
import {RIGHT_STATE, setRightState} from "../states/right_state";

export function ChatCompletionListLayer(): JSX.Element {
    const dispatch = useAppDispatch();

    return (
        <Layer style={{paddingLeft: "0", paddingRight: "0"}}>
            <List
                component="nav"
                aria-labelledby="chat_prompt_list"
                subheader={
                    <ListSubheader
                        component="div"
                        id="chat_prompt_list"
                        style={{
                            display: "flex",
                            justifyContent: "space-between",
                            alignItems: "flex-end"
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
                                size="sm"
                            />
                        </IconButton>
                    </ListSubheader>
                }
            >
                <ListItemButton
                    style={{
                        display: "flex",
                        justifyContent: "center",
                        height: "48px",
                        border: "1px solid",
                        borderRadius: "5px",
                        marginTop: "1rem"
                    }}
                    onClick={() => setRightState(dispatch, RIGHT_STATE.NEW_PROMPT)}
                >
                    <FontAwesomeIcon icon={faPlus}/>
                </ListItemButton>
            </List>
        </Layer>
    );
}