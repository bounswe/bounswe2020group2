import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'
import { sleep } from '../utils'
import uuidv4 from 'uuid/dist/v4'
import { api } from '../api'
import { Redirect } from 'react-router'

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
            const {
                data: { sc_items },
            } = await api.get(`/customer/${user.id}/shoppingcart`)
            setShoppingCart(sc_items)
        } catch (error) {
            notification.error({ description: 'Failed to get shopping cart' })
            console.error(error)
        } finally {
        }
    }
    const deleteShoppingCartItem = async sc_item_id => {
        try {
            const {
                data: {
                    status: { successful, message },
                },
            } = await api.delete(`/customer/${user.id}/shoppingcart/${sc_item_id}`)
            if (successful) {
                notification.success({ description: 'Succesfully deleted item from shopping cart' })
            } else {
                notification.error({ description: 'Failed to delete item from shopping cart' })
                console.error(message)
            }
            setShoppingCartRefreshId(i => i + 1)
        } catch (error) {
            notification.error({ description: 'Failed to delete item from shopping cart' })
            console.error(error)
        } finally {
        }
    }
    const addShoppingCartItem = async (product, amount) => {
        try {
            const {
                data: {
                    status: { successful, message },
                },
            } = await api.post(`/customer/${user.id}/shoppingcart`, {
                product_id: product.id,
                amount: amount,
            })
            if (successful) {
                notification.success({ description: 'Succesfully added item to shopping cart' })
            } else {
                notification.error({ description: 'Failed to add item to shopping cart item' })
            }
            setShoppingCartRefreshId(i => i + 1)
        } catch (error) {
            notification.error({ description: 'Failed to add item to shopping cart item' })
            console.error(error)
        } finally {
        }
    }

    const updateShoppingCartItem = async (sc_item_id, product_id, amount) => {
        if (amount == 0) {
            deleteShoppingCartItem(sc_item_id)
            return
        }
        try {
            const {
                data: {
                    status: { successful, message },
                },
            } = await api.put(`/customer/${user.id}/shoppingcart/${sc_item_id}`, {
                product_id: product_id,
                amount: amount,
            })
            if (successful) {
                notification.success({ description: 'Succesfully updated item' })
            } else {
                notification.error({ description: 'Failed to update item' })
            }
            setShoppingCartRefreshId(i => i + 1)
        } catch (error) {
            notification.error({ description: 'Failed to update item' })
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
            console.log(token, id, email, firstname, lastname)
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
            console.log(token, id, email, firstname, lastname)
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
        deleteShoppingCartItem,
        updateShoppingCartItem,
    }
}

export const [AppContextProvider, useAppContext] = constate(useApp)
