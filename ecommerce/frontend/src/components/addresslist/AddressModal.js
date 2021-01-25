import { useState } from 'react'
import 'react-credit-cards/es/styles-compiled.css'
import { Modal, Form, Spin, notification } from 'antd'
import { AddressModalInner } from './AddressModalInner'
import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'

import * as R from 'ramda'

export const AddressModal = ({ address, mode = 'add', visible = false, onCancel = () => {}, onSuccess = () => {} }) => {
    /* This might not remounted after closing and opening again, be careful */

    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)
    const { user } = useAppContext()

    const onEdit = async () => {
        setIsLoading(true)
        try {
            const fields = await form.validateFields()
            const {
                data: { status },
            } = await api.put(`/customer/${user.id}/addresses/${address.id}`, fields)
            if (status.successful) {
                onSuccess()
                notification.success({ message: status.message })
            } else {
                notification.warning({ message: status.message })
            }
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    const onAdd = async () => {
        setIsLoading(true)
        try {
            const fields = await form.validateFields()
            const {
                data: { status },
            } = await api.post(`/customer/${user.id}/addresses`, fields)
            if (status.successful) {
                onSuccess()
                notification.success({ message: status.message })
            } else {
                notification.warning({ message: status.message })
            }
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
            cancelText="Cancel"
            okText={mode === 'add' ? 'Add' : 'Edit'}>
            <Spin spinning={isLoading}>
                <AddressModalInner address={address ?? R.omit(['id'], address)} form={form} />
            </Spin>
        </Modal>
    )
}
