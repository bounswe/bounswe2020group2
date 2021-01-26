import './ProductPage.less'

import React, { useEffect, useState } from 'react'

import { api } from '../../api'
import { ProductHeader } from '../product/ProductHeader'
import { notification, Spin } from 'antd'
import { UserReviews } from '../UserReview/UserReviews'
import { HorizontalProductList } from '../HorizontalProductList'
import { formatProduct } from '../../utils'

export const ProductPage = props => {
    const [product, setProduct] = useState()
    const [loading, setLoading] = useState(true)
    const { productId } = props.match.params

    useEffect(() => {
        async function fetch() {
            try {
                setLoading(true)
                console.log('fetching product', productId)
                const { data } = await api.get(`/product/${productId}`)
                setProduct(formatProduct(data))
            } catch (error) {
                notification.error({ description: `Failed to get product ${productId}` })
            } finally {
                setLoading(false)
            }
        }
        fetch()
    }, [productId])

    const filters = {
        category: product?.category.id,
        sortBy: 'best-sellers',
        subcategory: product?.subcategory.id,
        type: 'products',
    }

    return (
        <div className="product-page">
            <div className="product-page-header">
                <ProductHeader loading={loading} product={product} />
            </div>
            <div className="product-page-reviews">
                <UserReviews productId={productId} />
            </div>
            <div className="product-page-best-sellers">
                {product && <HorizontalProductList key={productId} filters={filters} />}
            </div>
        </div>
    )
}
