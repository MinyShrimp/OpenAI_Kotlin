import {JSX} from "react";
import {Box} from "@mui/material";

import {Layer} from "../base/Layer";
import {useAppSelector} from "../RootStore";

import {NewPromptLayer} from "../new_prompt/NewPromptLayer";
import {ModelViewerLayer} from "../model_viewer/ModelViewerLayer";
import {ChatCompletionLayer} from "../chat_completion/ChatCompletionLayer";

import {RIGHT_STATE} from "../states/right_state";

export default function MainRightLayer(): JSX.Element {
    const selector = useAppSelector((state) => state.rightStateReducer);
    const convertMenu = (state: RIGHT_STATE): JSX.Element => {
        switch (state) {
            case RIGHT_STATE.CHAT_COMPLETION:
                return <ChatCompletionLayer/>;
            case RIGHT_STATE.NEW_PROMPT:
                return <NewPromptLayer/>;
            case RIGHT_STATE.MODEL:
                return <ModelViewerLayer/>;
            case RIGHT_STATE.DEFAULT:
            default:
                return <></>;
        }
    }

    return (
        <Box
            component={Layer}
            sx={{bgcolor: "background.default"}}
            style={{
                width: "85%",
                overflow: "hidden",
            }}
        >
            {convertMenu(selector.state)}
        </Box>
    );
}