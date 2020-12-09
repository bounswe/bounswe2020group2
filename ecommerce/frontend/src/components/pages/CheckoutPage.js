import { CreditCardList } from '../checkout/cardlist/CreditCardList'
import { AddressList } from '../checkout/addresslist/AddressList'

export const CheckoutPage = () => {
    return <div>
        <CreditCardList />
        <AddressList />
    </div>
}
