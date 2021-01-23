import './ProductList.less'

import { StopOutlined } from '@ant-design/icons'
import { Button, Collapse, notification, Popconfirm } from 'antd'
import { useState } from 'react'

import { api } from '../../api'
import { ProductListItem } from './ProductListItem'

export const ProductList = ({ list }) => {
    const [deleteLoading, setDeleteLoading] = useState(false)
    const onListDelete = async () => {
        setDeleteLoading(true)
        try {
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
    }

    return (
        <Collapse defaultActiveKey="list" collapsible={false}>
            <Collapse.Panel
                key="list"
                collapsible={false}
                showArrow={false}
                header={
                    <div className="product-list-header">
                        <div className={'product-list-header-title'}>{list.name}</div>
                        <div className="product-list-header-details">
                            {
                                <Popconfirm
                                    title="Delete this list?"
                                    onConfirm={onListDelete}
                                    okText="Yes"
                                    cancelText="No">
                                    <Button
                                        danger
                                        icon={<StopOutlined />}
                                        onClick={e => e.stopPropagation()}
                                        loading={deleteLoading}>
                                        Delete list
                                    </Button>
                                </Popconfirm>
                            }
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
