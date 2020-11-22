import { SignUpForm } from '../SignUpForm'
import './SignupPage.less'
import { api } from '../../api'
import { useHistory } from 'react-router-dom'
import { sleep } from '../../utils'
import { notification, Spin } from 'antd'
import { useState } from 'react'
export const SignupPage = () => {
    const [isLoading, setIsLoading] = useState(false);

    const history = useHistory()

    const onSubmit = async (values, userType) =>  {
        if(userType === "customer") {
            try {
                const { data } = await api.get("/products/homepage/3" /*TODO: post request to signup*/, values)
                if(true/* TODO: data.successful*/) {
                    notification.success({message:"You have successfully signed up!"})
                    setIsLoading(true)
                    await sleep(2000)
                    setIsLoading(false)
                    history.push({
                        pathname: "/login"
                    })
                }
                else {
                    notification.warning({message: data.message})
                }

            } catch (error) {
                notification.warning({message: "There was an error with your request."})
                
            } 
        }
        return
    }

    return <Spin spinning={isLoading}>
        <div className="signup-container">  
            <div className="signup-header">
                <h1>Signup to Getflix!</h1>
            </div>  
            <SignUpForm onSubmit={onSubmit} />
        </div>
    </Spin>
}
