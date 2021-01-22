import { Button, Cascader, Form, Input, Modal, Steps, Upload } from 'antd'
import { useState } from 'react'
import './ProductModalInner.less'
import { PlusOutlined } from '@ant-design/icons'
import { useAppContext } from '../../context/AppContext'
import { getBase64 } from 'image-blobber'
import * as R from 'ramda'

// Renders the product information form
const ProductModalInnerForm = ({ form, initialValues }) => {
    // Note: Initial values are probably won't be correctly set
    const { categories } = useAppContext()
    const categoryOptions = categories.map(category => {
        return {
            value: category.id,
            label: category.name,
            children: category.subcategories.map(subcategory => {
                return {
                    value: subcategory.id,
                    label: subcategory.name,
                }
            }),
        }
    })
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
    return (
        <Form
            {...formItemLayout}
            layout="horizontal"
            form={form}
            name={'productForm' + initialValues?.id}
            onValuesChange={console.log}
            scrollToFirstError
            initialValues={initialValues}>
            <Form.Item
                name="name"
                label="Product Name"
                rules={[
                    {
                        required: true,
                        message: 'Please input your product name!',
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name="price"
                label="Price (TL)"
                rules={[
                    {
                        required: true,
                        message: 'Please input a price',
                    },
                    {
                        message: 'Price must be a number',
                        validator: (rule, val) => {
                            if (!val) return Promise.resolve(true)
                            return Number.parseFloat(val)
                                ? Promise.resolve(true)
                                : Promise.reject('Price format is incorrect!')
                        },
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name="stock_amount"
                label="Stock Amount"
                rules={[
                    {
                        required: true,
                        message: 'Please input stock amount',
                    },
                    {
                        message: 'Stock amount must be an integer',
                        validator: (rule, val) => {
                            if (!val) return Promise.resolve(true)
                            return /^\d+$/.test(val)
                                ? Promise.resolve(true)
                                : Promise.reject('Stock amount is not an integer')
                        },
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'short_description'}
                label="Description"
                rules={[
                    {
                        required: true,
                        message: 'Please enter a short description for your product!',
                    },
                ]}>
                <Input.TextArea />
            </Form.Item>
            <Form.Item
                name={'discount'}
                label="Discount"
                rules={[
                    {
                        required: true,
                        message: 'Please enter your discount percentage',
                    },
                    {
                        message: 'Discount must be a number between 0 and 1',
                        validator: (rule, val) => {
                            if (!val) return Promise.resolve(true)
                            const value = Number.parseFloat(val)
                            return !Number.isNaN(value) && 0.0 <= value && value <= 1.0
                                ? Promise.resolve(true)
                                : Promise.reject('Discount format is incorrect!')
                        },
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'category'}
                label="Category"
                rules={[
                    {
                        required: true,
                        message: 'Please select a category',
                    },
                ]}>
                <Cascader options={categoryOptions} />
            </Form.Item>
        </Form>
    )
}

// Renders the picture selection form
const ProductModalInnerPictureSelect = ({ form }) => {
    const [previewVisible, setPreviewVisible] = useState(false)
    const [previewImage, setPreviewImage] = useState({})

    const onCancel = () => setPreviewVisible(false)

    const handlePreview = async file => {
        console.log(file)
        if (!file.url && !file.preview) {
            file.preview = (await getBase64(file.originFileObj)).base64
        }
        setPreviewImage(file.preview)
        setPreviewVisible(true)
    }

    const uploadButton = (
        <div>
            <PlusOutlined />
            <div style={{ marginTop: 8 }}>Upload</div>
        </div>
    )

    const normFile = event => {
        if (Array.isArray(event)) return event

        return event && event.fileList
    }

    return (
        <Form form={form}>
            <Form.Item dependencies={['images']}>
                {form => {
                    const fileList = form.getFieldValue('images') || []
                    // console.log(fileList)
                    return (
                        <Form.Item name="images" label="Image" valuePropName="fileList" getValueFromEvent={normFile}>
                            <Upload
                                accept="image/*"
                                listType="picture-card"
                                onPreview={handlePreview}
                                beforeUpload={file => {
                                    return false
                                }}>
                                {fileList.length >= 5 ? null : uploadButton}
                            </Upload>
                        </Form.Item>
                    )
                }}
            </Form.Item>
            <Modal visible={previewVisible} footer={null} onCancel={onCancel}>
                <img alt="image-preview" style={{ width: '100%' }} src={previewImage} />
            </Modal>
        </Form>
    )
}

export const ProductModalInner = ({ form, product }) => {
    const [currentStep, setCurrentStep] = useState(0)
    const formatImageAsFile = image => ({
        thumbUrl: image,
        preview: image,
        uid: image,
        type: 'image/jpeg',
    })

    const initialValues = product
        ? {
              ...R.pick(['name', 'discount', 'price', 'stock_amount', 'short_description'], product),
              category: [product.category.id, product.subcategory.id],
              images: product.images.map(formatImageAsFile),
          }
        : undefined

    return (
        <div className="product-modal-container">
            <Steps size="small" current={currentStep}>
                <Steps.Step title="Product Information" />
                <Steps.Step title="Upload Pictures" />
            </Steps>

            {/* This div contains the content */}
            <div className="product-modal-content">
                <div style={!currentStep ? undefined : { display: 'none' }}>
                    <ProductModalInnerForm form={form} initialValues={initialValues} />
                </div>
                <div style={currentStep ? undefined : { display: 'none' }}>
                    <ProductModalInnerPictureSelect form={form} initialValues={initialValues} />
                </div>
                {/* {!currentStep ? (
                ) : (
                )} */}
            </div>

            {/* This div contains the step controls (next, previous) */}
            <div className="product-modal-stepcontrol">
                <Button type="dashed" onClick={() => setCurrentStep(currentStep - 1)} disabled={!currentStep}>
                    Previous
                </Button>
                <Button type="dashed" onClick={() => setCurrentStep(currentStep + 1)} disabled={currentStep}>
                    Next
                </Button>
            </div>
        </div>
    )
}
