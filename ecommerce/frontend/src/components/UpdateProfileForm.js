import { Form, Input, Button, Tooltip, InputNumber } from 'antd'
import { QuestionCircleOutlined } from '@ant-design/icons'
import { useState } from 'react'

// Constants for layout
const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
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
            span: 16,
            offset: 8,
        },
    },
}

export const UpdateProfileForm = ({ onSubmit = () => {}, user }) => {
    const [form] = Form.useForm()

    const onFinish = values => {
        values.userType = user.userType
        if (isUpdateRequired(values)) {
            console.log('Received values of form: ', values)
            console.log('Initial Values: ', user)
            onSubmit(values)
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
        <Form
            {...formItemLayout}
            form={form}
            name="update-profile"
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            initialValues={{
                name: user.name,
                surname: user.surname,
                username: user.username,
                email: user.email,
                phone: user.phone,
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
                label={
                    <span>
                        Username&nbsp;
                        <Tooltip title="What do you want others to call you?">
                            <QuestionCircleOutlined />
                        </Tooltip>
                    </span>
                }
                rules={[
                    {
                        required: true,
                        message: 'Please input your username!',
                        whitespace: true,
                    },
                ]}>
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

            {/* Zip code input */}
            {user.userType === 'vendor' && (
                <Form.Item
                    name={['address', 'zipCode']}
                    label="Zip Code"
                    rules={[
                        {
                            required: true,
                            message: 'Please enter your zip code!',
                        },
                    ]}>
                    <InputNumber style={{ width: '100%' }} />
                </Form.Item>
            )}

            {/* City input */}
            {user.userType === 'vendor' && (
                <Form.Item
                    name={['address', 'city']}
                    label="City"
                    rules={[
                        {
                            required: true,
                            message: 'Please select your city!',
                        },
                    ]}>
                    <Input />
                </Form.Item>
            )}

            {/* State input */}
            {user.userType === 'vendor' && (
                <Form.Item
                    name={['address', 'state']}
                    label="State"
                    rules={[
                        {
                            required: true,
                            message: 'Please select your state!',
                        },
                    ]}>
                    <Input />
                </Form.Item>
            )}

            {/* Main address input */}
            {user.userType === 'vendor' && (
                <Form.Item
                    name={['address', 'mainAddress']}
                    label="Main Address"
                    rules={[
                        {
                            required: true,
                            message: 'Please enter your address!',
                        },
                    ]}>
                    <Input />
                </Form.Item>
            )}

            {/* Phone number input */}
            <Form.Item name="phone" label="Phone Number">
                <Input.Group compact>
                    <Form.Item
                        name={['phone', 'countryCode']}
                        noStyle
                        rules={[
                            {
                                required: true,
                                message: 'Please enter your country code!',
                            },
                            {
                                message: 'Wrong country code format',
                                validator: (rule, val) => {
                                    if (!val) return Promise.resolve(true)
                                    return /\+\d{1,3}/.test(val)
                                        ? Promise.resolve(true)
                                        : Promise.reject('Country code format is incorrect!')
                                },
                            },
                        ]}>
                        <Input style={{ width: '20%' }} placeholder="+90" />
                    </Form.Item>
                    <Form.Item
                        name={['phone', 'number']}
                        noStyle
                        rules={[
                            { required: true, message: 'Please enter your phone number!' },
                            {
                                message: 'Wrong phone number format',
                                validator: (rule, val) => {
                                    if (!val) return Promise.resolve(true)
                                    return /\d{4,}/.test(val)
                                        ? Promise.resolve(true)
                                        : Promise.reject('Phone number format is incorrect!')
                                },
                            },
                        ]}>
                        <InputNumber style={{ width: '80%' }} />
                    </Form.Item>
                </Input.Group>
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit">
                    Update Profile
                </Button>
            </Form.Item>
        </Form>
    )
}
