import './ProductPage.less'

import React, { useEffect, useState } from 'react'

import { api } from '../../api'
import { ProductCard } from '../product_card/ProductCard'
import { sleep } from '../../utils'
import { ProductHeader } from '../product/ProductHeader'
import { notification, Spin } from 'antd'
import { UserReviews } from '../UserReview/UserReviews'
import * as R from 'ramda'
import { formatProduct } from '../../utils'

export const ProductPage = props => {
    const [product, setProduct] = useState()
    const [loading, setLoading] = useState(true)
    const [bestSellers, setBestSellers] = useState([])
    const [subcategory, setSubcategory] = useState()
    const { productId } = props.match.params

    const formatSearchQueryParams = values => ({
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

    useEffect(() => {
        async function fetch() {
            try {
                setLoading(true)
                console.log('fetching product', productId)
                const { data } = await api.get(`/product/${productId}`)
                console.log(data)
                setProduct(data)
                setSubcategory(data.subcategory.name)
                const initialValues = {
                    category: data.category.id,
                    sortBy: 'best-sellers',
                    subcategory: data.subcategory.id,
                    type: 'products'
                }
                const values = {
                    search: { query: initialValues.query, type: initialValues.type },
                    filters: R.omit(['query', 'type', 'current'], initialValues),
                    pagination: {
                        pageSize: 20,
                        ...R.pick(['current', 'pageSize'], initialValues),
                    },
                }
                const {
                    data: {
                        data: { pagination },
                        products,
                    },
                } = await api.post(`/search/products`, formatSearchQueryParams(values))
                setBestSellers(products.map(formatProduct))
                await sleep(1000)
            } catch (error) {
                console.error('failed to load trending products', error)
                notification.error({ description: `Failed to get product ${productId}` })
            } finally {
                setLoading(false)
            }
        }
        fetch()
    }, [])

    return (
        <div className="product-page">
            <div className="product-page-header">
                <ProductHeader loading={loading} product={product} />
            </div>
            <div className="product-page-reviews">
                <UserReviews productId={productId} />
            </div>
            <h2 className="best-sellers-topic">Best Sellers in {subcategory}</h2>
            <div className="best-sellers">
                {bestSellers.map((
                    product,
                ) => (
                    <a key={product.id}>
                        <ProductCard product={product} />
                    </a>
                ))}
            </div>
        </div>
    )
}