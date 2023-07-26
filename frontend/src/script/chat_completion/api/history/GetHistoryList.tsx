import {useRecoilState} from "recoil";
import {ContextState, IPrompt, NowContextIdState} from "../../../states/context";
import {useQuery} from "react-query";
import {AxiosApiClient} from "../../../base/AxiosClient";
import {HistoryResponse} from "./dto/HistoryResponse";

export function GetHistoryList(
    onSuccessAfterHandler?: () => void
) {
    const [, setContextList] = useRecoilState(ContextState);
    const [nowContextId,] = useRecoilState(NowContextIdState);

    const onSuccess = (data: HistoryResponse[]) => {
        console.log("Get History List", data);

        const historyList: IPrompt[] = data.map(
            (p: HistoryResponse): IPrompt => {
                return {
                    role: p.role,
                    name: p.name === "system" ? undefined : p.name,
                    content: p.content,
                }
            }
        );

        setContextList(prevContextList =>
            prevContextList.map(context =>
                context.id === nowContextId
                    ? {...context, history: historyList}
                    : context
            )
        );

        onSuccessAfterHandler?.();
    }

    return useQuery(
        "GetHistoryList",
        async () => {
            if (nowContextId === "") return [];

            const resp = await AxiosApiClient().get("/context/" + nowContextId + "/history");
            return resp.data;
        },
        {
            refetchOnWindowFocus: false,
            retry: 0,
            enabled: nowContextId !== "",
            onSuccess: onSuccess,
            onError: error => {
                console.log(error)
            }
        }
    );
}