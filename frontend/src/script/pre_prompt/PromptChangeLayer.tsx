import {v4 as uuidv4} from "uuid";

import {JSX, useEffect, useState} from "react";

import {useAppDispatch, useAppSelector} from "../RootStore";
import {CONTEXT_ACTION, IContext, IPrompt} from "../states/context";
import {IPrePrompt, PRE_PROMPT_TYPE} from "./PrePromptTypes";
import {PromptElement} from "./PromptElement";
import {RIGHT_STATE, setRightState} from "../states/right_state";

export function PromptChangeLayer(): JSX.Element {
    const dispatch = useAppDispatch();
    const contextState = useAppSelector((selector) => selector.contextReducer);
    const nowContextState = useAppSelector((selector) => selector.nowContextReducer);

    const [defaultPromptList, setDefaultPromptList] = useState<IPrePrompt[]>([]);

    useEffect(() => {
        const context = contextState.contexts.find((context) => context.id === nowContextState.id);
        if (context === undefined) {
            setRightState(dispatch, RIGHT_STATE.CHAT_COMPLETION);
            return;
        }

        const newPrePromptList = context.prePrompt
            .map((prePrompt, index) => {
                return {
                    _id: uuidv4(),
                    index: index,
                    role: prePrompt.role,
                    type: prePrompt.name ?? PRE_PROMPT_TYPE.SYSTEM,
                    content: prePrompt.content,
                    disabled: false
                };
            });

        setDefaultPromptList(newPrePromptList);
    }, [nowContextState]);

    const commitHandler = (
        prePromptList: IPrompt[]
    ): IContext => {
        const context: IContext = {
            id: nowContextState.id,
            title: prePromptList[0]?.content.slice(0, 10) ?? "New Prompt",
            prePrompt: prePromptList,
            history: []
        };

        dispatch({
            type: CONTEXT_ACTION.CHANGE_CONTEXT,
            payload: context
        });
        return context;
    };

    return (
        <PromptElement
            defaultPromptList={defaultPromptList}
            commitHandler={commitHandler}
        />
    );
}