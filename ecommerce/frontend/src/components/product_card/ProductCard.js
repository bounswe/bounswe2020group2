import './ProductCard.less'

import { Button, Rate, Popconfirm } from 'antd'
import { PlusCircleOutlined, HeartOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import React, { useState } from 'react'
import { Link } from 'react-router-dom'

import { useAppContext } from '../../context/AppContext'
import { round, truncate } from '../../utils'
import { ListModal } from '../product_list_modal/ListModal'
import { ProductModal } from '../product_modal/ProductModal'
import { api } from '../../api'

export const ProductCard = ({ product, width = 350, editMode = false, onChange = () => {} }) => {
    const [isProductListModalVisible, setProductListModalVisible] = useState(false)
    const [isProductModalVisible, setIsProductModalVisible] = useState(false)
    const { addShoppingCartItem, user } = useAppContext()

    const onAddToCart = product => {
        addShoppingCartItem(product, 1)
    }

    const { title, rating, price, price_after_discount, currency = 'TL', images, id, vendor } = product
    const isVendor = user.type === 'vendor'
    const isVendorAndOwner = isVendor && vendor.id === user.id
    const editableProduct = editMode && isVendorAndOwner

    const onAddToList = product => {
        console.log('on add to list', product)
        setProductListModalVisible(true)
    }

    const onProductListModalOk = () => {
        setProductListModalVisible(false)
    }

    const onDelete = async () => {
        try {
            // DELETE vendor product cannot take id as body, needs to be changed in backend
            await api.delete(`/vendor/product/${product.id}`)
            onChange()
        } catch (error) {
            console.error('failed to delete product', error)
        }
    }
    const onEditClick = () => {
        setIsProductModalVisible(true)
    }

    const onProductModalCancel = () => {
        setIsProductModalVisible(false)
    }

    const onProductModalSuccess = () => {
        setIsProductModalVisible(false)
        onChange()
    }

    return (
        <div className="whole-card" style={{ minWidth: width, minHeight: width, maxWidth: width }}>
            <div>
                {editableProduct && (
                    <div className="product-card-editable-icons">
                        <EditOutlined onClick={onEditClick} />
                        <Popconfirm
                            title="Are you sure to delete this product?"
                            onConfirm={onDelete}
                            okText="Yes"
                            cancelText="No">
                            <DeleteOutlined />
                        </Popconfirm>
                    </div>
                )}
                <Link to={`/product/${id}`}>
                    <div className="product-card-img-container">
                        <img className="product-card-img" alt={title} src={images[0]} />
                    </div>
                    <div className="card-title">
                        <p>{truncate(title)}</p>
                    </div>
                </Link>
            </div>
            <div className="rate-and-price">
                <div className="card-rate">
                    <Rate disabled allowHalf defaultValue={rating}></Rate>
                </div>
                {round(price, 1) !== round(price_after_discount, 1) ? (
                    <div className="card-old-price">{round(price, 1) + ' ' + currency}</div>
                ) : null}
                <div className="card-new-price">{round(price_after_discount, 1) + ' ' + currency}</div>
            </div>
            {!isVendor && (
                <div className="card-add-button">
                    <Button
                        size="large"
                        type="primary"
                        icon={<PlusCircleOutlined />}
                        onClick={() => onAddToCart(product)}
                        block>
                        Add to cart
                    </Button>

                    <Button size="large" type="ghost" icon={<HeartOutlined />} onClick={() => onAddToList(product)} />
                </div>
            )}

            <ListModal product={product} visible={isProductListModalVisible} onOk={onProductListModalOk} />
            <ProductModal
                mode={'edit'}
                product={product}
                visible={isProductModalVisible}
                onCancel={onProductModalCancel}
                onSuccess={onProductModalSuccess}
            />
        </div>
    )
}
