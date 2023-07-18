import {v4 as uuidv4} from "uuid";

import {JSX, useEffect} from "react";

import {useAppDispatch} from "../RootStore";
import {CONTEXT_ACTION, IContext, IPrompt} from "../states/context";

import {defaultPrompt} from "./PrePromptTypes";
import {PromptElement} from "./PromptElement";
import {setNowContextId} from "../states/now_context";

export function PromptCreateLayer(): JSX.Element {
    const dispatch = useAppDispatch();

    useEffect(() => {
        setNowContextId(dispatch, "");
    }, []);

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
            defaultPromptList={[{...defaultPrompt}]}
            commitHandler={commitHandler}
        />
    );
}