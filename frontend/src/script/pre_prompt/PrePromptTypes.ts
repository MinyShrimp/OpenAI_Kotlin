import {v4 as uuidv4} from "uuid";
import {IPrompt, PROMPT_NAME, PROMPT_ROLE} from "../states/context";

export const PRE_PROMPT_TYPE = {
    SYSTEM: "system",
    ...PROMPT_NAME,
} as const;
export type PRE_PROMPT_TYPE = typeof PRE_PROMPT_TYPE[keyof typeof PRE_PROMPT_TYPE];

export const TransPrePromptType = (
    type: PRE_PROMPT_TYPE
): string => {
    switch (type) {
        case PRE_PROMPT_TYPE.SYSTEM:
            return "기본 역할";
        case PRE_PROMPT_TYPE.USER:
            return "질문 예시";
        case PRE_PROMPT_TYPE.AI:
            return "답변 예시";
        default:
            return "";
    }
}

export interface IPrePrompt extends IPrompt {
    _id: string,
    index: number,
    type: PRE_PROMPT_TYPE,
    disabled: boolean,
}

export const defaultPrompt: IPrePrompt = {
    _id: uuidv4(), index: 0,
    role: PROMPT_ROLE.SYSTEM,
    content: "",
    name: undefined,
    type: PRE_PROMPT_TYPE.SYSTEM,
    disabled: false
};
