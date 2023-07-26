import {ChangeEvent, Dispatch, MutableRefObject, SetStateAction, useEffect, useRef, useState} from "react";
import {FormControl, FormHelperText, InputLabel, OutlinedInput} from "@mui/material";

export function AuthInputForm(
    props: {
        target: string,
        setTarget: Dispatch<SetStateAction<string>>,
        validationCondition: () => boolean,
        validationHandlers: MutableRefObject<Map<string, () => boolean>>,
        htmlName: string,
        htmlType: string,
        helperText: string,
        alwaysShowHelperText?: boolean,
    }
) {
    const targetRef = useRef<HTMLInputElement>(null);
    const alwaysShowHelperText = props.alwaysShowHelperText ?? false;
    const [isTargetError, setIsTargetError] = useState<boolean>(false);

    useEffect(() => {
        if (isTargetError) {
            targetRef.current?.focus();
        }
    }, [isTargetError])

    useEffect(() => {
        props.validationHandlers.current.set(props.htmlName, validationHandler);
    }, [props.target]);

    const validationHandler = () => {
        const condition = props.validationCondition();
        setIsTargetError(!condition);
        return condition;
    }

    const onChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        if (isTargetError) {
            setIsTargetError(value === "")
        }
        props.setTarget(value);
    }

    return (
        <FormControl fullWidth required error={isTargetError}>
            <InputLabel htmlFor={props.htmlName}>{props.htmlName}</InputLabel>
            <OutlinedInput
                id={props.htmlName} name={props.htmlName} type={props.htmlType} label={props.htmlName}
                aria-describedby={props.htmlName + "-helper-text"}
                value={props.target}
                onChange={onChangeHandler}
                inputRef={targetRef}
            />
            <FormHelperText id={props.htmlName + "-helper-text"}>
                {alwaysShowHelperText ? props.helperText : isTargetError ? props.helperText : ""}
            </FormHelperText>
        </FormControl>
    )
}
