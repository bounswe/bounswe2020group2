import 'antd/dist/antd.css';
import {
    Form,
    Input,
    Tooltip,
    Row,
    Col,
    Checkbox,
    Button,
    Radio,
} from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';

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
};
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
};

export const SignUpForm = ({ onSubmit = () => { } }) => {
    const [form] = Form.useForm();

    /* SignUpForm sends all of the data it has received from the user to the onSubmit function 
    as a single properly labeled JSON object */
    const onFinish = (values) => {
        onSubmit(values);
        console.log('Received values of form: ', values);
    };

    return (
        <Form
            {...formItemLayout}
            form={form}
            name="register"
            onFinish={onFinish}
            scrollToFirstError
        >

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
                ]}
            >
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
                ]}
            >
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
                hasFeedback
            >
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
                                return Promise.resolve();
                            }

                            return Promise.reject('The two passwords that you entered do not match!');
                        },
                    }),
                ]}
            >
                <Input.Password />
            </Form.Item>

            {/* City input */}
            <Form.Item
                name={['address', 'city']}
                label="City"
                rules={[
                    {
                        required: true,
                        message: 'Please select your city!',
                    },
                ]}
            >
                <Input />
            </Form.Item>
            
            {/* State input */}
            <Form.Item
                name={['address', 'state']}
                label="State"
                rules={[
                    {
                        required: true,
                        message: 'Please select your state!',
                    },
                ]}
            >
                <Input />
            </Form.Item>

            {/* Zip code input */}
            <Form.Item
                name={['address', 'zipCode']}
                label="Zip Code"
                rules={[
                    {
                        required: true,
                        message: 'Please enter your zip code!',
                    },
                ]}
            >
                <Input />
            </Form.Item>
            
            {/* Main address input */}
            <Form.Item
                name={['address', 'mainAddress']}
                label="Main Address"
                rules={[
                    {
                        required: true,
                        message: 'Please enter your address!',
                    },
                ]}
            >
                <Input />
            </Form.Item>

            {/* Phone number input */}
            <Form.Item
                name="phone"
                label="Phone Number"
            >
                <Input.Group compact >
                    <Form.Item
                        name={['phone', 'countryCode']}
                        noStyle
                        rules={[{ required: true, message: 'Please enter your country code!' }]}
                    >
                        <Input style={{ width: '20%' }} placeholder="+90" />
                    </Form.Item>
                    <Form.Item
                        name={['phone', 'number']}
                        noStyle
                        rules={[{ required: true, message: 'Please enter your phone number!' }]}
                    >
                        <Input style={{ width: '80%' }} />
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
                            noStyle
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input the captcha you got!',
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Button>Get captcha</Button>
                    </Col>
                </Row>
            </Form.Item>

            {/* User type selection */}
            <Form.Item
                name="userType"
                rules={[
                    {
                        required: true,
                        message: "Please select your user type!"
                    },
                ]}
                {...tailFormItemLayout}
            >
                <Radio.Group>
                    <Radio value="customer">Register as customer</Radio>
                    <Radio value="vendor">Register as vendor</Radio>
                </Radio.Group>
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
                {...tailFormItemLayout}
            >
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
    );
};
