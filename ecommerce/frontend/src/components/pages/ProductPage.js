import React, { useEffect, useState } from 'react'
import ReactDOM from 'react-dom'
import { Link, useRouteMatch, useParams } from 'react-router-dom'
import { Rate, Spin } from 'antd'
import { sleep } from '../../utils'
import './ProductPage.less'

export const ProductPage = () => {
    const [product, setProduct] = useState({})
    const [isLoading, setIsLoading] = useState(true)
    const { productId } = useParams()

    console.log(productId)
    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)

                // const { product } = await api.get(`/products/${productId}`)
                const tempProduct = {
                    id: 1,
                    name: 'Mavi T-shirt',
                    price: 100,
                    creation_date: '2019-08-20T07:22:34Z',
                    image_url: 'https://picsum.photos/300/300',
                    total_rating: 4,
                    rating_count: 20,
                    stock_amount: 10,
                    description: 'yaza özel',
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
            <div className="product-info-detail">
                <h1>{product.name}</h1>
                <p>
                    {' '}
                    <strong>Category: </strong>
                    {product.category}
                </p>
                <p>
                    {' '}
                    <strong>Price: </strong>
                    {product.price + ' ₺'}
                </p>
                <p>
                    {' '}
                    <strong>Subcategory: </strong>
                    {product.subcategory}
                </p>
                <p>
                    {' '}
                    <strong>Vendor: </strong>
                    {product.vendor}
                </p>
                <p>
                    <strong>Brand: </strong>
                    {product.brand}
                </p>
                <div className="rating">
                    <h3>Rating: </h3>
                    {'  ' + product.total_rating + ' (' + product.rating_count + ')'}
                </div>
                <Rate allowHalf defaultValue={product.total_rating}></Rate>
            </div>
        </div>
    )
}
