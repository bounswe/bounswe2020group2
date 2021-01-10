import { Form, Modal, Spin } from "antd"
import { useState } from "react"
import { ProductModalInner } from "./ProductModalInner"

export const ProductModal = ({
    product,
    mode = 'add',
    visible = true, // don't forget to make this false later
    onCancel = () => { },
    onSuccess = () => { } }) => {

    const [form] = Form.useForm()
    const [isLoading, setIsLoading] = useState(false)

    const onAdd = () => {

    }
    const onEdit = () => {

    }


    return (
        <Modal
            forceRender
            visible={visible}
            title={mode === 'add' ? 'Add a new product' : 'Edit your product'}
            destroyOnClose
            onOk={mode === 'add' ? onAdd : onEdit}
            onCancel={onCancel}
            cancelText="Cancel"
            okText={mode === 'add' ? 'Add' : 'Edit'}>
            <Spin spinning={isLoading}>
                <ProductModalInner />
            </Spin>
        </Modal>
    )

}