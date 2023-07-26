import {AuthInputForm} from "./AuthInputForm";
import {Dispatch, MutableRefObject, SetStateAction} from "react";

export function PasswordInputForm(
    props: {
        target: string,
        setTarget: Dispatch<SetStateAction<string>>,
        validationHandlers: MutableRefObject<Map<string, () => boolean>>,
    }
) {
    const validationCondition = () => {
        return props.target.match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/) !== null;
    }

    return (
        <AuthInputForm
            target={props.target}
            setTarget={props.setTarget}
            validationCondition={validationCondition}
            validationHandlers={props.validationHandlers}
            htmlName={"pwd"}
            htmlType={"password"}
            helperText={"영문자 + 숫자 + 특수문자 조합으로 8자리 이상 입력해주세요"}
            alwaysShowHelperText={true}
        />
    )
}
