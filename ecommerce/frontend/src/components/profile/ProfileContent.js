import { UpdateProfileForm } from '../UpdateProfileForm'
import './ProfileContent.less'
import { api } from '../../api'
import { Alert, Button, notification, Space } from 'antd'
import { useHistory } from 'react-router-dom'
import { sleep } from '../../utils'
import { useState } from 'react'

export const ProfileContent = ({ user }) => {
    const [isLoading, setIsLoading] = useState(false)
    const [isVerifyLoading, setIsVerifyLoading] = useState(false)
    const history = useHistory()

    const onSubmit = async (values, userType) => {
        if (userType === 'customer') {
            try {
                const { data } = await api.post('/updateProfile', values)
                if (data.successful) {
                    notification.success({ message: 'Your profile updated successfully!' })
                    setIsLoading(true)
                    await sleep(2000)
                    setIsLoading(false)
                    history.push({
                        pathname: '/profile',
                    })
                } else {
                    notification.warning({ message: data.message })
                }
            } catch (error) {
                notification.warning({ message: 'There was an error with your request.' })
            }
        }
        return
    }

    const onVerify = async () => {
        try {
            setIsVerifyLoading(true)
            const {
                data: {
                    status: { successful, message },
                },
            } = await api.post('/user/verify', {})
            if (successful) {
                notification.success({ description: 'We have sent an email to your inbox' })
            }
        } catch {
            notification.error({ description: 'Oops.. Please try again, later' })
        } finally {
            setIsVerifyLoading(false)
        }
    }

    return (
        <>
            {!user?.is_verified && (
                <>
                    <Alert
                        message={
                            <>
                                Your email is not verified&nbsp;
                                <Button loading={isVerifyLoading} onClick={onVerify} type="primary">
                                    Verify
                                </Button>
                            </>
                        }
                        banner
                    />
                    <br />
                </>
            )}
            <div className="profile-content-container">
                <div className="profile-content-header">Profile Details</div>
                <div className="profile-content-content">
                    <UpdateProfileForm onSubmit={onSubmit} user={user} />
                </div>
            </div>
        </>
    )
}
