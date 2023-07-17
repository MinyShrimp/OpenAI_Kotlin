export const RIGHT_STATE = {
    DEFAULT: 0,
    COMPLETION: 1,
    CHAT_COMPLETION: 2,
    NEW_PROMPT: 3,
    MODEL: 4,
    FILE: 5,
    FINE_TUNING: 6,
    DASHBOARD: 7
} as const;
export type RIGHT_STATE = typeof RIGHT_STATE[keyof typeof RIGHT_STATE];

export const RIGHT_STATE_ACTION = {
    SET_RIGHT_STATE: "rightState/setRightState"
} as const;
export type RIGHT_STATE_ACTION = typeof RIGHT_STATE_ACTION[keyof typeof RIGHT_STATE_ACTION];

export const initialRightState = {
    state: RIGHT_STATE.DEFAULT
};

export interface IRightState {
    state: RIGHT_STATE;
}