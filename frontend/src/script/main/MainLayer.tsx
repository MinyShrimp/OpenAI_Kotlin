import MainLeftLayer from "./MainLeftLayer";
import MainRightLayer from "./MainRightLayer";
import {Box} from "@mui/material";

export function MainLayer() {
    return (
        <Box
            sx={{
                display: "flex",
                flexDirection: "row",
            }}
        >
            <MainLeftLayer/>
            <MainRightLayer/>
        </Box>
    );
}
