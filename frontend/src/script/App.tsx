import "../scss/App.scss";
import LeftContainer from "./components/LeftContainer";
import RightContainer from "./components/RightContainer";

export default function App() {
    return (
        <div className="App">
            <LeftContainer />
            <RightContainer />
        </div>
    );
}
