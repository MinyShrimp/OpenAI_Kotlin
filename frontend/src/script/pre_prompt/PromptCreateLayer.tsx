import {v4 as uuidv4} from "uuid";

import {JSX, useEffect} from "react";

import {useAppDispatch} from "../RootStore";
import {setNowContextId} from "../states/now_context";
import {CONTEXT_ACTION, IContext, IPrompt, ISetting} from "../states/context";

import {PromptElement} from "./PromptElement";
import {defaultContextSetting, defaultPrompt} from "./PrePromptTypes";

export function PromptCreateLayer(): JSX.Element {
    const dispatch = useAppDispatch();

    useEffect(() => {
        setNowContextId(dispatch, "");
    }, []);

    const commitHandler = (
        setting: ISetting,
        prePromptList: IPrompt[]
    ): IContext => {
        const context: IContext = {
            id: uuidv4(),
            setting: setting,
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
            defaultSetting={{...defaultContextSetting}}
            defaultPromptList={[{...defaultPrompt}]}
            commitHandler={commitHandler}
        />
    );
}