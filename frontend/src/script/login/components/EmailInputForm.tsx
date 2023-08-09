import {AuthInputForm} from "./AuthInputForm";
import {Dispatch, MutableRefObject, SetStateAction} from "react";

export function EmailInputForm(
    props: {
        target: string,
        setTarget: Dispatch<SetStateAction<string>>,
        validationHandlers: MutableRefObject<Map<string, () => boolean>>,
    }
) {
    const validationCondition = () => {
        return props.target.match(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\.[A-Za-z]+){1,2}$/) !== null;
    };

    return (
        <AuthInputForm
            target={props.target}
            setTarget={props.setTarget}
            validationCondition={validationCondition}
            validationHandlers={props.validationHandlers}
            htmlName={"email"}
            htmlType={"email"}
            helperText={"이메일 형식을 올바르게 입력해주세요"}
        />
    )
}
