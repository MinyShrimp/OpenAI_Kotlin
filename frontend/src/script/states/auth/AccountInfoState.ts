import {atom} from "recoil";
import {AccountInfo} from "./AccountInfoTypes";

export const AccountInfoState = atom<AccountInfo | null>({
    key: "AccountInfoState",
    default: null
});
