import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { sleep } from '../../utils'
import './ProductPage.less'
import { api } from '../../api'

export const ProductPage = props => {
    const [product, setProduct] = useState({})
    const [isLoading, setIsLoading] = useState(true)
    const { productId } = props.match.params

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                const { data } = await api.get(`/product/${productId}`)
                console.log(data)
                setProduct(data[0]) // Returns as an array of items. Take the first
                await sleep(1000)
            } catch (error) {
                console.error('failed to load trending products', error)
            } finally {
                setIsLoading(false)
            }
        }
        fetch()
    }, [])

    return (
        <div className="product-info">
            <div className="product-image">
                <Link to={'/product/' + product.productId}>
                    <img src={product.image_url} className="gallery-img" alt={product.name} />
                </Link>
            </div>
            <div className="product-info-props">
                <h1>{product.name}</h1>

                <div className="product-info-description">
                    <p>{product.description}</p>
                </div>
                <div className="product-info-detail">
                    <p>
                        {' '}
                        <strong>Price: </strong>
                        {product.price ?? '' + ' ₺'}
                    </p>

                    <p>
                        <strong>Brand: </strong>
                        {product.brand}
                    </p>
                    <p>
                        {' '}
                        <strong>Category: </strong>
                        {product.category}
                    </p>

                    <p>
                        {' '}
                        <strong>Subcategory: </strong>
                        {product.subcategory}
                    </p>
                    <p>
                        <strong>Rating: </strong>
                        {'  ' + product.total_rating + ' (' + product.rating_count + ')'}
                    </p>

                    <p>
                        <strong>Stock: </strong>
                        {product.stock_amount}
                    </p>
                    <p>
                        {' '}
                        <strong>Vendor: </strong>
                        {product.vendor}
                    </p>
                    <p>
                        <strong>Creation Date: </strong>
                        {product.creation_date}
                    </p>
                </div>
            </div>
        </div>
    )
}
