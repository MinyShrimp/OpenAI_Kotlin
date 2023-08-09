import {useMutation} from "react-query";
import {AxiosAuthClient} from "../../base/AxiosClient";
import {AuthResponse, SignupRequest} from "../dto/AuthTypes";

export function Signup() {
    return useMutation(
        async (dto: SignupRequest) => {
            const resp = await AxiosAuthClient().post("/signup", dto);
            return resp.data;
        }, {
            onSuccess: async (data: AuthResponse) => {
                console.log("signup", data);
            },
            onError: async (error: any) => {
                const msg = error.response.data.message;
                console.error("signup", msg);
            }
        }
    )
}
