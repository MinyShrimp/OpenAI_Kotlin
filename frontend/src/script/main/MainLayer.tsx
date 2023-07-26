import MainLeftLayer from "./MainLeftLayer";
import MainRightLayer from "./MainRightLayer";
import {Box} from "@mui/material";

export function MainLayer(
    props: {
        darkMode: boolean,
        darkModeHandler: () => void,
    }
) {
    return (
        <Box
            sx={{
                display: "flex",
                flexDirection: "row",
            }}
        >
            <MainLeftLayer darkMode={props.darkMode} darkModeHandler={props.darkModeHandler}/>
            <MainRightLayer/>
        </Box>
    );
}
