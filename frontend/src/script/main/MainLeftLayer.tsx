import {JSX, useState} from "react";
import {Box, Divider, Grid, IconButton, Typography} from "@mui/material";
import {Brightness4, Brightness7} from "@mui/icons-material";

import {faGear} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import {Layer} from "../base/Layer";
import {LeftNavLayer} from "../left_navigation/LeftNavLayer";
import {OptionModalLayer} from "../option/OptionModalLayer";

export default function MainLeftLayer(
    props: {
        darkMode: boolean,
        darkModeHandler: () => void
    }
): JSX.Element {

    const [optionModalOpen, setOptionModalOpen] = useState<boolean>(false);
    const optionModalCloseHandler = (): void => {
        setOptionModalOpen(false);
    }

    return (
        <Box
            component={Layer}
            sx={{bgcolor: "background.paper"}}
            style={{width: "15%"}}
        >
            <Box className={"logo"} marginBottom="1em">
                <Typography variant={"h5"}>
                    OpenAI Helper
                </Typography>
            </Box>
            <Divider/>
            <LeftNavLayer/>
            <Divider/>
            <Grid container className={"footer"} marginTop="1em">
                <Grid item xs={8}>
                    <Typography> &copy; 2023. HRC </Typography>
                    kimhm@hrc.co.kr
                </Grid>
                <Grid item xs={2} display="flex" alignContent="center">
                    <IconButton
                        onClick={props.darkModeHandler}
                        color="inherit"
                    >
                        {props.darkMode ? <Brightness7/> : <Brightness4/>}
                    </IconButton>
                </Grid>
                <Grid item xs={2} display="flex" alignContent="center">
                    <IconButton
                        onClick={() => setOptionModalOpen(true)}
                        color="inherit"
                    >
                        <FontAwesomeIcon icon={faGear}/>
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