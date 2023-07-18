import {JSX} from "react";
import {List, ListItemButton, Slide} from "@mui/material";

import {faPlus} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import {useAppDispatch, useAppSelector} from "../RootStore";
import {LEFT_STATE} from "../states/left_state";
import {RIGHT_STATE, setRightState} from "../states/right_state";

import {ContextListHeader} from "./ContextListHeader";
import {ContextListButton} from "./ContextListButton";

export function ChatCompletionListLayer(): JSX.Element {
    const dispatch = useAppDispatch();
    const leftState = useAppSelector((selector) => selector.leftStateReducer);
    const contextState = useAppSelector((selector) => selector.contextReducer);

    return (
        <Slide
            direction="right"
            in={leftState.state === LEFT_STATE.CHAT_COMPLETION}
        >
            <List
                component="nav"
                aria-labelledby="chat_prompt_list"
                subheader={<ContextListHeader/>}
            >
                {contextState.contexts.map((context) => (
                    <ContextListButton context={context} key={context.id}/>
                ))}
                <ListItemButton
                    style={{
                        display: "flex",
                        justifyContent: "center",
                        height: "48px",
                        border: "1px solid",
                        borderRadius: "5px",
                    }}
                    onClick={() => setRightState(dispatch, RIGHT_STATE.NEW_PROMPT)}
                >
                    <FontAwesomeIcon icon={faPlus}/>
                </ListItemButton>
            </List>
        </Slide>
    );
}