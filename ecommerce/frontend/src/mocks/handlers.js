import { config } from '../config'
import { rest } from 'msw'
import { trendingProducts, product, products, reviews } from './mocks'

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
    rest.post(url('/search/products'), (req, res, ctx) => {
        const { params, body } = req
        const { page_size = 10, page = 0 } = body

        return res(
            ctx.json({
                data: {
                    pagination: {
                        page_size,
                        page,
                        total_items: products.length,
                    },
                    products: products.slice(page * page_size, (page + 1) * page_size),
                },
            }),
        )
    }),
    rest.get(url('/review'), (req, res, ctx) => {
        const id = req.url.searchParams.get('product')
        const page_size = req.url.searchParams.get('page_size')
        const page = req.url.searchParams.get('page')
        return res(
            ctx.json({
                data: {
                    pagination: {
                        page_size,
                        page,
                        total_items: reviews.length,
                    },
                    reviews: reviews.slice(page * page_size, (page + 1) * page_size),
                },
            }),
        )
    }),
    rest.get(url('/init'), (req, res, ctx) => {
        return res(
            ctx.json({
                id: 9,
                email: 'mehdi.saffar@boun.edu.tr',
                token:
                    'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OSwiZXhwIjoxNjE3MzA3MzE1LCJpYXQiOjE2MDg2NjczMTV9.urCgrUAPSk40dh9-LygnBiLpH73MqXxjbO33d3KHmO8',
                firstname: 'Mehdi',
                lastname: 'Saffar',
                is_verified: false,
                role: 'CUSTOMER',
            }),
        )
    }),
]
