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
                console.log("SignupForm", error);
                if (error.code === "ERR_BAD_REQUEST") {
                    const msg = error.response.data.message
                    alert(msg);
                }
            }
        }
    )
}
