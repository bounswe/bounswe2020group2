import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'
import { sleep } from '../utils'
import uuidv4 from 'uuid/dist/v4'
import { api } from '../api'

const guestUser = { type: 'guest', id: '0' }
const customerUser = {
    type: 'customer',
    id: '3',
    name: 'Mehdi',
    surname: 'Saffar',
    email: 'example@gmail.com',
    role: '1',
}

function useApp() {
    const [user, setUser] = useState(customerUser)
    const [shoppingCart, setShoppingCart] = useState([])
    const [shoppingCartRefreshId, setShoppingCartRefreshId] = useState(0)

    const getShoppingCart = async () => {
        try {
            await sleep(1000)
            console.log(`/user/${user.id}/listShoppingCart`)

            const { cart } = await api.get(`/user/${user.id}/listShoppingCart/`)
            setShoppingCart(cart)
        } catch {
            notification.error({ description: 'Failed to get shopping cart' })
        } finally {
        }
    }

    const addShoppingCartItem = async (product, amount) => {
        console.log(product + ' click ' + amount)
        try {
            const { data } = await api.post(`/user/${user.id}/shoppingCart/`, {
                productId: product.id,
                amount: amount,
            })
            if (data.successful == true) {
                notification.success({ description: 'Added item to shopping cart' })
            } else {
                console.log(data)
                notification.error({ description: 'Failed to add item to shopping cart' })
            }

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

    const [requestInterceptorId, setRequestInterceptorId] = useState(null)

    const requestInterceptor = request => {
        request.headers.authorization = `Bearer ${localStorage.getItem('token')}`
        return request
    }

    const init = async () => {
        if (!localStorage.getItem('token')) {
            console.log('no token in local storage, not attempting init')
            return
        }

        try {
            const { data } = await api.post('/init', null, {
                headers: {
                    authorization: localStorage.getItem('token'),
                },
            })

            const { token, id, email, firstname, surname } = data

            setUser({ id, email, name: firstname, surname })

            setRequestInterceptorId(api.interceptors.request.use(requestInterceptor))

            notification.success({ message: `Welcome back, ${firstname}!` })
        } catch (error) {
            const status = error?.response?.status

            if (status === 401) {
                localStorage.removeItem('token')
                notification.error({ message: `Your token expired`, description: 'Please login to continue' })
                return
            }

            notification.error({ message: 'Failed to init' })
            console.error(error)
        }
    }

    const regularLogin = async ({ userType, username, password }) => {
        try {
            const { data } = await api.post('/regularlogin', { username, password })
            const { token, id, email, firstname, surname } = data

            localStorage.setItem('token', token)

            setUser({ id, email, name: firstname, surname })
            setRequestInterceptorId(api.interceptors.request.use(requestInterceptor))

            notification.success({ message: `Welcome back, ${firstname}!` })
        } catch (error) {
            notification.error({ message: 'Failed to login' })
            console.error(error)
        }
    }

    const logout = () => {
        try {
            notification.success({ description: `See you soon, ${user.name}!` })
            setUser(guestUser)
            localStorage.removeItem('token')
            api.interceptors.request.eject(requestInterceptorId)
            setRequestInterceptorId(null)
        } catch (error) {
            notification.error({ description: `Failed to logout` })
            console.error(error)
        }
    }

    return {
        user,
        init,
        regularLogin,
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
