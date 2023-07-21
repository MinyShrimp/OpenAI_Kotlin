export interface HistoryResponse {
    role: "system" | "assistant" | "user";
    name: "system" | "user" | "ai";
    content: string;
    createAt: string;
}