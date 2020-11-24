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

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit">
                    Update Profile
                </Button>
            </Form.Item>
        </Form>
    )
}
