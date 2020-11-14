import {
    loadCaptchaEnginge,
    LoadCanvasTemplate,
    LoadCanvasTemplateNoReload,
    validateCaptcha,
} from 'react-simple-captcha'
import { Form, Input, Button, Radio, Tooltip, Row, Col, Checkbox, InputNumber } from 'antd'
import { useEffect, useState } from 'react'
import { QuestionCircleOutlined } from '@ant-design/icons'

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

export const SignUpForm = ({ onSubmit = () => {} }) => {
    const [form] = Form.useForm()
    const initialValues = { userType: 'customer' }
    const [isVendor, setIsVendor] = useState(initialValues.userType === 'vendor')
    useEffect(() => {
        loadCaptchaEnginge(6)
    }, [])

    /* SignUpForm sends all of the data it has received from the user to the onSubmit function 
    as a single properly labeled JSON object */
    const onFinish = values => {
        console.log('Received values of form: ', values)
        onSubmit(values)
    }

    const onFinishFailed = errors => {
        console.log('Finish failed: ', errors)
    }

    const onUserTypeChange = ({ target: { value: userType } }) => setIsVendor(userType === 'vendor')

    return (
        <Form
            {...formItemLayout}
            form={form}
            name="register"
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            initialValues={initialValues}
            scrollToFirstError>
            {/* User type selection */}
            <Form.Item
                name="userType"
                label="User type:"
                rules={[
                    {
                        required: true,
                        message: 'Please select your user type!',
                    },
                ]}>
                <Radio.Group onChange={onUserTypeChange}>
                    <Radio value="customer">Register as customer</Radio>
                    <Radio value="vendor">Register as vendor</Radio>
                </Radio.Group>
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

            {/* Zip code input */}
            {isVendor && (
                <Form.Item
                    name={['address', 'zipCode']}
                    label="Zip Code"
                    rules={[
                        {
                            required: true,
                            message: 'Please enter your zip code!',
                        },
                    ]}>
                    <Input />
                </Form.Item>
            )}

            {/* City input */}
            {isVendor && (
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
            {isVendor && (
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
            {isVendor && (
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
                                validator: (rule, val) =>
                                    /\+\d{1,3}/.test(val) ? Promise.resolve(true) : Promise.reject(),
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
                                validator: (rule, val) =>
                                    /\d{4,}/.test(val) ? Promise.resolve(true) : Promise.reject(),
                            },
                        ]}>
                        <InputNumber style={{ width: '80%' }} />
                    </Form.Item>
                </Input.Group>
            </Form.Item>

            {/* Captcha input 
            TODO: This input is non-functional */}
            <Form.Item label="Captcha" extra="We must make sure that your are a human.">
                <Row gutter={8}>
                    <Col span={12}>
                        <Form.Item
                            name="captcha"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input the captcha you got!',
                                },
                                {
                                    validator: (rule, val) =>
                                        validateCaptcha(val, false) ? Promise.resolve(true) : Promise.reject(),
                                    message: 'Wrong captcha',
                                },
                            ]}
                            hasFeedback>
                            <Input />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <LoadCanvasTemplate />
                    </Col>
                </Row>
            </Form.Item>

            {/* Checkbox for the user agreement */}
            <Form.Item
                name="agreement"
                valuePropName="checked"
                rules={[
                    {
                        validator: (_, value) =>
                            value ? Promise.resolve() : Promise.reject('Should accept agreement'),
                    },
                ]}
                {...tailFormItemLayout}>
                <Checkbox>
                    I have read the <a href="">agreement</a>
                </Checkbox>
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit">
                    Register
                </Button>
            </Form.Item>
        </Form>
    )
}
