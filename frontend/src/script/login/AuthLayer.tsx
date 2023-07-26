import {JSX, useState} from "react";
import {Container} from "@mui/material";
import {LoginForm} from "./LoginForm";
import {SignupForm} from "./SignupForm";

export function AuthLayer(): JSX.Element {
    const [authPage, setAuthPage] = useState("login");

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
                authPage === "login"
                    ? <LoginForm setAuthPage={setAuthPage}/>
                    : <SignupForm setAuthPage={setAuthPage}/>
            }
        </Container>
    );
}
