import { Button, Steps } from "antd"
import { useState } from "react"
import "./ProductModalInner.less"

// Renders the product information form
const ProductModalInnerForm = ({form, initialValues}) => {
    return "Hello from form"
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