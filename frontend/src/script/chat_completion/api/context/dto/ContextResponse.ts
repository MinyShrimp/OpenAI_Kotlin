import {PrePromptResponse} from "../../prompt/dto/PrePromptResponse";

export interface ContextResponse {
    id: string;
    model: string;
    title: string;
    description?: string;
    update_at: string;
    pre_prompt: PrePromptResponse[];
}
