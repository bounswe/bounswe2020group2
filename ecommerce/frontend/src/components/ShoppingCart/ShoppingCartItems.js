import { ShoppingCartItem } from './ShoppingCartItem'

export const ShoppingCartItems = ({ cart = [] }) => {
    return (
        <div>
            {cart.map(cartItem => {
                return (
                    <div key={cartItem.product.productId} style={{ marginBottom: '15px' }}>
                        <ShoppingCartItem cartItem={cartItem} />
                    </div>
                )
            })}
        </div>
    )
}
