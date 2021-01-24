import { config } from './config'
import moment from 'moment'
import * as R from 'ramda'

export const productSortBy = {
    'best-sellers': 'Best sellers',
    'newest-arrivals': 'Newest arrivals',
    price: 'Price',
    'average-customer-review': 'Average customer review',
    'number-of-comments': 'Number of comments',
}

// TODO: stupid names of vendors, wont be used, only for testing
export const vendors = {
    mavi: 'Mavi',
    hi5: 'Hi5',
    mosaic: 'Mosaic',
}

// TODO: stupid names of brands, wont be used, only for testing
export const brands = {
    adidas: 'Adidas',
    nike: 'Nike',
    polar: 'Polar',
}

export function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms))
}

/**
 * Data formatter
 * A single credit card info from backend -> props of Cards component from react-credit-cards
 */
export function formatCreditCard({ id, name, owner_name, serial_number, expiration_date: { month, year }, cvv }) {
    return {
        name: owner_name,
        number: serial_number,
        expiry: month.toString().padStart(2, '0') + '/' + (year % 100),
        cvc: cvv,
    }
}

/**
 * Product from backend format to frontend format
 */
export function formatProduct({
    id,
    name,
    price,
    price_after_discount,
    discount,
    creation_date,
    total_rating,
    rating_count,
    stock_amount,
    short_description,
    long_description,
    images,
    subcategory,
    category,
    vendor,
    brand,
}) {
    return {
        id,
        title: name,
        price,
        price_after_discount,
        discount,
        currency: 'TL', // TODO: Backend is not returning currency so as a quick fix we set it as TL
        creation_date,
        rating: total_rating,
        rating_count,
        stock_amount,
        short_description,
        long_description,
        images: (images ?? []).map(formatImageUrl),
        subcategory,
        category,
        vendor,
        brand,
    }
}

export function formatImageUrl(imageUrl) {
    if (imageUrl.startsWith('/image/')) return config.apiUrl + imageUrl
    return imageUrl
}

/**
 * Round half away from zero ('commercial' rounding)
 * Uses correction to offset floating-point inaccuracies.
 * Works symmetrically for positive and negative numbers.
 */
export function round(num, decimalPlaces = 2) {
    var p = Math.pow(10, decimalPlaces)
    var m = num * p * (1 + Number.EPSILON)
    return Math.round(m) / p
}

export function truncate(input, limit = 50) {
    if (input.length > limit) return input.substring(0, limit) + '...'
    return input
}

export const orderStatusMap = {
    cancelled: -1,
    accepted: 0,
    at_cargo: 1,
    delivered: 2,
}

export const orderStatusInvMap = {
    '-1': 'cancelled',
    0: 'accepted',
    1: 'at_cargo',
    2: 'delivered',
}

export function formatPurchase(purchase) {
    return {
        ...purchase,
        product: formatProduct(purchase.product),
        status: orderStatusMap[purchase.status],
    }
}

export const orderStatusDisplayMapping = {
    cancelled: 'Cancelled',
    accepted: 'In progress',
    at_cargo: 'At cargo',
    delivered: 'Delivered',
}

export function formatOrderStatus(status) {
    return orderStatusDisplayMapping[orderStatusInvMap[status]]
}

export function formatOrder(order) {
    return {
        id: order.order_id,
        purchases: order.order_all_purchase.map(formatPurchase),
        ...R.omit(['order_id', 'purchases'], order),
    } // temporary solution
}

export const formatSearchQueryParams = values => ({
    query: values.search?.query,
    // query: this can be undefined it means doesn't matter
    // query: for now, case-insensitive search in title or description

    category: values.filters?.category,
    // category: if missing, assume all products

    subcategory: values.filters?.subcategory,
    // subcategory: if missing, assume all subcategories of the category
    // subcategory: subcategory cannot be given without specifying category

    brand: values.filters?.brands,
    // brand: OR semantic
    // brand: these are brand ids I get from the database
    // brand: if brand is undefined or empty array then consider it as any brand

    max_price: values.filters?.maxPrice,
    // max_price: if missing, assume +infinity

    min_rating: values.filters?.rating,
    // min_rating: if missing, assume 0
    // min_rating: min 0, max 5
    // min_rating: >= semantic

    // == sorting ==
    sort_by: values.filters?.sortBy,
    // sort_by: if missing, assume 'best-sellers'

    sort_order: 'increasing',
    // sort_order: if missing, assume 'increasing'
    // sort_order: decreasing best-sellers -> best sellers shown first
    // sort_order: decreasing newest-arrivals -> newest arrivals shown first
    // sort_order: increasing price -> low price products first
    // sort_order: increasing average-customer-review -> low review first
    // sort_order: increasing number-of-comments -> low comments first

    // == pagination ==
    page: values.pagination?.current,
    // page: pages start from 0
    // page: if missing, assume 0

    page_size: values.pagination?.pageSize,
    // page_size: smallest page_size should 1, biggest should be 100
    // page_size: if missing, assume 10
})

export const formatMessage = obj => {
    return {
        ...obj,
        date: moment.utc(obj.date),
        attachment_url: obj.attachment_url ? formatImageUrl(obj.attachment_url) : null,
    }
}

export const formatConversation = obj => {
    return { ...obj, messages: obj?.messages.map(formatMessage) }
}

export const getRetroAvatarUrl = id => {
    return `http://www.gravatar.com/avatar/${id}?s=64&d=retro&r=PG`
}

export const formatList = ({ list_id, name, products }) => {
    return {
        id: list_id,
        name,
        products: products.map(entry => ({ ...entry, product: formatProduct(entry.product) })),
    }
}

export const getVendorRatingLevel = rating => {
    if (rating <= 5.0) return 'low'
    if (rating <= 8.0) return 'medium'
    return 'high'
}
