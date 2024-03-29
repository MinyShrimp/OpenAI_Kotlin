import React from "react";
import ReactDOM from "react-dom/client";
import {CookiesProvider} from "react-cookie";
import {QueryClient, QueryClientProvider} from "react-query";

import {RecoilRoot} from "recoil";

import App from "./App";
import "../scss/index.scss";

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement);
const queryClient = new QueryClient();

root.render(
    <React.StrictMode>
        <CookiesProvider>
            <QueryClientProvider client={queryClient}>
                <RecoilRoot>
                    <App/>
                </RecoilRoot>
            </QueryClientProvider>
        </CookiesProvider>
    </React.StrictMode>
);
