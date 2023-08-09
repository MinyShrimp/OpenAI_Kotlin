import {JSX} from "react";
import {Container} from "@mui/material";
import {LoginForm} from "./LoginForm";
import {SignupForm} from "./SignupForm";
import {useRecoilState} from "recoil";
import {LoginContextState} from "../states/auth";

export function AuthLayer(): JSX.Element {
    const [loginContext,] = useRecoilState(LoginContextState);

    return (
        <Container
            fixed
            sx={{
                height: "100vh",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
            }}
        >
            {
                loginContext === "login"
                    ? <LoginForm/>
                    : <SignupForm/>
            }
        </Container>
    );
}
