export const CONTEXT_ACTION = {
    ADD_CONTEXT: "context/add",
    INIT_CONTEXT: "context/init",
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

export const CHAT_MODEL = {
    GPT_3_5: "gpt-3.5-turbo",
    GPT_3_5_16K: "gpt-3.5-turbo-16k",
    GPT_4: "gpt-4",
    GPT_4_32K: "gpt-4-32k",
} as const;
export type CHAT_MODEL = typeof CHAT_MODEL[keyof typeof CHAT_MODEL];

export interface IPrompt {
    role: PROMPT_ROLE;
    name?: PROMPT_NAME;
    content: string;
}

export interface ISetting {
    title: string;
    model: CHAT_MODEL;
    description?: string;
}

export interface IContext {
    id: string;
    setting: ISetting,
    prePrompt: IPrompt[];
    history: IPrompt[];
}

export interface IContextState {
    contexts: IContext[]
}

export const defaultContextState: IContextState = {
    contexts: []
};