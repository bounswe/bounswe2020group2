import { useAppContext } from '../context/AppContext'
import { Alert, Button, notification } from 'antd'
import { useState } from 'react'
import { api } from './../api'

export const IsVerifiedNotification = () => {
    const { user, setUser } = useAppContext()
    const [isLoading, setIsLoading] = useState(false)

    const onVerify = async () => {
        try {
            setIsLoading(true)
            const {
                data: {
                    status: { message, successful },
                },
            } = await api.post('/user/verify', {})
            if (successful) {
                notification.success({ description: 'We have sent a verification email to your inbox.' })
                setUser({ ...user, is_verified: true })
            }
        } catch (error) {
            notification.error({ description: 'Failed to send verification email' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    if (user?.is_verified) return null

    return (
        <Alert
            message={
                <>
                    Your email is not verified&nbsp;
                    <Button loading={isLoading} onClick={onVerify} type="primary">
                        Verify
                    </Button>
                </>
            }
            banner
        />
    )
}
