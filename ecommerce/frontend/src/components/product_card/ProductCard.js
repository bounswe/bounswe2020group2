import React from 'react'
import { Button, Rate } from 'antd'
import { PlusCircleOutlined, HeartOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import './ProductCard.less'
import { useAppContext } from '../../context/AppContext'
import { round, truncate } from '../../utils'
import { ISO_8601 } from 'moment'

export const ProductCard = ({ product, width = 350, editMode = false }) => {
    const { addShoppingCartItem } = useAppContext()

    const onAddToCart = product => {
        addShoppingCartItem(product, 1)
    }

    const onDeleteProductCard = () => {
        console.log('delete product card')
    }
    const onEditProductCard = () => {
        console.log('edit product card')
    }
    const onAddToList = product => {}

    const { title, rating, price, price_after_discount, currency = '₺', images, id, vendor } = product
    const { user } = useAppContext()
    const isVendor = user.type === 'vendor'
    const isVendorAndOwner = isVendor && vendor.id === user.id
    let editableProduct = editMode && isVendorAndOwner
    console.log(editMode)
    return (
        <div className="whole-card" style={{ minWidth: width, minHeight: width, maxWidth: width }}>
            <div>
                {editableProduct && (
                    <div className="product-card-editable-icons">
                        <Button
                            type="link"
                            icon={<EditOutlined />}
                            onClick={onEditProductCard}
                            style={{ color: '#472836', backgroundColor: '#e2be5a' }}>
                            Edit
                        </Button>
                        <Button
                            type="link"
                            icon={<DeleteOutlined />}
                            onClick={onDeleteProductCard}
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
        </div>
    )
}
