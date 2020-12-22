import { Button, Col, InputNumber, Popconfirm, Row } from 'antd'
import './ShoppingCartItem.less'
import { DeleteOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import { useAppContext } from '../../context/AppContext'
import { useState } from 'react'
import { round } from '../../utils'

export const ShoppingCartItem = ({ cartItem: { id, amount, product } }) => {
    const [itemAmount, setItemAmount] = useState(amount)
    const { addShoppingCartItem, deleteShoppingCartItem, updateShoppingCartItem } = useAppContext()

    const onClickDelete = () => {
        deleteShoppingCartItem(id)
    }

    const onAmountChange = value => setItemAmount(value)

    const onPressEnterAmount = () => updateShoppingCartItem(id, product.id, itemAmount)

    return (
        <div className="single-cart-item">
            <div className="cart-item-header">
                <div className="cart-item-title">
                    <Link to={`/product/${product.id}`}>{product.name ?? 'title'}</Link>
                </div>
            </div>
            <div className="cart-item-content">
                <div className="cart-item-picture">
                    <img alt={product.name} width={'100%'} src={product.images[0] ?? 'https://picsum.photos/300'}></img>
                </div>
                <div className="cart-item-description">
                    {product.short_description}
                    <p className="cart-item-vendor">
                        by{' '}
                        <Link to={`/vendors/${product.vendor?.name ?? 'some_vendor_id'}`}>
                            {product.vendor?.name ?? 'getflix'}
                        </Link>
                    </p>
                </div>
                <div className="cart-item-controls">
                    <div className="cart-item-amount">
                        <div className="cart-item-amount-text">Amount:</div>
                        <div className="cart-item-amount-input">
                            <InputNumber
                                min={1}
                                onChange={onAmountChange}
                                className="amount-counter"
                                defaultValue={itemAmount}
                                onPressEnter={onPressEnterAmount}
                            />
                        </div>
                    </div>
                    <div className="cart-item-price">
                        {round(product.price_after_discount, 2)}&nbsp;{product.currency ?? 'TL'}
                    </div>
                    <div className="cart-item-delete">
                        <Popconfirm
                            title="Are you sure to delete this product from your cart?"
                            onConfirm={onClickDelete}
                            okText="Yes"
                            cancelText="No">
                            <DeleteOutlined />
                        </Popconfirm>
                    </div>
                </div>
            </div>
        </div>
    )
}
