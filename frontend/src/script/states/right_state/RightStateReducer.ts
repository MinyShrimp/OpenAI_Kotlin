import {PayloadAction} from "@reduxjs/toolkit";
import {defaultRightState, IRightState, RIGHT_STATE_ACTION} from "./RightStateTypes";

export const RightStateReducer = (
    state: IRightState = defaultRightState,
    action: PayloadAction<IRightState, RIGHT_STATE_ACTION>
): IRightState => {
    switch (action.type) {
        case RIGHT_STATE_ACTION.SET_RIGHT_STATE:
            return {
                state: action.payload.state
            };
        default:
            return state;
    }
}