import {JSX} from "react";
import {List, ListItemButton, ListItemText, ListSubheader} from "@mui/material";

import {Layer} from "../base/Layer";

import {MENU} from "./MenuTypes";
import {useAppDispatch} from "../RootStore";
import {ConvertMenuToLRState} from "./ConvertMenuToLRState";

export function MenuLayer(): JSX.Element {
    const dispatch = useAppDispatch();

    return (
        <Layer style={{paddingLeft: "0", paddingRight: "0"}}>
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
        </Layer>
    );
}