import { Form, Modal, notification, Spin } from 'antd'
import { getBase64 } from 'image-blobber'
import { useEffect, useState } from 'react'

import { api } from '../../api'
import { ProductModalInner } from './ProductModalInner'

export const ProductModal = ({ mode = 'add', product, visible = false, onCancel = () => {}, onSuccess = () => {} }) => {
    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)
    // const [product, setProduct] = useState(null)

    // useEffect(() => {
    //     async function fetch() {
    //         try {
    //             setIsLoading(true)
    //             console.log('fetching product', productId)
    //             const { data } = await api.get(`/product/${productId}`)
    //             setProduct(formatProduct(data))
    //         } catch (error) {
    //             notification.error({ description: `Failed to get product ${productId}` })
    //         } finally {
    //             setIsLoading(false)
    //         }
    //     }
    //     if (productId !== undefined) fetch()
    // }, [])

    const formatProductForSend = async values => {
        const images = await Promise.all(
            (values?.images ?? []).map(async image => {
                if (image.originFileObj) return (await getBase64(image.originFileObj)).base64.split(',')[1]
                return image.preview
            }),
        )

        const [category_id, subcategory_id] = values.category

        if (mode === 'edit') {
            const oldImages = new Set(product?.images ?? [])
            const remainingUrlImages = new Set(images.filter(image => image.startsWith('http')))
            const newImages = new Set(images.filter(image => !image.startsWith('http')))
            const deletedImages = new Set([...oldImages].filter(image => !remainingUrlImages.has(image)))

            const finalValues = {
                ...values,
                name: values.title,
                id: product.id,
                subcategory_id,
                brand_id: values.brand.value,
                discount: Number.parseFloat(values.discount) / 100,
                price: Number.parseFloat(values.price),
                stock_amount: Number.parseFloat(values.stock_amount),
                images: newImages,
                image_urls_delete: [...deletedImages],
            }

            delete finalValues.category
            delete finalValues.brand
            delete finalValues.title

            return finalValues
        }

        const finalValues = {
            ...values,
            name: values.title,
            subcategory_id,
            brand_id: values.brand.value,
            discount: Number.parseFloat(values.discount) / 100,
            price: Number.parseFloat(values.price),
            stock_amount: Number.parseFloat(values.stock_amount),
            images,
        }

        delete finalValues.category
        delete finalValues.brand
        delete finalValues.title

        return finalValues
    }

    const onAdd = async () => {
        const values = await form.validateFields()
        const finalValues = await formatProductForSend(values)
        console.log('onAdd finalValues', finalValues)

        try {
            setIsLoading(true)
            const { data } = await api.post('/vendor/product', finalValues)

            if (data.status.successful) {
                onSuccess()
            }
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
            const { data } = await api.put('/vendor/product', finalValues)
            if (data.status.successful) {
                onSuccess()
            }
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
            width={700}
            visible={visible}
            title={mode === 'add' ? 'Add a new product' : 'Edit your product'}
            onOk={mode === 'add' ? onAdd : onEdit}
            okText={mode === 'add' ? 'Add' : 'Edit'}
            onCancel={onCancel}
            cancelText="Cancel">
            {mode === 'add' && (
                <Spin spinning={isLoading}>
                    <ProductModalInner form={form} />
                </Spin>
            )}
            {mode === 'edit' && (
                <Spin spinning={isLoading}>
                    <ProductModalInner form={form} product={product} />
                </Spin>
            )}
        </Modal>
    )
}
