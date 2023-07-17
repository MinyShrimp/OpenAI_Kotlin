import {JSX, MouseEvent} from "react";
import {IconButton, ListItemButton, ListItemText} from "@mui/material";

import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan, faWrench} from "@fortawesome/free-solid-svg-icons";

import {useAppDispatch} from "../RootStore";
import {setNowContextId} from "../states/now_context";
import {CONTEXT_ACTION, IContext} from "../states/context";
import {RIGHT_STATE, setRightState} from "../states/right_state";

export function ContextListButton(
    props: { context: IContext }
): JSX.Element {
    const dispatch = useAppDispatch();

    const selectEventHandler = (event: MouseEvent) => {
        event.stopPropagation();
        setNowContextId(dispatch, props.context.id);
        setRightState(dispatch, RIGHT_STATE.CHAT_COMPLETION);
    }

    const fixEventHandler = (event: MouseEvent) => {
        event.stopPropagation();
        setNowContextId(dispatch, props.context.id);
        setRightState(dispatch, RIGHT_STATE.NEW_PROMPT);
    }

    const deleteEventHandler = (event: MouseEvent) => {
        event.stopPropagation();
        setNowContextId(dispatch, "");
        dispatch({
            type: CONTEXT_ACTION.DELETE_CONTEXT,
            payload: props.context
        });
    }

    return (
        <ListItemButton
            style={{
                marginBottom: "1rem",
                border: "1px solid",
                borderRadius: "5px",
            }}
            onClick={selectEventHandler}
        >
            <ListItemText primary={props.context.title}/>
            <div style={{position: "relative", left: "8px"}}>
                <IconButton onClick={fixEventHandler}>
                    <FontAwesomeIcon
                        icon={faWrench}
                        size="2xs"
                    />
                </IconButton>
                <IconButton onClick={deleteEventHandler}>
                    <FontAwesomeIcon
                        icon={faTrashCan}
                        size="2xs"
                    />
                </IconButton>
            </div>
        </ListItemButton>
    );
}