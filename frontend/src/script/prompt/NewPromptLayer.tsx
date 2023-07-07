import {v4 as uuidv4} from "uuid";

import {JSX, useEffect, useRef, useState} from "react";
import {Button, ButtonGroup} from "react-bootstrap";
import {Accordion, AccordionDetails, AccordionSummary, Typography} from "@mui/material";

import {Layer} from "../base/Layer";
import {NewPromptForm} from "./NewPromptForm";
import {INewPromptData} from "./INewPromptData";

export function NewPromptLayer(): JSX.Element {
    const [promptList, setPromptList] = useState<INewPromptData[]>([]);
    const promptIndex = useRef(0);
    const lastPromptItem = useRef<HTMLTextAreaElement>(null);
    const addButtonRef = useRef<HTMLButtonElement>(null);

    const initPrompt = (): void => {
        setPromptList([{
            _id: uuidv4(), index: 0,
            role: "system", content: "", name: "",
            disabled: false
        }]);
        promptIndex.current = 0;
    };
    useEffect(initPrompt, []);

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

    const deletePrompt = (prompt: INewPromptData): void => {
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
        const requestPromptList = promptList.filter((item) => !item.disabled);
        console.log(requestPromptList);
    };

    const cancel = (): void => {
        initPrompt();
    };

    return (
        <Layer style={{justifyContent: "space-between"}}>
            <Layer style={{overflowX: "hidden", overflowY: "auto", height: "85vh"}}>
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
                                <NewPromptForm
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
                        variant="primary"
                        onClick={addPrompt}
                        style={{width: "100%"}}
                        ref={addButtonRef}
                    >+</Button>
                </div>
            </Layer>

            <div className={"footer"}>
                <ButtonGroup>
                    <Button variant="success" onClick={commit}>등록</Button>
                    <Button variant="danger" onClick={cancel}>취소</Button>
                </ButtonGroup>
            </div>
        </Layer>
    );
}