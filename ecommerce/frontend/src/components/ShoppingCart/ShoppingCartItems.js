import { useState } from "react"
import { ShoppingCartItem } from "./ShoppingCartItem"

export const ShoppingCartItems = ({cart = [], onChangeCart = () => {}}) => {

    const [currentCart, setCurrentCart] = useState(cart)
   
    const onChangeItem = (product, amount) => {
        const newCart = amount === 0 
            ? currentCart.filter(item => item.product.productId !== product.productId) 
            : currentCart.map(item => {
                return item.product.id === product.productId 
                ? {product, amount} 
                : item
            })
        setCurrentCart(newCart)
        onChangeCart(newCart)
    }

    return (
    <div>
        {currentCart.map(item => {
            return (<ShoppingCartItem onChangeItem={onChangeItem} key={item.product.productId} cartItem={item}></ShoppingCartItem>)
        })}
    </div>
    )

}