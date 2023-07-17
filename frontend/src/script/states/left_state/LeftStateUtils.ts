import {Dispatch} from "redux";
import {LEFT_STATE, LEFT_STATE_ACTION} from "./LeftStateTypes";

export const setLeftState = (
    dispatch: Dispatch,
    state: LEFT_STATE
): void => {
    dispatch({
        type: LEFT_STATE_ACTION.SET_LEFT_STATE,
        payload: {state: state}
    });
}