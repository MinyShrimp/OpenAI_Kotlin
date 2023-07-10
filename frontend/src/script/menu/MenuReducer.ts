import {PayloadAction} from "@reduxjs/toolkit";
import {IMenuState, initialMenuState, MENU_ACTION} from "./MenuTypes";

export const MenuReducer = (
    state: IMenuState = initialMenuState,
    action: PayloadAction<IMenuState, MENU_ACTION>
): IMenuState => {
    switch (action.type) {
        case MENU_ACTION.SET_MENU:
            return {
                ...state,
                menu: action.payload.menu
            };
        default:
            return state;
    }
}
