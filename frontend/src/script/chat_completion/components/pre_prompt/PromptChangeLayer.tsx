import {JSX, useEffect} from "react";

import {useRecoilState} from "recoil";
import {IContext, IPrompt, ISetting, NowContextIdState} from "../../../states/context";

import {PromptElement} from "./PromptElement";
import {UpdateContext} from "../../api/context/UpdateContext";
import {GetPrePrompt} from "../../api/prompt/GetPrePrompt";


export function PromptChangeLayer(): JSX.Element {
    const [nowContextIdState,] = useRecoilState(NowContextIdState);
    const updateContextMutation = UpdateContext();

    const {refetch} = GetPrePrompt();
    useEffect(() => {
        refetch();
    }, [nowContextIdState]);

    const commitHandler = (
        setting: ISetting,
        prePromptList: IPrompt[]
    ): IContext => {
        const context: IContext = {
            id: nowContextIdState,
            setting: setting,
            prePrompt: prePromptList,
            history: []
        };

        updateContextMutation.mutate(context);
        return context;
    };

    return (
        <PromptElement
            commitHandler={commitHandler}
        />
    );
}