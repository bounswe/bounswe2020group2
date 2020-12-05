import React from 'react'
import { Button, Rate, Card } from 'antd'
import { PlusCircleOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import './ProductCard.less'
import { useAppContext } from '../../context/AppContext'

const { Meta } = Card

export const ProductCard = ({ product, width = 350 }) => {
    const { addShoppingCartItem } = useAppContext()

    const onAddToCart = product => {
        addShoppingCartItem(product, 1)
    }
    const { title, rating, price, currency, imageUrl, id } = product

    return (
        <div className="whole-card" style={{ minWidth: width, minHeight: width, maxWidth: width }}>
            <Link to={`/product/${id}`}>
                <div className="product-card-img-container">
                    <img className="product-card-img" alt={title} src={imageUrl} />
                </div>
                <div className="card-title">
                    <p>{title}</p>
                </div>
            </Link>
            <div className="rate-and-price">
                <div className="card-rate">
                    <Rate disabled allowHalf defaultValue={rating}></Rate>
                </div>
                <div className="card-price">
                    <p>{price + ' ' + currency} </p>
                </div>
            </div>
            <div className="card-add-button">
                <Button type="primary" icon={<PlusCircleOutlined />} onClick={() => onAddToCart(product)}>
                    Add to cart
                </Button>
            </div>
        </div>
    )
}
