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

                    const chunk = decoder.decode(value);
                    text += chunkHandler(chunk);
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
