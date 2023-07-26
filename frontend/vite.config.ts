import * as path from "path";
import {defineConfig, loadEnv} from "vite";
import react from "@vitejs/plugin-react";

const mode = "development"; // "development" | "production"

export default defineConfig({
    mode: mode,
    root: path.resolve(__dirname, "src"),
    publicDir: path.resolve(__dirname, "public"),
    envDir: path.resolve(__dirname, "envs"),
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
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
            },
            "/auth": {
                target: "http://localhost:8080",
                changeOrigin: true,
            }
        }
    },
    define: {
        __APP_ENV__: JSON.stringify(
            loadEnv(mode, path.resolve(__dirname, "envs"), "")
        ),
        "process.env": process.env
    },
});
