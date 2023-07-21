import {JSX} from "react";
import {List, ListItemButton, ListItemText, ListSubheader, Slide} from "@mui/material";

import {useRecoilState} from "recoil";
import {LEFT_STATE, LeftMenuState} from "../states/left_state";

import {MENU} from "./MenuTypes";
import {ConvertMenuToLRState} from "./ConvertMenuToLRState";
import {RightLayerState} from "../states/right_state";

export function MenuLayer(): JSX.Element {
    const [leftMenuState, setLeftMenuState] = useRecoilState(LeftMenuState);
    const [, setRightLayerState] = useRecoilState(RightLayerState);

    return (
        <Slide
            direction="right"
            in={leftMenuState === LEFT_STATE.DEFAULT}
        >
            <List
                component="nav"
                aria-labelledby="main_menu_list"
                subheader={
                    <ListSubheader component="div" id="main_menu_list">
                        Menu
                    </ListSubheader>
                }
            >
                {
                    Object.entries(MENU)
                        .map((entry, index) => (
                            <ListItemButton
                                key={index}
                                onClick={() => ConvertMenuToLRState(
                                    MENU[entry[0] as keyof typeof MENU],
                                    setLeftMenuState,
                                    setRightLayerState
                                )}
                            >
                                <ListItemText primary={entry[1]}/>
                            </ListItemButton>
                        ))
                }
            </List>
        </Slide>
    );
}