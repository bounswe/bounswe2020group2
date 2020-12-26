import './CheckoutPage.less';

import { SmileOutlined } from '@ant-design/icons';
import { Alert, Button, Col, notification, Result, Row, Spin } from 'antd';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import { api } from '../../api';
import { AddressList } from '../addresslist/AddressList';
import { CreditCardList } from '../cardlist/CreditCardList';
import { CheckoutTermsAndConditions } from '../distant_sales_agreement/CheckoutTermsAndConditions';

export const CheckoutPage = () => {
    const [selectedCard, setSelectedCard] = useState()
    const [selectedAddress, setSelectedAddress] = useState()
    const [userAgreed, setUserAgreed] = useState(false)
    const [priceSummary, setPriceSummary] = useState()
    const [isPriceSummaryLoading, setIsPriceSummaryLoading] = useState(false)
    const [isCheckoutLoading, setIsCheckoutLoading] = useState(false)
    const [isCheckoutSuccessful, setIsCheckoutSuccessful] = useState(false)

    useEffect(() => {
        async function fetch() {
            setIsPriceSummaryLoading(true)
            try {
                const { data } = await api.get(`/checkout/details`)
                setPriceSummary(data)
            } catch (error) {
                console.error(error)
            } finally {
                setIsPriceSummaryLoading(false)
            }
        }
        fetch()
    }, [])

    const onCheckout = async () => {
        setIsCheckoutLoading(true)
        try {
            const {
                data: { status },
            } = await api.post(`/checkout/payment`, {
                address_id: selectedAddress,
                card_id: selectedCard,
            })
            if (status.successful) {
                setIsCheckoutSuccessful(true)
            } else {
                notification.warning({ message: status.message })
            }
        } catch (error) {
            console.error(error)
        } finally {
            setIsCheckoutLoading(false)
        }
    }

    return (
        <Spin spinning={isCheckoutLoading}>
            {!isCheckoutSuccessful ? (
                <div className="checkout-page-container">
                    <div className="payment-options-container">
                        <CreditCardList onSelect={setSelectedCard} />
                        <AddressList onSelect={setSelectedAddress} />
                        <CheckoutTermsAndConditions onChange={setUserAgreed} />
                    </div>

                    <Spin spinning={isPriceSummaryLoading}>
                        <div className="price-summary-container">
                            <div className="price-summary-header">Price summary</div>
                            <div className="price-summary-content">
                                <Row>
                                    <Col span={16}>Products:</Col>
                                    <Col span={8} className="price-summary-pricecol">
                                        {priceSummary?.products_price} TL
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span={16}>Discount:</Col>
                                    <Col span={8} className="price-summary-pricecol">
                                        -{priceSummary?.discount} TL
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span={16}>Delivery Fee:</Col>
                                    <Col span={8} className="price-summary-pricecol">
                                        {priceSummary?.delivery_price} TL
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span={16}>Total:</Col>
                                    <Col span={8} className="price-summary-pricecol">
                                        {priceSummary?.total_price} TL
                                    </Col>
                                </Row>
                            </div>
                            {!selectedCard ? <Alert message="You need to select a credit card!"></Alert> : null}
                            {!selectedAddress ? <Alert message="You need to select an address!"></Alert> : null}
                            {!userAgreed && <Alert message="You need to agree to the terms and conditions!"></Alert>}
                            <div className="price-summary-button">
                                <Button
                                    type="primary"
                                    onClick={onCheckout}
                                    size="large"
                                    disabled={!(selectedCard && selectedAddress && userAgreed)}>
                                    Checkout
                                </Button>
                            </div>
                        </div>
                    </Spin>
                </div>
            ) : (
                <Result
                    icon={<SmileOutlined />}
                    title='You have successfully made an order! You can check the status and the delivery info in "Past Orders" section of your account!'
                    extra={
                        <Button type="primary">
                            <Link to="/">Go back to shopping!</Link>
                        </Button>
                    }
                />
            )}
        </Spin>
    )
}
