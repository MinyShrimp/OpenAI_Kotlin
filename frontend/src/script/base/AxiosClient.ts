import axios from 'axios';

const defaultAxios = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_API_URL,
    headers: {
        'Content-Type': 'application/json; chat-set=utf-8'
    }
});

export const AxiosClient = () => {
    const openAiKey = localStorage.getItem("openAiKey");
    if (openAiKey) {
        defaultAxios.defaults.headers.common['Authorization'] = `Bearer ${openAiKey}`;
    } else {
        defaultAxios.defaults.headers.common['Authorization'] = "";
    }

    return defaultAxios;
}
