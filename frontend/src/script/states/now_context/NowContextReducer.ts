import {PayloadAction} from "@reduxjs/toolkit";
import {defaultNowContextState, INowContextState, NOW_CONTEXT_ACTION} from "./NowContextTypes";

export const NowContextReducer = (
    state: INowContextState = defaultNowContextState,
    action: PayloadAction<INowContextState, NOW_CONTEXT_ACTION>
): INowContextState => {
    switch (action.type) {
        case NOW_CONTEXT_ACTION.SET_NOW_CONTEXT:
            return {
                id: action.payload.id
            }
        default:
            return state;
    }
}