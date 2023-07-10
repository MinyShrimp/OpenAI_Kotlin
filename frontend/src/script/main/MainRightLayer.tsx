import {JSX} from "react";
import {Box} from "@mui/material";
import {Layer} from "../base/Layer";
import {useAppSelector} from "../RootStore";
import {MENU} from "../menu/MenuTypes";
import {ModelViewerLayer} from "../model_viewer/ModelViewerLayer";
import {NewPromptLayer} from "../new_prompt/NewPromptLayer";
import {ChatCompletionLayer} from "../chat_completion/ChatCompletionLayer";

export default function MainRightLayer(): JSX.Element {
    const selector = useAppSelector((state) => state.menuReducer);

    const convertMenu = (menu: MENU): JSX.Element => {
        switch (menu) {
            case MENU.CHAT_COMPLETION:
                return <ChatCompletionLayer/>;
            case MENU.NEW_PROMPT:
                return <NewPromptLayer/>;
            case MENU.MODEL:
                return <ModelViewerLayer/>;
            default:
                return <></>;
        }
    }

    return (
        <Box
            component={Layer}
            sx={{bgcolor: "background.secondary"}}
            style={{
                width: "85%",
                overflow: "auto",
            }}
        >
            {convertMenu(selector.menu)}
        </Box>
    );
}