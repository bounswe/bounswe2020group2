import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'
import { sleep } from '../utils'
import uuidv4 from 'uuid/dist/v4'
import { api } from '../api'

const guestUser = { type: 'guest' }
const customerUser = {
    type: 'customer',
    id: '3',
    name: 'Mehdi',
    surname: 'Saffar',
    email: 'example@gmail.com',
    role: '1',
}

function useApp() {
    const [user, setUser] = useState(guestUser)
    const [shoppingCart, setShoppingCart] = useState([])
    const [shoppingCartRefreshId, setShoppingCartRefreshId] = useState(0)

    const getShoppingCart = async () => {
        try {
            const { data } = await api.get(`/user/${user.id}/listShoppingCart`)
            const tmp = data.filter(item => {
                return item.amount > 0
            })
            setShoppingCart(tmp)
        } catch (error) {
            notification.error({ description: 'Failed to add item to shopping cart' })
            console.error(error)
        } finally {
        }
    }

    const addShoppingCartItem = async (product, amount) => {
        try {
            console.log('Add item: ', product, amount)
            const { data } = await api.post(`/user/${user.id}/shoppingCart`, {
                productId: product.id,
                amount: amount,
            })
            console.log(data)
            if (data.succesful) {
                notification.success({ description: 'Succesfully updated shopping cart' })
            } else {
                notification.error({ description: 'Failed to update shopping cart item' })
            }
            setShoppingCartRefreshId(i => i + 1)
        } catch (error) {
            notification.error({ description: 'Failed to update shopping cart item' })
            console.error(error)
        } finally {
        }
    }

    const checkoutShoppingCart = async cart => {
        try {
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
            const { data } = await api.get('/init', {
                headers: {
                    authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            })

            const { token, id, email, firstname, lastname } = data

            // TODO: get type from backend
            setUser({ id, type: 'customer', email, name: firstname, lastname })

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

    const regularLogin = async (userType, username, password) => {
        try {
            const { data } = await api.post('/regularlogin', { username, password })
            const { successful, message } = data.status
            const { token, id, email, firstname, lastname } = data.user

            if (successful) {
                localStorage.setItem('token', token)

                setUser({ id, email, name: firstname, lastname })
                setRequestInterceptorId(api.interceptors.request.use(requestInterceptor))
                notification.success({ message: `Welcome back, ${firstname}!` })

                return true
            } else {
                notification.error({ message: message })
                return false
            }
        } catch (error) {
            notification.error({ message: 'Failed to login' })
            console.error(error)
            return false
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
