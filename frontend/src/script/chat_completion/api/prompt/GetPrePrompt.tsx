import {useQuery} from "react-query";
import {AxiosClient} from "../../../base/AxiosClient";
import {useRecoilState} from "recoil";
import {ContextState, IPrompt, NowContextIdState} from "../../../states/context";
import {PrePromptResponse} from "./dto/PrePromptResponse";

export function GetPrePrompt() {
    const [, setContextList] = useRecoilState(ContextState);
    const [nowContextId,] = useRecoilState(NowContextIdState);

    const onSuccess = (data: PrePromptResponse[]) => {
        console.log("Get Prompt List in Context", data);

        data = data.sort((a: PrePromptResponse, b: PrePromptResponse) => a.order - b.order);
        const prePromptList: IPrompt[] = data.map(
            (p: PrePromptResponse): IPrompt => {
                return {
                    role: p.role,
                    name: p.name === "system" ? undefined : p.name,
                    content: p.content,
                }
            }
        );

        setContextList(prevContextList => {
            return prevContextList.map(context => {
                if (context.id === nowContextId) {
                    return {
                        ...context,
                        prePrompt: prePromptList
                    }
                } else {
                    return context;
                }
            })
        });
    }

    return useQuery(
        "GetPrePrompt",
        async () => {
            const resp = await AxiosClient().get("/context/" + nowContextId + "/prompt");
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
