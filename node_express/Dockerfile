FROM node:18-slim

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY ./app/package.json ./

RUN npm install

COPY ./app /app

EXPOSE 8080

CMD ["node", "index.js"]
