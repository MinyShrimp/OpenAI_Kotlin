import {useRecoilState} from "recoil";
import {AccountInfoState, LoginState} from "../../states/auth";
import {useMutation} from "react-query";
import {AxiosAuthClient} from "../../base/AxiosClient";
import {AuthResponse} from "../dto/AuthTypes";

export function LoginCheck() {
    const [, setIsLogin] = useRecoilState(LoginState);
    const [, setAccountInfo] = useRecoilState(AccountInfoState);

    return useMutation(
        async () => {
            const resp = await AxiosAuthClient().get("/login/check");
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
