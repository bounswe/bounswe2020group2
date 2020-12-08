import './ProductPage.less'

import React, { useEffect, useState } from 'react'

import { api } from '../../api'
import { sleep } from '../../utils'
import { ProductHeader } from '../product/ProductHeader'
import { notification, Spin } from 'antd'

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
                console.log('received product data', data)
                setProduct(data[0]) // Returns as an array of items. Take the first
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
        </div>
    )
}
