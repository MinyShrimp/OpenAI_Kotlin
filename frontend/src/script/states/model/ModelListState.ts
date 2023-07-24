import {atom} from "recoil";
import {Model} from "./ModelListTypes";

export const ModelListState = atom<Model[]>({
    key: "ModelListState",
    default: []
});
