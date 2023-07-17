export const MENU = {
    COMPLETION: "Completion",
    CHAT_COMPLETION: "Chat Completion",
    MODEL: "Model",
    FILE: "File",
    FINE_TUNING: "Fine Tuning",
    DASHBOARD: "Dashboard",
} as const;
export type MENU = typeof MENU[keyof typeof MENU];
