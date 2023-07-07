import {JSX} from "react";
import {Box} from "@mui/material";
import {Layer} from "../base/Layer";
import {ModelViewerLayer} from "../model_viewer/ModelViewerLayer";

export default function MainRightLayer(): JSX.Element {
    return (
        <Box
            component={Layer}
            sx={{bgcolor: "background.secondary"}}
            style={{
                width: "85%",
                overflow: "auto",
            }}
        >
            <ModelViewerLayer/>
            {/*<NewPromptLayer/>*/}
        </Box>
    );
}