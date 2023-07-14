import {ChangeEvent, JSX, KeyboardEvent, useState} from "react";
import {Form} from "react-bootstrap";
import {Box, Container, IconButton, TextareaAutosize} from "@mui/material";
import {request} from "../base/request";
import {PromptBox} from "./PromptBox";
import {PlusOne, PlusOneSharp} from "@mui/icons-material";

interface IHistoryPrompt {
    role: string;
    content: string;
}

export function ChatCompletionLayer(): JSX.Element {
    const [message, setMessage] = useState<string>("");
    const [history, setHistory] = useState<IHistoryPrompt[]>([]);
    const [respMsg, setRespMsg] = useState<string>("");
    const [pending, setPending] = useState<boolean>(false);

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
        setMessage(e.target.value);
    }

    const submit = async (): Promise<void> => {
        if (!message.trim()) {
            return;
        }

        const prompt = {
            role: "user",
            content: message
        }

        setRespMsg("");
        setMessage("");
        setPending(true);
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
        }, (chunk) => {
            setRespMsg(prevRespMsg => prevRespMsg + chunk);
            return chunk;
        }).then((result) => {
            addHistory({
                role: "assistant",
                content: result
            });
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
                {history.map((item, index) => <PromptBox role={item.role} content={item.content} key={index}/>)}
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
                <Box
                    sx={{
                        position: "absolute",
                        right: "0",
                        bottom: "-10px"
                    }}
                >
                    <IconButton>
                        <PlusOne/>
                    </IconButton>
                    <IconButton>
                        <PlusOneSharp/>
                    </IconButton>
                </Box>

            </Form>
        </Box>
    );
}