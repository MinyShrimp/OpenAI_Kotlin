export function request(
    data: {
        url: string,
        method: string,
        headers?: any,
        body?: string,
    },
    chunkHandler: (chunk: string) => string,
): Promise<string> {
    let text = "";
    let buffer = "";
    const decoder = new TextDecoder("utf-8");

    return fetch(data.url, {
        method: data.method,
        headers: data.headers,
        body: data.body,
    }).then((response) => {
        if (response.status === 200) {
            const reader = response.body?.getReader();

            return readChunk();

            // @ts-ignore
            function readChunk() {
                return reader?.read().then(({done, value}) => {
                    if (done) {
                        return text;
                    }

                    // TODO
                    //  - code block 위치 수정
                    const chunk = decoder.decode(value);
                    buffer = buffer + chunk;

                    // console.log("-------")
                    // console.log("chunk:", JSON.stringify(chunk));
                    // console.log("buffer:", JSON.stringify(buffer));

                    const valid = buffer.match(/data:((?!data:).)*\n\n/gs);
                    // console.log("valid:", valid);
                    if (valid === null) {
                        return readChunk();
                    }

                    const regChunk = valid
                        .map(c => c
                            .replace("\n\n", "")
                            .replace("data:", "")
                        )
                        .reduce((acc, cur) => acc + cur, "");
                    // console.log("regChunk:", JSON.stringify(regChunk));

                    text += chunkHandler(regChunk ?? "");
                    buffer = valid.reduce((tmp, c) => tmp.replace(c, ""), buffer);
                    return readChunk();
                });
            }
        } else {
            throw new Error(response.statusText);
        }
    }).catch((error) => {
        console.error(error);
    });
}
