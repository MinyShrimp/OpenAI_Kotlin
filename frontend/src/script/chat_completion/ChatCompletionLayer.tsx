import {ChangeEvent, JSX, KeyboardEvent, useRef, useState} from "react";
import {Form} from "react-bootstrap";
import {Box, TextareaAutosize} from "@mui/material";
import {request} from "../base/request";

interface IHistoryPrompt {
    role: string;
    content: string;
}

export function ChatCompletionLayer(): JSX.Element {
    const message = useRef("");
    const [history, setHistory] = useState<IHistoryPrompt[]>([]);
    const [respMsg, setRespMsg] = useState<string>("");

    const addHistory = ({role, content}: IHistoryPrompt): void => {
        setHistory(prevHistory => [...prevHistory, {
            role: role,
            content: content
        }]);
    }

    const onKeyDown = (e: KeyboardEvent<HTMLFormElement>): void => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            submit();
        }
    }

    const onChange = (e: ChangeEvent<HTMLTextAreaElement>): void => {
        message.current = e.target.value;
    }

    const submit = async (): Promise<void> => {
        if (!message.current.trim()) {
            return;
        }

        const prompt = {
            role: "user",
            content: message.current
        }

        setRespMsg("");
        addHistory(prompt);
        const url = "http://localhost:8080/api/chat/stream";
        const reqHeader = {
            Authorization: "Bearer " + (localStorage.getItem("openAiKey") ?? ""),
            "Content-Type": "application/json; charset=utf-8;"
        }
        const reqBody = {
            model: "gpt-4",
            messages: [...history, prompt]
        };

        request({
            url,
            method: "POST",
            headers: reqHeader,
            body: JSON.stringify(reqBody)
        }, (chunk): string => {
            const value = chunk
                .split("\n")
                .filter((line: string) => line !== "")
                .map((line: string) => {
                    const match = RegExp(/data:(.*)/).exec(line);
                    return match?.[1] ?? "";
                })
                .reduce((acc: string, curr: string) => {
                    return acc + curr;
                });

            setRespMsg(prevRespMsg => prevRespMsg + value);
            return value;
        }).then((result): void => {
            console.log("finally", result);
            addHistory({
                role: "assistant",
                content: result
            });
            setRespMsg("");
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
            <Box
                style={{
                    display: "flex",
                    flexDirection: "column",
                    marginBottom: "1em",
                    overflowX: "hidden",
                    overflowY: "auto"
                }}
            >
                {
                    history.map((item, index) => <Box key={index}>
                        {item.role}: {item.content}
                    </Box>)
                }
                <Box>
                    {!respMsg ? "" : "assistant: " + respMsg}
                </Box>
            </Box>
            <Form
                onKeyDown={onKeyDown}
                style={{
                    width: "100%",
                    display: "flex",
                    justifyContent: "center",
                }}
            >
                <Form.Control
                    as={TextareaAutosize}
                    maxRows={3}
                    placeholder="Send a message"
                    onChange={onChange}
                    style={{
                        width: "50%",
                        padding: "0.75rem",
                        minHeight: "24px"
                    }}
                />
            </Form>
        </Box>
    );
}