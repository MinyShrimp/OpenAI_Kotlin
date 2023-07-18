import {v4 as uuidv4} from "uuid";
import {JSX, useEffect, useRef, useState} from "react";

import {Accordion, AccordionDetails, AccordionSummary, Box, Button, Container, Typography} from "@mui/material";

import {PromptForm} from "./PromptForm";
import {IPrePrompt} from "./IPrePrompt";

import {useAppDispatch} from "../RootStore";
import {IContext, IPrompt} from "../states/context";
import {setNowContextId} from "../states/now_context";
import {RIGHT_STATE, setRightState} from "../states/right_state";

export function PromptElement(
    props: {
        commitHandler: (prePromptList: IPrompt[]) => IContext,
        defaultPromptList: IPrePrompt[],
    }
): JSX.Element {
    const dispatch = useAppDispatch();

    const promptIndex = useRef(props.defaultPromptList.length - 1);
    const [promptList, setPromptList] = useState<IPrePrompt[]>(props.defaultPromptList);

    const lastPromptItem = useRef<HTMLTextAreaElement>(null);
    const addButtonRef = useRef<HTMLButtonElement>(null);

    const init = (): void => {
        promptIndex.current = props.defaultPromptList.length - 1;
        setPromptList(props.defaultPromptList);
    }
    useEffect(init, [props.defaultPromptList]);

    const addPrompt = (): void => {
        promptIndex.current += 1;
        setPromptList((prevList) => {
            const newList = prevList.concat({
                _id: uuidv4(), index: promptIndex.current,
                role: "assistant", content: "", name: "",
                disabled: false
            });

            setTimeout(() => {
                addButtonRef.current?.scrollIntoView({behavior: 'smooth', block: 'end'});
            });

            return newList;
        });
    };

    const changePrompt = (_id: string, key: string, value: string | boolean): void => {
        const findIndex = promptList.findIndex((item): boolean => item._id === _id);
        if (findIndex === -1) {
            return;
        }

        promptList[findIndex][key] = value;
        setPromptList([...promptList]);
    };

    const deletePrompt = (prompt: IPrePrompt): void => {
        if (promptList.length === 1 || prompt.index === 0) {
            return;
        }

        setPromptList(
            promptList
                .filter((item): boolean => item._id !== prompt._id)
                .map((item, index) => {
                    item.index = index;
                    return item;
                })
        );
        promptIndex.current = promptList.length - 2;
    };

    const commit = (): void => {
        const prePromptList: IPrompt[] = promptList
            .filter((item) => !item.disabled && item.content)
            .map((prompt) => {
                return {
                    role: prompt.role,
                    name: prompt.name,
                    content: prompt.content,
                }
            });

        const context = props.commitHandler(prePromptList);
        setNowContextId(dispatch, context.id);
        setRightState(dispatch, RIGHT_STATE.CHAT_COMPLETION);
    };

    const cancel = (): void => {
        init();
        setRightState(dispatch, RIGHT_STATE.CHAT_COMPLETION);
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
                <div style={{marginBottom: "1em"}}>{
                    promptList.map((item) =>
                        <Accordion key={item._id} defaultExpanded={true}>
                            <AccordionSummary>
                                <Typography
                                    style={{
                                        textDecoration: item.disabled ? "line-through" : "none",
                                    }}
                                >
                                    Role #{item.index} - {item.role}
                                </Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <PromptForm
                                    item={item}
                                    changeEvent={changePrompt}
                                    deleteEvent={deletePrompt}
                                    ref={item.index === promptList.length - 1 ? lastPromptItem : undefined}
                                />
                            </AccordionDetails>
                        </Accordion>
                    )
                }</div>
                <div>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={addPrompt}
                        style={{width: "100%"}}
                        ref={addButtonRef}
                    >+</Button>
                </div>
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