import { UpdateProfileForm } from '../UpdateProfileForm'
import './ProfileContent.less'
import { api } from '../../api'
import { notification } from 'antd'
import { useHistory } from 'react-router-dom'
import { sleep } from '../../utils'
import { useState } from 'react'

export const ProfileContent = ({ user }) => {
    const [isLoading, setIsLoading] = useState(false)
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

    return <UpdateProfileForm onSubmit={onSubmit} user={user} />
}
