import {useMutation} from "react-query";
import {AxiosAuthClient} from "../../base/AxiosClient";
import {LoginRequest} from "../dto/AuthTypes";
import {useRecoilState} from "recoil";
import {LoginState} from "../../states/auth";

export function Login() {
    const [, setIsLogin] = useRecoilState(LoginState);

    return useMutation(
        async (dto: LoginRequest) => {
            const resp = await AxiosAuthClient().post("/login", dto);
            return resp.data;
        }, {
            onSuccess: async () => {
                setIsLogin(true);
            },
            onError: async () => {
                setIsLogin(false);
            }
        }
    )
}
