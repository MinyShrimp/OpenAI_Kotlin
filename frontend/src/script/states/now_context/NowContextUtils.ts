import {Dispatch} from "redux";
import {NOW_CONTEXT_ACTION} from "./NowContextTypes";

export const setNowContextId = (
    dispatch: Dispatch, id: string
) => {
    dispatch({
        type: NOW_CONTEXT_ACTION.SET_NOW_CONTEXT,
        payload: {id: id}
    });
};
