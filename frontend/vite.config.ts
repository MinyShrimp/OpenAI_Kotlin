import * as path from "path";
import {defineConfig} from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    mode: "development", // "development" | "production"
    root: path.resolve(__dirname, "src"),
    publicDir: path.resolve(__dirname, "public"),
    build: {
        outDir: path.resolve(__dirname, "dist"),
    },
    plugins: [react()],
    resolve: {
        alias: {
            "~bootstrap": path.resolve(__dirname, "node_modules/bootstrap"),
        },
    },
    server: {
        port: 3000,
    },
});
