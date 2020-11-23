import './ShoppingCartPage.less'

import { Button, Col, Row, Spin, Statistic } from 'antd'
import React, { Component, useEffect, useState } from 'react'

import { useAppContext } from '../../context/AppContext'
import { ShoppingCartItems } from './ShoppingCartItems'

export const ShoppingCartPage = ({ currency = 'TL' }) => {
    const [isLoading, setIsLoading] = useState(true)
    const { shoppingCart, shoppingCartRefreshId, getShoppingCart, checkoutShoppingCart } = useAppContext()

    const totalPrice = shoppingCart.reduce((total, item) => total + item.product.price * item.amount, 0)
    const itemCount = shoppingCart.reduce((count, item) => count + item.amount, 0)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                await getShoppingCart()
            } finally {
                setIsLoading(false)
            }
        }
        console.log('refresh: ', shoppingCartRefreshId)
        fetch()
    }, [shoppingCartRefreshId])

    const onCheckout = () => {
        if (itemCount == 0) {
            alert('Empty cart')
        } else {
            alert('Total price: ' + totalPrice)
            checkoutShoppingCart(shoppingCart)
        }
    }

    return (
        <div className="shopping-master">
            <div className="shopping-left">
                <h2>Your shopping cart</h2>
                <Spin spinning={isLoading}>
                    <ShoppingCartItems cart={shoppingCart} />
                </Spin>
            </div>
            <div className="shopping-right">
                <div className="shopping-price">
                    <Row gutter={16}>
                        <Col span={12}>
                            <Spin spinning={isLoading}>
                                <Statistic title="Total Price" value={totalPrice + ' ' + currency} />
                            </Spin>
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
