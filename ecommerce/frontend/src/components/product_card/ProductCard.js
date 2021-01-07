import React from 'react'
import { Button, Rate } from 'antd'
import { PlusCircleOutlined, HeartOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import './ProductCard.less'
import { useAppContext } from '../../context/AppContext'
import { round, truncate } from '../../utils'

export const ProductCard = ({ product, width = 350 }) => {
    const { addShoppingCartItem } = useAppContext()

    const onAddToCart = product => {
        addShoppingCartItem(product, 1)
    }

    const onAddToList = product => {}

    const { title, rating, price, price_after_discount, currency = '₺', images, id } = product

    // const discountPrice = price * (1 - discount)
    return (
        <div className="whole-card" style={{ minWidth: width, minHeight: width, maxWidth: width }}>
            <Link to={`/product/${id}`}>
                <div className="product-card-img-container">
                    <img className="product-card-img" alt={title} src={images[0]} />
                </div>
                <div className="card-title">
                    <p>{truncate(title)}</p>
                </div>
            </Link>
            <div className="rate-and-price">
                <div className="card-rate">
                    <Rate disabled allowHalf defaultValue={rating}></Rate>
                </div>
                {round(price, 1) !== round(price_after_discount, 1) ? (
                    <div className="card-old-price">{round(price, 1) + ' ' + currency}</div>
                ) : null}
                <div className="card-new-price">{round(price_after_discount, 1) + ' ' + currency}</div>
            </div>
            <div className="card-add-button">
                <Button
                    size="large"
                    type="primary"
                    icon={<PlusCircleOutlined />}
                    onClick={() => onAddToCart(product)}
                    block>
                    Add to cart
                </Button>
                <Button size="large" type="ghost" icon={<HeartOutlined />} onClick={() => onAddToList(product)} />
            </div>
        </div>
    )
}
