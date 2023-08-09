FROM node:18.16.1-slim

WORKDIR /app
COPY package.json .
RUN npm install

COPY public ./public
COPY src ./src
COPY tsconfig.json .
COPY tsconfig.node.json .
COPY vite.config.ts .

EXPOSE 3000
CMD ["npm", "run", "dev"]
