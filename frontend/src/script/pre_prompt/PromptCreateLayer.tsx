import {v4 as uuidv4} from "uuid";

import {JSX} from "react";

import {useAppDispatch} from "../RootStore";
import {CONTEXT_ACTION, IContext, IPrompt} from "../states/context";

import {IPrePrompt} from "./IPrePrompt";
import {PromptElement} from "./PromptElement";

export function PromptCreateLayer(): JSX.Element {
    const dispatch = useAppDispatch();

    const defaultPromptList: IPrePrompt[] = [{
        _id: uuidv4(), index: 0,
        role: "system", content: "", name: "",
        disabled: false
    }];

    const commitHandler = (
        prePromptList: IPrompt[]
    ): IContext => {
        const context: IContext = {
            id: uuidv4(),
            title: prePromptList[0]?.content.slice(0, 10) ?? "New Prompt",
            prePrompt: prePromptList,
            history: []
        };

        dispatch({
            type: CONTEXT_ACTION.ADD_CONTEXT,
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