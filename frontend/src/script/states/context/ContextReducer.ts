import {PayloadAction} from "@reduxjs/toolkit";
import {CONTEXT_ACTION, defaultContextState, IContext, IContextState} from "./ContextTypes";

export const ContextReducer = (
    state: IContextState = defaultContextState,
    action: PayloadAction<IContext, CONTEXT_ACTION>
): IContextState => {
    switch (action.type) {
        case CONTEXT_ACTION.ADD_CONTEXT:
            return {
                contexts: [
                    ...state.contexts,
                    action.payload
                ]
            };
        case CONTEXT_ACTION.CHANGE_CONTEXT:
            const tmpContexts = [...state.contexts];
            const index = tmpContexts.findIndex((value) => value.id === action.payload.id);
            if (index === -1) {
                return state;
            }

            tmpContexts[index].title = action.payload.title;
            tmpContexts[index].prePrompt = [...action.payload.prePrompt];
            return {contexts: [...tmpContexts]};
        case CONTEXT_ACTION.DELETE_CONTEXT:
            const newContexts = state.contexts.filter((value) => value.id !== action.payload.id);
            return {contexts: [...newContexts]};
        default:
            return state;
    }
}
