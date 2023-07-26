import {AuthInputForm} from "./AuthInputForm";
import {Dispatch, MutableRefObject, SetStateAction} from "react";

export function NameInputForm(
    props: {
        target: string,
        setTarget: Dispatch<SetStateAction<string>>,
        validationHandlers: MutableRefObject<Map<string, () => boolean>>,
    }
) {
    const validationCondition = () => props.target !== ""

    return (
        <AuthInputForm
            target={props.target}
            setTarget={props.setTarget}
            validationCondition={validationCondition}
            validationHandlers={props.validationHandlers}
            htmlName={"name"}
            htmlType={"text"}
            helperText={"이름을 입력해주세요"}
        />
    )
}
