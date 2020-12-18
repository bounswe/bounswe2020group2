import { config } from '../config'
import { rest } from 'msw'
import { trendingProducts, product } from './mocks'

// preprend config.apiUrl
const url = u => config.apiUrl + u

// DOCS: https://mswjs.io/docs/
// request: Information about the captured request.
// response: Function to create the mocked response.
// context: Context utilities specific to the current request handler.

// comment the handler when done with it, DO NOT REMOVE IT

export const handlers = [
    // url(...) is important here !!
    rest.get(url('/example/user/:userId'), (req, res, ctx) => {
        const { params, body } = req
        const { userId } = params

        if (!userId) return res(ctx.status(403), ctx.json({ successful: false, message: `Bad request` }))

        return res(ctx.json({ successful: true, user: { id: userId, name: 'Ali', surname: 'Batır' } }))
    }),
    rest.get(url('/products/homepage/:count'), (req, res, ctx) => {
        const { params, body } = req
        const { count } = params

        return res(ctx.json({ successful: true, products: trendingProducts.slice(0, count) }))
    }),
    rest.get(url('/product/:productId'), (req, res, ctx) => {
        const { params, body } = req
        const { productId } = params

        return res(ctx.json(product))
    }),
]
