import { useAppContext } from '../context/AppContext'
import { Alert, Button } from 'antd'
import { useEffect, useState } from 'react'

export const IsVerifiedNotification = () => {
    const { user } = useAppContext()
    const [currentUser, setCurrentUser] = useState(user)

    useEffect(() => {
        setCurrentUser(user)
    }, [user])

    return (
        <div>
            {currentUser.type === 'guest' || currentUser.is_verified ? null : (
                <Alert
                    message="Please verify your account."
                    type="warning"
                    showIcon
                    closable
                    action={
                        <Button size="small" type="text">
                            UNDO
                        </Button>
                    }
                />
            )}
        </div>
    )
}
