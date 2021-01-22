import { Form, Modal, notification, Spin } from 'antd'
import { useState } from 'react'
import { ProductModalInner } from './ProductModalInner'
import { vendorOrders } from '../../mocks/mocks'
import { api } from '../../api'
import { getBase64 } from 'image-blobber'

export const ProductModal = ({
    product = vendorOrders[0].product,
    mode = 'edit',
    visible = true, // don't forget to make this false later
    onCancel = () => {},
    onSuccess = () => {},
}) => {
    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)

    const formatProductForSend = async values => {
        const images = await Promise.all(
            values.images.map(async image => {
                if (image.originFileObj) return (await getBase64(image.originFileObj)).base64.split(',')[1]
                return image.preview
            }),
        )

        const [category_id, subcategory_id] = values.category

        const finalValues = {
            ...values,
            images,
            subcategory_id,
        }

        delete finalValues.category

        return finalValues
    }

    const onAdd = async () => {
        const values = await form.validateFields()
        const finalValues = await formatProductForSend(values)
        console.log('onAdd finalValues', finalValues)

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
        const values = await form.validateFields()
        const finalValues = await formatProductForSend(values)
        console.log('onEdit finalValues', finalValues)

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
