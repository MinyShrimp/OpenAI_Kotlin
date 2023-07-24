import {JSX, useEffect, useState} from "react";
import {useRecoilState} from "recoil";

import {Grid} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';

import {AxiosClient} from "../base/AxiosClient";
import {ModelToolbar} from "./ModelToolbar";
import {Model, ModelListState} from "../states/model";

export function ModelViewerLayer(): JSX.Element {
    // const [pending, setPending] = useState<boolean>(false);
    const [models, setModels] = useRecoilState(ModelListState);
    const [showModels, setShowModels] = useState<Model[]>([]);

    const initModelList = (models: Model[]) => {
        models.forEach(model => {
            model.parent = model.parent === undefined ? "" : model.parent;
        });

        setModels(models);
        setShowModels(models);
    }

    useEffect(() => {
        if (models.length > 0) {
            setShowModels(models);
        } else {
            AxiosClient()
                .get("/model/list")
                .then((response) => initModelList(response.data))
                .finally(() => {
                    // setPending(true);
                });
        }
    }, []);

    return (
        <Grid
            container
            direction="column"
            justifyContent="space-between"
            alignItems="stretch"
            height="100%"
        >
            <Grid item>
                <h1>Model List</h1>
            </Grid>
            <Grid item height="90%" minHeight="520px">
                <DataGrid
                    style={{maxHeight: "100%"}}
                    rows={showModels}
                    columns={[
                        {field: 'id', headerName: 'ID', flex: 0.4, minWidth: 0},
                        {
                            field: 'created', headerName: 'Created', flex: 0.15, minWidth: 0,
                            valueFormatter: params => {
                                return new Date(params.value * 1000).toLocaleString();
                            }
                        },
                        {field: 'owned_by', headerName: 'Owned_by', flex: 0.15, minWidth: 0},
                        {field: 'root', headerName: 'Root', flex: 0.1, minWidth: 0},
                        {field: 'parent', headerName: 'Parent', flex: 0.1, minWidth: 0},
                    ]}
                    initialState={{
                        pagination: {
                            paginationModel: {
                                page: 0,
                                pageSize: 20,
                            },
                        },
                        sorting: {
                            sortModel: [
                                {field: 'created', sort: 'desc'}
                            ]
                        }
                    }}
                    pageSizeOptions={[20]}
                    slots={{
                        toolbar: ModelToolbar(models, setShowModels)
                    }}
                />
            </Grid>
        </Grid>
    );
}