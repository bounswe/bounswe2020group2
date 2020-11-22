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
    }
}

export const [AppContextProvider, useAppContext] = constate(useApp)
