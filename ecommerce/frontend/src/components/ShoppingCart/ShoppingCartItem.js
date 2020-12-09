import { Button, Col, InputNumber, Popconfirm, Row } from 'antd'
import './ShoppingCartItem.less'
import { DeleteOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import { useAppContext } from '../../context/AppContext'
import { useState } from 'react'

export const ShoppingCartItem = ({ cartItem: { amount, product } }) => {
    const [tmpAmount, setTmpAmount] = useState(amount)
    const { addShoppingCartItem } = useAppContext()

    const onClickDelete = () => {
        console.log(product, -tmpAmount)
        addShoppingCartItem(product, -tmpAmount)
    }

    const onAmountChange = value => setTmpAmount(value)

    const onPressEnterAmount = () => addShoppingCartItem(product, tmpAmount - amount)

    return (
        <div className="single-cart-item">
            <div className="cart-item-header">
                <div className="cart-item-title">
                    <Link to={`/product/${product.id}`}>{product.name ?? 'title'}</Link>
                </div>
            </div>
            <div className="cart-item-content">
                <div className="cart-item-picture">
                    <img
                        alt={product.title}
                        width={'100%'}
                        src={product.image_url ?? 'https://picsum.photos/300'}></img>
                </div>
                <div className="cart-item-description">
                    {product.description}
                    <p className="cart-item-vendor">
                        by{' '}
                        <Link to={`/vendors/${product.vendor ?? 'some_vendor_id'}`}>{product.vendor ?? 'vendor'}</Link>
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
                                defaultValue={amount}
                                onPressEnter={onPressEnterAmount}
                            />
                        </div>
                    </div>
                    <div className="cart-item-price">
                        {product.price}&nbsp;{product.currency ?? 'TL'}
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
