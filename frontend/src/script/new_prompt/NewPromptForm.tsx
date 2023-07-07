import React, {forwardRef} from "react";
import {ButtonGroup, Col, Form, Row} from "react-bootstrap";

import {DeleteOutlined} from "@mui/icons-material";
import {IconButton, TextareaAutosize} from "@mui/material";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEye, faEyeSlash} from "@fortawesome/free-solid-svg-icons";

import {INewPromptData} from "./INewPromptData";

export const NewPromptForm = forwardRef((
    props: {
        item: INewPromptData,
        changeEvent: (_id: string, key: string, value: string | boolean) => void,
        deleteEvent: (prompt: INewPromptData) => void
    },
    ref: React.ForwardedRef<HTMLTextAreaElement> | undefined
) => {
    const onChangeEvent = (event: React.ChangeEvent<any>): void => {
        props.changeEvent(props.item._id, event.target.name, event.target.value);
    }

    return (
        <Form style={{position: "relative"}}>
            <Row style={{marginBottom: "0.5em"}}>
                <Form.Group as={Col} controlId={"role_" + props.item.index}>
                    <Form.Label>Role</Form.Label>
                    <Form.Select
                        name={"role"}
                        defaultValue={props.item.role}
                        onChange={onChangeEvent}
                        disabled={props.item.index === 0 || props.item.disabled}
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
                        disabled={props.item.disabled}
                    />
                </Form.Group>
            </Row>
            <Form.Group controlId={"content_" + props.item.index}>
                <Form.Label>Content</Form.Label>
                <Form.Control
                    as={TextareaAutosize} minRows={3} name={"content"}
                    defaultValue={props.item.content}
                    onChange={onChangeEvent} ref={ref}
                    disabled={props.item.disabled}
                />
            </Form.Group>

            <ButtonGroup style={{
                position: "absolute",
                top: "-12px", right: "-10px"
            }}
            >
                <IconButton
                    aria-label="disabled"
                    size={"small"}
                    color={props.item.disabled ? "error" : "primary"}
                    disabled={props.item.index === 0}
                    onClick={() => props.changeEvent(props.item._id, "disabled", !props.item.disabled)}
                >
                    {
                        props.item.disabled ?
                            <FontAwesomeIcon icon={faEyeSlash}/>
                            : <FontAwesomeIcon icon={faEye}/>
                    }
                </IconButton>
                <IconButton
                    aria-label={"delete"}
                    size={"medium"}
                    color={"error"}
                    disabled={props.item.index === 0}
                    onClick={() => props.deleteEvent(props.item)}
                >
                    <DeleteOutlined fontSize="inherit"/>
                </IconButton>
            </ButtonGroup>
        </Form>
    );
});