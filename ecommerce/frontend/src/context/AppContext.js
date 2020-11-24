import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'
import { api } from '../api'

const guestUser = { type: 'guest' }

// for testing purposes only
const customerUser = { type: 'customer', name: 'Mehdi', surname: 'Saffar', email: 'example@gmail.com' }

function useApp() {
    const [user, setUser] = useState(guestUser)
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
                    authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            })

            const { token, id, email, firstname, lastname } = data

            setUser({ id, email, name: firstname, lastname })

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
            const { data } = await api.post('/regularlogin/', { username, password })
            const { success, message } = data.status
            const { token, id, email, firstname, lastname } = data.user

            if (success) {
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
    }
}

export const [AppContextProvider, useAppContext] = constate(useApp)
