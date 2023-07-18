import React from "react";
import ReactDOM from "react-dom/client";

import {Provider} from "react-redux";
import {rootStore} from "./RootStore";

import App from "./App";
import "../scss/index.scss";

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement);

root.render(
    <React.StrictMode>
        <Provider store={rootStore}>
            <App/>
        </Provider>
    </React.StrictMode>
);
