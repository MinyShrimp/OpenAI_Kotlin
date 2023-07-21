import {atom} from "recoil";
import {IContext} from "./index";

export const ContextState = atom<IContext[]>({
    key: "ContextState",
    default: []
});

export const NowContextIdState = atom<string>({
    key: "NowContextIdState",
    default: ""
});
