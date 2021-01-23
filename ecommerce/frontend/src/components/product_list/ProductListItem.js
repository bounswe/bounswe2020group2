import { InputNumber, Popconfirm } from 'antd'
import './ShoppingCartItem.less'
import { DeleteOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import { useAppContext } from '../../context/AppContext'
import { useState } from 'react'
import { round } from '../../utils'

export const ShoppingCartItem = ({ cartItem: { id, amount, product } }) => {
    const [itemAmount, setItemAmount] = useState(amount)

    const onClickDelete = () => {
        deleteShoppingCartItem(id)
    }

    const onAmountChange = value => setItemAmount(value)

    const onPressEnterAmount = () => updateShoppingCartItem(id, product.id, itemAmount)

    return (
        <div className="product-list-item-container">
            <div className="product-list-item-header">
                <div className="product-list-item-title">
                    <Link to={`/product/${product.id}`}>{product.name ?? 'title'}</Link>
                </div>
            </div>
            <div className="product-list-item-content">
                <div className="product-list-item-picture">
                    <Link to={`/product/${product.id}`}>
                        <img
                            alt={product.name}
                            width={'100%'}
                            src={product.images[0] ?? 'https://picsum.photos/300'}></img>
                    </Link>
                </div>
                <div className="product-list-item-description">
                    {product.short_description}
                    <p className="product-list-item-vendor">
                        by{' '}
                        <Link to={`/vendors/${product.vendor?.id ?? 'some_vendor_id'}`}>
                            {product.vendor?.name ?? 'getflix'}
                        </Link>
                    </p>
                </div>
                <div className="product-list-item-controls">
                    <div className="product-list-item-amount">
                        <div className="product-list-item-amount-text">Amount:</div>
                        <div className="product-list-item-amount-input">
                            <InputNumber
                                min={1}
                                onChange={onAmountChange}
                                className="product-list-item-amount-counter"
                                defaultValue={itemAmount}
                                onPressEnter={onPressEnterAmount}
                            />
                        </div>
                    </div>
                    <div className="product-list-item-price">
                        {round(product.price_after_discount, 2)}&nbsp;{product.currency ?? 'TL'}
                    </div>
                    <div className="product-list-item-delete">
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
