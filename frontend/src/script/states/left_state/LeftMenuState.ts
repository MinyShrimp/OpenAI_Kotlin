import {atom} from "recoil";
import {LEFT_STATE} from "./LeftStateTypes";

export const LeftMenuState = atom<LEFT_STATE>({
    key: "LeftMenuState",
    default: LEFT_STATE.DEFAULT
});
