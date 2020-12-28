import { useAppContext } from '../context/AppContext'
import { Alert, Button, notification, Space, Spin } from 'antd'
import { useEffect, useState } from 'react'
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
                notification.success({ description: 'Successfully verified account' })
                setUser({ ...user, is_verified: true })
            }
        } catch (error) {
            notification.error({ description: 'Failed to verify account' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div>
            {!user?.is_verified && (
                <>
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
                </>
            )}
        </div>
    )
}
