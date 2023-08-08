import axios from 'axios';

const defaultAxios = axios.create({
    headers: {
        'Content-Type': 'application/json; chat-set=utf-8',
    },
    withCredentials: true
});

export const AxiosApiClient = () => {
    const openAiKey = localStorage.getItem("openAiKey");
    if (openAiKey) {
        defaultAxios.defaults.headers.common['Authorization'] = `Bearer ${openAiKey}`;
    } else {
        defaultAxios.defaults.headers.common['Authorization'] = "";
    }
    // @ts-ignore
    defaultAxios.defaults.baseURL = import.meta.env.VITE_BACKEND_API_URL + "/api";

    return defaultAxios;
}

export const AxiosAuthClient = () => {
    // @ts-ignore
    defaultAxios.defaults.baseURL = import.meta.env.VITE_BACKEND_API_URL + "/auth";
    return defaultAxios;
}
