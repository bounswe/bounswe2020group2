import { CreditCardList } from '../cardlist/CreditCardList'
import { AddressList } from '../addresslist/AddressList'

export const CheckoutPage = () => {
    return (
        <div>
            <CreditCardList />
            <AddressList />
        </div>
    )
}
