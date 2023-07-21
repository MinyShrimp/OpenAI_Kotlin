import {JSX, MouseEvent} from "react";
import {IconButton, ListItemButton, ListItemText} from "@mui/material";

import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan, faWrench} from "@fortawesome/free-solid-svg-icons";

import {useRecoilState} from "recoil";
import {RIGHT_STATE, RightLayerState} from "../../../states/right_state";
import {IContext, NowContextIdState} from "../../../states/context";

import {DeleteContext} from "../../api/context/DeleteContext";

export function ContextListButton(
    props: { context: IContext }
): JSX.Element {
    const [, setNowContextId] = useRecoilState(NowContextIdState);
    const [, setRightLayerState] = useRecoilState(RightLayerState);

    const deleteContextMutation = DeleteContext();

    const selectEventHandler = (event: MouseEvent) => {
        event.stopPropagation();
        setNowContextId(props.context.id);
        setRightLayerState(RIGHT_STATE.CHAT_COMPLETION);
    }

    const fixEventHandler = (event: MouseEvent) => {
        event.stopPropagation();
        setNowContextId(props.context.id);
        setRightLayerState(RIGHT_STATE.CHANGE_PROMPT);
    }

    const deleteEventHandler = (event: MouseEvent) => {
        event.stopPropagation();
        deleteContextMutation.mutate(props.context.id);
        setNowContextId("");
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
            <ListItemText
                style={{overflow: "hidden"}}
                primary={
                    props.context.setting.title
                }
            />
            <div
                style={{
                    left: "8px",
                    position: "relative",
                    minWidth: "61px"
                }}
            >
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