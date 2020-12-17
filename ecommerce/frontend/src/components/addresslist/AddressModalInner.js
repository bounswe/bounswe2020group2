import { Form, Input } from 'antd'

export const AddressModalInner = ({ form, address }) => {
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

    return (
        <Form
            {...formItemLayout}
            layout="horizontal"
            form={form}
            name={'addressForm' + address?.id}
            scrollToFirstError
            initialValues={address}>
            <Form.Item
                name="title"
                label="Address Title"
                rules={[
                    {
                        required: true,
                        message: 'Please input a title for your address!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name="name"
                label="Name"
                rules={[
                    {
                        required: true,
                        message: 'Please input your name!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name="surname"
                label="Surname"
                rules={[
                    {
                        required: true,
                        message: 'Please input your surname!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'province'}
                label="Province"
                rules={[
                    {
                        required: true,
                        message: 'Please select your province!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'city'}
                label="City"
                rules={[
                    {
                        required: true,
                        message: 'Please select your city!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'country'}
                label="Country"
                rules={[
                    {
                        required: true,
                        message: 'Please select your country!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'zipCode'}
                label="Zip Code"
                rules={[
                    {
                        required: true,
                        message: 'Please enter your zip code!',
                    },
                    {
                        message: 'Wrong zip code format',
                        validator: (rule, val) => {
                            if (!val) return Promise.resolve(true)
                            return /^\d+$/.test(val)
                                ? Promise.resolve(true)
                                : Promise.reject('Country code format is incorrect!')
                        },
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'address'}
                label="Main Address"
                rules={[
                    {
                        required: true,
                        message: 'Please enter your address!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item 
                name="phone" 
                label="Phone Number"
                required>
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
                        <Input style={{ width: '80%' }} />
                    </Form.Item>
                </Input.Group>
            </Form.Item>
            <div style={{paddingBottom: 8}}>
                <div style={{
                    color: "red", 
                    float:"left"}}>
                    *
                </div> 
                <div style={{
                    fontStyle: "italic",
                    float:"left"}}>
                    &nbsp; marked fields are required
                </div>
            </div>
        </Form>
    )
}
