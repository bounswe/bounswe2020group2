import './ProductHeader.less'

import { HeartOutlined, ShoppingCartOutlined } from '@ant-design/icons'
import { Button, Modal, Result, Skeleton } from 'antd'
import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { ProductImageGallery } from './ProductImageGallery'
import { ProductExtra } from './ProductExtra'
import { ChooseListModalInner } from '../ChooseListModalInner'
import { useAppContext } from '../../context/AppContext'
import { round } from '../../utils'
import { UserReviews } from '../UserReview/UserReviews'

export const ProductHeader = ({ product, loading = false }) => {
    const { addShoppingCartItem } = useAppContext()
    const [addLoading, setAddLoading] = useState(false)
    const [isChooseListModalVisible, setIsChooseListModalVisible] = useState(false)

    const onAddToCartClicked = async () => {
        console.log('add to cart', product)
        try {
            setAddLoading(true)
            await addShoppingCartItem(product, 1)
        } catch (error) {
            // error is probably already caught inside addShoppingCartItem
            console.error(error)
        } finally {
            setAddLoading(false)
        }
    }

    const onAddToListClicked = () => {
        console.log('on add to list', product)
        setIsChooseListModalVisible(true)
    }

    const onClose = () => {
        console.log('on close choose list')
        setIsChooseListModalVisible(false)
    }

    if (loading) {
        return (
            <div className="product-header">
                <div className="product-header-gallery">
                    <ProductImageGallery loading={loading} product={product} />
                </div>
                <div className="product-header-main">
                    <Skeleton active title paragraph={{ rows: 6 }} />
                </div>
                <div className="product-header-vendor"></div>
                <div className="product-header-extra">
                    <ProductExtra loading={loading} product={product} />
                </div>
                <div className="product-header-buttons">
                    <Button
                        disabled
                        onClick={onAddToCartClicked}
                        size="large"
                        type="primary"
                        icon={<ShoppingCartOutlined />}>
                        Add to cart
                    </Button>
                    <Button disabled onClick={onAddToListClicked} size="large" icon={<HeartOutlined />} />
                </div>
            </div>
        )
    }

    if (!loading && !product) {
        return <Result status="500" title="Something is not right..." subTitle="Sorry, we can't find the product" />
    }

    const getVendorRatingLevel = rating => {
        if (rating <= 5.0) {
            return 'low'
        }

        if (rating <= 8.0) {
            return 'medium'
        }

        return 'high'
    }

    return (
        <div>
            <div className="product-header">
                <div className="product-header-gallery">
                    <ProductImageGallery product={product} />
                </div>
                <div className="product-header-main">
                    <h1 className="product-header-main-title">{product.title}</h1>
                    <p>{product.short_description}</p>
                </div>
                <div className="product-header-vendor">
                    <span className="product-header-vendor-label">Vendor:</span>&nbsp;
                    <Link to={`/vendor/${product.vendor.id}`}>{product.vendor.name}</Link>
                    &nbsp;
                    <span
                        className={`product-header-vendor-rating product-header-vendor-rating__${getVendorRatingLevel(
                            product.vendor.rating ?? 9.7,
                        )}`}>
                        {round(product.vendor.rating ?? 9.7, 1)}
                    </span>
                </div>
                <div className="product-header-extra">
                    <ProductExtra product={product} loading={loading} />
                </div>
                <div className="product-header-buttons">
                    <Button
                        loading={addLoading}
                        onClick={onAddToCartClicked}
                        size="large"
                        type="primary"
                        icon={<ShoppingCartOutlined />}>
                        Add to cart
                    </Button>
                    <Button onClick={onAddToListClicked} size="large" icon={<HeartOutlined />} />
                </div>
                <Modal
                    visible={isChooseListModalVisible}
                    onCancel={onClose}
                    onOk={onClose}
                    cancelButtonProps={{ style: { display: 'none' } }}
                    title="Choose a list"
                    destroyOnClose>
                    <ChooseListModalInner product={product} />
                </Modal>
            </div>
        </div>
    )
}
