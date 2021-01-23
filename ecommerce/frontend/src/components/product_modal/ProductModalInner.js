import './ProductModalInner.less'

import { PlusOutlined } from '@ant-design/icons'
import { AutoComplete, Button, Cascader, Form, Input, Modal, Steps, Upload, Select } from 'antd'
import { getBase64 } from 'image-blobber'
import * as R from 'ramda'
import { useState } from 'react'

import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'

import { debounce } from 'debounce'

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

    const [isBrandLoading, setIsBrandLoading] = useState(false)
    const [brandOptions, setBrandOptions] = useState([])

    const onSearchBrand = debounce(async query => {
        console.log('search', query)
        const _query = query.trim()
        try {
            setIsBrandLoading(true)
            const {
                data: { data },
            } = await api.post('/search/brands', { query: _query })
            console.log('data', data)
            const brands = data.brands.map(brand => ({ value: brand.id, label: brand.name.trim() }))

            if (brands.length === 1)
                if (brands[0].label.toUpperCase() !== _query.toUpperCase())
                    brands.push({ value: _query, label: _query })
            if (brands.length === 0) brands.push({ value: _query, label: _query })

            setBrandOptions(brands)
        } catch (err) {
            console.error(err)
        } finally {
            setIsBrandLoading(false)
        }
    }, 200)

    return (
        <Form
            {...formItemLayout}
            layout="horizontal"
            form={form}
            name={'productForm' + initialValues?.id}
            //onValuesChange={console.log}
            scrollToFirstError
            initialValues={initialValues}>
            <Form.Item
                name="title"
                label="Product Name"
                rules={[
                    {
                        required: true,
                        message: 'Please input your product name!',
                    },
                    {
                        message: 'Product name should be at least 3 characters',
                        validator: (rule, val) => {
                            const _val = val.trim()
                            if (!_val) return Promise.reject(false)
                            if (_val.length < 3) return Promise.reject(false)
                            return Promise.resolve(true)
                        },
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
                            const _val = Number.parseFloat(val)
                            if (Number.isNaN(_val)) return Promise.reject(false)
                            if (_val <= 0) return Promise.reject(false)
                            return Promise.resolve(true)
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
                            const _val = Number.parseInt(val)
                            if (Number(val) !== _val) return Promise.reject(false)
                            if (Number.isNaN(_val)) return Promise.reject(false)
                            if (_val <= 0) return Promise.reject(false)
                            return Promise.resolve(true)
                        },
                    },
                ]}>
                <Input />
            </Form.Item>
            <Form.Item
                name={'short_description'}
                label="Short description"
                rules={[
                    {
                        required: true,
                        message: 'Please enter a short description for your product!',
                    },
                    {
                        message: 'Short description needs to be at least 16 characters',
                        validator: (rule, val) => {
                            const _val = val.trim()
                            if (!_val) return Promise.reject(false)
                            if (_val.length < 16) return Promise.reject(false)
                            return Promise.resolve(true)
                        },
                    },
                ]}>
                <Input.TextArea />
            </Form.Item>
            <Form.Item
                name={'long_description'}
                label="Long description"
                rules={[
                    {
                        required: true,
                        message: 'Please enter a long description for your product!',
                    },
                    {
                        message: 'Long description needs to be at least 64 characters',
                        validator: (rule, val) => {
                            const _val = val.trim()
                            if (!_val) return Promise.reject(false)
                            if (_val.length < 64) return Promise.reject(false)
                            return Promise.resolve(true)
                        },
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

            <Form.Item
                name={'brand'}
                label="Brand"
                rules={[
                    {
                        required: true,
                        message: 'Please select or enter a brand',
                    },
                ]}>
                <Select
                    showSearch
                    onSearch={onSearchBrand}
                    labelInValue
                    notFoundContent={null}
                    filterOption={false}
                    showArrow={false}
                    defaultActiveFirstOption={false}
                    autoClearSearchValue={false}
                    loading={isBrandLoading}>
                    {brandOptions.map(({ label, value }) => {
                        return (
                            <Select.Option key={value} value={value}>
                                {label}
                            </Select.Option>
                        )
                    })}
                </Select>
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
              ...R.pick(
                  ['title', 'discount', 'price', 'stock_amount', 'long_description', 'short_description'],
                  product,
              ),
              category: [product.category.id, product.subcategory.id],
              images: product.images.map(formatImageAsFile),
              brand: { value: product.brand.id, label: product.brand.name },
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
