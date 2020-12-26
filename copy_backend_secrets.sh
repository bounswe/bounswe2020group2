#!/usr/bin/env bash                                                                

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"     
cp $DIR/ecommerce/backend/.env.production $DIR/unencrypted-secrets/backend.env.production -v
