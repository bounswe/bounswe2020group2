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
        console.log(product, -amount)
        addShoppingCartItem(product, -amount)
    }

    const onAmountChange = value => setTmpAmount(value)

    const onPressEnterAmount = () => addShoppingCartItem(product, tmpAmount - amount)

    return (
        <div className="single-cart-item">
            <Row gutter={16} className="cart-item-header">
                <Col className="cart-item-title" span={24}>
                    <Link to={`/products/${product.productId}`}>{product.title}</Link>
                </Col>
            </Row>
            <Row gutter={16} className="cart-item-content">
                <Col span={5} className="cart-item-picture">
                    <img alt={product.title} width={'100%'} src={product.imageUrl}></img>
                </Col>
                <Col span={9} className="cart-item-description">
                    {product.description}
                    <p className="cart-item-vendor">
                        by{' '}
                        <Link to={`/vendors/${product.vendor ?? 'some_vendor_id'}`}>{product.vendor ?? 'vendor'}</Link>
                    </p>
                </Col>
                <Col span={2} className="cart-item-amount-text">
                    Amount:
                </Col>
                <Col span={3} className="cart-item-amount-input">
                    <InputNumber
                        min={1}
                        onChange={onAmountChange}
                        className="amount-counter"
                        defaultValue={amount}
                        onPressEnter={onPressEnterAmount}
                    />
                </Col>
                <Col span={3} className="cart-item-price">
                    {product.price}&nbsp;{product.currency}
                </Col>
                <Col span={2} className="cart-item-delete">
                    <Popconfirm
                        title="Are you sure to delete this product from your cart?"
                        onConfirm={onClickDelete}
                        okText="Yes"
                        cancelText="No">
                        <DeleteOutlined />
                    </Popconfirm>
                </Col>
            </Row>
        </div>
    )
}
