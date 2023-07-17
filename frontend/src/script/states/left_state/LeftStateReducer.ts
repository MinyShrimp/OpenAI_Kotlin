import {PayloadAction} from "@reduxjs/toolkit";
import {ILeftState, initialLeftState, LEFT_STATE_ACTION} from "./LeftStateTypes";

export const LeftStateReducer = (
    state: ILeftState = initialLeftState,
    action: PayloadAction<ILeftState, LEFT_STATE_ACTION>
): ILeftState => {
    switch (action.type) {
        case LEFT_STATE_ACTION.SET_LEFT_STATE:
            return {
                state: action.payload.state
            };
        default:
            return state;
    }
}