import {useMutation} from "react-query";
import {AxiosAuthClient} from "../../base/AxiosClient";
import {SignupRequest} from "../dto/AuthTypes";
import {LoginContextState} from "../../states/auth";
import {useRecoilState} from "recoil";

export function Signup() {
    const [, setLoginContext] = useRecoilState(LoginContextState);

    return useMutation(
        async (dto: SignupRequest) => {
            const resp = await AxiosAuthClient().post("/signup", dto);
            return resp.data;
        }, {
            onSuccess: async () => {
                setLoginContext("login");
            },
            onError: async (error: any) => {
                const msg = error.response.data.message;
                console.error("signup", msg);
            }
        }
    )
}
