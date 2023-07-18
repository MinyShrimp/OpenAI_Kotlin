import {JSX} from "react";
import {Box} from "@mui/material";
import {useAppSelector} from "../RootStore";

import {PromptCreateLayer} from "../pre_prompt/PromptCreateLayer";
import {ModelViewerLayer} from "../model_viewer/ModelViewerLayer";
import {ChatCompletionLayer} from "../chat_completion/ChatCompletionLayer";

import {RIGHT_STATE} from "../states/right_state";
import {PromptChangeLayer} from "../pre_prompt/PromptChangeLayer";

export default function MainRightLayer(): JSX.Element {
    const selector = useAppSelector((state) => state.rightStateReducer);
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
            {convertMenu(selector.state)}
        </Box>
    );
}