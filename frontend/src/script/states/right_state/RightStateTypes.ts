export const RIGHT_STATE = {
    DEFAULT: 0,
    COMPLETION: 1,
    CHAT_COMPLETION: 2,
    NEW_PROMPT: 3,
    CHANGE_PROMPT: 4,
    MODEL: 5,
    FILE: 6,
    FINE_TUNING: 7,
    DASHBOARD: 8
} as const;
export type RIGHT_STATE = typeof RIGHT_STATE[keyof typeof RIGHT_STATE];
