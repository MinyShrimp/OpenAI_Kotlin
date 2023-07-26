import {useMutation} from "react-query";
import {ContextState, IPrompt, NowContextIdState} from "../../../states/context";
import {AxiosApiClient} from "../../../base/AxiosClient";
import {HistoryRequest} from "./dto/HistoryRequest";
import {useRecoilState} from "recoil";
import {HistoryResponse} from "./dto/HistoryResponse";

export function AddHistory() {
    const [nowContextId,] = useRecoilState(NowContextIdState);
    const [, setContextList] = useRecoilState(ContextState);

    const getRequest = (
        prompt: IPrompt
    ): HistoryRequest => {
        return {
            id: nowContextId,
            role: prompt.role,
            name: prompt.name!,
            content: prompt.content,
        };
    }

    return useMutation(
        async (prompt: IPrompt) => {
            const resp = await AxiosApiClient().post("/context/history/add", getRequest(prompt));
            return resp.data;
        }, {
            onSuccess: async (data: HistoryResponse) => {
                console.log("Add History", data);

                setContextList(prevContextList =>
                    prevContextList.map(context =>
                        context.id === nowContextId
                            ? {
                                ...context,
                                history: [
                                    ...context.history,
                                    {
                                        role: data.role,
                                        name: data.name === "system" ? undefined : data.name,
                                        content: data.content,
                                    }
                                ]
                            }
                            : context
                    )
                );
            },
        }
    )
}
