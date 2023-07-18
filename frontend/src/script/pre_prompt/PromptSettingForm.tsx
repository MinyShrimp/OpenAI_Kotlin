import {ChangeEvent, Dispatch, JSX, SetStateAction} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import {TextareaAutosize} from "@mui/material";
import {CHAT_MODEL, ISetting} from "../states/context";

export function PromptSettingForm(
    props: {
        setting: ISetting,
        setSetting: Dispatch<SetStateAction<ISetting>>
    }
): JSX.Element {
    const changeTitle = (
        event: ChangeEvent<HTMLInputElement>
    ): void => {
        props.setSetting({
            ...props.setting,
            title: event.target.value
        })
    }

    const changeModel = (
        event: ChangeEvent<HTMLSelectElement>
    ): void => {
        props.setSetting({
            ...props.setting,
            model: event.target.value as CHAT_MODEL
        })
    }

    const changeDescription = (
        event: ChangeEvent<HTMLTextAreaElement>
    ): void => {
        props.setSetting({
            ...props.setting,
            description: event.target.value
        })
    }

    return (
        <Form>
            <Row style={{marginBottom: "0.5em"}}>
                <Form.Group as={Col}>
                    <Form.Label>Title</Form.Label>
                    <Form.Control
                        type={"text"}
                        value={props.setting.title}
                        onChange={changeTitle}
                    />
                </Form.Group>
                <Form.Group as={Col}>
                    <Form.Label>Model</Form.Label>
                    <Form.Select
                        value={props.setting.model}
                        onChange={changeModel}
                    >
                        <option value={CHAT_MODEL.GPT_3_5}>GPT 3.5</option>
                        <option value={CHAT_MODEL.GPT_3_5_16K}>GPT 3.5 - 16K</option>
                        <option value={CHAT_MODEL.GPT_4}>GPT 4</option>
                        <option value={CHAT_MODEL.GPT_4_32K}>GPT 4 - 32K</option>
                    </Form.Select>
                </Form.Group>
            </Row>
            <Row>
                <Form.Group as={Col}>
                    <Form.Label>Description (Optional)</Form.Label>
                    <Form.Control
                        as={TextareaAutosize}
                        minRows={1}
                        value={props.setting.description}
                        onChange={changeDescription}
                    />
                </Form.Group>
            </Row>
        </Form>
    );
}