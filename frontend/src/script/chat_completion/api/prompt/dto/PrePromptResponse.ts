export interface PrePromptResponse {
    order: number;
    role: "system" | "assistant" | "user";
    name: "system" | "user" | "ai";
    content: string;
}