import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'
import { sleep } from '../utils'
import uuidv4 from 'uuid/dist/v4'

const guestUser = { type: 'guest' }
const customerUser = { type: 'customer', id: 'fldsflksd', name: 'Mehdi', surname: 'Saffar', email: 'example@gmail.com' }

function useApp() {
    const [user, setUser] = useState(customerUser)
    const [shoppingCart, setShoppingCart] = useState([])
    const [shoppingCartRefreshId, setShoppingCartRefreshId] = useState(0)

    const getShoppingCart = async () => {
        try {
            await sleep(1000)
            console.log('getting shopping cart')

            const total = 5
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

            setShoppingCart(cart)
        } catch {
            notification.error({ description: 'Failed to get shopping cart' })
        } finally {
        }
    }

    const addShoppingCartItem = async ({ product, amount }) => {
        try {
            // lfjdskjlfds
            console.log('add shopping cart item ', { product, amount })
            setShoppingCartRefreshId(i => i + 1)
        } catch {
        } finally {
        }
    }

    const checkoutShoppingCart = async cart => {
        try {
            // lfjdskjlfds
            console.log('checkout shopping cart item ', cart)
            setShoppingCartRefreshId(i => i + 1)
        } catch {
        } finally {
        }
    }

    const logout = () => {
        console.log('logout')
        notification.success({ message: 'Logout', description: `See you soon, ${user.name}!` })
        setUser(guestUser)
    }

    return {
        user,
        logout,
        setUser,
        shoppingCart,
        shoppingCartRefreshId,
        checkoutShoppingCart,
        getShoppingCart,
        addShoppingCartItem,
    }
}

export const [AppContextProvider, useAppContext] = constate(useApp)
