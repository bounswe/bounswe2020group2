import './LoginPage.less'
import { LoginForm } from '../LoginForm'
import { api } from '../../api'
import { useHistory } from 'react-router-dom'
import { sleep } from '../../utils'
import { notification, Spin } from 'antd'
import { useState } from 'react'
import { useAppContext } from '../../context/AppContext'

export const LoginPage = () => {
    const [isLoading, setIsLoading] = useState(false);

    const history = useHistory()

    const { user, regularLogin } = useAppContext()

    const onSubmit = async (values, userType) =>  {
        if(userType === "customer") {
            try {
                const isLoginSuccess = await regularLogin('customer', values.username, values.password)
                if(isLoginSuccess) {
                    setIsLoading(true)
                    await sleep(2000)
                    setIsLoading(false)
                    console.log(user)
                    history.push({
                        pathname: "/"
                    })
                }
            } catch (error) {
                notification.warning({message: "There was an error with your request."})
            } 
        }
        return
    }

    return (
        <Spin spinning={isLoading}>
            <div className="signup-container">  
                <div className="signup-header">
                    <h1>Login to Getflix!</h1>
                </div>  
                <LoginForm onSubmit={onSubmit} />
            </div>
        </Spin>
    )
}
