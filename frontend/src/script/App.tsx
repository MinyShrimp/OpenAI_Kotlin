import {useMemo, useState} from "react";
import {Box, createTheme, ThemeProvider, useMediaQuery} from "@mui/material";

import MainLeftLayer from "./main/MainLeftLayer";
import MainRightLayer from "./main/MainRightLayer";

export default function App() {
    const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');
    const [darkMode, setDarkMode] = useState(prefersDarkMode);
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
        setDarkMode(!darkMode);
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
