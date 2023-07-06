import {JSX} from "react";
import {Layer} from "../base/Layer";

export default function MainLeftLayer(): JSX.Element {
    return (
        <Layer style={{width: "15%", background: "#2d2d2d"}}>
            <p>Left Container</p>
        </Layer>
    );
}