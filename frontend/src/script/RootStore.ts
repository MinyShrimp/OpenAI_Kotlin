import {combineReducers} from "redux";

import {configureStore} from "@reduxjs/toolkit";
import {TypedUseSelectorHook, useDispatch, useSelector} from "react-redux";

import {ContextReducer, defaultContextState} from "./states/context";
import {defaultNowContextState, NowContextReducer} from "./states/now_context";

import {defaultLeftState, LeftStateReducer} from "./states/left_state";
import {defaultRightState, RightStateReducer} from "./states/right_state";

const rootReducer = combineReducers({
    contextReducer: ContextReducer,
    nowContextReducer: NowContextReducer,
    leftStateReducer: LeftStateReducer,
    rightStateReducer: RightStateReducer
});

export const rootStore = configureStore({
    reducer: rootReducer,
    devTools: true,
    preloadedState: {
        contextReducer: defaultContextState,
        nowContextReducer: defaultNowContextState,
        leftStateReducer: defaultLeftState,
        rightStateReducer: defaultRightState
    }
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof rootStore.dispatch;
export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
