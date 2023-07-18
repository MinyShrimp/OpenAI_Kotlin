import {CHAT_MODEL, IPrompt} from "../states/context";

export interface ChatCompletionRequestDto {
    model: CHAT_MODEL,
    messages: IPrompt[]
}
