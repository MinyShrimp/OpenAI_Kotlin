import {useMemo, useState} from "react";
import {Box, createTheme, ThemeProvider} from "@mui/material";

import MainLeftLayer from "./main/MainLeftLayer";
import MainRightLayer from "./main/MainRightLayer";

export default function App() {
    const [darkMode, setDarkMode] = useState(localStorage.getItem("darkMode") === "1");
    const theme = useMemo(
        () =>
            createTheme({
                palette: {
                    mode: darkMode ? 'dark' : 'light',
                    background: {
                        default: darkMode ? "#424242" : "#fff",
                        paper: darkMode ? "#303030" : "#fafafa",
                    }
                },
            }),
        [darkMode],
    );

    const darkModeHandler = (): void => {
        const changeDarkMode = !darkMode;
        localStorage.setItem("darkMode", changeDarkMode ? "1" : "0");
        setDarkMode(changeDarkMode);
    };

    return (
        <ThemeProvider theme={theme}>
            <Box sx={{
                bgcolor: "background.default",
                color: "text.primary",
                display: "flex",
                flexDirection: "row",
            }}>
                <MainLeftLayer darkMode={darkMode} darkModeHandler={darkModeHandler}/>
                <MainRightLayer/>
            </Box>
        </ThemeProvider>
    );
}
