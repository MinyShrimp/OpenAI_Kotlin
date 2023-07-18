import {IPrompt} from "./ContextTypes";

export const ConvertHistoryViewToDto = (
    prompt: IPrompt
): IPrompt => {
    return {
        role: "assistant",
        name: prompt.role,
        content: prompt.content,
    };
}

export const ConvertPromptToRequest = (
    prompt: IPrompt
): IPrompt => {
    return {
        role: prompt.role,
        content: prompt.content,
        name: (!prompt.name) ? undefined : prompt.name,
    };
}