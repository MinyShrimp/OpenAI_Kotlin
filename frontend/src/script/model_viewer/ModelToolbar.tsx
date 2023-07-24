import {Dispatch, SetStateAction, useState} from "react";

import {
    GridToolbarColumnsButton,
    GridToolbarContainer,
    GridToolbarDensitySelector,
    GridToolbarExport
} from "@mui/x-data-grid";
import {Button, ButtonGroup, Grid} from "@mui/material";
import {Model} from "../states/model";

export function ModelToolbar(
    originModelList: Model[],
    setShowModelList: Dispatch<SetStateAction<Model[]>>,
) {
    const [selectIndex, setSelectIndex] = useState<number>(0);

    const showAll = () => {
        setShowModelList(originModelList);
        setSelectIndex(0);
    }

    const any = (
        model: Model,
        findModelNameList: string[]
    ): boolean => {
        for (const modelName of findModelNameList) {
            if (model.id.includes(modelName)) return true;
        }
        return false;
    }

    const filterCompletion = () => {
        const filtered = originModelList
            .filter(model => any(model, ["curie", "davinci", "ada", "babbage"]))
            .filter(model => !any(model, ["embedding", "similarity", "search", "moderation", "edit", "instruct"]));
        setShowModelList(filtered);
        setSelectIndex(1);
    }

    const filterChatCompletion = () => {
        const filtered = originModelList
            .filter(model => any(model, ["gpt-4", "gpt-3.5"]));

        setShowModelList(filtered);
        setSelectIndex(2);
    }

    const filterFineTune = () => {
        const filtered = originModelList
            .filter(model => any(model, ["curie", "davinci", "ada", "babbage"]))
            .filter(model => model.parent !== "");

        setShowModelList(filtered);
        setSelectIndex(3);
    }

    return () => (
        <GridToolbarContainer>
            <Grid item xs>
                <ButtonGroup size="small" variant="outlined">
                    <Button
                        onClick={showAll}
                        variant={selectIndex === 0 ? "contained" : "outlined"}
                    >
                        All
                    </Button>
                    <Button
                        onClick={filterCompletion}
                        variant={selectIndex === 1 ? "contained" : "outlined"}
                    >
                        Completion
                    </Button>
                    <Button
                        onClick={filterChatCompletion}
                        variant={selectIndex === 2 ? "contained" : "outlined"}
                    >
                        Chat
                    </Button>
                    <Button
                        onClick={filterFineTune}
                        variant={selectIndex === 3 ? "contained" : "outlined"}
                    >
                        Fine Tune
                    </Button>
                </ButtonGroup>
            </Grid>
            <Grid item xs={6} style={{
                display: "flex",
                justifyContent: "flex-end",
            }}>
                <GridToolbarColumnsButton/>
                <GridToolbarDensitySelector/>
                <GridToolbarExport/>
            </Grid>
        </GridToolbarContainer>
    )
}