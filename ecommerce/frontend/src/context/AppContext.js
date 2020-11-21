import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'

const guestUser = { type: 'guest' }
const customerUser = { type: 'customer', name: 'Mehdi', surname: 'Saffar', email: 'example@gmail.com' }

function useApp() {
    const [user, setUser] = useState(customerUser)
    const logout = () => {
        console.log('logout')
        notification.success({ message: 'Logout', description: `See you soon, ${user.name}!` })
        setUser(guestUser)
    }

    return {
        user,
        logout,
        setUser,
    }
}

export const [AppContextProvider, useAppContext] = constate(useApp)
