import {JSX} from "react";
import {List, ListItemButton, ListItemText, ListSubheader} from "@mui/material";
import {Layer} from "../base/Layer";
import {useAppDispatch} from "../RootStore";
import {MENU, MENU_ACTION} from "./MenuTypes";

export function MenuLayer(): JSX.Element {
    const dispatch = useAppDispatch();

    const setMenu = (menu: MENU): void => {
        dispatch({
            type: MENU_ACTION.SET_MENU,
            payload: {menu: menu}
        });
    }

    return (
        <Layer style={{paddingLeft: "0", paddingRight: "0"}}>
            <List
                component="nav"
                aria-labelledby="nested-list-subheader"
                subheader={
                    <ListSubheader component="div" id="nested-list-subheader">
                        Menu
                    </ListSubheader>
                }
            >
                <ListItemButton
                    onClick={() => setMenu(MENU.COMPLETION)}
                >
                    <ListItemText primary={"Completion"}/>
                </ListItemButton>
                <ListItemButton
                    onClick={() => setMenu(MENU.CHAT_COMPLETION)}
                >
                    <ListItemText primary={"Chat Completion"}/>
                </ListItemButton>
                <ListItemButton
                    onClick={() => setMenu(MENU.NEW_PROMPT)}
                >
                    <ListItemText primary={"New Prompt"}/>
                </ListItemButton>
                <ListItemButton
                    onClick={() => setMenu(MENU.MODEL)}
                >
                    <ListItemText primary={"Model"}/>
                </ListItemButton>
                <ListItemButton
                    onClick={() => setMenu(MENU.FILE)}
                >
                    <ListItemText primary={"File"}/>
                </ListItemButton>
                <ListItemButton
                    onClick={() => setMenu(MENU.FINE_TUNING)}
                >
                    <ListItemText primary={"Fine Tuning"}/>
                </ListItemButton>
                <ListItemButton
                    onClick={() => setMenu(MENU.DASHBOARD)}
                >
                    <ListItemText primary={"Dashboard"}/>
                </ListItemButton>
            </List>
        </Layer>
    );
}