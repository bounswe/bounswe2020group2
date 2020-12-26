import { CreditCardList } from '../cardlist/CreditCardList'
import { AddressList } from '../addresslist/AddressList'
import "./CheckoutPage.less"
import { CheckoutTermsAndConditions } from '../distant_sales_agreement/CheckoutTermsAndConditions'
import { useEffect, useState } from 'react'
export const CheckoutPage = () => {

    const [selectedCard, setSelectedCard] = useState()
    const [selectedAddress, setSelectedAddress] = useState()
    const [userAgreed, setUserAgreed] = useState(false)

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
                Price summary
            </div>
        </div>
    )
}
