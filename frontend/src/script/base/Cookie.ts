import {Cookies} from "react-cookie";

export const cookie = new Cookies();

export const setCooke = (name: string, value: string, option?: any) => {
    return cookie.set(name, value, {...option})
}

export const getCookie = (name: string) => {
    return cookie.get(name)
}
