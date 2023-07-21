import {JSX} from "react";
import {Box} from "@mui/material";

import {useRecoilState} from "recoil";
import {RIGHT_STATE, RightLayerState} from "../states/right_state";

import {PromptCreateLayer} from "../chat_completion/components/pre_prompt/PromptCreateLayer";
import {ChatCompletionLayer} from "../chat_completion/components/chat/ChatCompletionLayer";
import {PromptChangeLayer} from "../chat_completion/components/pre_prompt/PromptChangeLayer";
import {ModelViewerLayer} from "../model_viewer/ModelViewerLayer";

export default function MainRightLayer(): JSX.Element {
    const [rightLayerState,] = useRecoilState(RightLayerState);
    const convertMenu = (state: RIGHT_STATE): JSX.Element => {
        switch (state) {
            case RIGHT_STATE.CHAT_COMPLETION:
                return <ChatCompletionLayer/>;
            case RIGHT_STATE.NEW_PROMPT:
                return <PromptCreateLayer/>;
            case RIGHT_STATE.CHANGE_PROMPT:
                return <PromptChangeLayer/>;
            case RIGHT_STATE.MODEL:
                return <ModelViewerLayer/>;
            case RIGHT_STATE.DEFAULT:
            default:
                return <></>;
        }
    }

    return (
        <Box
            sx={{bgcolor: "background.default"}}
            style={{
                width: "85%",
                overflow: "hidden",
                display: "flex",
                flexDirection: "column",
                height: "100vh",
                padding: "1em"
            }}
        >
            {convertMenu(rightLayerState)}
        </Box>
    );
}