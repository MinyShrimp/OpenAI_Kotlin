import {JSX} from "react";
import {List, ListItemButton, Slide} from "@mui/material";

import {faPlus} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import {useRecoilState} from "recoil";
import {ContextState} from "../../../states/context";
import {LEFT_STATE, LeftMenuState} from "../../../states/left_state";
import {RIGHT_STATE, RightLayerState} from "../../../states/right_state";

import {GetContextList} from "../../api/context/GetContextList";

import {ContextListHeader} from "./ContextListHeader";
import {ContextListButton} from "./ContextListButton";

export function ContextListLayer(): JSX.Element {
    const [leftMenuState,] = useRecoilState(LeftMenuState);
    const [, setRightLayerState] = useRecoilState(RightLayerState);
    const [contextList,] = useRecoilState(ContextState);

    GetContextList();

    return (
        <Slide
            direction="right"
            in={leftMenuState === LEFT_STATE.CHAT_COMPLETION}
        >
            <List
                component="nav"
                aria-labelledby="chat_prompt_list"
                subheader={<ContextListHeader/>}
            >
                {contextList.map((context) => (
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
                    onClick={() => setRightLayerState(RIGHT_STATE.NEW_PROMPT)}
                >
                    <FontAwesomeIcon icon={faPlus}/>
                </ListItemButton>
            </List>
        </Slide>
    );
}