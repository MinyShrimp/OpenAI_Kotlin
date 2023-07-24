import {useMutation, useQueryClient} from "react-query";
import {IContext} from "../../../states/context";
import {ContextRequest} from "./dto/ContextRequest";
import {AxiosClient} from "../../../base/AxiosClient";

export function UpdateContext() {
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
            const resp = await AxiosClient().put("/context", getRequest(context));
            return resp.data;
        }, {
            onSuccess: async (data) => {
                console.log("UpdateContext", data);
                await queryClient.invalidateQueries("GetContextList");
                await queryClient.invalidateQueries("GetHistoryList");
            },
            onError: error => {
                console.error(error)
            }
        }
    )
}
