import "../scss/App.scss";
import MainLeftLayer from "./main/MainLeftLayer";
import MainRightLayer from "./main/MainRightLayer";

export default function App() {
    return (
        <div className="App">
            <MainLeftLayer/>
            <MainRightLayer/>
        </div>
    );
}
