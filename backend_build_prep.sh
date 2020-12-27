#!/usr/bin/env bash                                                                

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"     

cp $DIR/backend.env.production $DIR/ecommerce/backend/.env.production -v