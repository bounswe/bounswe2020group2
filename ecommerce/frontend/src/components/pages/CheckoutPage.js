import { CreditCardList } from '../cardlist/CreditCardList'
import { AddressList } from '../addresslist/AddressList'
import { Row, Col, Button } from 'antd'
import "./CheckoutPage.less"
import { CheckoutTermsAndConditions } from '../distant_sales_agreement/CheckoutTermsAndConditions'
import { useEffect, useState } from 'react'
import { api } from '../../api'
export const CheckoutPage = () => {

    const [selectedCard, setSelectedCard] = useState()
    const [selectedAddress, setSelectedAddress] = useState()
    const [userAgreed, setUserAgreed] = useState(false)
    const [priceSummary, setPriceSummary] = useState();

    // TODO: SHOW SPINNER ON PRICE SUMMARY & CHECKOUT
    // TODO: SHOW SUCCESSFUL ON SUCCESSFUL CHECKOUT
    // TODO: NOTIFICATION ON MISSING CARD SELECTION ETC.

    useEffect(() => {
        async function fetch() {
            try {
            const { data } = await api.get(`/checkout/details`)
            setPriceSummary(data)
            } catch (error) {
                console.error(error)
            } finally {
            }
        }
        fetch()
    }, [])

    useEffect(() => {
        console.log(selectedAddress, selectedCard, userAgreed)
    }, [selectedAddress, selectedCard, userAgreed])


    return (
        <div className="checkout-page-container">
            <div className="payment-options-container">
                <CreditCardList onSelect={setSelectedCard} />
                <AddressList onSelect={setSelectedAddress} />
                <CheckoutTermsAndConditions onChange={setUserAgreed} />
            </div>
            <div className="price-summary-container">
                <div className="price-summary-header">
                    Price summary
                </div>
                <div className="price-summary-content">
                    <Row>
                        <Col span={16}>Products:</Col>
                        <Col span={8} className="price-summary-pricecol">{priceSummary?.products_price} TL</Col>
                    </Row>
                    <Row>
                        <Col span={16}>Discount:</Col>
                        <Col span={8} className="price-summary-pricecol">-{priceSummary?.discount} TL</Col>
                    </Row>
                    <Row>
                        <Col span={16}>Delivery Fee:</Col>
                        <Col span={8} className="price-summary-pricecol">{priceSummary?.delivery_price} TL</Col>
                    </Row>
                    <Row>
                        <Col span={16}>Total:</Col>
                        <Col span={8} className="price-summary-pricecol">{priceSummary?.total_price} TL</Col>
                    </Row>
                </div>
                <div className="price-summary-button">
                    <Button type="primary" size="large" disabled={!(selectedCard&&selectedAddress&&userAgreed)}>Checkout</Button>
                </div>
            </div>
        </div>
    )
}
