import './ProductCard.less'

import { Button, Rate } from 'antd'
import { PlusCircleOutlined, HeartOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import React, { useState } from 'react'
import { Link } from 'react-router-dom'

import { useAppContext } from '../../context/AppContext'
import { round, truncate } from '../../utils'
import { ListModal } from '../product_list_modal/ListModal'

export const ProductCard = ({
    product,
    width = 350,
    editMode = false,
    onDeleteProductCard = () => {},
    onEditProductCard = () => {},
}) => {
    const [isProductListModalVisible, setProductListModalVisible] = useState(false)
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

    return (
        <div className="whole-card" style={{ minWidth: width, minHeight: width, maxWidth: width }}>
            <div>
                {editableProduct && (
                    <div className="product-card-editable-icons">
                        <Button
                            type="link"
                            icon={<EditOutlined />}
                            onClick={() => {
                                onEditProductCard(product)
                            }}
                            style={{ color: '#472836', backgroundColor: '#e2be5a' }}>
                            Edit
                        </Button>
                        <Button
                            type="link"
                            icon={<DeleteOutlined />}
                            onClick={() => {
                                onDeleteProductCard(product.id)
                            }}
                            style={{
                                color: '#472836',
                                backgroundColor: '#e2be5a',
                            }}>
                            Delete
                        </Button>
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
        </div>
    )
}
