import './ProductListItem.less'

import { DeleteOutlined } from '@ant-design/icons'
import { notification, Popconfirm, Spin } from 'antd'
import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'

import { api } from '../../api'
import { round, formatPrice } from '../../utils'

export const ProductListItem = ({ listId, product, onChange }) => {
    const [deleteLoading, setDeleteLoading] = useState(false)

    const onClickDelete = async () => {
        setDeleteLoading(true)
        try {
            const {
                data: { status },
            } = await api.delete(`/lists/${listId}/product/${product.id}`)
            if (status.successful) {
                notification.success({ message: status.message })
                onChange()
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
        <Spin spinning={deleteLoading}>
            <div className="product-list-item-container">
                <div className="product-list-item-header">
                    <div className="product-list-item-title">
                        <Link to={`/product/${product.id}`}>{product.title ?? 'title'}</Link>
                    </div>
                </div>
                <div className="product-list-item-content">
                    <div className="product-list-item-picture">
                        <Link to={`/product/${product.id}`}>
                            <img
                                alt={product.name}
                                width={'100%'}
                                src={product.images[0] ?? 'https://picsum.photos/300'}></img>
                        </Link>
                    </div>
                    <div className="product-list-item-description">
                        {product.short_description}
                        <p className="product-list-item-vendor">
                            by{' '}
                            <Link to={`/vendors/${product.vendor?.id ?? 'some_vendor_id'}`}>
                                {product.vendor?.name ?? 'getflix'}
                            </Link>
                        </p>
                    </div>
                    <div className="product-list-item-controls">
                        <div className="product-list-item-price">
                            {formatPrice(round(product.price_after_discount, 2))}&nbsp;{product.currency ?? 'TL'}
                        </div>
                        <div className="product-list-item-delete">
                            <Popconfirm
                                title="Are you sure to delete this product from your cart?"
                                onConfirm={onClickDelete}
                                okText="Yes"
                                cancelText="No">
                                <DeleteOutlined />
                            </Popconfirm>
                        </div>
                    </div>
                </div>
            </div>
        </Spin>
    )
}
