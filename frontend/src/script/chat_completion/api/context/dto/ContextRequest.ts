import {IPrompt} from "../../../../states/context";

export interface ContextRequest {
    id: string;
    model: string;
    title: string;
    description?: string;
    pre_prompt_list: IPrompt[];
}