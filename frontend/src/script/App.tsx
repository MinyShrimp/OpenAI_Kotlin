import {useEffect, useMemo} from "react";
import {Box, createTheme, ThemeProvider} from "@mui/material";
import {MainLayer} from "./main/MainLayer";
import {AuthLayer} from "./login/AuthLayer";
import {getCookie} from "./base/Cookie";
import {useRecoilState} from "recoil";
import {LoginState} from "./states/auth";
import {LoginCheck} from "./login/api/LoginCheck";
import {LightModeState} from "./states/config";

export default function App() {
    const [lightMode,] = useRecoilState(LightModeState);
    useEffect(() => {
        localStorage.setItem("lightMode", lightMode ? "1" : "0");
    }, [lightMode]);

    const theme = useMemo(
        () =>
            createTheme({
                palette: {
                    mode: lightMode ? 'light' : 'dark',
                    background: {
                        default: lightMode ? "#fff" : "#424242",
                        paper: lightMode ? "#D3D3D3" : "#303030",
                    }
                },
            }),
        [lightMode],
    );

    const loginMutation = LoginCheck();
    const [isLogin, setIsLogin] = useRecoilState(LoginState);

    useEffect(() => {
        const cookie = getCookie("AUTH_TOKEN");
        if (cookie !== undefined) {
            loginMutation.mutate();
        } else {
            setIsLogin(false);
        }
    }, []);

    return (
        <ThemeProvider theme={theme}>
            <Box sx={{
                bgcolor: "background.default",
                color: "text.primary"
            }}>
                {isLogin ? <MainLayer/> : <AuthLayer/>}
            </Box>
        </ThemeProvider>
    );
}
