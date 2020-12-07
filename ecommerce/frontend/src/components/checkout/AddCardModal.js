import { useState } from 'react'
import 'react-credit-cards/es/styles-compiled.css'
import { Modal, Form, Spin, notification } from 'antd'
import { sleep } from '../../utils'
import { CardModalInner } from './CardModalInner'

export const AddCardModal = ({
    visible = false,
    onCancel = () => {},
    onSuccessfulAdd = () => {}
}) => {
    /* This might not remounted after closing and opening again, be careful */

    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)

    const onAdd = async () => {
        try {
            setIsLoading(true)
            await sleep(2000)
            setIsLoading(false)
            const fields = await form.validateFields()
            console.log(fields)
            onSuccessfulAdd();
            onCancel();
            notification.success({ message: 'You have successfully added your credit card!' })
            
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
        }
    }

    return (
        <Modal
            visible={visible}
            title="Add a new credit card"
            destroyOnClose
            onOk={onAdd}
            onCancel={onCancel}
            cancelText="Go Back"
            okText="Add">
            <Spin spinning={isLoading}>
                <CardModalInner form={form} />
            </Spin>
        </Modal>
    )
}
