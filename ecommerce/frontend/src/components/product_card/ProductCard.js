import React from 'react'
import { Button, Rate, Card } from 'antd'
import { PlusCircleOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import './ProductCard.less'

const { Meta } = Card

export const ProductCard = ({ product, onAddToCart }) => {
    const { title, rating, width = 350, price, currency, imageUrl, productId } = product

    return (
        <Link to={`/products/${productId}`}>
            <Card style={{ width: width }} cover={<img alt={title} src={imageUrl} />}>
                <Meta description={price + ' ' + currency} title={title} />
                <div className="rate-and-addCart">
                    <div className="left">
                        <Rate allowHalf defaultValue={rating}></Rate>
                    </div>
                    <div className="right">
                        <Button type="primary" icon={<PlusCircleOutlined />} onClick={() => onAddToCart(productId)}>
                            Add to Cart
                        </Button>
                    </div>
                </div>
            </Card>
        </Link>
    )
}
