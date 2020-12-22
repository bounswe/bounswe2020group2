export const productSortBy = {
    'best-sellers': 'Best sellers',
    'newest-arrivals': 'Newest arrivals',
    price: 'Price',
    'average-customer-review': 'Average customer review',
    'number-of-comments': 'Number of comments',
}

// export const categories = {
//     electronics: 'Electronics',
//     'health-and-households': 'Health & Households',
//     'home-and-garden': 'Home & Garden',
//     clothing: 'Clothing',
//     hobbies: 'Hobbies',
//     others: 'Others',
// }

// export const subcategories = {
//     electronics: {
//         'camera-and-photo': 'Camera & Photo',
//         'cell-phones-and-accessories': 'Cell Phones & Accessories',
//         'digital-videos': 'Digital Videos',
//         software: 'Software',
//     },
//     'health-and-households': {
//         'sports-and-outdoor': 'Sports & Outdoor',
//         'beauty-and-personal-care': 'Beauty & Personal Care',
//     },
//     'home-and-garden': {
//         luggage: 'Luggage',
//         'pet-supplies': 'Pet Supplies',
//         furniture: 'Furniture',
//     },
//     clothing: {
//         'men-fashion': "Men's Fashion",
//         'women-fashion': "Women's Fashion",
//         'boys-fashion': "Boys' Fashion",
//         'girls-fashion': "Girls' Fashion",
//         baby: 'Baby',
//     },
//     hobbies: {
//         books: 'Books',
//         'music-and-cds': 'Music & CDs',
//         'movies-and-tvs': 'Movies & TVs',
//         'toys-and-games': 'Toys & Games',
//         'video-games': 'Video Games',
//         'arts-and-crafts': 'Arts & Crafts',
//     },
//     others: {
//         automotive: 'Automotive',
//         'industrial-and-scientific': 'Industrial & Scientific',
//     },
// }

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
export function formatCreditCard({ id, name, owner_name, serial_number, expiration_date: { month, year }, cvc }) {
    return {
        name: owner_name,
        number: serial_number,
        expiry: month.toString().padStart(2, '0') + '/' + (year % 100),
        cvc: cvc,
    }
}

/**
 * Product from backend format to frontend format
 */
export function formatProduct({
    id,
    name,
    price,
    creation_date,
    total_rating,
    rating_count,
    stock_amount,
    description,
    image_url,
    subcategory,
    category,
    vendor,
    brand,
}) {
    return {
        id,
        name,
        price,
        currency: 'TL', // TODO: Backend is not returning currency so as a quick fix we set it as TL
        creationDate: creation_date,
        rating: total_rating,
        ratingCount: rating_count,
        stockAmount: stock_amount,
        description,
        imageUrl: image_url,
        subcategory,
        category,
        vendor,
        brand,
    }
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
    if (input.length > limit) {
        return input.substring(0, limit) + '...'
    }
    return input
}
