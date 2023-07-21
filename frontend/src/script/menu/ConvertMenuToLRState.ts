import {SetterOrUpdater} from "recoil";

import {MENU} from "./MenuTypes";
import {LEFT_STATE} from "../states/left_state";
import {RIGHT_STATE} from "../states/right_state";

export const ConvertMenuToLRState = (
    menu: MENU,
    setLeftMenuState: SetterOrUpdater<LEFT_STATE>,
    setRightLayerState: SetterOrUpdater<RIGHT_STATE>
): void => {
    switch (menu) {
        case MENU.COMPLETION:
            setLeftMenuState(LEFT_STATE.DEFAULT);
            setRightLayerState(RIGHT_STATE.COMPLETION);
            break;
        case MENU.CHAT_COMPLETION:
            setLeftMenuState(LEFT_STATE.CHAT_COMPLETION);
            setRightLayerState(RIGHT_STATE.CHAT_COMPLETION);
            break;
        case MENU.MODEL:
            setLeftMenuState(LEFT_STATE.DEFAULT);
            setRightLayerState(RIGHT_STATE.MODEL);
            break;
        case MENU.FILE:
            setLeftMenuState(LEFT_STATE.DEFAULT);
            setRightLayerState(RIGHT_STATE.FILE);
            break;
        case MENU.FINE_TUNING:
            setLeftMenuState(LEFT_STATE.DEFAULT);
            setRightLayerState(RIGHT_STATE.FINE_TUNING);
            break;
        case MENU.DASHBOARD:
            setLeftMenuState(LEFT_STATE.DEFAULT);
            setRightLayerState(RIGHT_STATE.DASHBOARD);
            break;
        default:
            setLeftMenuState(LEFT_STATE.DEFAULT);
            setRightLayerState(RIGHT_STATE.DEFAULT);
            break;
    }
}