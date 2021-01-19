import { Button, Cascader, Form, Input, Steps } from "antd"
import { useState } from "react"
import "./ProductModalInner.less"
import { useAppContext } from '../../context/AppContext'

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
const ProductModalInnerPictureSelect = () => {
    return "Hello from picture select"
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