import React, { Component, useEffect, useState } from 'react'
import './ShoppingCart.less'
import { Button, Spin, Statistic, Row, Col, notification } from 'antd'
import uuidv4 from 'uuid/dist/v4'
import { useAppContext } from '../../context/AppContext'
import { ShoppingCartItems } from './ShoppingCartItems'

export const ShoppingCart = ({ currency = 'TL' }) => {
    const [isLoading, setIsLoading] = useState(true)
    const [totalPrice, setTotalPrice] = useState(0)
    const [itemCount, setItemCount] = useState(0)

    const onChangeCart = newCart => {
        let priceSum = 0
        let amountSum = 0
        for (let item of newCart) {
            priceSum += item.product.price * item.amount
            amountSum += item.amount
        }
        setTotalPrice(priceSum)
        setItemCount(amountSum)
    }

    const total = 3
    const cart = [...Array(total)].map(x => {
        return {
            product: {
                title: 'Title',
                rating: '5',
                price: 30,
                currency: 'TL',
                description: 'description',
                imageUrl: `https://picsum.photos/300?q=${uuidv4()}`,
                width: 300,
                productId: uuidv4(),
            },
            amount: 1,
        }
    })

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
            } catch {
                notification.error({ description: 'Failed to get shopping cart from backend' })
            } finally {
                setIsLoading(false)
            }
            fetch(`localhost:4000/user/${'userId'}/shoppingCart`).then(res => onChangeCart(res))
        }
        // fetch()
        onChangeCart(cart)
        setIsLoading(false)
    }, [])

    const onCheckout = () => {
        if (itemCount == 0) {
            alert('Empty cart')
        } else {
            alert('Total price: ' + totalPrice)
        }
    }

    return (
        <div className="shopping-master">
            <div className="shopping-left">
                <h2>Your shopping cart</h2>
                {<ShoppingCartItems onChangeCart={onChangeCart} cart={cart}></ShoppingCartItems>}
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
