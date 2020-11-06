# CMPE451 - E-commerce website - Frontend

# Instructions

1. `git pull`
2. `cd ecommerce/frontend/`
3. Open in your favorite editor (VSCode recommended) 
4. Make sure you have at least npm v6.14.8 and node v15.1.0
5. Run `npm install`
6. Run `npm start`
7. The web browser will be opened to "http://localhost:5000/"

# Docker instructions

## Build docker image
```
docker build -t frontend .
```

## Run docker container
```
docker run -p 5000:5000 frontend
```
