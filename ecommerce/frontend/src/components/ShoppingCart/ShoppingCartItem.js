import { Col, InputNumber, Popconfirm, Row } from 'antd'
import { useState } from 'react'
import "./ShoppingCartItem.less"
import { DeleteOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'

export const ShoppingCartItem = ({cartItem = {}, onChangeItem = () => {}}) => {
    const [product, setProduct] = useState(cartItem?.product)

    const [amount, setAmount] = useState(cartItem?.amount)
    


    const onClickDelete = () => {
        setAmount(0);
        onChangeItem(product, 0)
    }

    const onAmountChange = (value) => {
        setAmount(value);
        onChangeItem(product, value)
    }
    

    const AmountCounter = () => {
        return (<InputNumber min={1} onChange={onAmountChange} className="amount-counter" defaultValue={amount}></InputNumber>)
    }

    return (
        <>
            <div className="single-cart-item">
                <Row gutter={16} className="cart-item-header">
                    <Col className="cart-item-title" span={24}>{product.title}</Col>
                </Row>
                <Row gutter={16} className="cart-item-content">
                    <Col span={5} className="cart-item-picture">
                        <img alt={product.title} width={"100%"} src={product.imageUrl}></img>
                    </Col>
                    <Col span={9} className="cart-item-description">
                        {product.description}
                        <p className="cart-item-vendor">
                            by <Link to={`/vendor`}> vendor</Link>
                        </p>
                    </Col>
                    <Col span={2} className="cart-item-amount-text">Amount:</Col>
                    <Col span={3} className="cart-item-amount-input">
                        <AmountCounter />
                    </Col>
                    <Col span={3} className="cart-item-price">{product.price}&nbsp;{product.currency}</Col>
                    <Col span={2} className="cart-item-delete">
                    <Popconfirm
                        title="Are you sure to delete this product from your cart?"
                        onConfirm={onClickDelete}
                        onCancel={() => {}}
                        okText="Yes"
                        cancelText="No"
                    >
                        <DeleteOutlined />
                    </Popconfirm>
                    </Col>
                </Row>
            </div>
        </>
    )


}