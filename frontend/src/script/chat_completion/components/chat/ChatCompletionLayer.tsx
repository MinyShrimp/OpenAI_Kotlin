import {ChangeEvent, JSX, KeyboardEvent, useEffect, useRef, useState} from "react";
import {Form} from "react-bootstrap";
import {Box, Container, TextareaAutosize} from "@mui/material";

import {useRecoilState} from "recoil";
import {
    CHAT_MODEL,
    ContextState,
    ConvertPromptToRequest,
    GetContext,
    IPrompt,
    NowContextIdState
} from "../../../states/context";
import {requestStream} from "../../../base/requestStream";

import {PromptBox} from "./PromptBox";
import {ChatCompletionRequestDto} from "./ChatCompletionRequestDto";

import {AddHistory} from "../../api/history/AddHistory";
import {GetHistoryList} from "../../api/history/GetHistoryList";

export function ChatCompletionLayer(): JSX.Element {
    const [message, setMessage] = useState<string>("");
    const [reqMsg, setReqMsg] = useState<string>("");
    const [respMsg, setRespMsg] = useState<string>("");
    const [pending, setPending] = useState<boolean>(false);

    const [contextState,] = useRecoilState(ContextState);
    const [nowContextIdState,] = useRecoilState(NowContextIdState);

    const {refetch} = GetHistoryList(() => {
        setTimeout(moveScrollEnd);
    });
    const addHistoryMutation = AddHistory();

    const containerElement = useRef<HTMLDivElement>(null);
    const moveScrollEnd = () => {
        if (containerElement.current !== null) {
            containerElement.current.scrollTop = containerElement.current.scrollHeight;
        }
    }

    useEffect(() => {
        refetch();
    }, [nowContextIdState]);

    const getRequest = (
        newPrompt: IPrompt
    ): ChatCompletionRequestDto => {
        const context = GetContext(contextState, nowContextIdState);
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

    const addHistories = (
        userHistory: IPrompt,
        aiHistory: IPrompt
    ): void => {
        addHistoryMutation.mutate(
            userHistory,
            {
                onSuccess: () => {
                    addHistoryMutation.mutate(
                        aiHistory,
                        {
                            onSuccess: () => {
                                setReqMsg("");
                                setRespMsg("");
                                setPending(false);
                                moveScrollEnd();
                            }
                        }
                    );
                }
            }
        );
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

        setReqMsg(message);
        setRespMsg("");
        setMessage("");
        setPending(true);

        const url = process.env.VITE_BACKEND_API_URL + "/api/chat/stream";
        const reqHeader = {
            Authorization: "Bearer " + (localStorage.getItem("openAiKey") ?? ""),
            "Content-Type": "application/json; charset=utf-8;"
        }
        const reqBody = getRequest({
            role: "user",
            content: message
        });

        requestStream({
            url,
            method: "POST",
            headers: reqHeader,
            body: JSON.stringify(reqBody)
        }, (text) => {
            setRespMsg(text);
            moveScrollEnd();
            return text;
        }).then((result) => {
            if (result !== undefined) {
                addHistories({
                    role: "assistant",
                    name: "user",
                    content: message
                }, {
                    role: "assistant",
                    name: "ai",
                    content: result
                });
            }
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
                ref={containerElement}
            >
                {GetContext(contextState, nowContextIdState)?.history
                    .map(
                        (item, index) => <PromptBox
                            name={item.name ?? ""}
                            content={item.content}
                            key={index}
                        />
                    )}
                {
                    !reqMsg
                        ? null
                        : <PromptBox name="user" content={reqMsg}/>
                }
                {
                    !respMsg
                        ? null
                        : <PromptBox name="ai" content={respMsg}/>
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
                    disabled={
                        pending || nowContextIdState === ""
                    }
                />
            </Form>
        </Box>
    );
}