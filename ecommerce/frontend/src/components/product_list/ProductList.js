import './ProductList.less'

import { StopOutlined } from '@ant-design/icons'
import { Button, Collapse, Modal, notification } from 'antd'
import { useState } from 'react'

import { api } from '../../api'
import { ProductListItem } from './ProductListItem'

export const ProductList = ({ list }) => {
    const [deleteLoading, setDeleteLoading] = useState(false)
    const onListDelete = async e => {
        e.stopPropagation()

        Modal.confirm({
            content: 'Are you sure you want to delete the product list?',
            onOk: async () => {
                try {
                    setDeleteLoading(true)
                    const {
                        data: { status },
                    } = await api.delete(`/lists/${list.id}`)
                    if (status.successful) {
                        notification.success({ message: status.message })
                        // onAddressInfoChange()
                    } else {
                        notification.warning({ message: status.message })
                    }
                } catch (error) {
                    notification.warning({ message: 'There was an error with your request.' })
                    console.error(error)
                } finally {
                    setDeleteLoading(false)
                }
            },
        })
    }

    return (
        <Collapse defaultActiveKey="" collapsible={false}>
            <Collapse.Panel
                key="list"
                collapsible={false}
                showArrow={false}
                header={
                    <div className="product-list-header">
                        <div className={'product-list-header-title'}>{list.name}</div>
                        <div className="product-list-header-details">
                            <Button danger icon={<StopOutlined />} loading={deleteLoading} onClick={onListDelete}>
                                Delete list
                            </Button>
                        </div>
                    </div>
                }>
                <div className="product-list-items">
                    {list.products.map(({ id, product }) => {
                        return <ProductListItem key={id} listId={list.id} product={product} />
                    })}
                </div>
            </Collapse.Panel>
        </Collapse>
    )
}
