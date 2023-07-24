import {useMutation, useQueryClient} from "react-query";
import {AxiosClient} from "../../../base/AxiosClient";

export function DeleteContext() {
    const queryClient = useQueryClient();

    return useMutation(
        async (contextId: string) => {
            const resp = await AxiosClient().delete("/context/" + contextId);
            return resp.data;
        }, {
            onSuccess: async (data) => {
                console.log("DeleteContext", data);
                await queryClient.invalidateQueries("GetContextList");
            },
        }
    )
}