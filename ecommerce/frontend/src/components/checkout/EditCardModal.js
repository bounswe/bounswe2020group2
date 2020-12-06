import { useState } from 'react'
import Cards from 'react-credit-cards'
import 'react-credit-cards/es/styles-compiled.css'
import { Modal, Form, Input } from 'antd'

export const EditCardModal = ({
    cardProps = {
        cvc: '',
        expiry: '',
        number: '',
        name: '',
    },
    cardId = {},
    cardName = '',
    visible = false,
    setVisible = () => {},
}) => {
    /* This might not remounted after closing and opening again, be careful */
    const [cvc, setCvc] = useState(cardProps.cvc)
    const [expiry, setExpiry] = useState(cardProps.expiry)
    const [name, setName] = useState(cardProps.name)
    const [number, setNumber] = useState(cardProps.number)
    const [cardNickname, setCardNickname] = useState(cardName)
    const [focused, setFocused] = useState('')

    const [form] = Form.useForm()
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

    return (
        <Modal
            visible={visible}
            title="Edit your credit card information"
            destroyOnClose
            onOk={() => {
                setVisible(false)
            }}
            onCancel={() => {
                setVisible(false)
            }}
            cancelText="Go Back"
            okText="Edit">
            <Form
                {...formItemLayout}
                layout="horizontal"
                form={form}
                name="editCardForm"
                onFinish={() => {}}
                onFinishFailed={() => {}}
                scrollToFirstError
                initialValues={{ cardName: cardName, ...cardProps }}>
                <Form.Item
                    name="cardName"
                    label="Card Name"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card name!',
                        },
                    ]}>
                    <Input
                        onChange={val => {
                            setCardNickname(val.target.value)
                        }}
                    />
                </Form.Item>
                <Form.Item
                    name="name"
                    label="Name"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card name!',
                        },
                    ]}>
                    <Input
                        onFocus={handleInputFocus}
                        onChange={val => {
                            setName(val.target.value)
                        }}
                        name="name"
                    />
                </Form.Item>
                <Form.Item
                    name="number"
                    label="Card Number"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card number!',
                        },
                    ]}>
                    <Input
                        onFocus={handleInputFocus}
                        onChange={val => {
                            setNumber(val.target.value)
                        }}
                        name="number"
                    />
                </Form.Item>
                <Form.Item
                    name="expiry"
                    label="Expiry Date"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your card expiry date!',
                        },
                    ]}>
                    <Input
                        onFocus={handleInputFocus}
                        onChange={val => {
                            setExpiry(val.target.value)
                        }}
                        name="expiry"
                    />
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
                        onChange={val => {
                            setCvc(val.target.value)
                        }}
                        name="cvc"
                    />
                </Form.Item>
            </Form>
            <Cards focused={focused} cvc={cvc} name={name} number={number} expiry={expiry}></Cards>
        </Modal>
    )
}
