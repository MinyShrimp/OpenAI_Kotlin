export const MENU = {
    COMPLETION: "Completion",
    CHAT_COMPLETION: "Chat Completion",
    NEW_PROMPT: "New Prompt",
    MODEL: "Model",
    FILE: "File",
    FINE_TUNING: "Fine Tuning",
    DASHBOARD: "Dashboard",
} as const;
export type MENU = typeof MENU[keyof typeof MENU];

export const MENU_ACTION = {
    SET_MENU: "menu/setMenu",
} as const;
export type MENU_ACTION = typeof MENU_ACTION[keyof typeof MENU_ACTION];

export const initialMenuState = {
    menu: MENU.MODEL
};

export interface IMenuState {
    menu: MENU;
}
