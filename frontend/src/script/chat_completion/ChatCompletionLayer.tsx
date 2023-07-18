import {ChangeEvent, JSX, KeyboardEvent, useEffect, useState} from "react";
import {Form} from "react-bootstrap";
import {Box, Container, TextareaAutosize} from "@mui/material";

import {requestStream} from "../base/requestStream";

import {useAppDispatch, useAppSelector} from "../RootStore";
import {CHAT_MODEL, CONTEXT_ACTION, IPrompt} from "../states/context";
import {ConvertPromptToRequest} from "../states/context/ContextUtils";

import {PromptBox} from "./PromptBox";
import {ChatCompletionRequestDto} from "./ChatCompletionRequestDto";

export function ChatCompletionLayer(): JSX.Element {
    const [message, setMessage] = useState<string>("");
    const [historis, setHistoris] = useState<IPrompt[]>([]);
    const [respMsg, setRespMsg] = useState<string>("");
    const [pending, setPending] = useState<boolean>(false);

    const dispatch = useAppDispatch();
    const contextState = useAppSelector(state => state.contextReducer);
    const nowContextState = useAppSelector(state => state.nowContextReducer);

    useEffect(() => {
        setHistoris(getContext()?.history ?? []);
    }, [nowContextState, contextState]);

    const getContext = () => contextState.contexts.find(context => context.id === nowContextState.id);

    const getRequest = (
        newPrompt: IPrompt
    ): ChatCompletionRequestDto => {
        const context = getContext();
        if (context === undefined) {
            return {
                model: CHAT_MODEL.GPT_3_5,
                messages: [newPrompt]
            }
        }

        return {
            model: context.setting.model,
            messages: [
                ...context.prePrompt.map(ConvertPromptToRequest),
                ...context.history.map(ConvertPromptToRequest),
                newPrompt
            ],
        };
    }

    const addHistory = (history: IPrompt): void => {
        dispatch({
            type: CONTEXT_ACTION.ADD_HISTORY,
            payload: {
                id: nowContextState.id,
                history: [history]
            }
        });
    }

    const onKeyDown = (e: KeyboardEvent<HTMLFormElement>): void => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            submit();
        }
    }

    const onChange = (e: ChangeEvent<HTMLTextAreaElement>): void => {
        setMessage(e.target.value);
    }

    const submit = async (): Promise<void> => {
        if (!message.trim()) {
            return;
        }

        setRespMsg("");
        setMessage("");
        setPending(true);
        const url = "http://localhost:8080/api/chat/stream";
        const reqHeader = {
            Authorization: "Bearer " + (localStorage.getItem("openAiKey") ?? ""),
            "Content-Type": "application/json; charset=utf-8;"
        }
        const reqBody = getRequest({
            role: "user",
            content: message
        });

        addHistory({
            role: "assistant",
            name: "user",
            content: message
        });

        requestStream({
            url,
            method: "POST",
            headers: reqHeader,
            body: JSON.stringify(reqBody)
        }, (chunk) => {
            setRespMsg(prevRespMsg => prevRespMsg + chunk);
            return chunk;
        }).then((result) => {
            if (result !== undefined) {
                addHistory({
                    role: "assistant",
                    name: "ai",
                    content: result
                });
            }

            setRespMsg("");
            setPending(false);
        });
    }

    return (
        <Box style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
            height: "100%",
            maxHeight: "100%"
        }}>
            <Container
                maxWidth="lg"
                style={{
                    display: "flex",
                    flexDirection: "column",
                    overflowX: "hidden",
                    overflowY: "auto"
                }}
            >
                {historis.map(
                    (item, index) => <PromptBox
                        role={item.role}
                        name={item.name}
                        content={item.content}
                        key={index}
                    />
                )}
                {
                    !respMsg
                        ? null
                        : <PromptBox role="assistant" content={respMsg}/>
                }
            </Container>
            <Form
                onKeyDown={onKeyDown}
                style={{
                    width: "100%",
                    display: "flex",
                    justifyContent: "center",
                    padding: "1em",
                    paddingBottom: "0",
                    borderTop: "1px solid",
                    position: "relative"
                }}
            >
                <Form.Control
                    as={TextareaAutosize}
                    maxRows={3}
                    placeholder="Send a message"
                    value={message}
                    onChange={onChange}
                    style={{
                        width: "50%",
                        padding: "0.75rem",
                        minHeight: "24px"
                    }}
                    disabled={pending}
                />
            </Form>
        </Box>
    );
}