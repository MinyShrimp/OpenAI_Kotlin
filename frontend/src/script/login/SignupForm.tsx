import {JSX, useEffect, useRef, useState} from "react";
import {Button, Card, CardContent, CardHeader, Grid} from "@mui/material";
import {Signup} from "./api/Signup";
import {NameInputForm} from "./components/NameInputForm";
import {EmailInputForm} from "./components/EmailInputForm";
import {PasswordInputForm} from "./components/PasswordInputForm";
import {useRecoilState} from "recoil";
import {LoginContextState} from "../states/auth";

export function SignupForm(): JSX.Element {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [pwd, setPwd] = useState("");

    const signupMutation = Signup();
    const validationHandlers = useRef<Map<string, () => boolean>>(new Map<string, () => boolean>());

    useEffect(() => {
        setPwd("");
        setName("");
        setEmail("");
    }, []);

    const [, setLoginContext] = useRecoilState(LoginContextState)

    const submitHandler = () => {
        for (let handler of validationHandlers.current.values()) {
            if (!handler()) {
                return;
            }
        }

        signupMutation.mutate({
            name: name,
            email: email,
            password: pwd,
        })
    }

    return (
        <Card sx={{width: "480px", padding: "1em"}}>
            <CardHeader title="회원가입"/>
            <CardContent component={Grid} container spacing={2}>
                <Grid item xs={12}>
                    <NameInputForm
                        target={name}
                        setTarget={setName}
                        validationHandlers={validationHandlers}
                    />
                </Grid>
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
                <Grid item xs={8}>
                    <Button
                        fullWidth
                        variant="contained"
                        color="primary"
                        onClick={submitHandler}
                    >가입</Button>
                </Grid>
                <Grid item xs={4}>
                    <Button
                        fullWidth
                        variant="contained"
                        color="success"
                        onClick={() => setLoginContext("login")}
                    >로그인 페이지</Button>
                </Grid>
            </CardContent>
        </Card>
    )
}