import {useRecoilState} from "recoil";
import {AccountInfoState, LoginState} from "../../states/auth";
import {useMutation} from "react-query";
import {AxiosAuthClient} from "../../base/AxiosClient";

export function Logout() {
    const [, setIsLogin] = useRecoilState(LoginState);
    const [, setAccountInfo] = useRecoilState(AccountInfoState);

    return useMutation(
        async () => {
            await AxiosAuthClient().post("/logout");
        }, {
            onSuccess: async () => {
                setIsLogin(false);
                setAccountInfo(null);
            }
        }
    )
}
