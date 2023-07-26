import {JSX, useEffect, useRef, useState} from "react";
import {Button, Card, CardContent, CardHeader, Checkbox, FormControlLabel, Grid} from "@mui/material";
import {Login} from "./api/Login";
import {EmailInputForm} from "./components/EmailInputForm";
import {PasswordInputForm} from "./components/PasswordInputForm";

export function LoginForm(
    props: {
        setAuthPage: (page: string) => void,
    }
): JSX.Element {
    const [pwd, setPwd] = useState("");
    const [email, setEmail] = useState("");
    const [rememberMe, setRememberMe] = useState(localStorage.getItem("rememberMe") === "1");

    const loginMutation = Login();
    const validationHandlers = useRef<Map<string, () => boolean>>(new Map<string, () => boolean>());

    useEffect(() => {
        setPwd("");
        setEmail("");
    }, []);

    const submitHandler = () => {
        for (let handler of validationHandlers.current.values()) {
            if (!handler()) {
                return;
            }
        }
        
        loginMutation.mutate({
            email: email,
            password: pwd,
        });
    }

    const checkboxHandler = () => {
        const changeRememberMe = !rememberMe;
        localStorage.setItem("rememberMe", changeRememberMe ? "1" : "0");
        setRememberMe(changeRememberMe);
    }

    return (
        <Card sx={{width: "480px", padding: "1em"}}>
            <CardHeader title="로그인"/>
            <CardContent>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <EmailInputForm
                            target={email}
                            setTarget={setEmail}
                            validationHandlers={validationHandlers}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <PasswordInputForm
                            target={pwd}
                            setTarget={setPwd}
                            validationHandlers={validationHandlers}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            control={<Checkbox/>}
                            label="Remember me"
                            checked={rememberMe}
                            onChange={checkboxHandler}
                        />
                    </Grid>
                    <Grid item xs={8}>
                        <Button
                            fullWidth
                            variant="contained"
                            color="primary"
                            onClick={submitHandler}
                        >로그인</Button>
                    </Grid>
                    <Grid item xs={4}>
                        <Button
                            fullWidth
                            variant="contained"
                            color="success"
                            onClick={() => props.setAuthPage("signup")}
                        >회원가입</Button>
                    </Grid>
                </Grid>
            </CardContent>
        </Card>
    )
}
