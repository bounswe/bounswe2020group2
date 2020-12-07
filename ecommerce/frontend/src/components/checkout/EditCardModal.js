import { useState } from 'react'
import 'react-credit-cards/es/styles-compiled.css'
import { Modal, Form, Spin, notification } from 'antd'
import { sleep } from '../../utils'
import { CardModalInner } from './CardModalInner'

export const EditCardModal = ({
    card,
    visible = false,
    onCancel = () => {},
    onSuccessfulEdit = () => {}
}) => {
    /* This might not remounted after closing and opening again, be careful */

    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)

    const onEdit = async () => {
        try {
            setIsLoading(true)
            await sleep(2000)
            setIsLoading(false)
            onSuccessfulEdit();
            onCancel();
            notification.success({ message: 'You have successfully changed your credit card information!' })
            
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
        }
    }

    return (
        <Modal
            visible={visible}
            title="Edit your credit card information"
            destroyOnClose
            onOk={onEdit}
            onCancel={onCancel}
            cancelText="Go Back"
            okText="Edit">
            <Spin spinning={isLoading}>
                <CardModalInner mode="edit" card={card} form={form} />
            </Spin>
        </Modal>
    )
}
