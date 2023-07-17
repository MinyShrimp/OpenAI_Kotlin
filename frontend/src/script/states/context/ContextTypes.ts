export const CONTEXT_ACTION = {
    ADD_CONTEXT: "context/add",
    CHANGE_CONTEXT: "context/change",
    DELETE_CONTEXT: "context/delete"
} as const;
export type CONTEXT_ACTION = typeof CONTEXT_ACTION[keyof typeof CONTEXT_ACTION];

export interface IPrompt {
    role: "system" | "assistant" | "user";
    name?: string;
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