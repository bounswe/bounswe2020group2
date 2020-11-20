import React, { Component, useState } from 'react'
import './ShoppingCartMaster.less'
import { Button } from 'antd'
import { Statistic, Row, Col } from 'antd'

export const ShoppingCart = ({ currency = 'TL', price = 5, cart }) => {
    const [totalPrice, setTotalPrice] = useState(price)
    const [itemCount, setItemCount] = useState(0)

    // When there is any change in the items, call this method with new total price and new item count
    const onChangeCart = (newPrice, newCount) => {
        setTotalPrice(newPrice)
        setItemCount(newCount)
    }

    // husky test change
    const tempProduct = {
        id: 1,
        title: 'Product Title',
        // etc
    }
    const tempCart = [
        {
            product: tempProduct,
            amount: 5,
        },
        {
            product: tempProduct,
            amount: 3,
        },
    ]

    const onCheckout = () => {
        if (itemCount == 0) {
            alert('Empty cart')
        } else {
        }
    }

    return (
        <div className="shopping-master">
            <div className="shopping-left">
                {/* Master will create left side, passing current items as props: 
                1) onChangeCart function : Any operation like changing amounts, deleting item will trigger this method.
                2) cart object : consisting of items and their amounts
                <ShoppingCartItems onChangeCart={onChangeCart} cart={tempCart}></ShoppingCartItems>

                */}

                <p>Left part will come here</p>
            </div>
            <div className="shopping-right">
                <div className="shopping-price">
                    <Row gutter={16}>
                        <Col span={12}>
                            <Statistic title="Total Price" value={totalPrice + ' ' + currency} />
                        </Col>
                    </Row>
                </div>
                <div className="shopping-proceed-button">
                    <Button type="primary" onClick={onCheckout} block>
                        Proceed to payment
                    </Button>
                </div>
            </div>
        </div>
    )
}
