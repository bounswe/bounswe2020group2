import { Form, Input, Button } from 'antd'

// Constants for layout
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
            span: 18,
            offset: 6,
        },
    },
}

export const UpdateProfileForm = ({ onSubmit = () => {}, user }) => {
    const [form] = Form.useForm()

    const onFinish = values => {
        values.userType = user.userType
        if (isUpdateRequired(values)) {
            onSubmit(values, values.userType)
        } else {
            console.log('Nothing is changed')
        }
    }

    const onFinishFailed = errors => {
        console.log('Finish failed: ', errors)
    }

    const isUpdateRequired = newValues => {
        const keys = Object.keys(user)

        for (let key of keys) {
            if (key === 'phone') {
                if (
                    user[key]['countryCode'] != newValues[key]['countryCode'] ||
                    user[key]['number'] != newValues[key]['number']
                ) {
                    return true
                }
                continue
            } else if (user[key] !== newValues[key]) {
                return true
            }
        }
        return false
    }

    return (
        <div className="update-profile-form">
            <Form
                {...formItemLayout}
                form={form}
                name="update-profile"
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                initialValues={{
                    name: user.name,
                    surname: user.lastname,
                    email: user.email,
                }}
                scrollToFirstError>
                {/* Name input */}
                <Form.Item
                    name="name"
                    label="Name"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your name!',
                        },
                    ]}
                    hasFeedback>
                    <Input />
                </Form.Item>

                {/* Surname input */}
                <Form.Item
                    name="surname"
                    label="Surname"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your surname!',
                        },
                    ]}
                    hasFeedback>
                    <Input />
                </Form.Item>

                {/* Username input */}
                <Form.Item
                    name="username"
                    label="Username"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your username!',
                        },
                    ]}
                    hasFeedback>
                    <Input />
                </Form.Item>

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
                    ]}
                    hasFeedback>
                    <Input.Password />
                </Form.Item>

                {/* Password confirmation input */}
                <Form.Item
                    name="confirm"
                    label="Confirm Password"
                    dependencies={['password']}
                    hasFeedback
                    rules={[
                        {
                            required: true,
                            message: 'Please confirm your password!',
                        },
                        ({ getFieldValue }) => ({
                            validator(rule, value) {
                                if (!value || getFieldValue('password') === value) {
                                    return Promise.resolve()
                                }

                                return Promise.reject('The two passwords that you entered do not match!')
                            },
                        }),
                    ]}>
                    <Input.Password />
                </Form.Item>

                <Form.Item {...tailFormItemLayout}>
                    <Button type="primary" htmlType="submit">
                        Update Profile
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}
