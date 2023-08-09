import {atom} from "recoil";

export const LoginContextState = atom<string>({
    key: "LoginContextState",
    default: "login"
});
