import { CreditCardList } from '../cardlist/CreditCardList'
import { AddressList } from '../addresslist/AddressList'
import "./CheckoutPage.less"
export const CheckoutPage = () => {
    return (
        <div className="payment-options-container">
            <CreditCardList />
            <AddressList />
        </div>
    )
}
