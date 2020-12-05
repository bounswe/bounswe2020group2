import React from 'react'
import { Button, Rate, Card } from 'antd'
import { PlusCircleOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import './ProductCard.less'
import { useAppContext } from '../../context/AppContext'
import { InputNumber } from 'antd'

const { Meta } = Card

export const ProductCard = ({
    product = {
        id: 2,
        title:
            "Under Armour Women's Tech 2.0 PantsUnder Armour Women's Tech 2.0 PantsUnder Armour Women's Tech 2.0 PantsUnder Armour Women's Tech 2.0 PantsUnder Armour Women's Tech 2.0 PantsUnder Armour Women's Tech 2.0 Pants",
        price: 25,
        creation_date: '2018-08-20T07:21:34Z',
        imageUrl: 'https://m.media-amazon.com/images/I/71-oozU3bjL.jpg',
        rating: 3,
        rating_count: 19,
        stock_amount: 18,
        description:
            '84% Polyester 16% Elastane, UA Tech fabric is quick-drying, ultra-soft & has a more natural feel, Open hand pockets',
        subcategory: "Women's Fashion",
        category: 'Clothing',
        brand: 'Under Armour',
        vendor: 'omerfaruk deniz',
        currency: '₺',
    },
    width = 350,
}) => {
    const { addShoppingCartItem } = useAppContext()

    const onAddToCart = product => {
        addShoppingCartItem(product, 1)
    }
    const { title, rating, price, currency, imageUrl, id } = product

    return (
        <div className="whole-card" style={{ minWidth: width, minHeight: width, maxWidth: width }}>
            <div className="product-card-img-container">
                <img className="product-card-img" alt={title} src={imageUrl} />
            </div>
            <div className="card-title">
                <p>{title}</p>
            </div>
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
                    Add
                </Button>
            </div>
        </div>
    )
}
