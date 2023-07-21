import {v4 as uuidv4} from "uuid";
import {JSX, useEffect, useRef, useState} from "react";

import {Accordion, AccordionDetails, AccordionSummary, Box, Button, Container, Typography} from "@mui/material";

import {useRecoilState} from "recoil";
import {
    ContextState,
    GetContext,
    IContext,
    IPrompt,
    ISetting,
    NowContextIdState,
    PROMPT_ROLE
} from "../../../states/context";
import {RIGHT_STATE, RightLayerState} from "../../../states/right_state";

import {PromptForm} from "./PromptForm";
import {PromptSettingForm} from "./PromptSettingForm";
import {defaultContextSetting, defaultPrompt, IPrePrompt, PRE_PROMPT_TYPE, TransPrePromptType} from "./PrePromptTypes";

export function PromptElement(
    props: {
        commitHandler: (setting: ISetting, prePromptList: IPrompt[]) => IContext,
    }
): JSX.Element {
    const [setting, setSetting] = useState<ISetting>({...defaultContextSetting});
    const [promptList, setPromptList] = useState<IPrePrompt[]>([{...defaultPrompt}]);

    const addButtonRef = useRef<HTMLButtonElement>(null);
    const lastPromptItem = useRef<HTMLTextAreaElement>(null);

    const [contextList,] = useRecoilState(ContextState);
    const [nowContextId, setNowContextId] = useRecoilState(NowContextIdState);
    const [, setRightLayerState] = useRecoilState(RightLayerState);

    useEffect(() => {
        const context = GetContext(contextList, nowContextId);
        if (context === undefined) {
            setSetting({...defaultContextSetting});
            setPromptList([{...defaultPrompt}]);
            return;
        }

        setSetting({...context.setting});
        if (context.prePrompt.length === 0) {
            setPromptList([{...defaultPrompt}]);
        } else {
            setPromptList(
                context.prePrompt.map((prompt) => {
                    return {
                        ...prompt,
                        _id: uuidv4(),
                        type: prompt.name ?? PRE_PROMPT_TYPE.SYSTEM,
                        disabled: false,
                    }
                })
            );
        }

        setTimeout(() => {
            addButtonRef.current?.scrollIntoView({behavior: 'smooth', block: 'end'});
        });
    }, [nowContextId]);

    const addPrompt = (): void => {
        setPromptList((prevList) => {
            const newList = prevList.concat({
                _id: uuidv4(),
                role: PROMPT_ROLE.ASSISTANT,
                type: PRE_PROMPT_TYPE.USER,
                content: "",
                disabled: false
            });

            setTimeout(() => {
                addButtonRef.current?.scrollIntoView({behavior: 'smooth', block: 'end'});
            });

            return newList;
        });
    };

    const changePrompt = (_id: string, key: string, value: any): void => {
        const findIndex = promptList.findIndex((item): boolean => item._id === _id);
        if (findIndex === -1) {
            return;
        }

        // @ts-ignore
        promptList[findIndex][key] = value;
        setPromptList([...promptList]);
    };

    const deletePrompt = (prompt: IPrePrompt): void => {
        if (promptList.length === 1) {
            return;
        }

        setPromptList(
            promptList.filter((item): boolean => item._id !== prompt._id)
        );
    };

    const commit = (): void => {
        const _setting: ISetting = {
            model: setting.model,
            title: !setting.title ? "New Prompt" : setting.title,
            description: !setting.description ? undefined : setting.description
        }

        const prePromptList: IPrompt[] = promptList
            .filter((item) => !item.disabled && item.content)
            .map((prompt) => {
                return {
                    role: prompt.role,
                    content: prompt.content,
                    name: prompt.type === PRE_PROMPT_TYPE.SYSTEM ? undefined : prompt.type,
                }
            });

        const context = props.commitHandler(_setting, prePromptList);
        setNowContextId(context.id);
        setRightLayerState(RIGHT_STATE.CHAT_COMPLETION);
    };

    const cancel = (): void => {
        setRightLayerState(RIGHT_STATE.CHAT_COMPLETION);
    };

    return (
        <Container
            maxWidth="lg"
            style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                height: "100%",
                maxHeight: "100%"
            }}
        >
            <Box
                style={{
                    overflowX: "hidden",
                    overflowY: "auto",
                }}
            >
                <Box style={{marginBottom: "1em"}}>
                    <Accordion defaultExpanded={true}>
                        <AccordionSummary>
                            <Typography>기본 설정</Typography>
                        </AccordionSummary>
                        <AccordionDetails>
                            <PromptSettingForm
                                setting={setting}
                                setSetting={setSetting}
                            />
                        </AccordionDetails>
                    </Accordion>
                </Box>
                <Box style={{marginBottom: "1em"}}>{
                    promptList.map((item, index) =>
                        <Accordion key={item._id} defaultExpanded={true}>
                            <AccordionSummary>
                                <Typography
                                    style={{
                                        textDecoration: item.disabled ? "line-through" : "none",
                                    }}
                                >
                                    {TransPrePromptType(item.type)}
                                </Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <PromptForm
                                    item={item}
                                    index={index}
                                    changeEvent={changePrompt}
                                    deleteEvent={deletePrompt}
                                    ref={index === promptList.length - 1 ? lastPromptItem : undefined}
                                />
                            </AccordionDetails>
                        </Accordion>
                    )
                }</Box>
                <Box>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={addPrompt}
                        style={{width: "100%"}}
                        ref={addButtonRef}
                    >+</Button>
                </Box>
            </Box>

            <Box
                style={{
                    padding: "1em",
                    paddingBottom: "0",
                    display: "flex",
                    justifyContent: "center",
                    gap: "1rem"
                }}
            >
                <Button
                    variant="contained"
                    color="success"
                    onClick={commit}
                    style={{width: "50%", height: "40px"}}
                >등록</Button>
                <Button
                    variant="contained"
                    color="error"
                    onClick={cancel}
                    style={{width: "50%", height: "40px"}}
                >취소</Button>
            </Box>
        </Container>
    );
}