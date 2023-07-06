import {JSX} from "react";
import {Layer} from "../base/Layer";
import {NewPromptLayer} from "../prompt/NewPromptLayer";

export default function MainRightLayer(): JSX.Element {
    return (
        <Layer style={{width: "85%"}}>
            <NewPromptLayer/>
        </Layer>
    );
}