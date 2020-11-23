import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { useHistory } from 'react-router'
import { createBrowserHistory } from 'history'
import { Rate } from 'antd'
import { sleep } from '../../utils'
import './ProductPage.less'
import { match } from 'ramda'

export const ProductPage = props => {
    const [product, setProduct] = useState({})
    const [isLoading, setIsLoading] = useState(true)
    const { productId } = props.match.params.productId

    console.log(productId)
    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)

                // const { product } = await api.get(`/product/${productId}`)
                const tempProduct = {
                    id: 1,
                    name: 'Mavi T-shirt',
                    price: 100,
                    creation_date: '2019-08-20T07:22:34Z',
                    image_url: 'https://picsum.photos/300/300',
                    total_rating: 4,
                    rating_count: 20,
                    stock_amount: 10,
                    description:
                        'yaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürünyaza özel çok güzel bir ürün   ',
                    subcategory: "Men's Fashion",
                    category: 'Clothing',
                    brand: 'Mavi',
                    vendor: 'omerfaruk deniz',
                }
                setProduct(tempProduct)
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
                <Link to={'/product/' + product.id}>
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
                        {product.price + ' ₺'}
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
