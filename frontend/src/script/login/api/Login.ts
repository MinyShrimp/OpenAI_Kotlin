import {useMutation} from "react-query";
import {AxiosAuthClient} from "../../base/AxiosClient";
import {AuthResponse, LoginRequest} from "../dto/AuthTypes";
import {useRecoilState} from "recoil";
import {AccountInfoState, LoginState} from "../../states/auth";

export function Login() {
    const [, setIsLogin] = useRecoilState(LoginState);
    const [, setAccountInfo] = useRecoilState(AccountInfoState);

    return useMutation(
        async (dto: LoginRequest) => {
            const resp = await AxiosAuthClient().post("/login", dto);
            return resp.data;
        }, {
            onSuccess: async (dto: AuthResponse) => {
                setIsLogin(true);
                setAccountInfo({
                    name: dto.name,
                    email: dto.email,
                    loginAt: dto.update_at.replace("T", " ").slice(0, 19),
                });
            },
            onError: async () => {
                setIsLogin(false);
                setAccountInfo(null);
            }
        }
    )
}
