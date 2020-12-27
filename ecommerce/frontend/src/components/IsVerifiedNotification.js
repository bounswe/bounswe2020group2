import { useAppContext } from '../context/AppContext'
import { Alert, Button, notification, Space, Spin } from 'antd'
import { useEffect, useState } from 'react'
import { api } from './../api'

export const IsVerifiedNotification = () => {
    const { user, setUser } = useAppContext()
    const [currentUser, setCurrentUser] = useState(user)
    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        setCurrentUser(user)
    }, [user])

    const onVerify = async () => {
        try {
            setIsLoading(true)
            const {
                data: {
                    status: { message, successful },
                },
            } = await api.post('/user/verify', {})
            if (successful) {
                notification.successful({
                    description: 'Successfully verified account',
                    placement: 'topRight',
                    duration: 2,
                })
                setUser({ ...user, is_verified: true })
            }
        } catch {
            notification.error({
                description: 'Failed to verify account',
                placement: 'topRight',
                duration: 2,
            })
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div>
            {currentUser.type === 'guest' || currentUser.is_verified ? null : (
                <Alert
                    message="Please verify your account."
                    type="warning"
                    action={
                        <Spin spinning={isLoading}>
                            <Space>
                                <Button size="small" type="ghost" onClick={onVerify}>
                                    Done
                                </Button>
                            </Space>
                        </Spin>
                    }
                    closable
                />
            )}
        </div>
    )
}
