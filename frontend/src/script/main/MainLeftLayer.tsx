import {JSX, useState} from "react";
import {Box, Container, Divider, Grid, IconButton, Typography} from "@mui/material";

import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGear, faRightFromBracket} from "@fortawesome/free-solid-svg-icons";

import {MenuLayer} from "../menu/MenuLayer";
import {OptionModalLayer} from "../option/OptionModalLayer";
import {ContextListLayer} from "../chat_completion/components/context_list/ContextListLayer";

import {useRecoilState} from "recoil";
import {LEFT_STATE, LeftMenuState} from "../states/left_state";
import {Logout} from "../login/api/Logout";

export default function MainLeftLayer(): JSX.Element {
    const [optionModalOpen, setOptionModalOpen] = useState<boolean>(false);
    const optionModalCloseHandler = (): void => {
        setOptionModalOpen(false);
    }

    const [leftMenuState,] = useRecoilState(LeftMenuState);
    const convertMenu = (state: LEFT_STATE): JSX.Element => {
        switch (state) {
            case LEFT_STATE.CHAT_COMPLETION:
                return <ContextListLayer/>;
            case LEFT_STATE.DEFAULT:
            default:
                return <MenuLayer/>;
        }
    }

    const logoutMutation = Logout();
    const logoutHandler = (): void => {
        logoutMutation.mutate();
    }

    return (
        <Box
            sx={{bgcolor: "background.paper"}}
            style={{
                width: "15%",
                display: "flex",
                flexDirection: "column",
                height: "100vh",
            }}
        >
            <Box
                className="logo"
                style={{
                    padding: "1em"
                }}
            >
                <Typography variant={"h5"}>
                    OpenAI Helper
                </Typography>
            </Box>
            <Divider/>
            <Container
                className="menu"
                style={{
                    height: "100vh",
                    overflowX: "hidden",
                    overflowY: "auto",
                }}
            >
                {convertMenu(leftMenuState)}
            </Container>
            <Divider/>
            <Grid
                container
                className={"footer"}
                style={{
                    padding: "1em"
                }}
            >
                <Grid item xs={8}>
                    <Typography> &copy; 2023. HRC </Typography>
                    kimhm@hrc.co.kr
                </Grid>
                <Grid item xs={2} display="flex" alignContent="center">
                    <IconButton
                        onClick={() => setOptionModalOpen(true)}
                        color="inherit"
                    >
                        <FontAwesomeIcon icon={faGear}/>
                    </IconButton>
                </Grid>
                <Grid item xs={2} display="flex" alignContent="center">
                    <IconButton onClick={logoutHandler}>
                        <FontAwesomeIcon icon={faRightFromBracket}/>
                    </IconButton>
                </Grid>
            </Grid>
            <OptionModalLayer
                isOpen={optionModalOpen}
                closeHandler={optionModalCloseHandler}
            />
        </Box>
    );
}
