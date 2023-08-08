import {useEffect, useMemo, useState} from "react";
import {Box, createTheme, ThemeProvider} from "@mui/material";
import {MainLayer} from "./main/MainLayer";
import {AuthLayer} from "./login/AuthLayer";
import {getCookie} from "./base/Cookie";
import {AxiosApiClient} from "./base/AxiosClient";

export default function App() {
    const [darkMode, setDarkMode] = useState(localStorage.getItem("darkMode") === "1");
    const theme = useMemo(
        () =>
            createTheme({
                palette: {
                    mode: darkMode ? 'dark' : 'light',
                    background: {
                        default: darkMode ? "#424242" : "#fff",
                        paper: darkMode ? "#303030" : "#D3D3D3",
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

    const [isLogin, setIsLogin] = useState(false);
    useEffect(() => {
        const cookie = getCookie("AUTH_TOKEN");
        if (cookie !== undefined) {
            AxiosApiClient()
                .get("/model/list")
                .then(() => setIsLogin(true));
        }
    }, []);

    return (
        <ThemeProvider theme={theme}>
            <Box sx={{
                bgcolor: "background.default",
                color: "text.primary"
            }}>
                {
                    isLogin
                        ? <MainLayer darkMode={darkMode} darkModeHandler={darkModeHandler}/>
                        : <AuthLayer/>
                }
            </Box>
        </ThemeProvider>
    );
}
