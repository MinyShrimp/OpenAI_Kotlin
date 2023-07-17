import {Dispatch} from "redux";
import {RIGHT_STATE, RIGHT_STATE_ACTION} from "./RightStateTypes";

export const setRightState = (
    dispatch: Dispatch,
    state: RIGHT_STATE
): void => {
    dispatch({
        type: RIGHT_STATE_ACTION.SET_RIGHT_STATE,
        payload: {state: state}
    });
}