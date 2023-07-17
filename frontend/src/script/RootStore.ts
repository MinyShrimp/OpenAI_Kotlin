import {combineReducers} from "redux";

import {configureStore} from "@reduxjs/toolkit";
import {TypedUseSelectorHook, useDispatch, useSelector} from "react-redux";

import {initialLeftState, LeftStateReducer} from "./states/left_state";
import {initialRightState, RightStateReducer} from "./states/right_state";

const rootReducer = combineReducers({
    leftStateReducer: LeftStateReducer,
    rightStateReducer: RightStateReducer
});

export const rootStore = configureStore({
    reducer: rootReducer,
    devTools: true,
    preloadedState: {
        leftStateReducer: initialLeftState,
        rightStateReducer: initialRightState
    }
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof rootStore.dispatch;
export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
