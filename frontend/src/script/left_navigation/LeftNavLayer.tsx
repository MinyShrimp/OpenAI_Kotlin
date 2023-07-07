import {JSX} from "react";
import {List, ListItemButton, ListItemText, ListSubheader} from "@mui/material";
import {Layer} from "../base/Layer";

export function LeftNavLayer(): JSX.Element {
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
                <ListItemButton>
                    <ListItemText primary={"Completion"}/>
                </ListItemButton>
                <ListItemButton>
                    <ListItemText primary={"Chat Completion"}/>
                </ListItemButton>
                <ListItemButton>
                    <ListItemText primary={"Model"}/>
                </ListItemButton>
                <ListItemButton>
                    <ListItemText primary={"File"}/>
                </ListItemButton>
                <ListItemButton>
                    <ListItemText primary={"Fine Tuning"}/>
                </ListItemButton>
                <ListItemButton>
                    <ListItemText primary={"Dashboard"}/>
                </ListItemButton>
            </List>
        </Layer>
    );
}