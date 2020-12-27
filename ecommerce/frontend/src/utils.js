import { config } from './config'
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

export function formatOrderStatus(status) {
    const statusMapping = {
        cancelled: 'Cancelled',
        accepted: 'In progress',
        at_cargo: 'At cargo',
        delivered: 'Delivered',
    }
    return statusMapping[orderStatusInvMap[status]]
}

export function formatOrder(order) {
    return {
        id: order.order_id,
        purchases: order.order_all_purchase.map(formatPurchase),
        ...R.omit(['order_id', 'purchases'], order),
    } // temporary solution
}
