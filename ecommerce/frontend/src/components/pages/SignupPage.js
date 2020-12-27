import './SignupPage.less'

import { notification, Spin } from 'antd'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'

import { api } from '../../api'
import { SignUpForm } from '../SignUpForm'

export const SignupPage = () => {
    const [isLoading, setIsLoading] = useState(false)

    const history = useHistory()

    const onSubmit = async (values, userType) => {
        if (userType === 'customer') {
            setIsLoading(true)
            try {
                const { data } = await api.post('/regularsignup', values)
                if (data.successful) {
                    notification.success({ message: 'You have successfully signed up!' })
                    history.push({ pathname: '/login' })
                } else {
                    notification.warning({ message: data.message })
                }
            } catch (error) {
                notification.warning({ message: 'There was an error with your request.' })
            } finally {
                setIsLoading(false)
            }
        } else {
            console.log('userType', userType, 'not handled')
        }
    }

    return (
        <Spin spinning={isLoading}>
            <div className="signup-container">
                <div className="signup-header">
                    <h1>Signup to Getflix!</h1>
                </div>
                <SignUpForm onSubmit={onSubmit} />
            </div>
        </Spin>
    )
}
