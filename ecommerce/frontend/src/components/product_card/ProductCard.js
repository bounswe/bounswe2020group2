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
        console.log(product + 'click')
        addShoppingCartItem(product, 1)
    }
    const { title, rating, price, currency, imageUrl, id } = product

    return (
        <div style={{ minWidth: width, minHeight: width, maxWidth: width }}>
            <Link to={`/products/${id}`}>
                <Card
                    style={{ width: width, minWidth: width, minHeight: width }}
                    cover={<img alt={title} src={imageUrl} />}>
                    <Meta description={price + ' ' + currency} title={title} />
                </Card>
            </Link>
            <div className="rate-and-addCart">
                <div className="left">
                    <Rate allowHalf defaultValue={rating}></Rate>
                </div>
                <div className="right">
                    <Button type="primary" icon={<PlusCircleOutlined />} onClick={() => onAddToCart(product)}>
                        Add
                    </Button>
                </div>
            </div>
        </div>
    )
}
