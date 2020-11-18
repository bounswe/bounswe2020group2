import constate from 'constate'
import { useState } from 'react'

function useApp() {
    const [user, setUser] = useState(null)

    return {
        user,
        setUser,
    }
}

export const [AppContextProvider, useAppContext] = constate(useApp)
