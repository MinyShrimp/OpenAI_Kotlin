import {ChangeEvent, JSX, useEffect, useState} from "react";
import {Box, Button, Modal} from "@mui/material";
import {Form} from "react-bootstrap";

export function OptionModalLayer(
    props: {
        isOpen: boolean,
        closeHandler: () => void
    }
): JSX.Element {
    const [openAiKey, setOpenAiKey] = useState("");
    useEffect((): void => {
        if (props.isOpen) {
            setOpenAiKey(localStorage.getItem("openAiKey") ?? "");
        }
    }, [props.isOpen]);

    const onChange = (e: ChangeEvent<HTMLInputElement>): void => {
        setOpenAiKey(e.target.value);
    }

    const saveHandler = (): void => {
        localStorage.setItem("openAiKey", openAiKey);
        props.closeHandler();
    }

    const closeHandler = (): void => {
        setOpenAiKey(localStorage.getItem("openAiKey") ?? "");
        props.closeHandler();
    }

    return (
        <Modal
            open={props.isOpen}
            onClose={props.closeHandler}
            keepMounted
        >
            <Box
                sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: '20%',
                    bgcolor: 'background.default',
                    color: 'text.primary',
                    border: '2px solid #000',
                    boxShadow: 24,
                    p: "2em",
                }}
            >
                <Form style={{marginBottom: "2em"}}>
                    <Form.Group>
                        <Form.Label>OpenAI API Key</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Enter API Key"
                            name="apiKey"
                            value={openAiKey}
                            onChange={onChange}
                        />
                    </Form.Group>
                </Form>

                <Form.Group style={{
                    display: "flex",
                    justifyContent: "center"
                }}>
                    <Button
                        variant="contained"
                        color="primary"
                        style={{marginRight: "1em"}}
                        onClick={saveHandler}
                    >
                        저장
                    </Button>
                    <Button
                        variant="contained"
                        color="error"
                        onClick={closeHandler}
                    >
                        취소
                    </Button>
                </Form.Group>
            </Box>
        </Modal>
    );
}
