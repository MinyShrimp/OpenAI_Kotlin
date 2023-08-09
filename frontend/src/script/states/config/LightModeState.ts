import {atom} from "recoil";

export const LightModeState = atom<boolean>({
    key: "LightModeState",
    default: localStorage.getItem("darkMode") === "0"
});
