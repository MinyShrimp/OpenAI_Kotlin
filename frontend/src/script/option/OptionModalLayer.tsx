import {ChangeEvent, JSX, useEffect, useState} from "react";
import {Box, Grid, IconButton, Input, InputAdornment, Modal, Switch, Typography} from "@mui/material";

import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheck, faWrench, faXmark} from "@fortawesome/free-solid-svg-icons";

import {useRecoilState} from "recoil";
import {LightModeState} from "../states/config";
import {AccountInfoState} from "../states/auth";

export function OptionModalLayer(
    props: {
        isOpen: boolean,
        closeHandler: () => void
    }
): JSX.Element {
    const [accountInfo,] = useRecoilState(AccountInfoState);
    const [lightMode, setLightMode] = useRecoilState(LightModeState);

    const [openAiKey, setOpenAiKey] = useState("");
    const [changeMode, setChangeMode] = useState(false);
    useEffect((): void => {
        if (props.isOpen) {
            setOpenAiKey(localStorage.getItem("openAiKey") ?? "");
        }
    }, [props.isOpen]);

    const changeModeHandler = (): void => {
        setChangeMode(true);
    }

    const clipboardHandler = (): void => {
        if (changeMode) return;

        navigator.clipboard
            .writeText(openAiKey)
            .then(() => {
                console.log("copy success");
                console.log(openAiKey);
            });
    }

    const onChange = (e: ChangeEvent<HTMLInputElement>): void => {
        setOpenAiKey(e.target.value);
    }

    const saveHandler = (): void => {
        localStorage.setItem("openAiKey", openAiKey);
        setChangeMode(false);
    }

    const closeHandler = (): void => {
        setOpenAiKey(localStorage.getItem("openAiKey") ?? "");
        setChangeMode(false);
    }

    const itemStyle = {
        height: "37px",
        display: "flex",
        alignItems: "center",
    }
    const leftSize = 4;

    return (
        <Modal
            open={props.isOpen}
            onClose={props.closeHandler}
            keepMounted
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <Box
                sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: '30%',
                    bgcolor: 'background.default',
                    color: 'text.primary',
                    border: '2px solid #000',
                    boxShadow: 24,
                    p: "2em",
                }}
            >
                <Typography
                    id="modal-modal-title"
                    variant="h5"
                    sx={{marginBottom: "1em"}}
                >정보</Typography>

                <Grid
                    container
                    id="modal-modal-description"
                >
                    <Grid container item sx={itemStyle}>
                        <Grid item xs={leftSize}>
                            <Typography>Name</Typography>
                        </Grid>
                        <Grid item xs>
                            <Typography>{accountInfo?.name}</Typography>
                        </Grid>
                    </Grid>
                    <Grid container item sx={itemStyle}>
                        <Grid item xs={leftSize}>
                            <Typography>Email</Typography>
                        </Grid>
                        <Grid item xs>
                            <Typography>{accountInfo?.email}</Typography>
                        </Grid>
                    </Grid>
                    <Grid container item sx={itemStyle}>
                        <Grid item xs={leftSize}>
                            <Typography>Login Time</Typography>
                        </Grid>
                        <Grid item xs>
                            <Typography>{accountInfo?.loginAt}</Typography>
                        </Grid>
                    </Grid>
                    <Grid container item sx={itemStyle}>
                        <Grid item xs={leftSize}>
                            <Typography>Dark Mode</Typography>
                        </Grid>
                        <Grid item xs>
                            <Switch
                                size="small"
                                checked={!lightMode}
                                onClick={() => setLightMode(!lightMode)}
                            />
                        </Grid>
                    </Grid>
                    <Grid container item sx={itemStyle}>
                        <Grid item xs={leftSize}>
                            <Typography>API Key</Typography>
                        </Grid>
                        <Grid item xs>
                            <Input
                                type="text"
                                size="small"
                                fullWidth
                                value={openAiKey}
                                onChange={onChange}
                                onClick={clipboardHandler}

                                inputRef={input => {
                                    if (input !== null) {
                                        if (changeMode) {
                                            input.focus();
                                            input.setSelectionRange(0, input.value.length);
                                        } else {
                                            input.setSelectionRange(0, 0);
                                        }
                                    }
                                }}
                                readOnly={!changeMode}
                                sx={{
                                    cursor: changeMode ? "text" : "pointer",
                                }}

                                endAdornment={(
                                    <InputAdornment position="end">
                                        {changeMode
                                            ? <>
                                                <IconButton size="small" onClick={saveHandler}>
                                                    <FontAwesomeIcon icon={faCheck}/>
                                                </IconButton>
                                                <IconButton size="small" onClick={closeHandler}>
                                                    <FontAwesomeIcon icon={faXmark}/>
                                                </IconButton>
                                            </>
                                            : <IconButton size="small" onClick={changeModeHandler}>
                                                <FontAwesomeIcon icon={faWrench}/>
                                            </IconButton>}
                                    </InputAdornment>
                                )}
                            />
                        </Grid>
                    </Grid>
                </Grid>
            </Box>
        </Modal>
    );
}
