import { useState } from 'react'
import 'react-credit-cards/es/styles-compiled.css'
import { Modal, Form, Spin, notification } from 'antd'
import { sleep } from '../../utils'
import { AddressModalInner } from './AddressModalInner'

export const AddressModal = ({ address, mode = 'add', visible = false, onCancel = () => {}, onSuccess = () => {} }) => {
    /* This might not remounted after closing and opening again, be careful */

    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)

    const onEdit = async () => {
        try {
            setIsLoading(true)
            await sleep(2000)
            const fields = await form.validateFields()
            console.log(fields)
            onSuccess()
            notification.success({ message: 'You have successfully changed your address information!' })
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
            notification.success({ message: 'You have successfully added a new address!' })
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
            title={mode === 'add' ? 'Add a new address' : 'Edit your address'}
            destroyOnClose
            onOk={mode === 'add' ? onAdd : onEdit}
            onCancel={onCancel}
            cancelText="Go Back"
            okText={mode === 'add' ? 'Add' : 'Edit'}>
            <Spin spinning={isLoading}>
                <AddressModalInner address={address} form={form} />
            </Spin>
        </Modal>
    )
}
