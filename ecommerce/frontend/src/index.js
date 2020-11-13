import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { ProductCard } from './components/product_card/ProductCard'
import { Card, Avatar } from 'antd'

const { Meta } = Card
const products = [
    {
        title: 'Yellow Flowers',
        price: '14.99',
        currency: '₺',
        productId: 1,
        rating: 0.5,
        imageUrl:
            'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS9I14rV_Z52uoBWHbruHsl84HI13mv66EW8A&usqp=CAU',
    },
    {
        title: 'Blue Toy Car',
        price: '23.99',
        currency: '€',
        productId: 2,
        rating: 5,
        width: 400,
        imageUrl: 'https://i.ebayimg.com/images/g/2GgAAOSw~PVcpcby/s-l400.jpg',
    },
]

ReactDOM.render(
    <div style={{ display: 'flex' }}>
        {products.map(product => (
            <ProductCard
                product={product}
                onAddToCart={productId => {
                    console.log('Card clicked! ' + productId)
                }}></ProductCard>
        ))}
    </div>,
    document.getElementById('root'),
)
reportWebVitals()
