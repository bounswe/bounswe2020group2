FROM node:15.1.0

WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./
RUN npm install

COPY . ./
RUN npm run build
RUN npm i -g serve

CMD ["serve", "-s", "build", "-p", "5000"]
