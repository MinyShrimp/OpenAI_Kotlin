import React, {forwardRef} from "react";
import {Col, Form, Row} from "react-bootstrap";
import {IconButton, TextareaAutosize} from "@mui/material";
import {DeleteOutlined} from "@mui/icons-material";

export interface PromptData {
    _id: string,
    index: number,
    role: "system" | "assistant",
    name: string,
    content: string

    [index: string]: number | string,
}

export const NewPromptForm = forwardRef((
    props: {
        item: PromptData,
        changeEvent: (_id: string, key: string, value: string) => void,
        deleteEvent: (prompt: PromptData) => void
    },
    ref: React.ForwardedRef<HTMLTextAreaElement> | undefined
) => {
    const onChangeEvent = (event: React.ChangeEvent<any>): void => {
        props.changeEvent(props.item._id, event.target.name, event.target.value);
    }

    return (
        <Form style={{marginBottom: "1em", position: "relative"}}>
            <Row style={{marginBottom: "0.5em"}}>
                <Form.Group as={Col} controlId={"role_" + props.item.index}>
                    <Form.Label>Role #{props.item.index}</Form.Label>
                    <Form.Select
                        name={"role"}
                        defaultValue={props.item.role}
                        onChange={onChangeEvent}
                        disabled={props.item.index === 0}
                    >
                        <option value={"system"}>System</option>
                        <option value={"assistant"}>Assistant</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group as={Col} controlId={"name_" + props.item.index}>
                    <Form.Label>Name (Optional)</Form.Label>
                    <Form.Control
                        type={"text"} placeholder={"Optional"} name={"name"}
                        defaultValue={props.item.name}
                        onChange={onChangeEvent}
                    />
                </Form.Group>
            </Row>
            <Form.Group controlId={"content_" + props.item.index}>
                <Form.Label>Content</Form.Label>
                <Form.Control
                    as={TextareaAutosize} minRows={3} name={"content"}
                    defaultValue={props.item.content}
                    onChange={onChangeEvent} ref={ref}
                />
            </Form.Group>
            <IconButton
                aria-label={"delete"}
                style={{
                    position: "absolute",
                    top: "-12px", right: "-10px"
                }}
                size={"large"}
                color={"error"}
                disabled={props.item.index === 0}
                onClick={() => props.deleteEvent(props.item)}
            >
                <DeleteOutlined fontSize="inherit"/>
            </IconButton>
        </Form>
    );
});