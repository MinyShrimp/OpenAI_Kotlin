import "../scss/App.scss";
import MainLeftLayer from "./main/MainLeftLayer";
import MainRightLayer from "./main/MainRightLayer";
import {createTheme, ThemeProvider} from "@mui/material";

export default function App() {
    const darkTheme = createTheme({
        palette: {
            mode: "dark"
        }
    });

    return (
        <ThemeProvider theme={darkTheme}>
            <div className={"App"}>
                <MainLeftLayer/>
                <MainRightLayer/>
            </div>
        </ThemeProvider>
    );
}
