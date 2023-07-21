import {useQuery} from "react-query";
import {AxiosClient} from "../../../base/AxiosClient";

import {useRecoilState} from "recoil";
import {CHAT_MODEL, ContextState, NowContextIdState} from "../../../states/context";
import {ContextResponse} from "./dto/ContextResponse";

export function GetContextList() {
    const [, setContextList] = useRecoilState(ContextState);
    const [, setNowContextId] = useRecoilState(NowContextIdState);

    const onSuccess = (data: ContextResponse[]) => {
        console.log("Get ContextList", data);

        const convertedData = data.map((context: ContextResponse) => {
            return {
                id: context.id,
                setting: {
                    title: context.title,
                    model: context.model as CHAT_MODEL,
                    description: context.description,
                },
                prePrompt: [],
                history: []
            }
        })

        setContextList(convertedData);
        setNowContextId(convertedData[0]?.id ?? "");
    }

    return useQuery(
        "GetContextList",
        async () => {
            const resp = await AxiosClient.get("/context");
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