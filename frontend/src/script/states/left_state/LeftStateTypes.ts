export const LEFT_STATE = {
    DEFAULT: 0,
    CHAT_COMPLETION: 1,
} as const;
export type LEFT_STATE = typeof LEFT_STATE[keyof typeof LEFT_STATE];
