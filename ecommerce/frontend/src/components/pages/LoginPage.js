import './LoginPage.less'
import { Form, Input, Button, Row, Col } from 'antd'
import { Link } from 'react-router-dom'
import Column from 'antd/lib/table/Column'

export const LoginPage = ({ onSubmit = () => { } }) => {
    const [form] = Form.useForm()

    const onFinish = values => {
        console.log('Received values of form: ', values)
        onSubmit(values)
    }

    const onFinishFailed = errors => {
        console.log('Finish failed: ', errors)
    }

    const onLoginAsVendor = () => {
        const loginInfo = {
            "email": form.getFieldValue("email"),
            "password": form.getFieldValue("password")
        }
        console.log(loginInfo)
    }

    // Constants for layout
    const layout = {
        labelCol: {
            span: 8,
        },
        wrapperCol: {
            span: 16,
        }
    };
    const tailLayout = {
        wrapperCol: {
            offset: 8,
            span: 16,
        }
    };

    // TO DO ln: 96
    // Implement Google Login
    return (
        <div className="loginForm">
            <Form
                {...layout}
                layout="horizontal"
                form={form}
                name="loginForm"
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                scrollToFirstError>

                {/* Email input */}
                <Form.Item
                    name="email"
                    label="E-mail"
                    rules={[
                        {
                            type: 'email',
                            message: 'The input is not valid E-mail!',
                        },
                        {
                            required: true,
                            message: 'Please input your E-mail!',
                        },
                    ]}>
                    <Input />
                </Form.Item>

                {/* Password input */}
                <Form.Item
                    name="password"
                    label="Password"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your password!',
                        },
                    ]}>
                    <Input.Password />
                </Form.Item>

                <div className="loginButtons">
                    <Form.Item {...tailLayout}>
                        <Button className="loginButton" type="primary" htmlType="submit">
                            Login as User
                        </Button>
                        <Button className="loginButton" type="primary" htmlType="button" onClick={onLoginAsVendor}>
                            Login as Vendor
                        </Button>
                    </Form.Item>
                </div>
            </Form>
            <div className="routerButtons">
                <Button className="googleLoginButton">
                    <Link to="/">Google Login</Link>
                </Button>
                <br></br>
                <Button className="signUpButton">
                    <Link to="/signup">Already have an account? Sign Up</Link>
                </Button>
            </div>
        </div>
    )
}