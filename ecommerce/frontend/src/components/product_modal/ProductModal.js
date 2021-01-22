import { Form, Modal, notification, Spin } from 'antd'
import { useState } from 'react'
import { ProductModalInner } from './ProductModalInner'
import { products } from '../../mocks/mocks'
import { api } from '../../api'

export const ProductModal = ({
    product = products[0],
    mode = 'edit',
    visible = true, // don't forget to make this false later
    onCancel = () => {},
    onSuccess = () => {},
}) => {
    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)

    const onAdd = async () => {
        const store = await form.validateFields()
        console.log(store)

        try {
            setIsLoading(true)
            // const { data } = await api.post('/vendor/product', store)
            onSuccess()
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
        } finally {
            setIsLoading(false)
        }
    }

    const onEdit = async () => {
        const store = await form.validateFields()
        const values = form.getFieldsValue()
        console.log(values)

        try {
            setIsLoading(true)
            // const { data } = await api.put('/vendor/product', store)
            onSuccess()
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <Modal
            forceRender
            destroyOnClose
            visible={visible}
            title={mode === 'add' ? 'Add a new product' : 'Edit your product'}
            onOk={mode === 'add' ? onAdd : onEdit}
            okText={mode === 'add' ? 'Add' : 'Edit'}
            onCancel={onCancel}
            cancelText="Cancel">
            <Spin spinning={isLoading}>
                {mode === 'edit' ? (
                    <ProductModalInner form={form} product={product} />
                ) : (
                    <ProductModalInner form={form} />
                )}
            </Spin>
        </Modal>
    )
}
