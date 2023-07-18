import {ChangeEvent, ForwardedRef, forwardRef} from "react";
import {ButtonGroup, Col, Form, Row} from "react-bootstrap";

import {DeleteOutlined} from "@mui/icons-material";
import {IconButton, TextareaAutosize} from "@mui/material";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEye, faEyeSlash} from "@fortawesome/free-solid-svg-icons";

import {IPrePrompt, PRE_PROMPT_TYPE, TransPrePromptType} from "./PrePromptTypes";

export const PromptForm = forwardRef((
    props: {
        item: IPrePrompt,
        changeEvent: (_id: string, key: string, value: any) => void,
        deleteEvent: (prompt: IPrePrompt) => void
    },
    ref: ForwardedRef<HTMLTextAreaElement> | undefined
) => {
    const onChangeEvent = (event: ChangeEvent<any>): void => {
        props.changeEvent(props.item._id, event.target.name, event.target.value);
    }

    return (
        <Form style={{position: "relative"}}>
            <Row style={{marginBottom: "0.5em"}}>
                <Form.Group as={Col} controlId={"name_" + props.item.index}>
                    <Form.Label>Type</Form.Label>
                    <Form.Select
                        name={"type"}
                        defaultValue={props.item.type}
                        onChange={onChangeEvent}
                        disabled={props.item.index === 0 || props.item.disabled}
                    >
                        {
                            Object.values(PRE_PROMPT_TYPE)
                                .map(
                                    (value: string) => (
                                        <option
                                            value={value}
                                            disabled={value === PRE_PROMPT_TYPE.SYSTEM}
                                        >
                                            {TransPrePromptType(value as PRE_PROMPT_TYPE)}
                                        </option>
                                    )
                                )
                        }
                    </Form.Select>
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