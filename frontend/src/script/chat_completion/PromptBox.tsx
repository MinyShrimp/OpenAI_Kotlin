import {JSX} from "react";
import {Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, useTheme} from "@mui/material";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import {CopyBlock, dracula, tomorrow} from "react-code-blocks";
import {CodeProps, ReactMarkdownProps, TableRowProps} from "react-markdown/lib/ast-to-react";

function PromptCodeBlock({inline, className, children}: CodeProps) {
    const match = /language-(\w+)/.exec(className ?? '');
    const theme = useTheme();
    const codeBlockTheme = theme.palette.mode === "light" ? tomorrow : dracula;

    return !inline && match ? (
        // @ts-ignore
        <CopyBlock
            text={String(children).replace(/\n$/, "")}
            language={match[1]}
            theme={{...codeBlockTheme, mode: theme.palette.mode}}
            codeBlock
        />
    ) : (
        <code className={className}>
            {children}
        </code>
    )
}

const PromptTable = ({children}: ReactMarkdownProps) => <TableContainer><Table>{children}</Table></TableContainer>;
const PromptTableHead = ({children}: ReactMarkdownProps) => <TableHead>{children}</TableHead>;
const PromptTableBody = ({children}: ReactMarkdownProps) => <TableBody>{children}</TableBody>;
const PromptTableRow = ({children}: TableRowProps) => <TableRow>{children}</TableRow>;
const PromptTableCell = ({children}: ReactMarkdownProps) => <TableCell>{children}</TableCell>;

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
                code: PromptCodeBlock,
                table: PromptTable,
                thead: PromptTableHead,
                tbody: PromptTableBody,
                tr: PromptTableRow,
                td: PromptTableCell,
                th: PromptTableCell
            }}
        >
            {props.content}
        </ReactMarkdown>
    </Box>
}
