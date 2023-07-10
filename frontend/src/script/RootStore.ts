import {combineReducers} from "redux";

import {configureStore} from "@reduxjs/toolkit";
import {TypedUseSelectorHook, useDispatch, useSelector} from "react-redux";

import {MenuReducer} from "./menu/MenuReducer";
import {initialMenuState} from "./menu/MenuTypes";

const rootReducer = combineReducers({
    menuReducer: MenuReducer
});

export const rootStore = configureStore({
    reducer: rootReducer,
    devTools: true,
    preloadedState: {
        menuReducer: initialMenuState
    }
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof rootStore.dispatch;
export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
