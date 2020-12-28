import './LoginPage.less'

import { notification, Spin } from 'antd'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'

import { useAppContext } from '../../context/AppContext'
import { LoginForm } from '../LoginForm'

export const LoginPage = () => {
    const [isLoading, setIsLoading] = useState(false)

    const history = useHistory()

    const { user, regularLogin } = useAppContext()

    const onSubmit = async (values) => {
        try {
            setIsLoading(true)
            const isLoginSuccess = await regularLogin(values.username, values.password)
            if (isLoginSuccess) history.push({ pathname: '/' })
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <Spin spinning={isLoading}>
            <div className="login-container">
                <div className="login-header">
                    <h1>Login to Getflix!</h1>
                </div>
                <LoginForm onSubmit={onSubmit} />
            </div>
        </Spin>
    )
}
