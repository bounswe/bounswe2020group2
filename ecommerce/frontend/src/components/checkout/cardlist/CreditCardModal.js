import { useState } from 'react'
import 'react-credit-cards/es/styles-compiled.css'
import { Modal, Form, Spin, notification } from 'antd'
import { sleep } from '../../../utils'
import { CardModalInner } from './CardModalInner'

export const CreditCardModal = ({ card, mode = 'add', visible = false, onCancel = () => {}, onSuccess = () => {} }) => {
    /* This might not remounted after closing and opening again, be careful */

    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)

    const title = mode === 'add' ? 'Add a new credit card' : 'Edit your credit card information'

    const emptyCard = {
        id: '',
        name: '',
        owner_name: '',
        serial_number: '',
        expiration_date: {
            month: 1,
            year: 2020,
        },
        cvc: '',
    }

    // Add a new credit card
    const onEdit = async () => {
        try {
            setIsLoading(true)
            await sleep(2000)
            const fields = await form.validateFields()
            console.log(fields)
            onSuccess()
            notification.success({ message: 'You have successfully changed your credit card information!' })
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    const onAdd = async () => {
        try {
            setIsLoading(true)
            await sleep(2000)
            const fields = await form.validateFields()
            console.log(fields)
            onSuccess()
            notification.success({ message: 'You have successfully added your credit card!' })
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <Modal
            forceRender
            visible={visible}
            title={mode === 'add' ? 'Add a new credit card' : 'Edit your credit card information'}
            destroyOnClose
            onOk={mode === 'add' ? onAdd : onEdit}
            onCancel={onCancel}
            cancelText="Go Back"
            okText={mode === 'add' ? 'Add' : 'Edit'}>
            <Spin spinning={isLoading}>
                <CardModalInner card={mode === 'add' ? emptyCard : card} form={form} />
            </Spin>
        </Modal>
    )
}
