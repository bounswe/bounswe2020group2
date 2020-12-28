import { UpdateProfileForm } from '../UpdateProfileForm'
import './ProfileContent.less'
import { api } from '../../api'
import { notification } from 'antd'
import { useHistory } from 'react-router-dom'
import { useState } from 'react'
import { IsVerifiedNotification } from '../IsVerifiedNotification'

export const ProfileContent = ({ user }) => {
    const [isLoading, setIsLoading] = useState(false)
    const history = useHistory()

    const onSubmit = async (values, userType) => {
        if (userType === 'customer') {
            setIsLoading(true)
            try {
                const { data } = await api.post('/updateProfile', values)
                if (data.successful) {
                    notification.success({ message: 'Your profile updated successfully!' })
                    history.push({ pathname: '/profile' })
                } else {
                    notification.warning({ message: data.message })
                }
            } catch (error) {
                notification.warning({ message: 'There was an error with your request.' })
            }
        }
    }

    return (
        <div className={'profile-content-root'}>
            <IsVerifiedNotification />
            <div className="profile-content-container">
                <div className="profile-content-header">Profile Details</div>
                <div className="profile-content-content">
                    <UpdateProfileForm onSubmit={onSubmit} user={user} />
                </div>
            </div>
        </div>
    )
}
