import { Button, Cascader, Form, Input, Modal, Steps, Upload } from "antd"
import { useState } from "react"
import "./ProductModalInner.less"
import { PlusOutlined } from '@ant-design/icons';
import { useAppContext } from '../../context/AppContext'
import { getBase64 } from 'image-blobber'

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
                    label: subcategory.name
                }
            })
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
    return <Form
        {...formItemLayout}
        layout="horizontal"
        form={form}
        name={'productForm' + initialValues?.id}
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
            ]}>
            <Input />
        </Form.Item>
        <Form.Item
            name={'short_description'}
            label="Description"
            rules={[
                {
                    required: true,
                    message: 'Please select your province!',
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
}

// Renders the picture selection form
const ProductModalInnerPictureSelect = (pictures, onChange) => {
    // const picturesToFileList = pictures => pictures.map(picture => {
    //     return { 

    //     }
    // })
    const [previewVisible, setPreviewVisible] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const [previewTitle, setPreviewTitle] = useState('')
    const [fileList, setFileList] = useState([])

    const onCancel = () => setPreviewVisible(false)

    const handlePreview = async file => {
        console.log(file)
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj);
        }
        setPreviewImage(file.preview)
        setPreviewVisible(true)
        setPreviewTitle(file.name || file.url.substring(file.url.lastIndexOf('/') + 1))
    }

    const handleChange = ({ fileList }) => {
        setFileList(fileList)
        //onChange(format(fileList)) gibi bir sey gerekecek buraya
    }

    const uploadButton = (
        <div>
            <PlusOutlined />
            <div style={{ marginTop: 8 }}>Upload</div>
        </div>
    );

    return <>
        <Upload
            accept="image/*"
            listType="picture-card"
            fileList={fileList}
            onPreview={handlePreview}
            onChange={handleChange}
            beforeUpload={file => { 
                setFileList([...fileList, file])
                return false
            }}
        >
            {fileList.length >= 5 ? null : uploadButton}
        </Upload>
        <Modal
            visible={previewVisible}
            title={previewTitle}
            footer={null}
            onCancel={onCancel}
        >
            <img alt="image-preview" style={{ width: '100%' }} src={previewImage.base64 || previewImage} />
        </Modal>
    </>
}

export const ProductModalInner = ({ form, product }) => {
    const [currentStep, setCurrentStep] = useState(0)


    return <div className="product-modal-container">
        <Steps
            size="small"
            current={currentStep}
        >
            <Steps.Step title="Product Information" />
            <Steps.Step title="Upload Pictures" />

        </Steps>

        {/* This div contains the content */}
        <div className="product-modal-content">
            {!currentStep ?
                <ProductModalInnerForm /> :
                <ProductModalInnerPictureSelect />}
        </div>

        {/* This div contains the step controls (next, previous) */}
        <div className="product-modal-stepcontrol">
            <Button
                type="dashed"
                onClick={() => setCurrentStep(currentStep - 1)}
                disabled={!currentStep}
            >
                Previous
             </Button>
            <Button
                type="dashed"
                onClick={() => setCurrentStep(currentStep + 1)}
                disabled={currentStep}
            >
                Next
             </Button>
        </div>
    </div>
} 