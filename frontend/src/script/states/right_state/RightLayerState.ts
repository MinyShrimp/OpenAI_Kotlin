import {atom} from "recoil";
import {RIGHT_STATE} from "./RightStateTypes";

export const RightLayerState = atom<RIGHT_STATE>({
    key: "RightLayerState",
    default: RIGHT_STATE.DEFAULT
});
