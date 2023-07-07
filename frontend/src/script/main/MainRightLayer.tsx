import {JSX} from "react";
import {Box} from "@mui/material";
import {Layer} from "../base/Layer";
import {NewPromptLayer} from "../prompt/NewPromptLayer";

export default function MainRightLayer(): JSX.Element {
    return (
        <Box
            component={Layer}
            sx={{bgcolor: "background.secondary"}}
            style={{width: "85%"}}
        >
            <NewPromptLayer/>
        </Box>
    );
}