import {IPrompt} from "./ContextTypes";

export const ConvertPromptToRequest = (
    prompt: IPrompt
): IPrompt => {
    return {
        role: prompt.role,
        content: prompt.content,
        name: (!prompt.name) ? undefined : prompt.name,
    };
}