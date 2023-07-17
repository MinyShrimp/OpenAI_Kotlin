export const NOW_CONTEXT_ACTION = {
    SET_NOW_CONTEXT: "nowContext/set",
} as const;
export type NOW_CONTEXT_ACTION = typeof NOW_CONTEXT_ACTION[keyof typeof NOW_CONTEXT_ACTION];

export interface INowContextState {
    id: string;
}

export const defaultNowContextState: INowContextState = {id: ""};