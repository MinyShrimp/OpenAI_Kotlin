import {useMutation} from "react-query";
import {AxiosAuthClient} from "../../base/AxiosClient";
import {AuthResponse, LoginRequest} from "../dto/AuthTypes";

export function Login() {
    return useMutation(
        async (dto: LoginRequest) => {
            const resp = await AxiosAuthClient().post("/login", dto);
            return resp.data;
        }, {
            onSuccess: async (data: AuthResponse) => {
                console.log("login", data);
            },
        }
    )
}
