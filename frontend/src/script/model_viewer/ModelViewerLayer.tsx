import {JSX, useEffect, useState} from "react";

import {Grid} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';

export function ModelViewerLayer(): JSX.Element {
    // const [pending, setPending] = useState<boolean>(false);
    const [models, setModels] = useState<any[]>([]);

    useEffect(() => {
        fetch(
            import.meta.env.VITE_BACKEND_API_URL + "/model/list",
            {
                method: "GET",
                headers: {
                    Authorization: "Bearer " + (localStorage.getItem("openAiKey") ?? ""),
                    "Content-Type": "application/json; charset=utf-8;"
                }
            }
        ).then(
            (response) => response.json()
        ).then((data) => {
            setModels(data);
        }).finally(() => {
            // setPending(true);
        });
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
                    rows={models}
                    columns={[
                        {field: 'id', headerName: 'ID', flex: 0.4, minWidth: 0},
                        {field: 'object', headerName: 'Object', flex: 0.1, minWidth: 0},
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
                    }}
                    pageSizeOptions={[20]}
                />
            </Grid>
        </Grid>
    );
}