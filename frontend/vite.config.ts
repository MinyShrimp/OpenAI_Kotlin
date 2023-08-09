import * as path from "path";
import {defineConfig, loadEnv} from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig(({mode}) => {
    const env = loadEnv(mode, path.resolve(__dirname, "envs"));
    const processEnvValues = {
        'process.env': Object.entries(env)
            .reduce((prev, [key, val]) => {
                return {...prev, [key]: val}
            }, {})
    }

    return {
        root: path.resolve(__dirname, "src"),
        publicDir: path.resolve(__dirname, "public"),
        build: {
            outDir: path.resolve(__dirname, "dist"),
        },
        plugins: [react()],
        server: {port: 3000},
        resolve: {
            alias: {
                "~bootstrap": path.resolve(__dirname, "node_modules/bootstrap"),
            },
        },
        define: processEnvValues
    }
});
