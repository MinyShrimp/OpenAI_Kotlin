export const CONTEXT_ACTION = {
    ADD_CONTEXT: "context/add",
    CHANGE_CONTEXT: "context/change",
    DELETE_CONTEXT: "context/delete",
    ADD_HISTORY: "context/history/add",
} as const;
export type CONTEXT_ACTION = typeof CONTEXT_ACTION[keyof typeof CONTEXT_ACTION];

export const PROMPT_ROLE = {
    USER: "user",
    SYSTEM: "system",
    ASSISTANT: "assistant"
} as const;
export type PROMPT_ROLE = typeof PROMPT_ROLE[keyof typeof PROMPT_ROLE];

export const PROMPT_NAME = {
    USER: "user",
    AI: "ai",
} as const;
export type PROMPT_NAME = typeof PROMPT_NAME[keyof typeof PROMPT_NAME];

export interface IPrompt {
    role: PROMPT_ROLE;
    name?: PROMPT_NAME;
    content: string;
}

export interface IContext {
    id: string;
    title: string;
    prePrompt: IPrompt[];
    history: IPrompt[];
}

export interface IContextState {
    contexts: IContext[]
}

export const defaultContextState: IContextState = {
    contexts: []
};