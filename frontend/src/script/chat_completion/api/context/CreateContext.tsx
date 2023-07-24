import {useMutation, useQueryClient} from "react-query";
import {AxiosClient} from "../../../base/AxiosClient";
import {ContextRequest} from "./dto/ContextRequest";
import {IContext} from "../../../states/context";
import {ContextResponse} from "./dto/ContextResponse";

export function CreateContext() {
    const queryClient = useQueryClient();

    const getRequest = (
        context: IContext
    ): ContextRequest => {
        return {
            id: context.id,
            model: context.setting.model,
            title: context.setting.title,
            description: context.setting.description,
            pre_prompt_list: context.prePrompt,
        };
    }

    return useMutation(
        async (context: IContext) => {
            const resp = await AxiosClient().post("/context", getRequest(context));
            return resp.data;
        }, {
            onSuccess: async (data: ContextResponse) => {
                console.log("CreateContext", data);
                await queryClient.invalidateQueries("GetContextList");
            },
        }
    )
}
