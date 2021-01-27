import './ShoppingCartPage.less'

import { Button, Col, Row, Spin, Statistic } from 'antd'
import React, { useEffect, useState } from 'react'

import { useAppContext } from '../../context/AppContext'
import { ShoppingCartItems } from '../ShoppingCart/ShoppingCartItems'
import { round, formatPrice } from '../../utils'
import { Link } from 'react-router-dom'

export const ShoppingCartPage = ({ currency = 'TL' }) => {
    const [isLoading, setIsLoading] = useState(true)
    const { shoppingCart, shoppingCartRefreshId, getShoppingCart, checkoutShoppingCart, user } = useAppContext()
    const totalPrice = shoppingCart.reduce((total, item) => total + item.product.price_after_discount * item.amount, 0)
    const itemCount = shoppingCart.length

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                await getShoppingCart()
            } finally {
                setIsLoading(false)
            }
        }

        if (user.type === 'customer') {
            console.log('refresh: ', shoppingCartRefreshId)
            fetch()
        }
    }, [shoppingCartRefreshId, user.id])

    return (
        <div className="shopping-master">
            <div className="shopping-left">
                <h2>Your shopping cart ({itemCount})</h2>
                <Spin spinning={isLoading}>
                    <ShoppingCartItems cart={shoppingCart} />
                </Spin>
            </div>
            <div className="shopping-right">
                <div className="shopping-price">
                    <Row gutter={16}>
                        <Col span={12}>
                            <Spin spinning={isLoading}>
                                <Statistic
                                    title="Total Price"
                                    value={formatPrice(round(totalPrice, 2)) + ' ' + currency}
                                />
                            </Spin>
                        </Col>
                    </Row>
                </div>
                <div className="shopping-proceed-button">
                    <Button type="primary" block disabled={shoppingCart?.length === 0}>
                        <Link to="/checkout">Proceed to payment</Link>
                    </Button>
                </div>
            </div>
        </div>
    )
}
