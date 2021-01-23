import { config } from '../config'
import { rest } from 'msw'
import {
    trendingProducts,
    product,
    products,
    reviews,
    categories,
    addresses,
    cards,
    verifications,
    accounts,
    conversations,
    vendorOrders,
    productLists,
} from './mocks'
import { orderStatusMap } from '../utils'
import * as moment from 'moment'

// preprend config.apiUrl
const url = u => config.apiUrl + u

// DOCS: https://mswjs.io/docs/
// request: Information about the captured request.
// response: Function to create the mocked response.
// context: Context utilities specific to the current request handler.

// comment the handler when done with it, DO NOT REMOVE IT

export const handlers = [
    // url(...) is important here !!
    // rest.get(url('/example/user/:userId'), (req, res, ctx) => {
    //     const { params, body } = req
    //     const { userId } = params
    //     if (!userId) return res(ctx.status(403), ctx.json({ successful: false, message: `Bad request` }))
    //     return res(ctx.json({ successful: true, user: { id: userId, name: 'Ali', surname: 'Batır' } }))
    // }),
    // rest.get(url('/products/homepage/:count'), (req, res, ctx) => {
    //     const { params, body } = req
    //     const { count } = params
    //     return res(ctx.json({ successful: true, products: trendingProducts.slice(0, count) }))
    // }),
    // rest.get(url('/product/:productId'), (req, res, ctx) => {
    //     const { params, body } = req
    //     const { productId } = params
    //     return res(ctx.json(product))
    // }),
    // rest.post(url('/search/products'), (req, res, ctx) => {
    //     const { params, body } = req
    //     const { page_size = 10, page = 0 } = body
    //     return res(
    //         ctx.json({
    //             data: {
    //                 pagination: {
    //                     page_size,
    //                     page,
    //                     total_items: products.length,
    //                 },
    //                 products: products.slice(page * page_size, (page + 1) * page_size),
    //             },
    //         }),
    //     )
    // }),
    // rest.get(url('/email-verify/:token'), (req, res, ctx) => {
    //     const { params, body } = req
    //     const { token } = params
    //     return res(ctx.json({ data: { message: verifications[Math.floor(Math.random() * verifications.length)] } }))
    // }),
    // rest.get(url('/review'), (req, res, ctx) => {
    //     const id = req.url.searchParams.get('product')
    //     const page_size = req.url.searchParams.get('page_size')
    //     const page = req.url.searchParams.get('page')
    //     return res(
    //         ctx.json({
    //             data: {
    //                 pagination: {
    //                     page_size,
    //                     page,
    //                     total_items: reviews.length,
    //                 },
    //                 reviews: reviews.slice(page * page_size, (page + 1) * page_size),
    //             },
    //         }),
    //     )
    // }),
    // rest.get(url('/review'), (req, res, ctx) => {
    //     const id = req.url.searchParams.get('product')
    //     const page_size = req.url.searchParams.get('page_size')
    //     const page = req.url.searchParams.get('page')
    //     return res(
    //         ctx.json({
    //             data: {
    //                 pagination: {
    //                     page_size,
    //                     page,
    //                     total_items: reviews.length,
    //                 },
    //                 reviews: reviews.slice(page * page_size, (page + 1) * page_size),
    //             },
    //         }),
    //     )
    // }),
    rest.get(url('/init'), (req, res, ctx) => {
        return res(ctx.json(accounts.ozdenz))
    }),
    rest.get(url('/categories'), (req, res, ctx) => {
        return res(ctx.json({ categories }))
    }),
    // rest.get(url('/customer/:userId/addresses'), (req, res, ctx) => {
    //     return res(ctx.json({ status, addresses }))
    // }),
    // rest.get(url('/customer/:userId/cards'), (req, res, ctx) => {
    //     return res(ctx.json({ status, cards }))
    // }),
    // rest.get(url('/customer/orders'), (req, res, ctx) => {
    //     const order = {
    //         order_id: 0,
    //         order_all_purchase: [
    //             {
    //                 id: 1,
    //                 amount: 2,
    //                 product: products[0],
    //                 status: 'accepted',
    //                 unit_price: 10,
    //                 purchase_date: moment.utc().toISOString(),
    //                 vendor: { id: 2, name: 'Can Batuk İletişim', rating: 3 },
    //                 address: addresses[0],
    //             },
    //             {
    //                 id: 2,
    //                 amount: 4,
    //                 product: products[2],
    //                 status: 'at_cargo',
    //                 unit_price: 40,
    //                 purchase_date: moment.utc().toISOString(),
    //                 vendor: { id: 2, name: 'Can Batuk İletişim', rating: 3 },
    //                 address: addresses[1],
    //             },
    //         ],
    //     }
    //     return res(
    //         ctx.json({
    //             status: { successful: true, message: '' },
    //             orders: [order, { ...order, id: 100 }],
    //         }),
    //     )
    // }),
    // rest.post(url('/review'), (req, res, ctx) => {
    //     return res(ctx.json({}))
    // }),
    // rest.get(url('/customer/:userId/addresses'), (req, res, ctx) => {
    //     return res(ctx.json({ status, addresses }))
    // }),
    // rest.get(url('/customer/:userId/cards'), (req, res, ctx) => {
    //     return res(ctx.json({ status, cards }))
    // }),
    // rest.get(url('/messages'), (req, res, ctx) => {
    //     return res(
    //         ctx.json({
    //             conversations,
    //             status: { successful: true, messages: null },
    //         }),
    //     )
    // }),
    // rest.post(url('/messages'), (req, res, ctx) => {
    //     console.log(req.body)
    //     return res(ctx.json({}))
    // }),
    // rest.get(url('/vendor/order'), (req, res, ctx) => {
    //     return res(ctx.json({ status: { successful: true, message: '' }, orders: vendorOrders }))
    // }),
    // rest.get(url('/lists'), (req, res, ctx) => {
    //     return res(ctx.json({ status: { successful: true, message: '' }, lists: productLists }))
    // }),
]

if (process.env.NODE_ENV === 'development') {
    console.warn(`FYI: THERE ARE ${handlers.length} ACTIVE MOCK HANDLERS!`)
}
