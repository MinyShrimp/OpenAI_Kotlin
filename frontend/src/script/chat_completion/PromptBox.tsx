import {JSX} from "react";
import {Box, useTheme} from "@mui/material";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import {CopyBlock, dracula, tomorrow} from "react-code-blocks";


// @ts-ignore
function PromptCodeBlock({node, inline, className, children, ...props}) {
    const match = /language-(\w+)/.exec(className || '');
    const theme = useTheme();
    const codeBlockTheme = theme.palette.mode === "light" ? tomorrow : dracula;

    return !inline && match ? (
        <CopyBlock
            text={String(children).replace(/\n$/, "")}
            language={match[1]}
            // @ts-ignore
            theme={codeBlockTheme}
            codeBlock
        />
    ) : (
        <code className={className} {...props}>
            {children}
        </code>
    )
}


export function PromptBox(
    props: {
        role: string,
        content: string
    }
): JSX.Element {
    return <Box
        sx={{
            border: 1,
            borderRadius: 2,
            whiteSpace: "pre-wrap",
            wordWrap: "break-word",
            width: "100%",
            padding: "1em",
            marginBottom: "1em",
            bgcolor: props.role === "user"
                ? "background.default"
                : "background.paper",
        }}
    >
        <ReactMarkdown
            className={"reactMarkDown"}
            remarkPlugins={[remarkGfm]}
            components={{
                // @ts-ignore
                code: PromptCodeBlock
            }}
        >
            {props.content}
        </ReactMarkdown>
    </Box>
}
