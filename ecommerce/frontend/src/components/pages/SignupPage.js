import { SignUpForm } from '../SignUpForm'
import './SignupPage.less'
export const SignupPage = () => {
    return <>
        <div className="signup-container">  
            <div className="signup-header">
                <h1>Signup to Getflix!</h1>
            </div>  
            <SignUpForm />
        </div>
    </>
}
