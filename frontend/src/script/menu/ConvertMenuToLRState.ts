import {Dispatch} from "redux";
import {MENU} from "./MenuTypes";
import {LEFT_STATE, setLeftState} from "../states/left_state";
import {RIGHT_STATE, setRightState} from "../states/right_state";

export const ConvertMenuToLRState = (
    dispatch: Dispatch,
    menu: MENU
): void => {
    switch (menu) {
        case MENU.COMPLETION:
            setLeftState(dispatch, LEFT_STATE.DEFAULT);
            setRightState(dispatch, RIGHT_STATE.COMPLETION);
            break;
        case MENU.CHAT_COMPLETION:
            setLeftState(dispatch, LEFT_STATE.CHAT_COMPLETION);
            setRightState(dispatch, RIGHT_STATE.CHAT_COMPLETION);
            break;
        case MENU.MODEL:
            setLeftState(dispatch, LEFT_STATE.DEFAULT);
            setRightState(dispatch, RIGHT_STATE.MODEL);
            break;
        case MENU.FILE:
            setLeftState(dispatch, LEFT_STATE.DEFAULT);
            setRightState(dispatch, RIGHT_STATE.FILE);
            break;
        case MENU.FINE_TUNING:
            setLeftState(dispatch, LEFT_STATE.DEFAULT);
            setRightState(dispatch, RIGHT_STATE.FINE_TUNING);
            break;
        case MENU.DASHBOARD:
            setLeftState(dispatch, LEFT_STATE.DEFAULT);
            setRightState(dispatch, RIGHT_STATE.DASHBOARD);
            break;
        default:
            setLeftState(dispatch, LEFT_STATE.DEFAULT);
            setRightState(dispatch, RIGHT_STATE.DEFAULT);
            break;
    }
}