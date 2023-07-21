import {v4 as uuidv4} from "uuid";
import {JSX, useEffect} from "react";

import {useRecoilState} from "recoil";
import {IContext, IPrompt, ISetting, NowContextIdState} from "../../../states/context";

import {CreateContext} from "../../api/context/CreateContext";

import {PromptElement} from "./PromptElement";

export function PromptCreateLayer(): JSX.Element {
    const [, setNowContextId] = useRecoilState(NowContextIdState);
    const createContextMutation = CreateContext();

    useEffect(() => {
        setNowContextId("");
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

        createContextMutation.mutate(context);
        return context;
    };

    return (
        <PromptElement
            commitHandler={commitHandler}
        />
    );
}