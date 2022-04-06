### STAGE 1: Build ###
FROM node:12.7-alpine AS build
#### make the 'app' folder the current working directory
WORKDIR /usr/src/app
COPY package.json ./
#### install angular cli
RUN npm install -g @angular/cli
#### install project dependencies
RUN npm install
#### copy things
COPY . .
RUN npm run build

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
#### copy nginx conf
COPY default.conf /etc/nginx/conf.d/default.conf
#### copy artifact build from the 'build environment'
COPY --from=build /usr/src/app/dist/kanban-ui /usr/share/nginx/html
EXPOSE 8080:8080
CMD ["nginx", "-g", "daemon off;"]
