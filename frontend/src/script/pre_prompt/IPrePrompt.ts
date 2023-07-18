export interface IPrePrompt {
    _id: string,
    index: number,
    role: "system" | "assistant",
    name: string,
    content: string,
    disabled: boolean,

    [index: string]: string | number | boolean,
}
