import { Form, Input, InputNumber } from 'antd'
import { useState } from 'react'
import * as R from 'ramda'
import Cards from 'react-credit-cards'

import { formatCreditCard } from '../../utils'

export const CardModalInner = ({ form, card }) => {
    const [cardState, setCardState] = useState(card)
    const [focused, setFocused] = useState('')

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

    const handleInputFocus = val => setFocused(val.target.name)

    const onFieldsChange = () =>
        setCardState({
            id: cardState.id,
            ...form.getFieldsValue(),
        })

    return (
        <>
            <Form
                {...formItemLayout}
                layout="horizontal"
                form={form}
                name={'cardForm' + card.id}
                onFinish={() => {}}
                onFinishFailed={() => {}}
                onFieldsChange={onFieldsChange}
                scrollToFirstError
                initialValues={{ ...R.omit(['id'], cardState) }}>
                <Form.Item
                    name="name"
                    label="Card Name"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card name!',
                        },
                    ]}>
                    <Input
                        onFocus={() => {
                            setFocused('owner_name')
                        }}
                    />
                </Form.Item>
                <Form.Item
                    name="owner_name"
                    label="Owner Name"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card owner name!',
                        },
                    ]}>
                    <Input onFocus={handleInputFocus} name="name" />
                </Form.Item>
                <Form.Item
                    name="serial_number"
                    label="Card Number"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card number!',
                        },
                        {
                            message: 'Your credit card number must be a 16 digit number!',
                            validator: (rule, val) => {
                                if (!val) return Promise.resolve(true)
                                return /^\d{16}$/.test(val)
                                    ? Promise.resolve(true)
                                    : Promise.reject('Wrong card number format!')
                            },
                        },
                    ]}>
                    <Input onFocus={handleInputFocus} name="number" />
                </Form.Item>
                <Form.Item
                    name="expiration_date"
                    label="Expiry Date"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card expiry date!',
                        },
                    ]}>
                    <Input.Group compact>
                        <Form.Item name={['expiration_date', 'month']} noStyle rules={[]}>
                            <InputNumber
                                max={12}
                                min={1}
                                formatter={value => (value >= 10 ? value : `0${value}`)}
                                parser={value => (value >= 10 ? value : `0${value}`)}
                                onFocus={handleInputFocus}
                                name="month"
                            />
                        </Form.Item>
                        <Form.Item name={['expiration_date', 'year']} noStyle>
                            <InputNumber min={2021} onFocus={handleInputFocus} name="year" />
                        </Form.Item>
                    </Input.Group>
                </Form.Item>
                <Form.Item
                    name="cvv"
                    label="CVC"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your CVC!',
                        },
                        {
                            message: 'Your CVC number must be a 3 or 4 digit number!',
                            validator: (rule, val) => {
                                if (!val) return Promise.resolve(true)
                                return /^\d{3,4}$/.test(val)
                                    ? Promise.resolve(true)
                                    : Promise.reject('Wrong CVC number format!')
                            },
                        },
                    ]}>
                    <Input onFocus={handleInputFocus} name="cvc" />
                </Form.Item>
            </Form>
            <Cards focused={focused} {...formatCreditCard(cardState)}></Cards>
        </>
    )
}
