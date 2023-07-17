import {initialRightState, IRightState, RIGHT_STATE_ACTION} from "./RightStateTypes";
import {PayloadAction} from "@reduxjs/toolkit";

export const RightStateReducer = (
    state: IRightState = initialRightState,
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