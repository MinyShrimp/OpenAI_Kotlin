import {useQuery} from "react-query";
import {AxiosApiClient} from "../../../base/AxiosClient";

import {useRecoilState} from "recoil";
import {CHAT_MODEL, ContextState, IPrompt, NowContextIdState} from "../../../states/context";
import {ContextResponse} from "./dto/ContextResponse";
import {PrePromptResponse} from "../prompt/dto/PrePromptResponse";

export function GetContextList() {
    const [, setContextList] = useRecoilState(ContextState);
    const [, setNowContextId] = useRecoilState(NowContextIdState);

    const onSuccess = (data: ContextResponse[]) => {
        console.log("Get ContextList", data);

        const prePromptList: IPrompt[] = data.flatMap(
            (c: ContextResponse): IPrompt[] => {
                return c.pre_prompt
                    .sort((a: PrePromptResponse, b: PrePromptResponse) => a.order - b.order)
                    .map(
                        (p: PrePromptResponse): IPrompt => {
                            return {
                                role: p.role,
                                name: p.name === "system" ? undefined : p.name,
                                content: p.content,
                            }
                        }
                    );
            }
        );

        const convertedData = data.map((context: ContextResponse) => {
            return {
                id: context.id,
                setting: {
                    title: context.title,
                    model: context.model as CHAT_MODEL,
                    description: context.description,
                },
                prePrompt: prePromptList,
                history: []
            }
        })

        setContextList(convertedData);
        setNowContextId(convertedData[0]?.id ?? "");
    }

    return useQuery(
        "GetContextList",
        async () => {
            const resp = await AxiosApiClient().get("/context");
            return resp.data;
        },
        {
            refetchOnWindowFocus: false,
            retry: 0,
            onSuccess: onSuccess,
            onError: error => {
                console.log(error)
            }
        }
    );
}