import {JSX} from "react";
import {List, ListItemButton, ListItemText, ListSubheader, Slide} from "@mui/material";

import {MENU} from "./MenuTypes";
import {LEFT_STATE} from "../states/left_state";
import {useAppDispatch, useAppSelector} from "../RootStore";

import {ConvertMenuToLRState} from "./ConvertMenuToLRState";

export function MenuLayer(): JSX.Element {
    const dispatch = useAppDispatch();
    const leftState = useAppSelector((selector) => selector.leftStateReducer)

    return (
        <Slide
            direction="right"
            in={leftState.state === LEFT_STATE.DEFAULT}
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
                                    dispatch,
                                    MENU[entry[0] as keyof typeof MENU]
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