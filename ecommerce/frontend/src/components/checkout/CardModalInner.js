import { Form, Input, InputNumber } from 'antd'
import { useState } from 'react'
import * as R from 'ramda'
import Cards from 'react-credit-cards'

import { formatCreditCard } from '../../utils'

export const CardModalInner = ({
    form,
    card,
    mode = 'add'
}) => {

    const [cardState, setCardState] = useState(
        mode === 'edit' ? 
        card :
        {
            id: '',
            name: '',
            owner_name: '',
            serial_number: '',
            expiration_date: {
                month: '',
                year: ''
            },
            cvc: ''
        }
    )
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

    const onFieldsChange = () => setCardState({
        id: cardState.id,
        ...form.getFieldsValue()
    })

    return <>
        <Form
            {...formItemLayout}
            layout="horizontal"
            form={form}
            name="editCardForm"
            onFinish={() => {}}
            onFinishFailed={() => {}}
            onFieldsChange={onFieldsChange}
            scrollToFirstError
            initialValues={{...R.omit(['id'], cardState)}}>
            <Form.Item
                name="name"
                label="Card Name"
                rules={[
                    {
                        required: true,
                        message: 'Please input your card name!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name="owner_name"
                label="Owner Name"
                rules={[
                    {
                        required: true,
                        message: 'Please input your card name!',
                    },
                ]}>
                <Input
                    onFocus={handleInputFocus}
                    name="name"
                />
            </Form.Item>
            <Form.Item
                name="serial_number"
                label="Card Number"
                rules={[
                    {
                        required: true,
                        message: 'Please input your card number!',
                    },
                ]}>
                <Input
                    onFocus={handleInputFocus}
                    name="number"
                />
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
                <Input.Group compact> {/** TODO: solve this */}
                    <Form.Item
                        name={['expiration_date', 'month']}
                        noStyle>
                        <InputNumber
                            max={12}
                            min={1}
                            formatter={value => value >= 10 ? value : `0${value}`}
                            parser={value => value >= 10 ? value : `0${value}`}
                            onFocus={handleInputFocus}
                            name="month"/>
                    </Form.Item>
                    <Form.Item
                        name={['expiration_date', 'year']}
                        noStyle>
                        <InputNumber
                            min={2020}
                            onFocus={handleInputFocus}
                            name="year"/>
                    </Form.Item>
                
                </Input.Group>
            </Form.Item>
            <Form.Item
                name="cvc"
                label="CVC"
                rules={[
                    {
                        required: true,
                        message: 'Please input your CVC!',
                    },
                ]}>
                <Input
                    onFocus={handleInputFocus}
                    name="cvc"
                />
            </Form.Item>
        </Form>
        <Cards focused={focused} {...formatCreditCard(cardState)}></Cards>

    </>
}