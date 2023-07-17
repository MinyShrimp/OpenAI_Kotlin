export const LEFT_STATE = {
    DEFAULT: 0,
    CHAT_COMPLETION: 1,
} as const;
export type LEFT_STATE = typeof LEFT_STATE[keyof typeof LEFT_STATE];

export const LEFT_STATE_ACTION = {
    SET_LEFT_STATE: "leftState/setLeftState"
} as const;
export type LEFT_STATE_ACTION = typeof LEFT_STATE_ACTION[keyof typeof LEFT_STATE_ACTION];

export const initialLeftState = {
    state: LEFT_STATE.DEFAULT
};

export interface ILeftState {
    state: LEFT_STATE
}