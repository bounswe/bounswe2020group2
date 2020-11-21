import { useState } from "react"
import { ShoppingCartItem } from "./ShoppingCartItem"
import uuidv4 from 'uuid/dist/v4'

export const ShoppingCartItems = ({cart = [], onChangeCart = () => {}}) => {
    const tempcart = [...Array(5)].map(x => {
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
    const [currentCart, setCurrentCart] = useState(tempcart)

    const onChangeItem = (product, amount) => {
        const newCart = amount === 0 
            ? tempcart.filter(item => item.product.productId !== product.productId) 
            : tempcart.map(item => {
                return item.product.id === product.productId 
                ? {product, amount} 
                : item
            })
        console.log(newCart)
        setCurrentCart(newCart)
        
        console.log(currentCart)
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