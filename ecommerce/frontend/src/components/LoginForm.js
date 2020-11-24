import './LoginForm.less'
import { Form, Input, Button } from 'antd'
import { Link } from 'react-router-dom'

export const LoginForm = ({ onSubmit = () => {} }) => {
    const [form] = Form.useForm()

    const onFinish = values => {
        const valuesToSend = {
            username: values.username,
            password: values.password,
        }
        onSubmit(valuesToSend, 'customer')
    }

    const onFinishFailed = errors => {
        console.log('Finish failed: ', errors)
    }

    const onLoginAsVendor = async () => {
        form.validateFields()
            .then(values => {
                const valuesToSend = {
                    username: values.username,
                    password: values.password,
                }
                onSubmit(valuesToSend, 'vendor')
            })
            .catch(errorInfo => {
                console.log(errorInfo)
            })
    }

    const formItemLayout = {
        labelCol: {
            xs: {
                span: 24,
            },
            sm: {
                span: 6,
            },
        },
        wrapperCol: {
            xs: {
                span: 24,
            },
            sm: {
                span: 18,
            },
        },
    }
    const tailFormItemLayout = {
        wrapperCol: {
            xs: {
                span: 24,
                offset: 0,
            },
            sm: {
                span: 24,
                offset: 0,
            },
        },
    }

    // TO DO ln: 96
    // Implement Google Login
    return (
        <div className="login-form">
            <Form
                {...formItemLayout}
                layout="horizontal"
                form={form}
                name="loginForm"
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                scrollToFirstError>
                {/* Username input */}
                <Form.Item
                    name="username"
                    label="Username"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your username!',
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

                <div className="login-buttons">
                    <Form.Item {...tailFormItemLayout}>
                        <Button className="login-button" type="primary" htmlType="submit">
                            Login as Customer
                        </Button>
                        <Button className="login-button" type="primary" htmlType="button" onClick={onLoginAsVendor}>
                            Login as Vendor
                        </Button>
                        <Button className="google-login-button">
                            <Link to="/">Google Login</Link>
                        </Button>
                    </Form.Item>
                </div>
            </Form>
            <div className="router-buttons">
                <Button className="signup-button">
                    <Link to="/signup">New to Getflix? Sign Up</Link>
                </Button>
            </div>
        </div>
    )
}
