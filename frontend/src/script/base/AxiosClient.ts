import axios from 'axios';

export const AxiosClient = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_API_URL,
    headers: {
        'Content-Type': 'application/json; chat-set=utf-8',
    }
})