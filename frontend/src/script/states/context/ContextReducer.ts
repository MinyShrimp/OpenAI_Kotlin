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
            const {id, title, prePrompt} = action.payload;
            const updatedContexts = state.contexts.map(
                (context) => context.id === id
                    ? {...context, title, prePrompt: [...prePrompt]}
                    : context
            );
            return {contexts: updatedContexts};
        case CONTEXT_ACTION.DELETE_CONTEXT:
            const newContexts = state.contexts.filter((value) => value.id !== action.payload.id);
            return {contexts: [...newContexts]};
        default:
            return state;
    }
}
